package config;

import org.aeonbits.owner.Config;

public interface CustomerConfig extends Config {
    @Key("password")
    @DefaultValue("123456")
    String password();

    @Key("requestVerificationTokenCookie")
    @DefaultValue("7QdHkZIlaV8wlgY9ATVrX2mDsZIdFlwtb01e1z13qWP86HEBD4wA2TZ2vmcPUmEMxDvjfVr7YTKcsNs_W3TYwuetyOFOZZ7i9r9Yr3MndDw1")
    String requestVerificationTokenCookie();

    @Key("requestVerificationTokenParam")
    @DefaultValue("JLTSNbucM7YbizI8ieJlKK2XEa3Y60xZ54-uRYxFhMcHC-0-R13YtCh2NP1FCL9nHmUwgxq47_KILxutt4_Di-G1zr1HkpRnJan576ilq9Q1")
    String requestVerificationTokenParam();

    @Key("registrationURL")
    @DefaultValue("/register")
    String registrationURL();

    @Key("loginURL")
    @DefaultValue("/login")
    String loginURL();

    @Key("authCookieName")
    @DefaultValue("NOPCOMMERCE.AUTH")
    String authCookieName();

    @Key("minimalContent")
    @DefaultValue("/Themes/DefaultClean/Content/images/logo.png")
    String minimalContent();

    @Key("profileURL")
    @DefaultValue("/customer/info")
    String profileURL();
}