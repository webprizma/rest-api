package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:selenoid.properties"
})
public interface SelenoidConfig extends Config {
    @Key("remoteUrl")
    String remoteUrl();

    @Key("login")
    String login();

    @Key("password")
    String password();
}