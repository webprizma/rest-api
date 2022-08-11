package tests;

import com.codeborne.selenide.WebDriverRunner;
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
    void registerUser() {
        step("Регистрируем пользователя через API", () -> {
            given()
                    .filter(withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                    .cookie("__RequestVerificationToken", testData.requestVerificationTokenCookie)
                    .formParam("__RequestVerificationToken", testData.requestVerificationTokenParam)
                    .formParam("FirstName", testData.firstName)
                    .formParam("LastName", testData.lastName)
                    .formParam("Email", testData.email)
                    .formParam("Password", testData.password)
                    .formParam("ConfirmPassword", testData.password)
                    .when()
                    .post(testData.registrationURL)
                    .then()
                    .statusCode(302);
        });

        step("Авторизуем пользователя через API и получаем cookie", () -> {
            step("Проверяем авторизацию через UI", () -> {
                String authCookieValue = given()
                        .filter(withCustomTemplates())
                        .log().all()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .formParam("Email", testData.email)
                        .formParam("Password", testData.password)
                        .when()
                        .post(testData.loginURL)
                        .then()
                        .log().all()
                        .statusCode(302)
                        .extract()
                        .cookie(testData.authCookieName);
                open(testData.minimalContent);
                WebDriverRunner.getWebDriver().manage().addCookie(new Cookie(testData.authCookieName, authCookieValue));
                open("");
                mainPage.accountField.shouldHave(text(testData.email));


                step("Редактируем профиль пользователя", () -> {
                    given()
                            .filter(withCustomTemplates())
                            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                            .cookie(testData.authCookieName, authCookieValue)
                            .cookie("__RequestVerificationToken", testData.requestVerificationTokenCookie)
                            .formParam("__RequestVerificationToken", testData.requestVerificationTokenParam)
                            .formParam("FirstName", testData.newFirstName)
                            .formParam("LastName", testData.newLastName)
                            .formParam("Email", testData.email)
                            .when()
                            .post(testData.profileURL)
                            .then();

                    step("Проверяем изменения через UI", () -> {
                        open(testData.profileURL);
                        accountPage.firstNameInput.shouldHave(value(testData.newFirstName));
                        accountPage.lastNameInput.shouldHave(value(testData.newLastName));
                    });
                });
            });
        });
    }
}