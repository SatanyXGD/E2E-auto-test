package ru.e2eautotest.entity.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.e2eautotest.entity.ConfigManager;
import ru.e2eautotest.entity.LoggerWrapper;
import ru.e2eautotest.entity.MsgElement;
import java.util.List;

public class MyProfilePage extends Page {
    // Инициализация логера
    private static final LoggerWrapper LOG = LoggerWrapper.get(MyProfilePage.class);

    private By congratulation = By.cssSelector("div.content-frame-responsive.my-account-mix-messages > h3");
    private By finalRegistration = By.cssSelector("div.content-frame-responsive.my-account-mix-messages > p");
    private By howVerified = By.cssSelector("p.bc-top-text");

    public MyProfilePage(WebDriver driver) {
        super(driver, "mvideo");//"my-account");
    }

    /*
    //Проверка всех сообщений в ЛК сразу после регистрации
    @param userName - {@link String}
    @return {@link MyProfilePage}
    */
    public MyProfilePage checkedMsgfirstAutoLoginAfterRegistration(String userName) {
        LOG.debug("Проверка всех сообщений в ЛК сразу после регистрации");

        assertEquals(congratulationMsg(userName));
        assertEquals(finalRegistrationMsg());
        assertEquals(notVerifiedMessage());
        assertEquals(howVerifiedMessage());
        return new MyProfilePage(getDriver());
    }

    /*
    //Проверка всех сообщений в ЛК после рефреша
    @param userName - {@link String}
    @return {@link MyProfilePage}
    */
    public MyProfilePage checkedMsgInNotVerifiedProfile(String userName) {
        LOG.debug("Проверка всех сообщений в ЛК после рефреша");

        getDriver().navigate().refresh();
        assertEquals(welcomeMsg(false, userName));
        assertEquals(notVerifiedMessage());
        assertEquals(howVerifiedMessage());
        assertNotEquals("Не исчезло сообщение", congratulationMsg(userName));
        assertNotEquals("Не исчезло сообщение", finalRegistrationMsg());
        //может нужен новый объект?
        return this;
    }

    /*
    //Проверка всех сообщений в ЛК после верификации
    @param userName - {@link String}
    @return {@link MyProfilePage}
    */
    public MyProfilePage checkedMsgInMyProfileAfterVerification(String userName) {
        LOG.debug("Проверка всех сообщений в ЛК после верификации");

        assertEquals(welcomeMsg(true, userName));
        assertNull(notVerifiedMessage());
        assertNull(howVerifiedMessage());
        assertNull(congratulationMsg(userName));
        assertNull(finalRegistrationMsg());
        return this;
    }

    /*
    "Поздравляем, %s!"
    @param userName - {@link String}
    @return {@link MsgElement}
    */
    private MsgElement congratulationMsg(String userName) {
        //Достаем из конфига нотификации
        String msg  = ConfigManager.evaluate("/notification_messages/my_profile/congratulation").
                getTextContent();
        return new MsgElement(findElement(congratulation), String.format(msg, userName));
    }

    /*
    "Вы успешно зарегистрировались на сайте «М.Видео»."
    @return {@link MsgElement}
    */
    private MsgElement finalRegistrationMsg() {
        //Достаем из конфига нотификации
        String msg  = ConfigManager.evaluate("/notification_messages/my_profile/final_registration").
                getTextContent();
        return new MsgElement(findElement(finalRegistration), msg);
    }

    /*
    "Чтобы активировать учётную запись, пройдите" +
                " по ссылке в письме. Неподтверждённая учётная запись будет удалена через 10 дней."
    @return {@link MsgElement}
    */
    private MsgElement howVerifiedMessage() {
        //Достаем из конфига нотификации
        String msg  = ConfigManager.evaluate("/notification_messages/my_profile/how_verified").
                getTextContent();
        return new MsgElement(findElement(howVerified), msg);
    }

    /*
    "Пожалуйста, подтвердите регистрацию!"
    @return {@link MsgElement}
    */
    private MsgElement notVerifiedMessage() {
        //Достаем из конфига нотификации
        String msg  = ConfigManager.evaluate("/notification_messages/my_profile/please_confirm").
                getTextContent();
        List<WebElement> result = findElements(congratulation);
        WebElement resultElement = null;
        try {
            resultElement = result.get(result.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            LOG.error(e.toString());
        }
        return new MsgElement(resultElement, msg);
    }

    /*
    "Здравствуйте, %s, добро пожаловать в Ваш личный кабинет."
    @param isVerifiedProfile - {@link Boolean}
    @param userName - {@link String}
    @return {@link MsgElement}
    */
    private MsgElement welcomeMsg(Boolean isVerifiedProfile, String userName) {
        By locator = isVerifiedProfile ? By.cssSelector(".my-account-welcome > p:nth-child(1)")
                : By.cssSelector(".my-account-mix-messages > p");

        //Достаем из конфига нотификации
        String msg  = ConfigManager.evaluate("/notification_messages/my_profile/welcome").
                getTextContent();
        return new MsgElement(findElement(locator),
                String.format(msg, userName));
    }
}