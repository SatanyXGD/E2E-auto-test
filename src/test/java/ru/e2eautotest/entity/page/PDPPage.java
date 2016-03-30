package ru.e2eautotest.entity.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.e2eautotest.entity.LoggerWrapper;
import java.util.List;

public class PDPPage extends Page {
    private static final LoggerWrapper LOG = LoggerWrapper.get(PDPPage.class);

    //кнопка "Добавить в корзину"
    @FindBy(id = "js-form-submit-id")
    private WebElement addToBasketButton;

    //Вкладка "Наличие в магазинах"
    @FindBy(linkText = "Наличие в магазинах")
    private WebElement storeList;

    //Вкладка "О товаре"
    @FindBy(linkText = "О товаре")
    private WebElement aboutProduct;

    //Описание товара
    @FindBy(css = "div.pds-top-description > p")
    private WebElement description;

    //Вкладка "Характеристики"
    @FindBy(linkText = "Характеристики")
    private WebElement characteristics;

    //Кнопка "Посмотреть все"
    @FindBy(xpath = "//*[@id=\"store-locator-list\"]/div[2]/div/div[3]/a")
    private WebElement showAllButton;

    //Имя магазина
    private By nameStoreLocator = By.xpath("div[2]/div[1]/div/h2/span/a");

    //Локатор для магазинов
    private By storeLocator = By.xpath("//*[@id=\"js-stock-level-tooltips\"]/ul/li");

    //Кнопка "Выбрать" у магазина
    private By addToBasketInStoreLocator = By.cssSelector("div.store > form");

    public PDPPage(WebDriver driver, String URL_MATCH) {
        super(driver, URL_MATCH);
    }

    //Проверить наличие описания товара на вкладке "О товаре"
    public PDPPage checkDescription()
    {
        LOG.debug("Проверить наличие описания товара на вкладке \"О товаре\"");
        LOG.debug(description.getText());
        assertNotEquals("", description);
        return this;
    }

    //Открыть Открыть вкладку "Наличие в магазинах" и найти магазин
    public PDPPage openStoreListAndFindStore(String storeAddress) {
        openStoreList();
        WebElement store = findStore(storeAddress);
        assertNotNull("Не найден магазин " + storeAddress, store);
        return this;
    }

    //На вкладке "Наличие в магазине" у магазина нажать "Выбрать"
    public BasketPage openStoreListAndFindStoreAndAddProduct(String storeAddress) {
        LOG.debug("Добавляем товар в корзину, выбрав магазин");
        openStoreList();
        WebElement store = findStore(storeAddress);
        assertNotNull(String.format("Не найден магазин %s",storeAddress), store);
        store.findElement(addToBasketInStoreLocator).click();
        return new BasketPage(getDriver());
    }


    //Открыть вкладку "Наличие в магазинах"
    public PDPPage openStoreList()
    {
        LOG.debug("Открываем вкладку \"Наличие в магазинах\"");
        scrollToElement(storeList);
        storeList.click();
        return this;
    }

    //добавить в корзину
    public BasketPage addToBasket(){
        LOG.debug("Добавляем товар в корзину");
        scrollToElement(addToBasketButton);
        addToBasketButton.click();
        return new BasketPage(getDriver());
    }

    //Поиск магазина из списка
    private WebElement findStore(String storeAddress){
        LOG.debug("Ищем нужный магазин");
        try{
            scrollToElement(showAllButton);
            showAllButton.click();
            LOG.debug("Нажимаем кнопку \"Показать все\"");
        }catch (Exception e){}

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> stores = findElements(storeLocator);
        //Удаляем шапку таблицы
        stores.remove(0);
        LOG.debug(String.format("Найдено %s магазинов", stores.size()));
        for(WebElement e : stores)
        {
            scrollToElement(e);
            WebElement name = e.findElement(nameStoreLocator);
            LOG.debug(String.format("Магазин \"%s\" является \"%s\"?", name.getText(), storeAddress));
            if (name.getText().equals(storeAddress)){
                WebElement stock = e.findElement(By.cssSelector("div.stock > i"));
                LOG.debug(String.format("Наличие товара: %s", stock.getAttribute("data-title")));
                if (stock.getAttribute("data-title").equals("На складе"))return e;
            }
        }
        return null;
    }
}
