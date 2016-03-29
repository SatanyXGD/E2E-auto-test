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
    private static String url = "https://www.atguat4.mvideo.ru/";
    private CityType cityType1;
    private CityType cityType2;
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
        cityType2 = CityType.KAZAN;

        departament = "Ноутбуки, планшеты и компьютеры";
        category = "/elektronnye-knigi-i-aksessuary/elektronnye-knigi-3935";
        storeAddress = "ст. м. «Бульвар Дмитрия Донского»";
        user = new UserAccount("Test", "teste2e340@mail.ru", "8kjkszpj");
    }

    @Test
    public void test(){
        LOG.debug("НАЧАЛО ТЕСТА");
        driver.get(url);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

        //new HomePage(driver).checkRegion(cityType1).selectCity(cityType2).checkRegion(cityType2).selectDepartament(departament);

        driver.get(url + category);

        MyProfilePage page = new CategoryPage(driver, category).
                selectProduct().openStoreListAndFindStoreAndAddProduct(storeAddress).
                checkoutOrderForGuest().checkoutWithoutRegister().
                editDeliveryBlock().setCourierDelivery("Москва", "Улица", "Дом", "Кв").closeDeliveryBlock().
                editPersonalBlock(user.getMail(), user.getName(), "9046954641").closePersonalBlock().
                editPaymentBlock().closePaymentBlock().completeOrder().register(user.getPassword()).
                checkedMsgfirstAutoLoginAfterRegistration(user.getName()).checkedMsgInNotVerifiedProfile(user.getName());

        MailManager mailManager = new MailManager();
        mailManager.setMail(user.getMail(), driver);
        mailManager.verifityMail(user).verificationMail(user.getName());
        mailManager.getMail().logOut();
        mailManager.verifityMail(user).secondTryVerificationMail();

        new LoginPage(driver).relogin(user).
                checkedMsgInMyProfileAfterVerification(user.getName());
    }

    @After
    public void tearDown() {
        LOG.debug("Очистка после теста");
        driver.quit();
    }
}
