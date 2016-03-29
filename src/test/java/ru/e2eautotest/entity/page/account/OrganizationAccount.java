package ru.e2eautotest.entity.page.account;

public class OrganizationAccount implements Account{
    private String name;
    private String contact;
    private String email;
    private String phone;
    private String address;
    private String bank;
    private String settlement;
    private String cor;
    private String inn;
    private String kpp;
    private String bic;
    private String okdp;
    private String okpo;
    private String okonxOkved;
    private String site;
    private String password;

    public OrganizationAccount(String name, String contact, String email, String phone,
                               String address, String bank, String settlement, String cor,
                               String inn, String kpp, String bic, String okdp, String okpo,
                               String okonxOkved, String site, String password) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bank = bank;
        this.settlement = settlement;
        this.cor = cor;
        this.inn = inn;
        this.kpp = kpp;
        this.bic = bic;
        this.okdp = okdp;
        this.okpo = okpo;
        this.okonxOkved = okonxOkved;
        this.site = site;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getMail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getBank() {
        return bank;
    }

    public String getSettlement() {
        return settlement;
    }

    public String getCor() {
        return cor;
    }

    public String getInn() {
        return inn;
    }

    public String getKpp() {
        return kpp;
    }

    public String getBic() {
        return bic;
    }

    public String getOkdp() {
        return okdp;
    }

    public String getOkpo() {
        return okpo;
    }

    public String getOkonxOkved() {
        return okonxOkved;
    }

    public String getSite() {
        return site;
    }

    public String getPassword() {
        return password;
    }
}
