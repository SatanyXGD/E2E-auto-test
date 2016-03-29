package ru.e2eautotest.entity.page.mail;

import org.openqa.selenium.WebDriver;
import ru.e2eautotest.entity.page.account.Account;

public interface Mail {
    Mail registration(Account account);

    Mail login(Account account);

    Mail findMailAndVerifyRegistration();

    Mail findOrderMail();

    Mail logOut();

    WebDriver getDriver();
}
