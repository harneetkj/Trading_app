package ca.jrvs.apps.trading;


import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static org.springframework.util.StringUtils.isEmpty;


@Configuration
@EnableTransactionManagement
public class AppConfig {


    private Logger logger = LoggerFactory.getLogger(AppConfig.class);
    @Value("${HOST}")
    private String host;

    @Bean
    public MarketDataConfig marketDataConfig() {
        if (isEmpty(System.getenv("TOKEN")) || isEmpty(host)) {
            throw new IllegalArgumentException("ENV:IEX_PUB_TOKEN or property:iex_host is not set");
        }
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setToken(System.getenv("TOKEN"));
        marketDataConfig.setHost(host);
        return marketDataConfig;
    }

    @Bean
    public DataSource DataSource() {

        String jdbcUrl;
        String user;
        String password;

        jdbcUrl = "jdbc:postgresql://localhost:5432/jrvstrading"; //System.getenv("PSQL_URL");
        user = "postgres";//System.getenv("PSQL_USER");
        password = "password";//System.getenv("PSQL_PASSWORD");

        logger.error("JDBC:" + jdbcUrl);

        if (isEmpty(jdbcUrl) || isEmpty(user) || isEmpty(password)) {
            throw new IllegalArgumentException("Missing data source config env vars");
        }

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUrl(jdbcUrl);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }

    //http://bit.ly/2tWTmzQ connectionPool
    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        return cm;
    }

}

