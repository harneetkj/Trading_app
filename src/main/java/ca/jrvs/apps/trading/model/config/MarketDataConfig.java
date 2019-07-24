package ca.jrvs.apps.trading.model.config;

import jdk.internal.dynalink.beans.StaticClass;

public class MarketDataConfig {

    public String Host;
    public String Token;

    public String getHost() {
        String host = System.getenv("HOST");
        return host;
    }

    public void setHost(String getHost) {
        this.Host = getHost;
    }
    public String getToken() {
        String token = System.getenv("TOKEN");
        return token;
    }

    public void setToken(String getToken) {
        this.Token = getToken;
    }



}
