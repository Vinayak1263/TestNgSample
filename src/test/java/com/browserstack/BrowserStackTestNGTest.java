package com.browserstack;

import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.browserstack.local.Local;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BrowserStackTestNGTest {
    public WebDriver driver;
    private Local l;

    @BeforeMethod(alwaysRun = true)
    @org.testng.annotations.Parameters(value = { "config", "environment" })
    @SuppressWarnings("unchecked")
    public void setUp(String config_file, String environment) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/" + config_file));
        JSONObject envs = (JSONObject) config.get("environments");

       
        DesiredCapabilities capabilities = new DesiredCapabilities();

HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
capabilities.setCapability("platformName", "android");
capabilities.setCapability("platformVersion", "9.0");
capabilities.setCapability("deviceName", "Google Pixel 3");
capabilities.setCapability("app", "app_url");
browserstackOptions.put("local", "false");
capabilities.setCapability("bstack:options", browserstackOptions);








        driver = new RemoteWebDriver(
                new URL("http://username:accesskey@hub-cloud.browserstack.com/wd/hub"), capabilities);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        if (l != null) {
            l.stop();
        }
    }
}
