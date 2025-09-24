package tests;

import com.codeborne.pdftest.PDF;
import data.MenuItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;
import static com.codeborne.selenide.CollectionCondition.containExactTextsCaseSensitive;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class MasterChainPageTests extends TestBase {

    @DisplayName("Переход на главную страницу при клике на лого сайта")
    @Test
    @Tag("simple")
    void logoLinkToMainPageTest() {

        step("Открываем главную страницу Master Chain", () -> {
            open("https://www.dltru.org/");
        });
        step("Ищем кнопку Мастерчейн, нажимаем", () -> {
            $("div[class='main__links hide_lg aos-init aos-animate']")
                    .find(byText("Мастерчейн")).click();
        });
        step("Ищем логотип, нажимаем", () -> {
            $("img[class='hide_sm']")
                    .click();
        });
        step("Проверяем, что заголовок сайта соответствует ожидаемому", () -> {
            $("h1[class='article_title aos-init aos-animate']")
                    .shouldHave(text("МАСТЕРЧЕЙН — ТЕХНОЛОГИЯ НОВОЙ ЭКОНОМИКИ"));
        });
    }

    @DisplayName("Страница 'Контакты' содержит ожидаемый адрес, телефон и email")
    @Test
    @Tag("simple")
    void contactPageContentTest() {

        step("Открываем главную страницу Master Chain", () -> {
            open("https://www.dltru.org/");
        });
        step("Переходим на страницу Контакты", () -> {
            $("nav[class='header__navbar hide_lg']")
                    .find(byText("Контакты")).click();
        });
        step("Проверка адреса", () -> {
            $("div[class='contact_item aos-init aos-animate']")
                    .shouldHave(text("119435, г. Москва, ул Малая Пироговская, дом 14 строение 5"));
        });
        step("Проверка номера телефона", () -> {
            $("div[class='contact_item aos-init aos-animate']")
                    .shouldHave(text("+7(495)120-75-42"));
        });
        step("Прорверка email", () -> {
            $("div[class='contact_item aos-init aos-animate']")
                    .shouldHave(text("info@masterchain.ru"));
        });
    }

    @DisplayName("Ссылка 'Устав' скачивает устав компании")
    @Test
    @Tag("simple")
    void pdfOrganisationChartTest() throws Exception {

        step("Открываем главную страницу Master Chain", () -> {
            open("https://www.dltru.org/");
        });
        step("Скачиваем Устав и проверяем, что в нем есть слово 'устав'", () -> {
            File downloadedFile = $("a[href='/local/assets/docs/Устав.pdf']").download();
            PDF chartContent = new PDF(downloadedFile);
            assertThat(chartContent).containsExactText("Устав");
        });
    }

    static Stream<Arguments> mainMenuContentTest() {
        return Stream.of(
                Arguments.of(MenuItems.SERVICES, List.of("Консалтинг", "IT-аудит", "Разработка", "Интеграция", "Тех.поддержка")),
                Arguments.of(MenuItems.SOLUTIONS, List.of("Все отрасли", "Культура и искусство", "Наука", "Здравоохранение",
                        "Государственное управление", "Торговля", "Логистика", "Строительство"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Меню {0} содержит ожидаемый список пунктов {1}")
    @Tag("menu")
    void mainMenuContentTest(
            MenuItems item,
            List<String> line
    ) {

        step("Открываем главную страницу Master Chain", () -> {
            open("https://www.dltru.org/");
        });
        step("Выбираем заданный пункт меню и переходим в него", () -> {
            $("nav[class='header__navbar hide_lg']")
                    .find(byText(item.toString())).click();
        });
        step("Проверяем, что на страниеце есть заданный список элементов", () -> {
            $$(".tabs div")
                    .shouldHave(containExactTextsCaseSensitive(line));
        });
    }

    @Test
    @DisplayName("Ссылка главного меню загружает нужную страницу")
    @Tag("menu")
    void mainMenuLinkTest() {
        String menuItem = System.getProperty("menuItem","Цифровые финансовые активы");;
        step("Открываем главную страницу Master Chain", () -> {
            open("https://www.dltru.org/");
        });
        step("Выбираем заданный пункт меню", () -> {
            $(".main__links.hide_lg.aos-init.aos-animate")
                    .find(byText(menuItem)).click();
        });
        step("Проверяем, что заголовок сайта соответствует ожидаемому", () -> {
            $(".main__col:not(.aos-init)")
                    .shouldHave(text(menuItem));
        });
    }
}