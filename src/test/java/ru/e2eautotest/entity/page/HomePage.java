package ru.e2eautotest.entity.page;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.e2eautotest.entity.CityType;
import ru.e2eautotest.entity.Context;
import ru.e2eautotest.entity.LoggerWrapper;

public class HomePage extends Page{
    private static final LoggerWrapper LOG = LoggerWrapper.get(HomePage.class);

    //города в хидере
    @FindBy(xpath = "/html/body/div[2]/div/div[2]/div/div[3]/div/div[2]/a[2]")
    private WebElement city;

    @FindBy(xpath = "//*[@id=\"header-regions\"]/div/div[1]/div[2]/ul[1]/li[1]/label")
    private WebElement moscow;

    @FindBy(xpath = "//*[@id=\"header-regions\"]/div/div[1]/div[2]/ul[1]/li[2]/label")
    private WebElement volgograd;

    @FindBy(xpath = "//*[@id=\"header-regions\"]/div/div[1]/div[2]/ul[1]/li[3]/label")
    private WebElement ekaterinburg;

    @FindBy(xpath = "//*[@id=\"header-regions\"]/div/div[1]/div[2]/ul[1]/li[4]/label")
    private WebElement kazan;

    @FindBy(xpath = "//*[@id=\"header-regions\"]/div/div[1]/div[2]/ul[1]/li[5]/label")
    private WebElement kalyga;

    @FindBy(xpath = "//*[@id=\"header-regions\"]/div/div[1]/div[2]/ul[2]/li[1]/label")
    private WebElement krasnoyarsk;

    @FindBy(xpath = "//*[@id=\"header-regions\"]/div/div[1]/div[2]/ul[2]/li[2]/label")
    private WebElement nizhnyiNovgorod;

    @FindBy(xpath = "//*[@id=\"header-regions\"]/div/div[1]/div[2]/ul[2]/li[3]/label")
    private WebElement novosibirsk;

    @FindBy(xpath = "//*[@id=\"header-regions\"]/div/div[1]/div[2]/ul[2]/li[4]/label")
    private WebElement perm;

    @FindBy(xpath = "//*[@id=\"header-regions\"]/div/div[1]/div[2]/ul[2]/li[5]/label")
    private WebElement rostovNaDony;

    @FindBy(xpath = "//*[@id=\"header-regions\"]/div/div[1]/div[2]/ul[3]/li[1]/label")
    private WebElement samara;

    @FindBy(xpath = "//*[@id=\"header-regions\"]/div/div[1]/div[2]/ul[3]/li[2]/label")
    private WebElement piter;

    @FindBy(xpath = "//*[@id=\"header-regions\"]/div/div[1]/div[2]/ul[3]/li[3]/label")
    private WebElement stavropol;

    @FindBy(xpath = "//*[@id=\"header-regions\"]/div/div[1]/div[2]/ul[3]/li[4]/label")
    private WebElement tumen;

    @FindBy(xpath = "//*[@id=\"header-regions\"]/div/div[1]/div[2]/ul[3]/li[5]/label")
    private WebElement yfa;

    public static HomePage open(){
        if (!Context.getInstance().getBrowser().getCurrentUrl().
                equals(Context.getInstance().getSiteUrl())) Context.getInstance().getBrowser().
                get(Context.getInstance().getSiteUrl());
        return new HomePage();
    }

    public HomePage() {
        super(Context.getInstance().getBrowser(), "mvideo.ru");
    }

    //Проверка, что система правильно определила регион.
    public HomePage checkRegion(CityType expectedCityType){
        LOG.debug("Проверка, что система правильно определила регион");
        assertEquals(expectedCityType.getCity(), city);
        return this;
    }

    //Выбираем город в хидере
    public HomePage selectCity(CityType cityTypeType){
        LOG.debug(String.format("%s >> %s",city.getText(),cityTypeType.getCity()));
        LOG.debug("Раскрываем окно выбора города");

        if(!city.getText().equals(cityTypeType.getCity())) {
        city.click();
        LOG.debug(String.format("Выбираем город \"%s\"",cityTypeType.getCity()));
        WebElement selectCity;
        switch (cityTypeType){
            case MOSCOW : selectCity = moscow;
                break;
            case VOLGOGRAD : selectCity = volgograd;
                break;
            case EKATERINBURG : selectCity = ekaterinburg;
                break;
            case KAZAN : selectCity = kazan;
                break;
            case KALUGA : selectCity = kalyga;
                break;
            case KRASNOYARSK : selectCity = krasnoyarsk;
                break;
            case NIZHNY_NOVGOROD : selectCity = nizhnyiNovgorod;
                break;
            case NOVOSIBIRSK : selectCity = novosibirsk;
                break;
            case PERM : selectCity = perm;
                break;
            case ROSTOV_NA_DONU : selectCity = rostovNaDony;
                break;
            case SAMARA : selectCity = samara;
                break;
            case ST_PITERSBURG : selectCity = piter;
                break;
            case STAVROPOL : selectCity = stavropol;
                break;
            case TYUMEN : selectCity = tumen;
                break;
            case UFA : selectCity = yfa;
                break;
            default: selectCity = moscow;
        }
            selectCity.click();

            LOG.debug(String.format("Cсылка \"%s\" должна содержать \"s\"",
                    getDriver().getCurrentUrl(),cityTypeType.getCityID()));
            Assert.assertTrue("Не произошел редирект на сайт выбранного региона", getDriver().
                    getCurrentUrl().contains(cityTypeType.getCityID()));
        }
        return this;
    }

    //Выбираем департамент и категорию
    public CategoryPage selectDepartmentAndCategory(String departamentName, String categoryName){
        LOG.debug("Выбираем департамент");
        WebElement departament = getDriver().findElement(By.linkText(departamentName));
        departament.click();

        LOG.debug("Выбираем категорию");
        WebElement category = getDriver().findElement(By.linkText(categoryName));
        String href = category.getAttribute("href");
        category.click();

        return new CategoryPage(getDriver(), href);
    }
}
