package tests;

import com.github.javafaker.Faker;
import config.CustomerConfig;
import org.aeonbits.owner.ConfigFactory;

public class TestData {
    CustomerConfig config = ConfigFactory.create(CustomerConfig.class);
    Faker faker = new Faker();
    public String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            newFirstName = faker.name().firstName(),
            newLastName = faker.name().lastName(),
            email = faker.internet().emailAddress(),
            password = faker.internet().password(6, 12, true, true, true),
            registrationRequestVerificationTokenCookie = config.registrationRequestVerificationTokenCookie(),
            registrationRequestVerificationTokenParam = config.registrationRequestVerificationTokenParam(),
            profileRequestVerificationTokenCookie = config.profileRequestVerificationTokenCookie(),
            profileRequestVerificationTokenParam = config.profileRequestVerificationTokenParam(),
            authCookieName = config.authCookieName(),
            minimalContent = config.minimalContent(),
            registrationURL = config.registrationURL(),
            loginURL = config.loginURL(),
            profileURL = config.profileURL();
}
