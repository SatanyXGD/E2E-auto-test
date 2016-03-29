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

    //Вкладка "Наличие в магазинах
    @FindBy(linkText = "Наличие в магазинах")
    private WebElement storeList;

    //Кнопка "Посмотреть все"
    @FindBy(xpath = "//*[@id=\"store-locator-list\"]/div[2]/div/div[3]/a")
    private WebElement showAllButton;

    //Локатор для магазинов
    private By storeLocator = By.xpath("//*[@id=\"js-stock-level-tooltips\"]/ul/li");

    public PDPPage(WebDriver driver, String URL_MATCH) {
        super(driver, URL_MATCH);
    }

    //Открыть Открыть вкладку "Наличие в магазинах" и найти магазин
    public PDPPage openStoreListAndFindStore(String storeAddress) {
        openStoreList();
        WebElement store = findStore(storeAddress);
        assertNotNull("Не найден магазин " + storeAddress, store);

        return this;
    }

    //Открыть Открыть вкладку "Наличие в магазинах" и найти магазин
    public BasketPage openStoreListAndFindStoreAndAddProduct(String storeAddress) {
        LOG.debug("Добавляем товар в корзину, выбрав магазин");
        openStoreList();
        WebElement store = findStore(storeAddress);
        assertNotNull("Не найден магазин " + storeAddress, store);

        store.findElement(By.cssSelector("div.store > form")).click();

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
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> stores = findElements(storeLocator);
        //Удаляем шапку таблицы
        stores.remove(0);

        LOG.debug("Найдено " + stores.size() + " магазинов");

        for(WebElement e : stores)
        {
            scrollToElement(e);
            WebElement name = e.findElement(By.cssSelector("div.name > h3 > a"));
            LOG.debug("Магазин \"" + name.getText() + "\" является \"" + storeAddress + "\"?");
            if (name.getText().equals(storeAddress)){
                WebElement stock = e.findElement(By.cssSelector("div.stock > i"));
                LOG.debug("Наличие товара: " + stock.getAttribute("data-title"));
                if (stock.getAttribute("data-title").equals("На складе"))return e;
            }
        }

        return null;
    }
}
