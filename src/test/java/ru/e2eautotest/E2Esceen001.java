package ru.e2eautotest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.e2eautotest.entity.*;
import ru.e2eautotest.entity.page.*;
import ru.e2eautotest.entity.account.UserAccount;
import ru.e2eautotest.entity.page.mail.MailManager;

public class E2Esceen001 {
    private static final LoggerWrapper LOG = LoggerWrapper.get(E2Esceen001.class);

    private CityType cityType1;
    private String departament;
    private String category;
    private String storeAddress;
    private UserAccount user;
    private static DeliveryAddress address;

    @Before
    public void setUp() {
        LOG.debug("Подготовка к тесту");
        //Регион
        cityType1 = CityType.MOSCOW;
        //Департамент и категория товара
        departament = "Ноутбуки, планшеты и компьютеры";
        category = "Электронные книги";
        //Адрес магазина, где проверяем отсутствие стоков --> "На складе"
        storeAddress = "ст. м. «Бульвар Дмитрия Донского»";
        //Адрес доставки
        address = new DeliveryAddress("Москва", "Улица", "Дом", "Кв");
        //Пользователь
        user = new UserAccount("Уhgraasr", "teste2e407@mail.ru", "8kjkszpj", "", "9045854946");
        Context.initInstance(BrowserType.FF, "https://www.atguat6.mvideo.ru");
    }

    @Test
    public void test(){
        LOG.debug("НАЧАЛО ТЕСТА");
        //Проверка регионов

        HomePage.open().selectCity(cityType1).checkRegion(cityType1);
        //проверка покупки и регистрации
        HomePage.open().selectDepartmentAndCategory(departament, category).
                selectProduct().checkDescription().addToBasket(). //openStoreListAndFindStoreAndAddProduct(storeAddress).
                checkoutOrderForGuest().checkoutWithoutRegister().
                editDeliveryBlock().
                setCourierDelivery(address).closeDeliveryBlock().
                checkCourierAddress(address).
                editPersonalBlock(user).closePersonalBlock().
                editPaymentBlock().closePaymentBlock().
                completeOrder().checkOrderSummary().register(user.getPassword()).
                checkedMsgfirstAutoLoginAfterRegistration(user.getName()).
                checkedMsgInNotVerifiedProfile(user.getName());

        //Подтверждение регистрации

        MailManager mailManager = new MailManager();
        mailManager.setMail(user.getMail());
        mailManager.getMail().login(user).findOrderMail();
        mailManager.verifityMail(user).verificationMail(user.getName());
        mailManager.getMail().logOut();
        mailManager.verifityMail(user).secondTryVerificationMail();

        LoginPage.open().relogin(user).
                checkedMsgInMyProfileAfterVerification(user.getName());
    }

    @After
    public void tearDown() {
        LOG.debug("Очистка после теста");
        Context.getInstance().close();
    }
}
