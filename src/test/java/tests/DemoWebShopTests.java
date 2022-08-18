package tests;

import com.codeborne.selenide.WebDriverRunner;
import helpers.AuthCookie;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.open;
import static helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class DemoWebShopTests extends TestBase {
    @Test
    void registerAndAuthUser() {
        step("Регистрируем пользователя через API", () -> {
            given()
                    .filter(withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                    .cookie("__RequestVerificationToken", testData.getRequestVerificationTokenCookie())
                    .param("__RequestVerificationToken", testData.getRequestVerificationTokenParam())
                    .param("FirstName", testData.getFirstName())
                    .param("LastName", testData.getLastName())
                    .param("Email", testData.getEmail())
                    .param("Password", testData.getPassword())
                    .param("ConfirmPassword", testData.getPassword())
                    .log().params()
                    .when()
                    .post(testData.getRegistrationURL())
                    .then()
                    .log().cookies()
                    .log().status()
                    .statusCode(302);
        });

        AuthCookie authCookie = new AuthCookie();
        String authCookieValue = authCookie.get();

        step("Авторизуем пользователя через API и получаем cookie", () -> {
            open(testData.getMinimalContent());
            WebDriverRunner.getWebDriver().manage().addCookie(new Cookie(testData.getAuthCookieName(), authCookieValue));
        });

        step("Проверяем авторизацию через UI", () -> {
            open("");
            mainPage.accountField.shouldHave(text(testData.getEmail()));
        });

        //todo не работает замена
        step("Редактируем профиль пользователя", () -> {
            given()
                    .filter(withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                    .cookie(testData.getAuthCookieName(), authCookieValue)
                    .cookie("__RequestVerificationToken", testData.getRequestVerificationTokenCookie())
                    .formParam("__RequestVerificationToken", testData.getRequestVerificationTokenParam())
                    .formParam("FirstName", testData.getNewFirstName())
                    .formParam("LastName", testData.getNewLastName())
                    .formParam("Email", testData.getEmail())
                    .log().all()
                    .when()
                    .post(testData.getProfileURL())
                    .then()
                    .log().all();
        });

        step("Проверяем изменения через UI", () -> {
            open(testData.getProfileURL());
            accountPage.firstNameInput.shouldHave(value(testData.getNewFirstName()));
            accountPage.lastNameInput.shouldHave(value(testData.getNewLastName()));
        });
    }
}