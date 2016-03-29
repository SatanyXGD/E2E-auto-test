package ru.e2eautotest.entity.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.e2eautotest.entity.LoggerWrapper;

public class ConfirmationPage extends Page {
    private static final LoggerWrapper LOG = LoggerWrapper.get(ConfirmationPage.class);

    //Номер заказа
    @FindBy(xpath = "/html/body/div[2]/div[1]/div/div[3]/div[1]/div/div[4]/div[1]")
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
    @FindBy(xpath = "//*[@id=\"order-register-form\"]/div[5]/div[2]/label[1]/span")
    private WebElement registrationBtn;

    public ConfirmationPage(WebDriver driver) {
        super(driver, "confirmation");
    }

    public MyProfilePage register(String password){
        clearAndSendKey(passwordEdit, password);
        clearAndSendKey(confirmPasswordEdit, password);
        scrollToElement(confirmPolConf);
        confirmPolConf.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        registrationBtn.click();

        return new MyProfilePage(getDriver());
    }
}
