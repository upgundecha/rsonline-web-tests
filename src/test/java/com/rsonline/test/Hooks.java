package com.rsonline.test;

import com.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.UUID;

/**
 * This is Hooks class managing WebDriver lifecycle
 *
 * @author upgundecha
 */
public class Hooks {

    private static WebDriver driver;

    @Before
    public void setUp() {
        if (SystemUtils.IS_OS_WINDOWS) {
            System.setProperty("webdriver.chrome.driver",
                    "./src/test/resources/drivers/chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver",
                    "./src/test/resources/drivers/chromedriver");
        }
        driver = new ChromeDriver();
    }

    @After
    public void tearDown(Scenario scenario) throws Exception {
        try {
            // capture and add screenshot to report if scenario is failed
            if (scenario.isFailed()) {
                String filename = UUID.randomUUID().toString();
                File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scr, new File("./test-output/img/" + filename + ".png"));
                Reporter.addScreenCaptureFromPath("img/" + filename + ".png");
            }
        } finally {
            driver.quit();
        }
    }

    /**
     * Get driver instance
     * @return WebDriver
     */
    public static WebDriver getDriver() {
        return driver;
    }
}