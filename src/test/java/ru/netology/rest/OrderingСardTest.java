package ru.netology.rest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


class OrderingСardTest {

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));

    }

    @Test
    void sendCardAeliveryForm() {

        Selenide.open("http://localhost:9999/");
        SelenideElement formElement = $("form");
        formElement.$("[placeholder='Город']").setValue("Москва");
        formElement.$("[name='name']").setValue("Иванов Иван");
        String expectedDate = generateDate(3, "dd.MM.yyyy");
        formElement.$("[placeholder='Дата встречи']").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        formElement.$("[placeholder='Дата встречи']").setValue(expectedDate);
        formElement.$("[name='phone']").setValue("+79012345678");
        formElement.$("label").click();
        $(withText("Забронировать")).click();
        $(Selectors.withText("Успешно")).should(Condition.visible, Duration.ofSeconds(15));
        $("[class='notification__content']").shouldHave(Condition.text(expectedDate)).shouldBe(Condition.visible);
    }

    @Test
    void interactionWithComplexElements() {
        Selenide.open("http://localhost:9999/");
        SelenideElement formElement = $("form");
        formElement.$("[placeholder='Город']").setValue("Ка");
        $$("[class='popup__content'").find(Condition.text("Калуга")).click();
        formElement.$("[name='name']").setValue("Иванов Иван");
        formElement.$("[placeholder='Дата встречи']").click();
        if (!generateDate(3, "MM").equals(generateDate(7, "MM"))) $("calendar__name").click();
        $$(".calendar__day").findBy(Condition.exactText(generateDate(7, "d"))).click();
        formElement.$("[name='phone']").setValue("+79012345678");
        formElement.$("label").click();
        $(withText("Забронировать")).click();
        $(Selectors.withText("Успешно")).should(Condition.visible, Duration.ofSeconds(15));
        $("[class='notification__content']").shouldHave(Condition.text(generateDate(7, "dd.MM.yyyy"))).shouldBe(Condition.visible);

    }

}