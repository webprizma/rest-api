package tests;

import com.github.javafaker.Faker;
import config.CustomerConfig;
import org.aeonbits.owner.ConfigFactory;

public class TestData {
    CustomerConfig config = ConfigFactory.create(CustomerConfig.class);
    Faker faker = new Faker();

    final String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            email = config.email(),
            password = config.password(),
            requestVerificationTokenCookie = config.requestVerificationTokenCookie(),
            requestVerificationTokenParam = config.requestVerificationTokenParam(),
            registrationURL = config.registrationURL(),
            loginURL = config.loginURL(),
            authCookieName = config.authCookieName(),
            minimalContent = config.minimalContent(),
            profileURL = config.profileURL(),
            newFirstName = faker.name().firstName(),
            newLastName = faker.name().lastName();

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public  String getEmail() {
        return email;
    }

    public  String getPassword() {
        return password;
    }

    public String getRequestVerificationTokenCookie() {
        return requestVerificationTokenCookie;
    }

    public String getRequestVerificationTokenParam() {
        return requestVerificationTokenParam;
    }

    public String getRegistrationURL() {
        return registrationURL;
    }

    public String getLoginURL() {
        return loginURL;
    }

    public String getAuthCookieName() {
        return authCookieName;
    }

    public String getMinimalContent() {
        return minimalContent;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public String getNewFirstName() {
        return newFirstName;
    }

    public String getNewLastName() {
        return newLastName;
    }
}
