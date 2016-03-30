package ru.e2eautotest.entity.page;

import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ru.e2eautotest.entity.LoggerWrapper;
import ru.e2eautotest.entity.MsgElement;
import java.util.List;

public abstract class Page {
    private static final LoggerWrapper LOG = LoggerWrapper.get(Page.class);

    private String URL_MATCH;
    private String baseURL;
    private WebDriver driver;

    public Page(WebDriver driver, String URL_MATCH) {
        //Проверить, что находимся на верной странице
        LOG.debug(String.format("Go to \"%s\" | \"%s\"", driver.getCurrentUrl(), URL_MATCH));
        if (!driver.getCurrentUrl().contains(URL_MATCH)) {
            throw LOG.getIllegalArgumentException(String.format("This is not the page are expected. %s",
                    driver.getCurrentUrl()), new IllegalStateException());
        }
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.URL_MATCH = URL_MATCH;
        setBaseURL();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getBaseURL() {
        return baseURL;
    }


    public void setBaseURL() {
        String currentURL = driver.getCurrentUrl();
        this.baseURL = currentURL.substring(0, currentURL.indexOf(".ru") + ".ru".length());
    }

    public void scrollToElement(WebElement webElement) {
        LOG.debug(String.format("Scroll to element %s", webElement.getTagName()));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", webElement);
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -200)");
    }

    public WebElement findElement(By locator) {
        LOG.debug(String.format("Поиск элемента по локатору - %s", locator.toString()));
        try {
            WebElement element = driver.findElement(locator);
            return element;
        } catch (Exception e) {
            LOG.error("Элемент не найден", e);
        }
        return null;
    }

    public List<WebElement> findElements(By locator) {
        LOG.debug(String.format("Поиск элементов по локатору - %s", locator.toString()));
        try {
            return driver.findElements(locator);
        } catch (Exception e) {
            LOG.error("Элементы не найдены", e);
        }
        return null;
    }

    public void clearAndSendKey(WebElement element, String text) {
        if(!text.isEmpty()) {
            LOG.debug(String.format("Ввод строки \"%s\" в веб-элемент %s", text, element.getTagName()));
            element.clear();
            element.sendKeys(text);
        }
    }

    public void assertEquals(String expected, WebElement actual) throws AssertionFailedError
    {
        String actualStr = "";
        try{
            actualStr = actual.getText();
        }catch (Exception e) {
            LOG.error("Элемет не содержит текст" ,e);}
        LOG.debug(String.format("expected: \"%s\" actual: \"%s\"", expected, actualStr));
        Assert.assertEquals(expected, actualStr);
    }

    public void assertNotEquals(String expected, WebElement actual) throws AssertionFailedError
    {
        String actualStr = "";
        try{
            actualStr = actual.getText();
        }catch (Exception e) {
            LOG.error("Элемет не содержит текст" ,e);}
        LOG.debug(String.format("expected: \"%s\" actual: \"%s\"", expected, actualStr));
        Assert.assertNotEquals(expected, actualStr);
    }

    public void assertNull(String msg, Object obj){
        Assert.assertNull(msg, obj);
    }

    public void assertNotNull(String msg, Object obj){
        Assert.assertNotNull(msg, obj);
    }

    public void assertEquals(List<MsgElement> elements) {
        for (MsgElement e : elements)
            assertEquals(e);
    }

    public void assertEquals(MsgElement element) {
        Assert.assertEquals(element.getActual(), element.getExpected());
    }

    public void assertNotEquals(String msg, MsgElement element) {
        Assert.assertNotEquals(msg, element.getActual(), element.getExpected());
    }

    public void assertNull(MsgElement element) {
        Assert.assertNull(element.getElement());
    }
}
