package tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

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

        // Use Java Client v6.0.0 or above
        DesiredCapabilities capabilities = new DesiredCapabilities();
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("appiumVersion", "2.0.1");
//        browserstackOptions.put("idleTimeout", "600");
//        browserstackOptions.put("networkLogs", "true");
//		browserstackOptions.put("automationVersion", "latest");
//		browserstackOptions.put("enableCameraImageInjection", "true");
//		browserstackOptions.put("timezone", "UTC");
//		browserstackOptions.put("enableBiometric", "true");
        browserstackOptions.put("buildName", "upload media");
//		Map<String, Object> networkLogsOptions = new HashMap<>();
//		networkLogsOptions.put("captureContent", "true");
//		browserstackOptions.put("networkLogsOptions", networkLogsOptions);
        browserstackOptions.put("uploadMedia", new String[]{"media://c64b91ee51fdaa7ee729419825a7468b908d3e87", "media://c34bebb9f72624205e075fa271a192e8068eb05b"});
        capabilities.setCapability("bstack:options", browserstackOptions);
        capabilities.setCapability("platformName", "ios");
        capabilities.setCapability("platformVersion", "17");
        capabilities.setCapability("deviceName", "iPhone 15");
        capabilities.setCapability("app", "bs://sample.app");
//        capabilities.setCapability("language", "fi");
//        capabilities.setCapability("locale", "fi_FI");
//		capabilities.setCapability("automationName", "XCUITest");
//		capabilities.setCapability("noReset", "false");

        //// Legacy way
//        capabilities.setCapability("browserstack.uploadMedia", new String[]{"media://c64b91ee51fdaa7ee729419825a7468b908d3e87", "media://c34bebb9f72624205e075fa271a192e8068eb05b"});

        driver = new AndroidDriver(new URL(url), capabilities);

//        //////// IOS
        WebElement textButton = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Text Button")));
        textButton.click();
        WebElement textInput = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Text Input")));
        textInput.sendKeys("hello@browserstack.com" + "\n");

        Thread.sleep(5000);

        WebElement textOutput = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Text Output")));

        Assert.assertEquals(textOutput.getText(), "hello@browserstack.com");

        driver.quit();
    }
}
