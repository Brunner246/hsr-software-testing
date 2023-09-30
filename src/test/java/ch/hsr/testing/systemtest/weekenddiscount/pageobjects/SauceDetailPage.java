package ch.hsr.testing.systemtest.weekenddiscount.pageobjects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SauceDetailPage extends Page {

    private static final Log LOG = LogFactory.getLog(SauceDetailPage.class);

    private By addToCartButtonLocator = By.xpath("//button[contains(@class,'js-addToCart')]");

    private final By totalSavings = By.xpath("//div[contains(@class,'discount')]/span");

    public SauceDetailPage(WebDriver driver) {
        super(driver);
        LOG.debug("HotSauces Detail Page created successfully");
    }

    public void buySauce() {
        driver.findElement(addToCartButtonLocator).click();
    }

    public String getTotalSavings() {
        return driver.findElement(totalSavings).getText();
    }

}
