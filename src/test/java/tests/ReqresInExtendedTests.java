package tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ReqresInExtendedTests {
    @Test
    void getListUsersTest() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(12));
    }

    @Test
    void getSingleUserTest() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("https://reqres.in/api/users/3")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.email", is("emma.wong@reqres.in"));
    }

    @Test
    void getSingleUserNotFoundTest() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    void postCreateUserTest() {
        String body = "{ \"name\": \"neo\", \"job\": \"clerk\" }";

        given()
                .log().uri()
                .log().body()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("neo"))
                .body("job", is("clerk"));
    }

    @Test
    void patchUpdateUserTest() {
        String body = "{ \"name\": \"neo\", \"job\": \"chosen one\" }";

        given()
                .log().uri()
                .log().body()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .patch("https://reqres.in/api/users/386")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("job", is("chosen one"));
    }

    @Test
    void deleteDeleteUserTest() {
        given()
                .log().uri()
                .log().body()
                .when()
                .delete("https://reqres.in/api/users/386")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }

    @Test
    void postRegisterUserSuccessfulTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .log().uri()
                .log().body()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void postRegisterUserUnsuccessfulTest() {
        String body = "{ \"email\": \"sydney@fife\" }";

        given()
                .log().uri()
                .log().body()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void postLoginSuccessfulTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        given()
                .log().uri()
                .log().body()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void postLoginUnsuccessfulTest() {
        String body = "{ \"email\": \"peter@klaven\" }";

        given()
                .log().uri()
                .log().body()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
