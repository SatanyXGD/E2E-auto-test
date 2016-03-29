package ru.e2eautotest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.e2eautotest.entity.LoggerWrapper;
import ru.e2eautotest.entity.page.*;
import ru.e2eautotest.entity.page.account.UserAccount;
import ru.e2eautotest.entity.page.mail.MailManager;

import java.util.concurrent.TimeUnit;

public class E2Esceen001 {
    private static final LoggerWrapper LOG = LoggerWrapper.get(E2Esceen001.class);

    public static WebDriver driver;
    private static String url = "https://www.atguat6.mvideo.ru/";
    private CityType cityType1;
    private String departament;
    private String category;
    private String storeAddress;
    private UserAccount user;


    @Before
    public void setUp() {
        LOG.debug("Подготовка к тесту");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

        cityType1 = CityType.MOSCOW;

        departament = "Ноутбуки, планшеты и компьютеры";
        category = "Электронные книги";
        storeAddress = "ст. м. «Бульвар Дмитрия Донского»";
        user = new UserAccount("Уhgraasr", "teste2e407@mail.ru", "8kjkszpj", "", "9045854946");
    }

    @Test
    public void test(){
        LOG.debug("НАЧАЛО ТЕСТА");
        driver.get(url);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        //Проверка регионов

        new HomePage(driver).selectCity(cityType1).checkRegion(cityType1);
        //проверка покупки и регистрации
        new HomePage(driver).selectDepartmentAndCategory(departament, category).
                selectProduct().addToBasket(). //openStoreListAndFindStoreAndAddProduct(storeAddress).
                checkoutOrderForGuest().checkoutWithoutRegister().
                editDeliveryBlock().
                setCourierDelivery("Москва", "Улица", "Дом", "Кв").
                closeDeliveryBlock().
                editPersonalBlock(user.getMail(), user.getName(), user.getMobile()).closePersonalBlock().
                //openDeliveryBlock().openPersonalBlock();
                editPaymentBlock().
                closePaymentBlock().completeOrder().checkOrderSummary().register(user.getPassword()).
                checkedMsgfirstAutoLoginAfterRegistration(user.getName()).
                checkedMsgInNotVerifiedProfile(user.getName());
        //Подтверждение регистрации

        MailManager mailManager = new MailManager();
        mailManager.setMail(user.getMail(), driver);
        mailManager.getMail().login(user).findOrderMail();
        mailManager.verifityMail(user).verificationMail(user.getName());
        mailManager.getMail().logOut();
        mailManager.verifityMail(user).secondTryVerificationMail();

        driver.get(url + "/login");

        new LoginPage(driver).relogin(user).
                checkedMsgInMyProfileAfterVerification(user.getName());
    }

    @After
    public void tearDown() {
        LOG.debug("Очистка после теста");
        driver.quit();
    }
}
