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
	
@Test(enabled = false)
    public void addToCartItem() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String[] itemsNeeded = {"Cucumber", "Brocolli", "Beetroot"};
        
        List<WebElement> products = driver.findElements(By.cssSelector("h4.product-name"));
        List<String> itemsNeededList = Arrays.asList(itemsNeeded);

        int itemsAdded = 0;

        for (int i = 0; i < products.size(); i++) {
            String productName = products.get(i).getText().split("-")[0].trim();

            if (itemsNeededList.contains(productName)) {
                driver.findElements(By.xpath("//div[@class='product-action']/button")).get(i).click();
                itemsAdded++;

                if (itemsAdded == itemsNeeded.length) {
                    break;
                }
            }
        }

        driver.findElement(By.cssSelector("img[alt='Cart']")).click();
        driver.findElement(By.xpath("//button[contains(text(),'PROCEED TO CHECKOUT')]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.promoCode")));

        driver.findElement(By.cssSelector("input.promoCode")).sendKeys("rahulshettyacademy");
        driver.findElement(By.cssSelector("button.promoBtn")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.promoInfo")));

        System.out.println(driver.findElement(By.cssSelector("span.promoInfo")).getText());
    }
@Test
	public void buttonDisabled() {
		
		driver.findElement(By.cssSelector("img[alt='Cart']")).click();
		Boolean buttonStatus =driver.findElement(By.xpath("//button[contains(text(),'PROCEED TO CHECKOUT')]")).isDisplayed();
		Boolean buttonClick =driver.findElement(By.xpath("//button[contains(text(),'PROCEED TO CHECKOUT')]")).isEnabled();

		Assert.assertEquals(true, buttonStatus);
		Assert.assertEquals(false, buttonClick);
		System.out.println("Displayed: " + buttonStatus);
		System.out.println("Enabled: " +buttonClick );
	}


}
