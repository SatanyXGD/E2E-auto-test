package ru.e2eautotest.entity.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.e2eautotest.entity.LoggerWrapper;

public class CategoryPage extends Page{
    private static final LoggerWrapper LOG = LoggerWrapper.get(CategoryPage.class);
    //Товар
    @FindBy(xpath = "//*[@id=\"js-product-tile-list\"]/div[1]/div[1]")
    private WebElement product;

    //SKU товара
    @FindBy(css = "div.product-data-rating-code > p")
    private WebElement sku;

    public CategoryPage(WebDriver driver, String URL_MATCH) {
        super(driver, URL_MATCH);
    }

    public PDPPage selectProduct(){
        LOG.debug("Выбираем товар");
        WebElement linkToPDP = product.findElement(By.xpath("div[2]/div[1]/div/h2/span/a"));
        linkToPDP.click();

        String code = sku.getText();
        code = code.substring(code.indexOf("  ")+ 2);

        LOG.debug("\"" + code + "\"");
        return new PDPPage(getDriver(), code);
    }
}
