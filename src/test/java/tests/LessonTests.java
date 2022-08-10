package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class LessonTests {

    String authCookieName = "NOPCOMMERCE.AUTH",
            email = "vbdv@feferf.ru",
            password = "itLf7@U@Bf6khGH";

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "http://demowebshop.tricentis.com";
        RestAssured.baseURI = "http://demowebshop.tricentis.com";

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @Test
    void addToNewCartAsAnonymTest() {
        String body = "product_attribute_72_5_18=52" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=57" +
                "&product_attribute_72_8_30=93" +
                "&product_attribute_72_8_30=94" +
                "&addtocart_72.EnteredQuantity=1";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body(body)
                .log().all()
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is(("(1)")));
    }

    @Test
    void addToOldCartAsAnonymTest() {
        /*
        curl 'http://demowebshop.tricentis.com/addproducttocart/details/72/1' \
        -H 'Accept: *\/*' \
        -H 'Accept-Language: en-US,en;q=0.9,ru-RU;q=0.8,ru;q=0.7' \
        -H 'Connection: keep-alive' \
        -H 'Content-Type: application/x-www-form-urlencoded; charset=UTF-8' \
        -H 'Cookie: ARRAffinity=92eb765899e80d8de4d490df907547e5cb10de899e8b754a4d5fa1a7122fad69; __utmc=78382081; __utmz=78382081.1658165585.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utma=78382081.533468626.1658165585.1658165585.1658770752.2; ASP.NET_SessionId=huz4n4vdutpf3ski5chhlw44; Nop.customer=d08317fb-2d29-45c5-86cf-4d497c5a321e; NopCommerce.RecentlyViewedProducts=RecentlyViewedProductIds=72&RecentlyViewedProductIds=31&RecentlyViewedProductIds=2; __atuvc=5%7C30; __atuvs=62ded54a86a2d5df004; __utmb=78382081.12.10.1658770752' \
        -H 'Origin: http://demowebshop.tricentis.com' \
        -H 'Referer: http://demowebshop.tricentis.com/build-your-cheap-own-computer' \
        -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36' \
        -H 'X-Requested-With: XMLHttpRequest' \
        --data-raw 'product_attribute_72_5_18=52&product_attribute_72_6_19=54&product_attribute_72_3_20=57&product_attribute_72_8_30=93&product_attribute_72_8_30=94&addtocart_72.EnteredQuantity=1' \
        --compressed \
        --insecure
         */

        String body = "product_attribute_72_5_18=52" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=57" +
                "&product_attribute_72_8_30=93" +
                "&product_attribute_72_8_30=94" +
                "&addtocart_72.EnteredQuantity=1";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("Nop.customer", "d08317fb-2d29-45c5-86cf-4d497c5a321e;")
                .body(body)
                .log().all()
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
    }

    @Test
    void addToOldCartAsAuthorizedTest() {
//      -H 'Cookie:  Nop.customer=d08317fb-2d29-45c5-86cf-4d497c5a321e; \
//      -H 'Cookie:  NOPCOMMERCE.AUTH=63E5398085346F0233D4A2299C60E9E0710421161177AABC36623E720D917BB24A026CC8DCA04040109FCDCDD3081EF7F9262065FA5581E8F933B5B6F34EC6BFD264D5A4A92168BC5AF1EBF77F52470D22079504CB57DEE018A853E4D54F9C7EA98654CE3975D875710DB36A41A63226C38E81010357E456CAED7292634757B0F42E965F580DF2C930D899551C6B7F82; Nop.customer=bccc3d3b-96f1-4e02-aef5-52bd4ae25d47;' \

        String authCookie = given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("Email", "vbdv@feferf.ru")
                .formParam("Password", "itLf7@U@Bf6khGH")
//                .body("Email=vbdv%40feferf.ru&Password=itLf7%40U%40Bf6khGH&RememberMe=false")
                .log().all()
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(302)
                .extract()
                .cookie("NOPCOMMERCE.AUTH");

        String body = "product_attribute_72_5_18=52" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=57" +
                "&product_attribute_72_8_30=93" +
                "&product_attribute_72_8_30=94" +
                "&addtocart_72.EnteredQuantity=1";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("NOPCOMMERCE.AUTH", authCookie)
                .body(body)
                .log().all()
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
    }

    @Test
    void addToOldCartAsAuthorizedSizeInWebTest() {
        String authCookieName = "NOPCOMMERCE.AUTH";
        String authCookieValue = given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("Email", "vbdv@feferf.ru")
                .formParam("Password", "itLf7@U@Bf6khGH")
                .log().all()
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(302)
                .extract()
                .cookie(authCookieName);

        String body = "product_attribute_72_5_18=52" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=57" +
                "&product_attribute_72_8_30=93" +
                "&product_attribute_72_8_30=94" +
                "&addtocart_72.EnteredQuantity=1";

        String cartSize = given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie(authCookieName, authCookieValue)
                .body(body)
                .log().all()
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .extract()
                .path("updatetopcartsectionhtml");

        open("/Themes/DefaultClean/Content/images/logo.png");

        Cookie authCookie = new Cookie(authCookieName, authCookieValue);
        WebDriverRunner.getWebDriver().manage().addCookie(authCookie);

        open("");
        $(".cart-qty").shouldHave(text(cartSize));
    }

    @Test
    void addToCartWithAllureTest() {
        String authCookieValue = getAuthCookie(email, password);

        String body = "product_attribute_72_5_18=52" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=57" +
                "&product_attribute_72_8_30=93" +
                "&product_attribute_72_8_30=94" +
                "&addtocart_72.EnteredQuantity=1";

        String cartSize = getCartSize(body, authCookieValue);

        step("Open minimal content, because cookie can be set when site is opened", () ->
                open("/Themes/DefaultClean/Content/images/logo.png"));

        step("Set cookie to to browser", () -> {
            Cookie authCookie = new Cookie(authCookieName, authCookieValue);
            WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        });

        step("Open main page", () ->
                open(""));
        step("Check cart size", () ->
                $(".cart-qty").shouldHave(text(cartSize)));
    }

    @Step("Get authorization cookie")
    String getAuthCookie(String email, String password) {
        return given()
//                .filter(new AllureRestAssured())
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("Email", email)
                .formParam("Password", password)
                .log().all()
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(302)
                .extract()
                .cookie(authCookieName);
    }

    @Step("Get cart size")
    String getCartSize(String body, String authCookieValue) {
        return given()
//                .filter(new AllureRestAssured())
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie(authCookieName, authCookieValue)
                .body(body)
                .log().all()
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .extract()
                .path("updatetopcartsectionhtml");
    }
}