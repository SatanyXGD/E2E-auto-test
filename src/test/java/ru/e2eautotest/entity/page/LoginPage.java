package ru.e2eautotest.entity.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.e2eautotest.entity.LoggerWrapper;
import ru.e2eautotest.entity.page.account.Account;

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

    /*
    Авторизоваться
    @param user - {@link UserAccount}
    */
    public void signIn(Account account) {
        LOG.debug("LogIn " + account.getMail() + "|" + account.getPassword());

        getDriver().get(getBaseURL() + "/login");
        clearAndSendKey(emailEdit, account.getMail());
        clearAndSendKey(passwordEdit, account.getPassword());
        logInButton.click();
        getDriver().get(getBaseURL() + "/my-account");
    }

    /*
    Выйти из профиля
    */
    public void signOut() {
        LOG.debug("logOut");

        getDriver().get(getBaseURL() + "/logout.jsp");
    }

    /*
    Релогин
    @param user - {@link UserAccount}
    @return {@link MyProfilePage}
    */
    public MyProfilePage relogin(Account account) {
        signOut();
        signIn(account);
        getDriver().get(getBaseURL() + "/my-account");
        return new MyProfilePage(getDriver());
    }

}
