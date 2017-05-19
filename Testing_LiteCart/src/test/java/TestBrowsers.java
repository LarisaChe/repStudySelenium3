import org.openqa.selenium.WebDriver;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by Администратор on 19.05.2017.
 */
public class TestBrowsers {
  //  private TestDriverIE testIEd;

    @BeforeTest
    public void start() {
    }

    @Test
    public void test() {
        TestDriverIE ttestIEd = new TestDriverIE();
        ttestIEd.testIE();

        /*TestDriverChrome ttestChromed = new TestDriverChrome();
        testChromed.testChrome();

        TestDriverFF ttestFFd = new TestDriverFF();
        ttestFFd.testFF();*/

    }

    @AfterTest
    public void stop() {
    }
}
