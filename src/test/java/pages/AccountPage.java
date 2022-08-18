package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import tests.TestBase;
import tests.TestData;

import static com.codeborne.selenide.Selenide.$;

public class AccountPage {
    TestData testData = new TestData();

    public SelenideElement firstNameInput = $("#FirstName"),
            lastNameInput = $("#LastName");

    public void open() {
        Selenide.open(testData.profileURL);
    }
}
