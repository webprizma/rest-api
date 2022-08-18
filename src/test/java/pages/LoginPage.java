package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public SelenideElement emailField = $(".email"),
            passwordField = $(".password"),
            loginButton = $(".login-button");

}
