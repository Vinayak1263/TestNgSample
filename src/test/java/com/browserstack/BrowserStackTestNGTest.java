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
capabilities.setCapability("browserName", "Chrome");
capabilities.setCapability("browserVersion", "latest-beta");
HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
browserstackOptions.put("os", "Windows");
browserstackOptions.put("osVersion", "10");
browserstackOptions.put("local", "false");
browserstackOptions.put("seleniumVersion", "3.14.0");
capabilities.setCapability("bstack:options", browserstackOptions);


//For HTTP

System.getProperties().put("http.proxyHost", "<HOST>");
System.getProperties().put("http.proxyPort", "<PORT>");
System.getProperties().put("http.proxyUser", "<USER>");
System.getProperties().put("http.proxyPassword", "<PASSWORD>");

//For HTTPS

System.getProperties().put("https.proxyHost", "<HOST>");
System.getProperties().put("https.proxyPort", "<PORT>");
System.getProperties().put("https.proxyUser", "<USER>");
System.getProperties().put("https.proxyPassword", "<PASSWORD>");




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
