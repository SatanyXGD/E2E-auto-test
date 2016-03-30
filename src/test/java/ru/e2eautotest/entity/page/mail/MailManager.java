package ru.e2eautotest.entity.page.mail;

import ru.e2eautotest.entity.Context;
import ru.e2eautotest.entity.LoggerWrapper;
import ru.e2eautotest.entity.page.EmailConfirmationPage;
import ru.e2eautotest.entity.account.Account;

public class MailManager {
    // Инициализация логера
    private static final LoggerWrapper LOG = LoggerWrapper.get(MailManager.class);
    private static MailManager instance;

    private Mail mail;

    private MailManager() {}

    public static void initInstance(String mail){
        LOG.debug("Инифиализация MailManager");
        if(instance == null) {
            instance = new MailManager();
            instance.setMail(mail);
        }
    }

    public static MailManager getInstance() {
        if (instance == null) {
            throw LOG.getIllegalStateException("MailManager is not initialized",
                    new IllegalStateException());
        }
        return instance;
    }


    public Mail getMail() {
        if(mail != null) return mail;

        throw LOG.getIllegalStateException("Mail is not initialized",
                new IllegalStateException());
    }

    public void setMail(String email) {

        if (email.contains("@mail.ru")) {
            Context.getInstance().getBrowser().get("https://mail.ru");
            mail = new MailRuPage(Context.getInstance().getBrowser());
        }
    }
}
