package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${start}.properties"
})
public interface WebDriverConfig extends Config {

    @Key("browser")
    @DefaultValue("CHROME")
    String getBrowser();

    @Key("browser_version")
    @DefaultValue("100")
    String getBrowserVersion();

    @Key("remoteUrl")
    String getRemoteURL();
}