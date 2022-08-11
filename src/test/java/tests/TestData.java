package tests;

import com.github.javafaker.Faker;
import config.CustomerConfig;
import org.aeonbits.owner.ConfigFactory;

public class TestData {
    CustomerConfig config = ConfigFactory.create(CustomerConfig.class);
    Faker faker = new Faker();
    String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            email = faker.internet().emailAddress(),
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
}
