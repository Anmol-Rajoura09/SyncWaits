package Selenium_Introduction.SyncWaits;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LearningWaits extends BaseSetup
{
	
@Test(priority = 2)
    public void addToCartItem() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String[] itemsNeeded = {"Cucumber", "Brocolli", "Beetroot"};
        
        List<WebElement> products = driver.findElements(By.cssSelector("h4.product-name"));
        List<String> itemsNeededList = Arrays.asList(itemsNeeded);

        int itemsAdded = 0;
        
        log.info("🚀 Starting test: addToCartItem");
        log.info("🛒 Items to add: {}", itemsNeededList);

        for (int i = 0; i < products.size(); i++) {
            String productName = products.get(i).getText().split("-")[0].trim();

            if (itemsNeededList.contains(productName)) {
                log.info("✅ Found item: {}, adding to cart...", productName);

                driver.findElements(By.xpath("//div[@class='product-action']/button")).get(i).click();
                itemsAdded++;

                if (itemsAdded == itemsNeeded.length) {
                	log.info("🎯 All desired items added to cart.");
                    break;
                }
            }
        }
        log.info("🛒 Opening cart...");
        driver.findElement(By.cssSelector("img[alt='Cart']")).click();
        driver.findElement(By.xpath("//button[contains(text(),'PROCEED TO CHECKOUT')]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.promoCode")));
        log.info("💸 Entering promo code...");
        driver.findElement(By.cssSelector("input.promoCode")).sendKeys("rahulshettyacademy");
        driver.findElement(By.cssSelector("button.promoBtn")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.promoInfo")));
        String promoMessage = driver.findElement(By.cssSelector("span.promoInfo")).getText();
        log.info("🎁 Promo info displayed: {}", promoMessage);

        log.info("✅ Test addToCartItem completed.");

        System.out.println(driver.findElement(By.cssSelector("span.promoInfo")).getText());
    }
@Test(priority = 1)
	public void buttonDisabled() {
	log.info("🚀 Starting test: buttonDisabled");

    // Open Cart
    driver.findElement(By.cssSelector("img[alt='Cart']")).click();

    // Locate checkout button
    WebElement checkoutBtn = driver.findElement(By.xpath("//button[contains(text(),'PROCEED TO CHECKOUT')]"));

    Boolean buttonStatus = checkoutBtn.isDisplayed();
    Boolean buttonClick = checkoutBtn.isEnabled();

    log.info("🔍 Checkout button - Displayed: {}, Enabled: {}", buttonStatus, buttonClick);

    try {
        Assert.assertTrue(buttonStatus, "❌ Checkout button should be displayed");
        Assert.assertFalse(buttonClick, "❌ Checkout button should be disabled");
        log.info("✅ Assertion Passed: Checkout button is displayed but disabled as expected.");
    } catch (AssertionError e) {
        log.error("❌ Assertion Failed in buttonDisabled test: {}", e.getMessage());
        throw e; // rethrow so test fails in report
    }

    log.info("✅ Test buttonDisabled completed.");
}


}
