package ru.e2eautotest.entity;

import org.openqa.selenium.WebElement;

public class MsgElement {
    // Инициализация логера
    private static final LoggerWrapper LOG = LoggerWrapper.get(MsgElement.class);

    WebElement element;
    String expected;
    String actual;

    public MsgElement(WebElement element, String expected) {
        this.element = element;
        this.expected = expected;
        setActual();
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public void setActual() {
        try {
            LOG.debug(String.format("Веб элемент содержит следующий текст: %s", element.getText()));
            this.actual = element.getText();
        } catch (NullPointerException e) {
            LOG.error("Элемент не содержит текст", e);
        }
    }

    public WebElement getElement() {
        return element;
    }

    public String getExpected() {
        return expected;
    }

    public String getActual() {
        return actual;
    }
}
