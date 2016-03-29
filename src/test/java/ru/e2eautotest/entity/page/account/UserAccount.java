package ru.e2eautotest.entity.page.account;

public class UserAccount implements Account{
    private String name;
    private String mail;
    private String password;
    private String secondName;
    private String mobile;

    public UserAccount(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public UserAccount(String name, String mail, String password, String secondName, String mobile) {
        this(name, mail, password);
        this.secondName = secondName;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getMobile() {
        return mobile;
    }
}
