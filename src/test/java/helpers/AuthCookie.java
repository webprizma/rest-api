package helpers;

import tests.TestData;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;

public class AuthCookie {
    TestData testData = new TestData();

    public String get() {
        return given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("Email", testData.getEmail())
                .formParam("Password", testData.getPassword())
                .when()
                .post(testData.getLoginURL())
                .then()
                .log().all()
                .statusCode(302)
                .extract()
                .cookie(testData.getAuthCookieName());
    }
}
