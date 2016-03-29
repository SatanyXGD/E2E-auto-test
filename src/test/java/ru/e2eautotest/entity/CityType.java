package ru.e2eautotest.entity;

public enum CityType {
    MOSCOW("Москва", "CityCZ_975"),
    VOLGOGRAD ("Волгоград", "CityCZ_1272"),
    EKATERINBURG ("Екатеринбург", "CityCZ_2030"),
    KAZAN("Казань", "CityCZ_1458"),
    KALUGA("Калуга", "CityCZ_106"),
    KRASNOYARSK("Красноярск", "CityCZ_1854"),
    NIZHNY_NOVGOROD("Нижний Новгород", "CityCZ_974"),
    NOVOSIBIRSK("Новосибирск", "CityCZ_2246"),
    PERM("Пермь", "CityCZ_1250"),
    ROSTOV_NA_DONU("Ростов-на-дону", "CityCZ_2446"),
    SAMARA("Самара", "CityCZ_1780"),
    ST_PITERSBURG("Санкт-Петербург", "CityCZ_1638"),
    STAVROPOL("Ставрополь", "CityCZ_1370"),
    TYUMEN("Тюмень", "CityCZ_1024"),
    UFA("Уфа", "CityCZ_2534");

    private final String city;
    private final String cityID;

    CityType(String city, String cityID) {
        this.city = city;
        this.cityID = cityID;
    }

    public String getCity() {
        return city;
    }

    public String getCityID() {
        return cityID;
    }
}
