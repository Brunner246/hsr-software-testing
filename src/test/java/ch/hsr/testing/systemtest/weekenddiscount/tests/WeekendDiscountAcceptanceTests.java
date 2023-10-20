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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.time.Month;


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

    // savings with discount of 50% (change this value if the discount changes)
    private static final double DISCOUNT_LAST_WEEKEND_OF_MONTH = 0.5;

    // Savings without discount
    private static final double NO_DISCOUNT = 0.0;

    // delta for comparing doubles with a precision of 1e-3 (0.001)
    private static final double DELTA_3 = 1e-3;

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

    /**
     * Test that the weekend discount is enabled on the last weekend of the
     * month.
     */
    @Test
    public void testWeekendDiscountEnabled() {

        LOG.info("Test start: testWeekendDiscountEnabled");
        // ARRANGE
        Date within4thWeekend = DateFactory.createDate(2023, Month.SEPTEMBER.getValue()
                , 23, 0, 0, 0);


        LOG.info("Test date: " + within4thWeekend);

        DBUtil.setTestTime(within4thWeekend);

        var homePage = HomePage.navigateTo(driver);

        MatcherAssert.assertThat(homePage.getNofObjectsInCart(), Matchers.is(0));

        HotSaucesPage hotSaucesPage = homePage.jumpToHotSauces();

        SauceDetailPage saucePage = hotSaucesPage.sauceDayOfTheDeadHabaneroDetails();

        saucePage.buySauce();

        // ACT
        var cartPage = saucePage.goToCart();

        LOG.info(String.format("Price with discount of %.1f %%",
                WeekendDiscountAcceptanceTests.DISCOUNT_LAST_WEEKEND_OF_MONTH * 100));
        double lPriceExpected = cartPage.getSubTotal() * WeekendDiscountAcceptanceTests.DISCOUNT_LAST_WEEKEND_OF_MONTH;

        double lPriceExpectedRounded = Math.round(lPriceExpected * 100.0) / 100.0;

        String lPriceExpectedRoundedString = String.format("Saving must be equals %.2f", lPriceExpectedRounded);

        boolean lDiscountEnabled = Math.abs(cartPage.getSavingsFromItemInCart() - lPriceExpectedRounded)
                < WeekendDiscountAcceptanceTests.DELTA_3;

        // ASSERT
        MatcherAssert.assertThat(lPriceExpectedRoundedString, lDiscountEnabled);

        LOG.info("Test end: testWeekendDiscountEnabled");
    }

    /**
     * Test that the weekend discount is disabled for a weekend that is not the last one of the month.
     */
    @Test
    public void testWeekendDiscountDisabled() {

        LOG.info("Test start: testWeekendDiscountDisabled");

        // ARRANGE
        Date after4thWeekend = DateFactory.createDate(2018, Month.JUNE.getValue()
                , 25, 0, 0, 0);

        LOG.info("Test date: " + after4thWeekend);

        DBUtil.setTestTime(after4thWeekend);

        var homePage = HomePage.navigateTo(driver);

        MatcherAssert.assertThat(homePage.getNofObjectsInCart(), Matchers.is(0));

        HotSaucesPage hotSaucesPage = homePage.jumpToHotSauces();

        SauceDetailPage saucePage = hotSaucesPage.sauceDayOfTheDeadHabaneroDetails();

        saucePage.buySauce();

        // ACT
        var cartPage = saucePage.goToCart();
        // check that the discount is disabled (equals to zero) with a delta of 1e-3
        boolean lIsDiscountDisabled = Math.abs((cartPage.getSavingsFromItemInCart() - WeekendDiscountAcceptanceTests.NO_DISCOUNT))
                < DELTA_3;

        String lPriceExpectedRoundedString = String.format("Total savings must be equals $0.00 %.2f",
                WeekendDiscountAcceptanceTests.NO_DISCOUNT);

        // ASSERT
        MatcherAssert.assertThat(lPriceExpectedRoundedString, lIsDiscountDisabled);

        LOG.info("Test end: testWeekendDiscountDisabled");
    }
}
