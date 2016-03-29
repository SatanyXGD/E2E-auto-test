package ru.e2eautotest.entity.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.e2eautotest.entity.LoggerWrapper;
import ru.e2eautotest.entity.account.UserAccount;

public class PurchasePage extends Page{
    private static final LoggerWrapper LOG = LoggerWrapper.get(PurchasePage.class);

    //Кнопка "Закончить оформление"
    @FindBy(css = "input.btn.btn-fluid")
    private WebElement completeOrderBtn;

    /*
    Кнопки изменить и продолжить
    */
    //Кнопка "изменить" у модуля "Способ получения товара"
    @FindBy(xpath = "//a[text()=\"Изменить\"][1]")
    private WebElement editDeliveryBlockBtn;

    //Кнопка "продолжить" у модуля "Способ получения товара"
    @FindBy(css = "div[data-id=deliveryBlocks] > div.nc-step-footer > input.btn.btn-primary.btn-nc-next.js-nc-next")
    private WebElement closeDeliveryBlockBtn;

    //Кнопка "изменить" у модуля "Личные данные"
    // //div[contains(@class, 'nc-personal-item clearfix')]/div[3]/a
    @FindBy(xpath= "/html/body/div[1]/div/div[2]/div/div/div[3]/div/div[2]/div[5]/div/form/div/div[2]/div/div[3]/a")
    private WebElement editPersonalBlockBtn;

    //Кнопка "продолжить" у модуля "Личные данные"
    @FindBy(xpath = "//*[@id=\"personal-form\"]/div/div[2]/div/div[2]/a")
    private WebElement closePersonalBlockBtn;

    //Кнопка "изменить" у модуля "Cпособ оплаты"
    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div/div[3]/div/div[2]/div[6]/div/form/div/div[2]/div/div/div[3]/a")
    private WebElement editPaymentBlockBtn;

    //Кнопка "продолжить" у модуля "Cпособ оплаты"
    @FindBy(xpath = "//*[@id=\"payment-form\"]/div/div[2]/div[2]/a")
    private WebElement closePaymentBlockBtn;

    //Кнопка "Заказать без регистрации
    @FindBy(xpath = "//*[@id=\"purchaseGuestForm\"]/input[7]")
    private WebElement checkOutWithoutRegisterButton;

    /*
    Блок личные данные
    */
    //Поле "email"
    @FindBy(id = "email")
    private WebElement mailEdit;

    //Поле "Имя"
    @FindBy(id = "name")
    private WebElement nameEdit;

    //Поле "Телефон"
    @FindBy(id = "phone")
    private WebElement phoneEdit;

    //Чекбокс "Подтверждение политики конф"
    @FindBy(css = "label.label-checkbox")
    private WebElement politConfCheckbox;

    /*
    Типы оплаты
    */

    //Оплата при получении
    //@FindBy(id = "checkout-payment-ondelivery")
    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div/div[3]/div/div[2]/div[6]/div/form/div/div[2]/div[1]/div/div[2]/div[1]/label[1]/span")
    private WebElement onDeliveryRadio;

    //Онлайн-оплата
    //@FindBy(id = "checkout-payment-card")
    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div/div[3]/div/div[2]/div[6]/div/form/div/div[2]/div[1]/div/div[3]/div[1]/label[1]/span")
    private WebElement cardRadio;

    /*
    Блок доставки. Элементы курьерки
    */

    //Пикап
    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div/div[3]/div/div[2]/div[4]/div[1]/form/div/div/div[2]/div[1]/div/div[1]/div[1]/label[1]/span")
    private WebElement pickupRadio;

    //Доставка курьером
    @FindBy(xpath = "//*[@id=\"delivery-form\"]/div/div/div[2]/div[1]/div/div[2]/div[1]/label[1]/span")
    private WebElement courierRadio;

    //Поле "Город"
    @FindBy(id = "frm-city")
    private WebElement cityEdit;

    //Поле "Улица"
    @FindBy(id = "frm-street")
    private WebElement streetEdit;

    //Поле "Дом"
    @FindBy(id = "frm-building")
    private WebElement houseEdit;

    //Поле "Квартира"
    @FindBy(id = "frm-apartment")
    private WebElement apartmentEdit;

    //Поле "Адресс доставки"
    @FindBy(css = "div.nc-delivery-additional-info > p.nc-offer-delivery-date")
    private WebElement courierAddress;

    //Кнопка "Далее"
    @FindBy(xpath = "//*[@id=\"delivery-form\"]/div/div/div[2]/div[1]/div/div[2]/div[3]/div[5]/a")
    private WebElement proceedBtn;


    public PurchasePage(WebDriver driver) {
        super(driver, "purchase");
    }

    //Заказать без регистрации
    public PurchasePage checkoutWithoutRegister()
    {
        LOG.debug("Заказать без регистрации");
        scrollToElement(checkOutWithoutRegisterButton);
        checkOutWithoutRegisterButton.click();

        return this;
    }

    //Выбрать пикап
    public PurchasePage setPickUpDelivery(String storeName){
        LOG.debug("Выбираем пикап");
        pickupRadio.click();

        //тут добавить магазин

        return this;
    }

    //Выбрать доставку курьером
    public PurchasePage setCourierDelivery(String city, String street, String house, String apartament){
        LOG.debug("Выбираем доставку курьером");
        courierRadio.click();
       // clearAndSendKey(cityEdit, city);
        clearAndSendKey(streetEdit, street);
        clearAndSendKey(houseEdit, house);
        clearAndSendKey(apartmentEdit, apartament);
        proceedBtn.click();

        return this;
    }

    //Провериить, что система запомнила адресс доставки и время
    public PurchasePage checkCourierAddress(String city, String street, String house, String apartament){
        LOG.debug("Проверяем возможность системы запомнить адресс доставки и время");

        String expected = String.format("%s, %s, %s, %s", city, street, house, apartament);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(expected, courierAddress);

        return this;
    }

    //Раскрываем блок "Способ получения товара"
    public PurchasePage openDeliveryBlock(){
        LOG.debug("Раскрываем блок \"Способ получения товара\"");
        try{
            scrollToElement(editDeliveryBlockBtn);
            editDeliveryBlockBtn.click();
        }catch (Exception e){}
        return this;
    }

    //Редактируем блок "Способ получения товара"
    public PurchasePage editDeliveryBlock(){
        LOG.debug("Редактируем блок \"Способ получения товара\"");

        return this;
    }

    //Закрываем блок "Способ получения товара"
    public PurchasePage closeDeliveryBlock(){
        LOG.debug("Закрываем блок \"Способ получения товара\"");
        scrollToElement(closeDeliveryBlockBtn);
        closeDeliveryBlockBtn.click();

        return this;
    }

    //Раскрываем блок "Личные данные"
    public PurchasePage openPersonalBlock(){
        LOG.debug("Раскрываем блок \"Личные данные\"");
        try{
            scrollToElement(editPersonalBlockBtn);
            editPersonalBlockBtn.click();
        }catch (Exception e){}

        return this;
    }

    //Редактируем блок "Личные данные"
    public PurchasePage editPersonalBlock(UserAccount user){
        LOG.debug("Редактируем блок \"Личные данные\"");

        clearAndSendKey(mailEdit, user.getMail());
        clearAndSendKey(nameEdit, user.getName());
        clearAndSendKey(phoneEdit, user.getMobile());

        scrollToElement(politConfCheckbox);
        politConfCheckbox.click();

        return this;
    }

    //Закрываем блок "Личные данные"
    public PurchasePage closePersonalBlock(){
        LOG.debug("Закрываем блок \"Личные данные\"");
        scrollToElement(closePersonalBlockBtn);
        closePersonalBlockBtn.click();

        return this;
    }

    //Раскрываем блок "Cпособ оплаты"
    public PurchasePage openPaymentBlock(){
        LOG.debug("Раскрываем блок \"Cпособ оплаты\"");
        try{
            scrollToElement(editPaymentBlockBtn);
            editPaymentBlockBtn.click();
        }catch (Exception e){}

        return this;
    }

    //Редактируем блок "Cпособ оплаты"
    public PurchasePage editPaymentBlock(){
        LOG.debug("Редактируем блок \"Cпособ оплаты\"");
        openPaymentBlock();

        /*scrollToElement(cardRadio);
        cardRadio.click();
        scrollToElement(onDeliveryRadio);
        onDeliveryRadio.click();*/

        return this;
    }

    //Закрываем блок "Cпособ оплаты"
    public PurchasePage closePaymentBlock(){
        LOG.debug("Закрываем блок \"Способ оплаты\"");
        scrollToElement(closePaymentBlockBtn);
        closePaymentBlockBtn.click();

        return this;
    }

    //Заканчиваем оформление
    public ConfirmationPage completeOrder(){
        LOG.debug("Заканчиваем оформление");
        scrollToElement(completeOrderBtn);
        completeOrderBtn.click();

        return new ConfirmationPage(getDriver());
    }
}
