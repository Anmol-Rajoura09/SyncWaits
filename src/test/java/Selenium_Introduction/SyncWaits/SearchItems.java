package Selenium_Introduction.SyncWaits;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchItems extends BaseSetup {	

    @Test(priority = 1)
    public void searchItemsAndAddToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        log.info("🚀 Starting test: Search and Add Products to Cart");

        // Step 1: Extract all product names into a list of Strings
        List<WebElement> productElements = driver.findElements(By.className("product-name"));
        List<String> productNames = new ArrayList<>();

        for (WebElement productElement : productElements) {
            String name = productElement.getText().split("-")[0].trim(); // safer: take only product name
            productNames.add(name);
        }

        log.info("🛒 Total products found: " + productNames.size());

        // Step 2: Loop over product names, search and add to cart
        for (String productText : productNames) {
            try {
                WebElement searchBox = driver.findElement(By.xpath("//input[@placeholder='Search for Vegetables and Fruits']"));
                searchBox.clear();
                log.info("🔎 Searching for product: " + productText);
                searchBox.sendKeys(productText);

                // Wait for search result to appear
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h4[contains(@class,'product-name') and contains(text(),'" + productText + "')]")
                ));

                // Wait for and click the Add to Cart button
                WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[text()='ADD TO CART']")));
                addToCartBtn.click();

                log.info("✅ Clicked 'ADD TO CART' for: " + productText);

            } catch (StaleElementReferenceException stale) {
                log.warn("♻ Retrying after stale element for product: " + productText);
                driver.navigate().refresh();
            } catch (Exception e) {
                log.error("❌ Failed to add product: " + productText, e);
            }
        }
    }

    @Test(priority = 2)
    public void productPrice() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click on the cart button
        driver.findElement(By.cssSelector("img[alt='Cart']")).click();

        // Click on the Proceed to checkout button
        driver.findElement(By.xpath("//button[contains(text(),'PROCEED TO CHECKOUT')]")).click();

        // Apply promo code
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.promoCode")));
        log.info("💸 Entering promo code...");
        driver.findElement(By.cssSelector("input.promoCode")).sendKeys("rahulshettyacademy");
        driver.findElement(By.cssSelector("button.promoBtn")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.promoInfo")));
        String promoMessage = driver.findElement(By.cssSelector("span.promoInfo")).getText();
        log.info("🎁 Promo info displayed: {}", promoMessage);

        // ✅ FIX: Only pick product row prices (5th column) to avoid promos/discounts
        List<WebElement> priceRows = driver.findElements(By.cssSelector("tr td:nth-child(5) p.amount"));
        int totalAmount = 0;

        for (WebElement amount : priceRows) {
            String text = amount.getText().trim();
            if (!text.isEmpty()) {
                int value = Integer.parseInt(text);
                totalAmount += value;
            }
        }

        log.info("💰 Total calculated from items: {}", totalAmount);
        log.info("📊 Price rows found: {}", priceRows.size());

        // Verify total price displayed matches sum
        String ttlAmountText = driver.findElement(By.className("totAmt")).getText().trim();
        int displayedTotal = Integer.parseInt(ttlAmountText);

        log.info("🛒 Displayed total: {}", displayedTotal);

        Assert.assertEquals(displayedTotal, totalAmount, "❌ Mismatch in total calculation!");
        log.info("✅ Assertion Passed: Total amount matches displayed total.");
    }
}
