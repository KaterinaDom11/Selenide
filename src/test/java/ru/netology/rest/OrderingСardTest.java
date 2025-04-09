package ru.netology.rest;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;

class OrderingСardTest {

    public String generateDate(int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));

    }

    @Test
    void sendCardAeliveryForm () {
        String planningDate = generateDate(3, "dd.MM.yyyy");

        Selenide.open("http://localhost:9999/");
        SelenideElement formElement = $("form");
        formElement.$("[placeholder='Город']").setValue("Москва");
        formElement.$("[name='name']").setValue("Иванов Иван");
        formElement.$("[name='phone']").setValue("+7920015698");
        formElement.$("label").click();
        //formElement.$("button").click();
    }
}
