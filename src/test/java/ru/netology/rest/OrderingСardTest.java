package ru.netology.rest;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        $(withText("Успешно")).should(Condition.visible, Duration.ofSeconds(15));
        String actualNotificationContent = $("[class='notification__content']").getText(); //Извлечение текста уведомления:
        assertTrue(actualNotificationContent.contains(expectedDate), "Дата в уведомлении совпадает с ожидаемой."); //сравнение даты из формы и окна

    }

    @Test
    void interactionWithComplexElements() {
        String datePattern = "dd.MM.yyyy"; // Замените на нужный формат, если он другой
        String expectedDateString = generateDate(7, datePattern);

        LocalDate expectedLocalDate = LocalDate.now().plusDays(7);
        Date expectedDate = Date.from(expectedLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        long expectedTimestamp = expectedDate.getTime();

        Selenide.open("http://localhost:9999/");
        SelenideElement formElement = $("form");
        formElement.$("[placeholder='Город']").setValue("Ка");
        $$("body").find(Condition.text("Калуга")).should(Condition.visible, Duration.ofSeconds(15)).$(withText("Калуга")).click();
        formElement.$("[name='name']").setValue("Иванов Иван");


        formElement.$("[placeholder='Дата встречи']").click();
        SelenideElement calendarPopup = $(".popup_direction_bottom-left").shouldBe(Condition.visible, Duration.ofSeconds(15));
        SelenideElement dateElement = calendarPopup.$("[data-day='" + expectedTimestamp + "']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        dateElement.click();
        formElement.$("[name='phone']").setValue("+79012345678");
        formElement.$("label").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно")).should(Condition.visible, Duration.ofSeconds(15));
        String actualNotificationContent = $("[class='notification__content']").getText(); //Извлечение текста уведомления:
        assertTrue(actualNotificationContent.contains(expectedDateString), "Дата в уведомлении совпадает с ожидаемой.");

    }

}
