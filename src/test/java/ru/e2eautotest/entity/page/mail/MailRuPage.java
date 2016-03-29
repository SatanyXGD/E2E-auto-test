package ru.e2eautotest.entity.page.mail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.e2eautotest.entity.LoggerWrapper;
import ru.e2eautotest.entity.page.Page;
import ru.e2eautotest.entity.page.account.Account;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public MailRuPage(WebDriver driver) {
        super(driver, "mail.ru");
    }

    /*
    Регистрация почтового ящика
    @param user - {@link UserAccount}
    */
    public void registration(Account account) {
        LOG.debug("Регистрация почты");
        /*driver.get("https://e.mail.ru/signup?from=main&rf=mail.ru");
        driver.manage().window().maximize();
        driver.findElement(By.id("x_9e01d53a0ae1946f")).clear();
        driver.findElement(By.id("x_9e01d53a0ae1946f")).sendKeys("sdfsdfs");
        driver.findElement(By.id("x_4114c810fa61a495")).clear();
        driver.findElement(By.id("x_4114c810fa61a495")).sendKeys("sdf");
        driver.findElement(By.id("man1")).click();*/
    }

    /*
    Логин
    @param user - {@link UserAccount}
    */
    public void login(Account account) {
        LOG.debug("logIn");

        getDriver().get(getBaseURL());
        clearAndSendKey(mailEdit, account.getMail());
        clearAndSendKey(passwordEdit, account.getPassword());
        authButton.click();
    }

    /*
    Выход
    @param user - {@link UserAccount}
    @return {@link MyProfilePage}
    */
    public void logOut() {
        LOG.debug("logOut");

        getDriver().get("https://auth.mail.ru/cgi-bin/logout");
    }

    /*
    Подтверждение пользователя по письму
    */
    public void findMailAndVerifyRegistration() {
        LOG.debug("Подтверждение пользователя по письму");

        clearAndSendKey(findEdit, "uat_atg@mvideo.ru");
        searchButton.click();

        WebElement mail = findElement(mailLocator);

        //проверка, что нашлось то самое письмо
        WebElement link = mail.findElement(By.cssSelector("div > a"));
        if (!isTrueMessage(link)) throw new IllegalArgumentException("Не пришло письмо");

        scrollToElement(mail);

        getDriver().get(String.format("https://e.mail.ru/message/%s", mail.getAttribute("data-id")));
        getDriver().get(acceptButton.getAttribute("href"));
    }

    /*
    Преверка, что мы нашли верное письмо
    @param element - {@link WebElement}
    @return {@link Boolean}
    */
    private Boolean isTrueMessage(WebElement element) {
        if (!element.getAttribute("data-subject").contains("Пожалуйста, подтвердите вашу регистрацию")) return false;

        WebElement data = element.findElement(By.cssSelector("div.b-datalist__item__panel > div.b-datalist__item__date > span"));
        Locale local = new Locale("ru", "RU");
        String[] russianMonat = {
                "января",
                "февраля",
                "марта",
                "апреля",
                "мая",
                "июня",
                "июля",
                "августа",
                "сентября",
                "октября",
                "ноября",
                "декабря"
        };
        DateFormatSymbols russSymbol = new DateFormatSymbols(local);
        russSymbol.setMonths(russianMonat);
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM", russSymbol);
        Date currentDate = new Date();

        if (!data.getAttribute("title").contains(sdf.format(currentDate))) return false;

        return true;
    }
}
