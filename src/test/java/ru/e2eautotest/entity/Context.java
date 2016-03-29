package ru.e2eautotest.entity;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class Context {
    public static final String BROWSER_IE = "*iexplore";
    public static final String BROWSER_FF = "*firefox";
    public static final String BROWSER_CH = "*chrome";

    private static Context context;
    private static String siteUrl;

    private WebDriver browser;

    private Context() {
    }

    public static void initInstance(BrowserType browserType, String siteURL) {
        if(context == null) {
            context = new Context();
            siteUrl = siteURL;
            context.setBrowser(browserType.getDriver());
            context.start();
        }
    }
    public static Context getInstance() {
        if (context == null) {
            throw new IllegalStateException("Context is not initialized");
        }
        return context;
    }

    public WebDriver getBrowser() {
        if (browser != null) {
            return browser;
        }
        throw new IllegalStateException("WebBrowser is not initialized");
    }

    public String getSiteUrl() {
        return siteUrl;
    }


    public void close() {
        browser.quit();
    }

    private void start(){
        browser.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        browser.get(siteUrl);
        browser.manage().deleteAllCookies();
        browser.manage().window().maximize();
    }

    public void setBrowser(WebDriver browser) {
        this.browser = browser;
    }
}