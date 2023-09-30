/*
 * HSR Hochschule fuer Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 *
 * Thomas Briner, thomas.briner@gmail.com
 */
package ch.hsr.testing.systemtest.weekenddiscount.tests;

import ch.hsr.testing.systemtest.weekenddiscount.Constants;
import ch.hsr.testing.systemtest.weekenddiscount.extension.ScreenshotOnFailureExtension;
import ch.hsr.testing.systemtest.weekenddiscount.extension.WebDriverKeeper;
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.HomePage;
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.HotSaucesPage;
import ch.hsr.testing.systemtest.weekenddiscount.pageobjects.SauceDetailPage;
import ch.hsr.testing.systemtest.weekenddiscount.util.DBUtil;
import ch.hsr.testing.systemtest.weekenddiscount.util.DateFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.io.IOException;
import java.time.Month;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * The Class HeatClinicAcceptanceTests. In this class the acceptance Tests for
 * the weekend discount features are implemented.
 */
@ExtendWith(ScreenshotOnFailureExtension.class)
public class WeekendDiscountAcceptanceTests implements Constants {

    private static final Log LOG = LogFactory
            .getLog(WeekendDiscountAcceptanceTests.class);

    public ScreenshotOnFailureExtension screenshot = new ScreenshotOnFailureExtension();

    private WebDriver driver;

    @BeforeEach
    public void setup() {

        System.setProperty("webdriver.chrome.driver", getChromeDriverPath());
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        WebDriverKeeper.getInstance().setDriver(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    @Test
    public void testWeekendDiscountEnabled() {

        Date within4thWeekend = DateFactory.createDate(2023, 9, 23, 0, 0, 0);
        DBUtil.setTestTime(within4thWeekend);

        var homePage = HomePage.navigateTo(driver);

        MatcherAssert.assertThat(homePage.getNofObjectsInCart(), Matchers.is(0));

        HotSaucesPage hotSaucesPage = homePage.jumpToHotSauces();

        SauceDetailPage saucePage = hotSaucesPage.sauceDayOfTheDeadHabaneroDetails();

        saucePage.buySauce();
        saucePage.goToCart();
        var lSavings = saucePage.getTotalSavings();
        LogFactory.getLog(WeekendDiscountAcceptanceTests.class).info(lSavings);
        MatcherAssert.assertThat(saucePage.getTotalSavings(), Matchers.is("$3.50"));
    }

    @Test
    public void testWeekendDiscountDisabled() {

        Date after4thWeekend = DateFactory.createDate(2018, 6, 25, 0, 0, 0);
        DBUtil.setTestTime(after4thWeekend);

        // TODO: Implement this
        driver.get("http://localhost:8080");

       // WeekendDiscountPage weekendDiscountPage = PageFactory.initElements(driver, WeekendDiscountPage.class);

        Assertions.fail("Implement Testcase");

    }

}
