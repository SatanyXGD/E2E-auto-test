package ru.e2eautotest.entity.page.mail;

import org.openqa.selenium.WebDriver;
import ru.e2eautotest.entity.LoggerWrapper;
import ru.e2eautotest.entity.page.EmailConfirmationPage;
import ru.e2eautotest.entity.page.account.Account;


public class MailManager {
    // Инициализация логера
    private static final LoggerWrapper LOG = LoggerWrapper.get(MailManager.class);

    private Mail mail;

    public Mail getMail() {
        return mail;
    }

    public void setMail(String email, WebDriver driver) {
        if (email.contains("@mail.ru")) {
            driver.get("https://mail.ru");
            mail = new MailRuPage(driver);
        }
    }

    /*
    Подтверждение зарегистрированного пользователя
    @param user - {@link UserAccount}
    @return {@link EmailConfirmationPage}
    */
    public EmailConfirmationPage verifityMail(Account account) {
        LOG.debug("Подтверждение зарегистрированного пользователя");

        if (mail == null) throw new NullPointerException("Не установлен тип почтового ящика");
        getMail().login(account);
        getMail().findMailAndVerifyRegistration();
        return new EmailConfirmationPage(getMail().getDriver());
    }


}