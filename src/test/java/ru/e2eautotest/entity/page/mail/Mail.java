package ru.e2eautotest.entity.page.mail;

import org.openqa.selenium.WebDriver;
import ru.e2eautotest.entity.account.Account;
import ru.e2eautotest.entity.page.EmailConfirmationPage;

public interface Mail {
    Mail registration(Account account);

    Mail login(Account account);

    EmailConfirmationPage findMailAndVerifyRegistration();

    Mail findOrderMail();

    Mail logOut();

    WebDriver getDriver();
}
