package ru.e2eautotest.entity.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.e2eautotest.entity.Context;
import ru.e2eautotest.entity.LoggerWrapper;
import ru.e2eautotest.entity.account.Account;

public class LoginPage extends Page {
    // Инициализация логера
    private static final LoggerWrapper LOG = LoggerWrapper.get(LoginPage.class);

    //Поле "почта"
    @FindBy(id = "frm-email")
    private WebElement emailEdit;

    //Поле "почта"
    @FindBy(id = "frm-password")
    private WebElement passwordEdit;

    //Кнопка войти
    @FindBy(xpath = "//*[@id=\"login-form\"]/input[3]")
    private WebElement logInButton;

    public LoginPage(WebDriver driver) {
        super(driver, "login");
    }

    public static LoginPage open(){
        Context.getInstance().getBrowser().get(String.format("%s/login",
                Context.getInstance().getSiteUrl()));
        return new LoginPage(Context.getInstance().getBrowser());
    }

    /*
    Авторизоваться
    @param user - {@link UserAccount}
    */
    public void signIn(Account account) {
        LOG.debug(String.format("LogIn %s | %s", account.getMail(), account.getPassword()));
        getDriver().get(String.format("%s/login", getBaseURL()));
        clearAndSendKey(emailEdit, account.getMail());
        clearAndSendKey(passwordEdit, account.getPassword());
        logInButton.click();
        getDriver().get(String.format("%s/my-account", getBaseURL()));
    }

    /*
    Выйти из профиля
    */
    public void signOut() {
        LOG.debug("logOut");
        getDriver().get(String.format("%s/logout.jsp", getBaseURL()));
    }

    /*
    Релогин
    @param user - {@link UserAccount}
    @return {@link MyProfilePage}
    */
    public MyProfilePage relogin(Account account) {
        signOut();
        signIn(account);
        LOG.debug("Переходим в ЛК");
        getDriver().get(String.format("%s/my-account", getBaseURL()));
        LOG.debug(getDriver().getCurrentUrl());
        return new MyProfilePage(getDriver());
    }

}
