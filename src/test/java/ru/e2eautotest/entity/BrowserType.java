package ru.e2eautotest.entity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public enum BrowserType {
    IE(new InternetExplorerDriver()),
    FF(new FirefoxDriver()),
    CH(new ChromeDriver());

    private final WebDriver driver;

    BrowserType(WebDriver driver){
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
