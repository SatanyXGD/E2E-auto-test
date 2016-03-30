package ru.e2eautotest.entity.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.e2eautotest.entity.LoggerWrapper;

public class ConfirmationPage extends Page {
    private static final LoggerWrapper LOG = LoggerWrapper.get(ConfirmationPage.class);

    //Номер заказа
    @FindBy(className = "order-confirmation-number")
    private WebElement numOrder;

    //Поле "Пароль"
    @FindBy(id = "register-form-password")
    private WebElement passwordEdit;

    //Поле "Подтверждение пароля"
    @FindBy(id = "register-form-confirm-password")
    private WebElement confirmPasswordEdit;

    //Чекбокс "Подтверждение политики конф"
    @FindBy(xpath = "//*[@id=\"order-register-form\"]/div[5]/div[2]/label[1]/span")
    private WebElement confirmPolConf;

    //Кнопка "Зарегистрироваться"
    @FindBy(xpath = "//*[@id=\"order-register-form\"]/input[9]")
    private WebElement registrationBtn;

    public ConfirmationPage(WebDriver driver) {
        super(driver, "confirmation");
    }


    //Регистрация пользователя после оформления заказа
    public MyProfilePage register(String password){
        LOG.debug("Регистрация гостя после покупки");
        clearAndSendKey(passwordEdit, password);
        clearAndSendKey(confirmPasswordEdit, password);
        scrollToElement(confirmPolConf);
        confirmPolConf.click();
        registrationBtn.click();

        return new MyProfilePage(getDriver());
    }

    //Проверка деталей заказа
    public ConfirmationPage checkOrderSummary(){
        LOG.debug(numOrder.getText());
        //добавить проверку саммари заказа

        return this;
    }
}
