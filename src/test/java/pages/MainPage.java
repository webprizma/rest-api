package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    public SelenideElement logIn = $(".ico-login"),
            account = $(".account");

    public void open() {
        Selenide.open("/");
    }
}
