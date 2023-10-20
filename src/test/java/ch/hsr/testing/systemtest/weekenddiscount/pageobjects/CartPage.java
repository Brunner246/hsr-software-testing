package ch.hsr.testing.systemtest.weekenddiscount.pageobjects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends Page {

    private static final Log LOG = LogFactory.getLog(HomePage.class);

    private final By totalSavingsSummary = By.xpath("//div[@class='row cart-summary-breakdown']" +
            "//span[contains(text(), 'Total Savings')]/following-sibling::span[@class='pull-right']");

    private final By subTotalSummary = By.xpath("//div[@class='row cart-summary-breakdown']" +
            "//span[contains(text(), 'Subtotal')]/following-sibling::span[@class='pull-right']");

    @FindBy(id = "cart_products")
    private WebElement productsInCartTable;

    public CartPage(WebDriver driver) {
        super(driver);

        if (!(driver.getPageSource().contains("checkout"))) {
            throw new IllegalStateException("This is not the cart page");
        }
        LOG.debug("CartPage created successfully");
    }

    public double getSavingsFromItemInCart() {
        var lSavings = driver.findElement(totalSavingsSummary).getText();
        double lSavingsNotRounded = parseSavings(lSavings);
        return roundSavings(lSavingsNotRounded);
    }

    public double getSubTotal() {
        var lSubTotal = driver.findElement(subTotalSummary).getText();
        double lSubTotalNotRounded = parseSavings(lSubTotal);
        return roundSavings(lSubTotalNotRounded);
    }

    private double parseSavings(String savings) {
        var lNumericPart = savings.replace("$", "");
        return Double.parseDouble(lNumericPart);
    }

    private double roundSavings(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
