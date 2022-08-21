package tests;

import enums.Paths;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapper;
import models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static specs.APISpecs.*;

public class ReqresInModelsAndSpecsTests {
    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void getListUsersTest() {
        given()
                .spec(logRequestSpec)
                .when()
                .get(Paths.USERS.url + "?page=2")
                .then()
                .spec(logResponseSpec)
                .spec(status200ResponseSpec)
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()", hasItem("byron.fields@reqres.in"))
                .body("data.findAll{it.first_name =~ /\\D+/}.first_name.flatten()", hasItem("Lindsay"));
    }

    @Test
    void postCreateUserTest() {
        UserModel userModel = new UserModel();
        userModel.setName("neo");
        userModel.setJob("clerk");

        UserModel response = given()
                .spec(logRequestSpec)
                .spec(contentTypeJSONRequestSpec)
                .body(userModel)
                .when()
                .post(Paths.USERS.url)
                .then()
                .spec(logResponseSpec)
                .spec(status201ResponseSpec)
                .extract()
                .as(UserModel.class);

        assertThat(response.getJob()).isEqualTo("clerk");
    }

    @Test
    void patchUpdateUserTest() {
        UserModel userModel = new UserModel();
        userModel.setName("neo");
        userModel.setJob("chosen one");

        UserModel response = given()
                .spec(logRequestSpec)
                .spec(contentTypeJSONRequestSpec)
                .body(userModel)
                .when()
                .patch(Paths.USERS.url + "386")
                .then()
                .spec(logResponseSpec)
                .spec(status200ResponseSpec)
                .extract()
                .as(UserModel.class);

        assertThat(response.getJob()).isEqualTo("chosen one");
    }

    @Test
    void postRegisterUserSuccessfulTest() {
        UserCredentialsModel userCredentialsModel = new UserCredentialsModel();
        userCredentialsModel.setEmail("eve.holt@reqres.in");
        userCredentialsModel.setPassword("pistol");

        RegistrationAndLoginModel response = given()
                .spec(logRequestSpec)
                .spec(contentTypeJSONRequestSpec)
                .body(userCredentialsModel)
                .when()
                .post(Paths.REGISTER.url)
                .then()
                .spec(logResponseSpec)
                .spec(status200ResponseSpec)
                .extract()
                .as(RegistrationAndLoginModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    void postRegisterUserUnsuccessfulTest() {
        UserCredentialsModel userCredentialsModel = new UserCredentialsModel();
        userCredentialsModel.setEmail("sydney@fife");

        RegistrationAndLoginModel response = given()
                .spec(logRequestSpec)
                .spec(contentTypeJSONRequestSpec)
                .body(userCredentialsModel)
                .when()
                .post(Paths.REGISTER.url)
                .then()
                .spec(logResponseSpec)
                .spec(status400ResponseSpec)
                .extract()
                .as(RegistrationAndLoginModel.class);

        assertThat(response.getError()).isEqualTo("Missing password");
    }

    @Test
    void postLoginSuccessfulTest() {
        UserCredentialsModel userCredentialsModel = new UserCredentialsModel();
        userCredentialsModel.setEmail("eve.holt@reqres.in");
        userCredentialsModel.setPassword("cityslicka");

        RegistrationAndLoginModel response = given()
                .spec(logRequestSpec)
                .spec(contentTypeJSONRequestSpec)
                .body(userCredentialsModel)
                .when()
                .post(Paths.LOGIN.url)
                .then()
                .spec(logResponseSpec)
                .spec(status200ResponseSpec)
                .extract()
                .as(RegistrationAndLoginModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    void postLoginUnsuccessfulTest() {
        UserCredentialsModel userCredentialsModel = new UserCredentialsModel();
        userCredentialsModel.setEmail("peter@klaven");

        RegistrationAndLoginModel response = given()
                .spec(logRequestSpec)
                .spec(contentTypeJSONRequestSpec)
                .body(userCredentialsModel)
                .when()
                .post(Paths.LOGIN.url)
                .then()
                .spec(logResponseSpec)
                .spec(status400ResponseSpec)
                .extract()
                .as(RegistrationAndLoginModel.class);

        assertThat(response.getError()).isEqualTo("Missing password");
    }
}
