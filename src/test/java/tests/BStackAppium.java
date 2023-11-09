package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class BStackAppium {

	public AndroidDriver driver;

	@Test
	public void test() throws MalformedURLException, InterruptedException {

		final String USERNAME = (System.getenv("BROWSERSTACK_USERNAME") != null)
				? System.getenv("BROWSERSTACK_USERNAME")
				: "username";
		final String AUTOMATE_KEY = (System.getenv("BROWSERSTACK_ACCESS_KEY") != null)
				? System.getenv("BROWSERSTACK_ACCESS_KEY")
				: "accesskey";
		final String url = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.browserstack.com/wd/hub";

		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("deviceName", "OnePlus 11R");
		caps.setCapability("os_version", "13.0");
		caps.setCapability("project", "Appium");
		caps.setCapability("build", "Appium Legacy");
		caps.setCapability("app", "bs://sample.app");

		driver = new AndroidDriver(new URL(url), caps);

		WebElement searchElement = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30))
				.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Search Wikipedia")));

		searchElement.click();
		WebElement insertTextElement = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30))
				.until(ExpectedConditions.elementToBeClickable(AppiumBy.id("org.wikipedia.alpha:id/search_src_text")));
		insertTextElement.sendKeys("BrowserStack");
		Thread.sleep(5000);

		List<WebElement> allProductsName = driver.findElements(AppiumBy.className("android.widget.TextView"));
		Assert.assertTrue(allProductsName.size() > 0);

		driver.quit();

	}

}
