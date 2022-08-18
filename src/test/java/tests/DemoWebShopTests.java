package tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
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
    void registrationTest() {
        step("Регистрируем пользователя через API", () -> {
            given()
                    .filter(withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                    .cookie("__RequestVerificationToken", testData.registrationRequestVerificationTokenCookie)
                    .param("__RequestVerificationToken", testData.registrationRequestVerificationTokenParam)
                    .param("FirstName", testData.firstName)
                    .param("LastName", testData.lastName)
                    .param("Email", testData.email)
                    .param("Password", testData.password)
                    .param("ConfirmPassword", testData.password)
                    .log().params()
                    .when()
                    .post(testData.registrationURL)
                    .then()
                    .log().status()
                    .statusCode(302);
        });

        step("Авторизуем пользователя через UI", () -> {
            mainPage.open();
            mainPage.logIn.click();
            loginPage.emailField.setValue(testData.email);
            loginPage.passwordField.setValue(testData.password);
            loginPage.loginButton.click();
        });

        step("Проверяем авторизацию через UI", () ->
                mainPage.account.shouldHave(text(testData.email)));
    }

    @Test
    void profileEditTest() {
        step("Редактируем пользователя через API", () -> {
            given()
                    .filter(withCustomTemplates())
                    .cookie(testData.authCookieName, getAuthCookie(config.email(), config.password()))
                    .cookie("__RequestVerificationToken", testData.profileRequestVerificationTokenCookie)
                    .formParam("__RequestVerificationToken", testData.profileRequestVerificationTokenParam)
                    .formParam("FirstName", testData.newFirstName)
                    .formParam("LastName", testData.newLastName)
                    .formParam("Email", config.email())
                    .log().all()
                    .when()
                    .post(testData.profileURL)
                    .then()
                    .log().all();
        });

        step("Авторизуем пользователя через API и получаем cookie", () -> {
            open(testData.minimalContent);
            WebDriverRunner.getWebDriver().manage().addCookie(new Cookie(testData.authCookieName, getAuthCookie(config.email(), config.password())));
        });

        step("Проверяем изменения через UI", () -> {
            accountPage.open();
            accountPage.firstNameInput.shouldHave(value(testData.newFirstName));
            accountPage.lastNameInput.shouldHave(value(testData.newLastName));
        });
    }

    @Step("Get authorization cookie")
    String getAuthCookie(String email, String password) {
        return given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("Email", email)
                .formParam("Password", password)
                .log().all()
                .when()
                .post(testData.loginURL)
                .then()
                .log().all()
                .statusCode(302)
                .extract()
                .cookie(testData.authCookieName);
    }
}