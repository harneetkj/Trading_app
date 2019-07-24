package ca.jrvs.apps.trading.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.stream.Collectors;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MarketDataDao {

    private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private String BATCH_QUOTE_URL;
    private String URL_helper = "/stock/market/batch?symbols=%s&types=quote&token";
    private static final String BASE_URL = "https://cloud.iexapis.com/v1/stock/market/batch?types=quote";
    private static final String ACCESS_TOKEN = System.getenv("TOKEN");
    private HttpClientConnectionManager httpClientConnectionManager;
    private MarketDataConfig marketDataConfig;


    @Autowired
    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        try {

            BATCH_QUOTE_URL =
                    marketDataConfig.getHost() + URL_helper
                            + marketDataConfig.getToken();
            String host = marketDataConfig.getHost();
            String token = marketDataConfig.getToken();
        } catch (DataRetrievalFailureException e) {
            e.printStackTrace();
            List<String> ticker = null;
            List<IexQuote> tickerList = findIexQouteByticker(ticker);
            StringBuilder sb = new StringBuilder();

        }

    }
        public IexQuote findIexQouteByticker(String ticker){
        List<String> TickerList = new ArrayList<>();
        TickerList.add(ticker);
        List<IexQuote> qoute = findIexQouteByticker(TickerList);
        return qoute.get(0);

        }
        public List<IexQuote> findIexQouteByticker(List<String> tickerList)
        {

          String tickerListtoString = tickerList.stream().map(String::valueOf).collect(Collectors.joining(","));
            ;
            StringBuilder sb = new StringBuilder();

            String stringUri = BASE_URL + "&token=" + ACCESS_TOKEN + "&symbols=" + tickerListtoString ;
            String response = executeHttpGet(stringUri);

            logger.info("get Uri" +stringUri);//
            return null;

        }

    private String executeHttpGet(String stringUri) {

        {
            try (CloseableHttpClient httpClient = getHttpClient()) {
                HttpGet httpGet = new HttpGet(stringUri);
                try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                    switch (response.getStatusLine().getStatusCode()) {
                        case 200:
                            //EntityUtils toString will also close inputStream in Entity
                            String body = EntityUtils.toString(response.getEntity());
                            return Optional.ofNullable(body).orElseThrow(
                                    () -> new IOException("Unexpected empty http response body"));
                        case 404:
                            System.out.println(

                                    "Resource not found"
                            );
                        default:
                            throw new DataRetrievalFailureException(
                                    "Unexpected status:" + response.getStatusLine().getStatusCode());
                    }
                }
            } catch (IOException e) {
                throw new DataRetrievalFailureException("Unable Http execution error", e);
            }
    }


    }

    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                //prevent connectionManager shutdown when calling httpClient.close()
                .setConnectionManagerShared(true)
                .build();
    }
    }



