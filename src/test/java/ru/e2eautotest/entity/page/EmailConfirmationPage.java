package ru.e2eautotest.entity.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.e2eautotest.entity.ConfigManager;
import ru.e2eautotest.entity.LoggerWrapper;
import ru.e2eautotest.entity.MsgElement;
import java.util.ArrayList;
import java.util.List;

public class EmailConfirmationPage extends Page {
    private static final LoggerWrapper LOG = LoggerWrapper.get(EmailConfirmationPage.class);

    //Сообщение "Поздравляем!"
    @FindBy(css = "div.error-content > div.error-text > h3")
    private WebElement congratulation;

    private By finalVerifiedLocator = By.cssSelector("div.error-content > div.error-text > p");

    public EmailConfirmationPage(WebDriver driver) {
        super(driver, "email-confirmation");
    }

    /*
    Проверка сообщений после перехода по ссылке для подтверждения почты
    @param userName - {@link String}
    @return {@link EmailConfirmationPage}
    */
    public EmailConfirmationPage checkVerificationMail(String userName) {
        LOG.debug("Проверка сообщений после перехода по ссылке для подтверждения почты");
        assertEquals(finalVerifiedMessage(userName));
        return this;
    }

    /*
    Проверка сообщений после второго перехода по ссылке для подтверждения почты
    @return {@link EmailConfirmationPage}
    */
    public EmailConfirmationPage checkSecondTryVerificationMail() {
        LOG.debug("Проверка сообщений после второго перехода по ссылке для подтверждения почты");
        assertEquals(errorFinalVerifyMsg());
        return this;
    }

    /*
    "Поздравляем!"
    "Спасибо, %s!"
    "Вы успешно подтвердили адрес электронной почты."
    @param userName - {@link String}
    @return {@link List<MsgElement>}
    */
    private List<MsgElement> finalVerifiedMessage(String userName) {
        List<WebElement> elements = findElements(finalVerifiedLocator);
        elements.add(congratulation);
        List<MsgElement> result = new ArrayList<MsgElement>();

        //Достаем из конфига нотификации
        String thanks  = ConfigManager.evaluate("/notification_messages/email_confirm/thanks").
                getTextContent();
        String confirm  = ConfigManager.evaluate("/notification_messages/email_confirm/verified_succes").
                getTextContent();
        String congratulation  = ConfigManager.evaluate("/notification_messages/email_confirm/congratulation").
                getTextContent();
        result.add(new MsgElement(elements.get(0), String.format(thanks, userName)));
        result.add(new MsgElement(elements.get(1), confirm));
        result.add(new MsgElement(elements.get(2), congratulation));
        return result;
    }

    /*
    "Спасибо, Ваш адрес электронной почты уже подтверждён."
    @return {@link MsgElement}
    */
    private MsgElement errorFinalVerifyMsg() {
        //Достаем из конфига нотификации
        String msg  = ConfigManager.evaluate("/notification_messages/email_confirm/already_verified").
                getTextContent();
        return new MsgElement(congratulation,
                msg);
    }
}
