
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

//  Задание 11. Сделайте сценарий регистрации пользователя

public class TestLogin {
    private WebDriver driver;
    private WebDriverWait wait;
    String [][] userData = new String[11][2];

    boolean alertCheck(String alertString){
        WebElement element = driver.findElement(By.cssSelector(".alert"));
        String dopString=element.getAttribute("textContent").toString();
        //System.out.println(dopString);
        return (element.isDisplayed() && dopString.equals(alertString));
        }

    void registration() {
        driver.findElement(By.cssSelector("a[href$='create_account']")).click();
        WebElement element=driver.findElement(By.id("box-create-account"));
        for (int i=0; i<=userData.length-1; i++) {
            element.findElement(By.name(userData[i][0])).sendKeys(userData[i][1]);
        }
        new Select(element.findElement(By.name("country_code"))).selectByVisibleText("United States");

        Random rn = new Random();
        int stateNum = rn.nextInt(65);
        WebElement elementState = element.findElement(By.name("zone_code"));
        wait.until(ExpectedConditions.elementToBeClickable(elementState));
        Select select = new Select(elementState);
        select.selectByIndex(stateNum);

        element.findElement(By.name("create_account")).click();

        if (alertCheck("× Your customer account has been created.")) {
            System.out.println("Зарегистрировались");
        } else {System.out.println("Не удалось зарегистрироваться");}
    }

    void logout () {
        driver.findElement(By.cssSelector("a[href$='logout']")).click();

        if (alertCheck("× You are now logged out.")) {
            System.out.println("Отлогинились");
        } else {System.out.println("Не удалось отлогиниться");}
    }

    void login (String email, String password, String nameForAlert ) {
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();

        if (alertCheck("× You are now logged in as "+nameForAlert+".")) {
            System.out.println("Залогинились");
        } else {System.out.println("Не удалось залогиниться");}
    }

    @BeforeTest
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 100);
    }

    @Test
    public void test() {
        driver.get("http://localhost/litecart/public_html/en/");
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        String dopString = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", LocalDateTime.now());
        //System.out.println(dopString);
        userData[0][0]="firstname";
        userData[0][1]="n"+dopString;
        userData[1][0]="lastname";
        userData[1][1]="sn"+dopString;
        userData[2][0]="address1";
        userData[2][1]="st "+dopString;
        userData[3][0]="postcode";
        userData[3][1]=dopString.substring(9);
        userData[4][0]="email";
        userData[4][1]="e"+dopString+"@testmail.com";
        userData[5][0]="password";
        userData[5][1] = "p"+dopString;
        userData[6][0]="confirmed_password";
        userData[6][1] = "p"+dopString;
        userData[7][0]="tax_id";
        userData[7][1]=dopString.substring(7);
        userData[8][0]="company";
        userData[8][1] = "company"+dopString.substring(11);
        userData[9][0]="city";
        userData[9][1] = "city"+dopString.substring(12);
        userData[10][0]="phone";
        userData[10][1] = "+1"+dopString.substring(8);

        /*for (int i=0; i<=6; i++) {
            System.out.println(userData[i][0]);
            System.out.println(userData[i][1]);
        }*/

        registration();

        logout();

        login(userData[4][1], userData[5][1], userData[0][1]+" "+userData[1][1]);

        logout();
    }

    @AfterTest
    public void stop() {
        driver.quit();
        driver = null;
    }
}
