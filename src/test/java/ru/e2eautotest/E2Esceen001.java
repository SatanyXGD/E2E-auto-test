package ru.e2eautotest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;

public class E2Esceen001 {
    private static final LoggerWrapper LOG = LoggerWrapper.get(E2Esceen001.class);

    public static WebDriver driver;
    private static String url = "https://www.mvideo.ru";

    @Before
    public void setUp() {
        LOG.debug("Подготовка к тесту");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

    }

    @Test
    public void test(){
        LOG.debug("НАЧАЛО ТЕСТА");
        driver.get(url);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        LOG.debug("Очистка после теста");
        driver.quit();
    }
}
