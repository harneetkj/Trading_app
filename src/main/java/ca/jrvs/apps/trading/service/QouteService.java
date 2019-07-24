package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QouteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Qoute;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class QouteService {
    private QouteDao qouteDao;
    private MarketDataDao marketDataDao;

    public  QouteService(QouteDao qouteDao, MarketDataDao marketDataDao){
       /* Creates a new QuoteService object which relies on a QuoteDao to store quotes and
     MarketDataDao to pull quotes from the Iex server */
        this.qouteDao = qouteDao;
        this.marketDataDao = marketDataDao;
    }


    /**
     * Retrieves the stock information for the user given ticker symbol
     *
     * @param ticker - stock symbol being retrieved
     * @return the IexQuote that matches the symbol passed, or null if it does not exists
     */
    public IexQuote getIexQoute(String tickr){
        IexQuote iexQuote = marketDataDao.findIexQouteByticker(tickr);
        if (iexQuote == null){
            System.out.println("Ticker not found");

        }

        return iexQuote;
    }
    /**
     * Adds a new quote to the daily list and stores it's current data in the database
     *
     * @param ticker - quote ticker being added to the daily list
     * @return data of the quote stored in the database
     */

    public Qoute AddToList(String tickr){

        if(tickr == ""){
           throw new IllegalArgumentException("Empty ticker value");
        }
        return InsertQoute(tickr,false);

    }

    public List<Qoute> AddtoList(List<String> tickrs){
        if(tickrs.isEmpty()){
            throw new IllegalArgumentException(" Empty tickr list");
        }
        return InsertQoute(tickrs,false);
    }

    /**
     * Updates a list of quotes in the daily list and stores their current Iex data in the database
     *
     * @param tickers - quote tickers being updated in the daily list
     * @return data of the quotes stored in the database
     */

    public Qoute UpdateList(String tickr){

      return InsertQoute(tickr,true);

    }

    public List<Qoute> UpdateList(List<String> tickrs)
    {
        return InsertQoute(tickrs, true);
    }

    public Qoute InsertQoute(String tickr, boolean Update)
    {
        List<String> tickers = new ArrayList<>();
        tickers.add(tickr);
        List<Qoute> qoutes = InsertQoute(tickers,true);

        return qoutes.get(0);
    }

    public List<Qoute> InsertQoute(List<String> tickrs, boolean Update){
        List<IexQuote> iexqoute = marketDataDao.findIexQouteByticker(tickrs);
        List<Qoute> qoutelist = IextoQuote(iexqoute);

        //if update is equal to true
        if(Update== true){

            for ( Qoute q : qoutelist){
                if(!qouteDao.existsById(q.getID())){
                    qouteDao.create(q);
                }
            }
        }
    return qoutelist;
    }

    //* This method is used to iterate through the IexQouteList and each one is again sent to the IexQoute method *//

    public List<Qoute> IextoQuote (List<IexQuote> iexQuote)
    {
        List<Qoute> qouteList = new ArrayList<>();
        for (IexQuote q: iexQuote){

            qouteList.add(IextoQuote(q));
        }
        return  qouteList;
    }
    public List<Qoute> getAllQuotes() {
        return qouteDao.FindAll();
    }

    public Qoute IextoQuote (IexQuote q){
        Qoute qoute = new Qoute();
        qoute.setAskPrice(Integer.parseInt(q.getIexAskPrice()));
        qoute.setAskSize(Integer.parseInt(q.getIexAskSize()));
        qoute.setBidPrice(Double.parseDouble(q.getIexBidPrice()));
        qoute.setBidSize(Integer.parseInt(q.getIexBidSize()));
        qoute.setLastPrice(Double.parseDouble(q.getLatestPrice()));
        qoute.setTicker(q.getSymbol());
        return qoute;

    }
}

