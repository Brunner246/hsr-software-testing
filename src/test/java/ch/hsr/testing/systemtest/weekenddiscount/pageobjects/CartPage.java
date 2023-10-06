package ch.hsr.testing.systemtest.weekenddiscount.pageobjects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends Page {

    private static final Log LOG = LogFactory.getLog(HomePage.class);

    private final By savingsItemInCart = By.xpath("//div[contains(@class,'discount')]/span");

    private final By totalSavingsSummary = By.xpath("//div[@class='row cart-summary-breakdown']"+
            "//span[contains(text(), 'Total Savings')]/following-sibling::span[@class='pull-right']");

    @FindBy(id = "cart_products")
    private WebElement productsInCartTable;

    public CartPage(WebDriver driver) {
        super(driver);

        if (!(driver.getPageSource().contains("checkout"))) {
            throw new IllegalStateException("This is not the cart page");
        }
        LOG.debug("CartPage created successfully");
    }

    public String getSavingsItemInCart() {
        return driver.findElement(savingsItemInCart).getText();
    }

    public String getTotalSavingsSummary() {
        return driver.findElement(totalSavingsSummary).getText();
    }
}
