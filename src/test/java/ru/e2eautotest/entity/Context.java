package ru.e2eautotest.entity;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class Context {
    private static final LoggerWrapper LOG = LoggerWrapper.get(Context.class);
    private static Context context;
    private static String siteUrl;

    private WebDriver browser;

    private Context() {
    }

    public static void initInstance(BrowserType browserType, String siteURL) {
        LOG.debug("Инифиализация WebDriver");
        if(context == null) {
            context = new Context();
            siteUrl = siteURL;
            context.setBrowser(browserType.getDriver());
            context.start();
        }
    }
    public static Context getInstance() {
        if (context == null) {
            throw LOG.getIllegalStateException("WebBrowser is not initialized",
                    new IllegalStateException());
        }
        return context;
    }

    public WebDriver getBrowser() {
        if (browser != null) {
            return browser;
        }
        throw LOG.getIllegalStateException("WebBrowser is not initialized",
                new IllegalStateException());
    }

    public String getSiteUrl() {
        return siteUrl;
    }


    public void close() {
        browser.quit();
    }

    private void start(){
        LOG.debug("Конфигурируем WebDriver");
        browser.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        browser.get(siteUrl);
        browser.manage().deleteAllCookies();
        browser.manage().window().maximize();
    }

    public void setBrowser(WebDriver browser) {
        this.browser = browser;
    }
}