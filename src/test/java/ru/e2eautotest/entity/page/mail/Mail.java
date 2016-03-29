package ru.e2eautotest.entity.page.mail;

import org.openqa.selenium.WebDriver;
import ru.e2eautotest.entity.page.account.Account;

public interface Mail {
    void registration(Account account);

    void login(Account account);

    void findMailAndVerifyRegistration();

    void logOut();

    WebDriver getDriver();
}
