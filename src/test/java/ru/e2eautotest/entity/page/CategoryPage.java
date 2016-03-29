package ru.e2eautotest.entity.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.e2eautotest.entity.LoggerWrapper;

public class CategoryPage extends Page{
    private static final LoggerWrapper LOG = LoggerWrapper.get(CategoryPage.class);

    public CategoryPage(WebDriver driver, String URL_MATCH) {
        super(driver, URL_MATCH);
    }

    public PDPPage selectProduct(){
        WebElement element = getDriver().findElement(By.
                xpath("//*[@id=\"js-product-tile-list\"]/div[1]/div[1]/div[2]/div[1]/div/h2/span/a"));
        element.click();

        String code = getDriver().findElement(By.
                xpath("/html/body/div[4]/div/div[3]/div[1]/div[2]/div/div[1]/div/div[1]/div/div[2]/p")).getText();
        code = code.substring(code.indexOf("  ")+ 2);

        LOG.debug("\"" + code + "\"");
        return new PDPPage(getDriver(), code);
    }
}
