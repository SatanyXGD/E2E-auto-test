package ru.e2eautotest.entity.page.mail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.e2eautotest.entity.LoggerWrapper;
import ru.e2eautotest.entity.page.EmailConfirmationPage;
import ru.e2eautotest.entity.page.Page;
import ru.e2eautotest.entity.account.Account;

import java.util.List;

public class MailRuPage extends Page implements Mail {
    // Инициализация логера
    private static final LoggerWrapper LOG = LoggerWrapper.get(MailRuPage.class);

    //поле ввода почты
    @FindBy(id = "mailbox__login")
    private WebElement mailEdit;

    //поле ввода пароля
    @FindBy(id = "mailbox__password")
    private WebElement passwordEdit;

    //кнопка авторизации
    @FindBy(id = "mailbox__auth__button")
    private WebElement authButton;

    //поле поиска
    @FindBy(xpath = "//*[@id=\"portal-menu__search__form\"]/span[1]/span[1]/input")
    private WebElement findEdit;

    //кнопка поиск
    @FindBy(xpath = "//*[@id=\"portal-menu__search__form\"]/span[3]/button")
    private WebElement searchButton;

    //письмо
    private By mailLocator = By.cssSelector("div.b-datalist_search > div.b-datalist__body > div.b-datalist__item");

    //кнопка "Подтвердить"
    @FindBy(linkText = "Подтвердить")
    private WebElement acceptButton;

    //Дата получения письма
    @FindBy(css = "div.b-datalist__item__panel > div.b-datalist__item__date > span")
    private WebElement sendMailData;

    public MailRuPage(WebDriver driver) {
        super(driver, "mail.ru");
    }

    /*
    Регистрация почтового ящика
    @param user - {@link UserAccount}
    */
    public MailRuPage registration(Account account) {
        LOG.debug("Регистрация почты");
        /*driver.get("https://e.mail.ru/signup?from=main&rf=mail.ru");
        driver.manage().window().maximize();
        driver.findElement(By.id("x_9e01d53a0ae1946f")).clear();
        driver.findElement(By.id("x_9e01d53a0ae1946f")).sendKeys("sdfsdfs");
        driver.findElement(By.id("x_4114c810fa61a495")).clear();
        driver.findElement(By.id("x_4114c810fa61a495")).sendKeys("sdf");
        driver.findElement(By.id("man1")).click();*/

        return this;
    }

    /*
    Логин
    @param user - {@link UserAccount}
    */
    public MailRuPage login(Account account) {
        LOG.debug("logIn");
        getDriver().get(getBaseURL());
        clearAndSendKey(mailEdit, account.getMail());
        clearAndSendKey(passwordEdit, account.getPassword());
        authButton.click();
        return this;
    }

    /*
    Выход
    @param user - {@link UserAccount}
    @return {@link MyProfilePage}
    */
    public MailRuPage logOut() {
        LOG.debug("logOut");
        getDriver().get("https://auth.mail.ru/cgi-bin/logout");
        return this;
    }

    /*
    Подтверждение пользователя по письму
    */
    public EmailConfirmationPage findMailAndVerifyRegistration() {
        LOG.debug("Подтверждение пользователя по письму");
        WebElement mail = findMail("Пожалуйста, подтвердите вашу регистрацию");
        scrollToElement(mail);
        getDriver().get(String.format("https://e.mail.ru/message/%s", mail.getAttribute("data-id")));
        getDriver().get(acceptButton.getAttribute("href"));
        return new EmailConfirmationPage(getDriver());
    }

    //Поиск письма с информацией о заказе
    public MailRuPage findOrderMail(){
        LOG.debug("Поиск письма с заказом");
        WebElement mail = findMail("Ваш заказ принят");

        scrollToElement(mail);
        getDriver().get(String.format("https://e.mail.ru/message/%s", mail.getAttribute("data-id")));
        return this;
    }

    //Поиск письма по заголовку
    private WebElement findMail(String header) {
        LOG.debug("Поиск письма \"" + header + "\"");

        clearAndSendKey(findEdit, "uat_atg@mvideo.ru");
        searchButton.click();

        List<WebElement> mails = findElements(mailLocator);
        LOG.debug("Найдено " + mails.size() + " элементов");

        WebElement result = null;
        for(WebElement mail : mails) {
            //проверка, что нашлось то самое письмо
            WebElement link = mail.findElement(By.cssSelector("div > a"));
            if (isTrueMessage(link, header)) {
                result = mail;
                break;
            }
        }
        if(result == null) throw new IllegalArgumentException("Не пришло письмо");

        return result;
    }

    /*
    Проверка, что мы нашли верное письмо
    @param element - {@link WebElement}
    @return {@link Boolean}
    */
    private Boolean isTrueMessage(WebElement element, String header) {
        LOG.debug("Проверка, что мы нашли верное письмо");
        if (!element.getAttribute("data-subject").contains(header)) return false;
        LOG.debug("Проверка даты");
        if (!sendMailData.getAttribute("title").contains("Сегодня")) return false;
        LOG.debug("Дата соответствует");
        return true;
    }
}
