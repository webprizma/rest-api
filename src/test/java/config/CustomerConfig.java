package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:customer.properties"
})
public interface CustomerConfig extends Config {
    @Key("registrationRequestVerificationTokenCookie")
    String registrationRequestVerificationTokenCookie();

    @Key("registrationRequestVerificationTokenParam")
    String registrationRequestVerificationTokenParam();

    @Key("profileRequestVerificationTokenCookie")
    String profileRequestVerificationTokenCookie();

    @Key("profileRequestVerificationTokenParam")
    String profileRequestVerificationTokenParam();

    @Key("authCookieName")
    String authCookieName();

    @Key("minimalContent")
    String minimalContent();

    @Key("registrationURL")
    String registrationURL();

    @Key("loginURL")
    String loginURL();

    @Key("profileURL")
    String profileURL();

    @Key("email")
    String email();

    @Key("password")
    String password();
}