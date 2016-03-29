package ru.e2eautotest.entity.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.e2eautotest.entity.LoggerWrapper;

import java.util.List;

public class PurchasePage extends Page{
    private static final LoggerWrapper LOG = LoggerWrapper.get(PurchasePage.class);

    //Кнопка "Закончить оформление"
    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div/div[3]/div/div[1]/div/div/div/div/div/form/input[9]")
    private WebElement completeOrderBtn;

    /*
    Кнопки изменить и продолжить
    */

    //Кнопка "изменить" у модуля "Способ получения товара"
    @FindBy(css = "div.nc-delivery-item.clearfix")
    private WebElement editDeliveryBlockBtn;

    //Кнопка "продолжить" у модуля "Способ получения товара"
    @FindBy(xpath = "//*[@id=\"delivery-form\"]/div/div/div[2]/div[2]/input[5]")
    private WebElement closeDeliveryBlockBtn;

    //Кнопка "изменить" у модуля "Личные данные"
    @FindBy(xpath= "//*[@id=\"personal-form\"]/div/div[2]/div/div[3]/a")
    private WebElement editPersonalBlockBtn;

    //Кнопка "продолжить" у модуля "Личные данные"
    @FindBy(xpath = "//*[@id=\"personal-form\"]/div/div[2]/div/div[2]/a")
    private WebElement closePersonalBlockBtn;

    //Кнопка "изменить" у модуля "Cпособ оплаты"
    @FindBy(xpath = "(//a[contains(text(),'Изменить')])[3]")
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
    @FindBy(css = "#personal-form > div > div:nth-child(2) > div > div.nc-order-personal > div > div.control-group.closed > label.label-checkbox > span")
    private WebElement politConfCheckbox;

    /*
    Типы оплаты
    */

    //Оплата при получении

    @FindBy(css = "#payment-form > div > div:nth-child(3) > div.nc-payment > div > div.nc-payment-item.nc-ondelivery-item.clearfix > div.nc-order-details-graphics-holder > label.label-radio > span")
    private WebElement onDeliveryRadio;

    //Онлайн-оплата
    @FindBy(css = "#payment-form > div > div:nth-child(3) > div.nc-payment > div > div.nc-payment-item.nc-card-item.clearfix > div.nc-order-details-graphics-holder > label.label-radio > span")
    private WebElement cardRadio;

    /*
    Блок доставки. Элементы курьерки
    */

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
    @FindBy(xpath = "//*[@id=\"delivery-form\"]/div/div/div[2]/div[1]/div/div[2]/div[4]/div/div/div[1]")
    private WebElement addressEdit;

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
        editDeliveryBlock();

        String expected = String.format("%s, %s, %s, %s", city, street, house, apartament);

        assertEquals(expected, addressEdit);

        closeDeliveryBlock();
        return this;
    }

    //Раскрываем блок "Способ получения товара"
    public PurchasePage openDeliveryBlock(){
        LOG.debug("Раскрываем блок \"Способ получения товара\"");
        try{
            Thread.sleep(10000);
            LOG.debug(editDeliveryBlockBtn.getText());
            scrollToElement(editDeliveryBlockBtn);
            editDeliveryBlockBtn.findElement(By.cssSelector("input[data-form-action=changeDelivery]")).click();
        }catch (Exception e){
            LOG.debug(e.toString());
            System.out.println("zzz");
        }
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
    public PurchasePage editPersonalBlock(String email, String name, String phone){
        LOG.debug("Редактируем блок \"Личные данные\"");

        clearAndSendKey(mailEdit, email);
        clearAndSendKey(nameEdit, name);
        clearAndSendKey(phoneEdit, phone);

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
