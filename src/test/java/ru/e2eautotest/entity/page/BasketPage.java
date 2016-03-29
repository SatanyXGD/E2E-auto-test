package ru.e2eautotest.entity.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.e2eautotest.entity.LoggerWrapper;


/**
 * Created by пк on 29.03.2016.
 */
public class BasketPage extends Page {
    private static final LoggerWrapper LOG = LoggerWrapper.get(BasketPage.class);

    //Кнопка "Оформить заказ"
    @FindBy(xpath = "//*[@id=\"basket-form\"]/div/div[2]/div/div/div[1]/div/div/input[5]")
    private WebElement checkoutButton;

    public BasketPage(WebDriver driver) {
        super(driver, "cart");
    }

    //Оформить заказ для неавторизированного пользователя
    public PurchasePage checkoutOrderForGuest(){
        checkoutOrder();

        return new PurchasePage(getDriver());
    }

    private void checkoutOrder(){
        LOG.debug("Оформляем заказ");
        scrollToElement(checkoutButton);
        checkoutButton.click();

    }
}
