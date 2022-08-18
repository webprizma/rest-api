package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:customer.properties"
})
public interface CustomerConfig extends Config {
    @Key("email")
    String email();
    @Key("password")
    String password();

    @Key("requestVerificationTokenCookie")
    String requestVerificationTokenCookie();

    @Key("requestVerificationTokenParam")
    String requestVerificationTokenParam();

    @Key("registrationURL")
    String registrationURL();

    @Key("loginURL")
    String loginURL();

    @Key("authCookieName")
    String authCookieName();

    @Key("minimalContent")
    String minimalContent();

    @Key("profileURL")
    String profileURL();
}