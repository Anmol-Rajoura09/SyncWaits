package Selenium_Introduction.SyncWaits;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class SearchItems extends BaseSetup {
	
	@Test
	public void searchItemsAndAddToCart(){
		
//		Store all searchable values in list
//		traverse all and pass them one by one in sendkeys		
//		how to do AI agents testing;

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        log.info("🚀 Starting test: Search and Add Products to Cart");

	        // Step 1: Extract all product names into a list of Strings (avoid stale elements)
	        List<WebElement> productElements = driver.findElements(By.className("product-name"));
	        List<String> productNames = new ArrayList<>();

	        for (WebElement productElement : productElements) {
	            String name = productElement.getText();
	            productNames.add(name);
	            //.split("-")[0].trim()
	        }

	        log.info("🛒 Total products found: " + productNames.size());

	        // Step 2: Locate search box once
	        WebElement searchBox = driver.findElement(By.xpath("//input[@placeholder='Search for Vegetables and Fruits']"));

	        // Step 3: Loop over product names, search and add to cart
	        for (String productText : productNames) {
	            try {
	                searchBox.clear();
	                log.info("🔎 Searching for product: " + productText);
	                searchBox.sendKeys(productText);

	                // Wait for search result to appear
	                wait.until(ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//h4[contains(@class,'product-name') and contains(text(),'" + productText + "')]")
	                ));

	                // Wait for and click the Add to Cart button
	                WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
	                        By.xpath("//button[text()='ADD TO CART']")
	                ));
	                addToCartBtn.click();
	                log.info("✅ Clicked 'ADD TO CART' for: " + productText);

	            } catch (Exception e) {
	                log.error("❌ Failed to add product: " + productText, e);
	            }
	        }

	    }
	
//	public void productPrice() {
//		// Click on the cart button
//		
//		// Click on the Proceed to checkout button
//		
//		// Apply promo code.
//		
//		// Verify No. of Ietms.
//		
//		
//		// Verify price.
//		
//		
//		// Verify total price
//		
//		
//	}

}
