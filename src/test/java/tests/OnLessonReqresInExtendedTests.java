package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.lombok.LoginBodyLombokModel;
import models.lombok.LoginResponseLombokModel;
import models.pojo.LoginBodyPojoModel;
import models.pojo.LoginResponsePojoModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpecs.loginRequestSpec;
import static specs.LoginSpecs.loginResponseSpec;

public class OnLessonReqresInExtendedTests {
    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void postLoginSuccessfulTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void postLoginWithPojoModelSuccessfulTest() {
        LoginBodyPojoModel loginBodyPojoModel = new LoginBodyPojoModel();
        loginBodyPojoModel.setEmail("eve.holt@reqres.in");
        loginBodyPojoModel.setPassword("cityslicka");

        LoginResponsePojoModel response = given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .contentType(ContentType.JSON)
                .body(loginBodyPojoModel)
                .when()
                .post("/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponsePojoModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void postLoginWithLombokModelSuccessfulTest() {
        LoginBodyLombokModel loginBodyLombokModel = new LoginBodyLombokModel();
        loginBodyLombokModel.setEmail("eve.holt@reqres.in");
        loginBodyLombokModel.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .contentType(ContentType.JSON)
                .body(loginBodyLombokModel)
                .when()
                .post("/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);
        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    void postLoginWithSpecsSuccessfulTest() {
        LoginBodyLombokModel loginBodyLombokModel = new LoginBodyLombokModel();
        loginBodyLombokModel.setEmail("eve.holt@reqres.in");
        loginBodyLombokModel.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .spec(loginRequestSpec)
                .body(loginBodyLombokModel)
                .when()
                .post()
                .then()
                .spec(loginResponseSpec)
                .extract().as(LoginResponseLombokModel.class);
        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }
}
