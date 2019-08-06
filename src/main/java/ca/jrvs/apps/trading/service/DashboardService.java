package ca.jrvs.apps.trading.service;


import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QouteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Qoute;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DashboardService {


    private TraderDao traderDao;
    private PositionDao positionDao;
    private AccountDao accountDao;
    private QouteDao quoteDao;

    @Autowired
    public DashboardService(TraderDao traderDao, PositionDao positionDao, AccountDao accountDao, QouteDao quoteDao) {
        this.traderDao = traderDao;
        this.positionDao = positionDao;
        this.accountDao = accountDao;
        this.quoteDao = quoteDao;
    }

    /**
     * Create and return a traderAccountView by trader ID
     *
     * @param traderId trader ID
     * @return traderAccountView
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException                    for invalid input
     */
    public TraderAccountView getTraderAccount(Integer traderId) {
        Account account = accountDao.findByTraderId(traderId);
        Trader trader = traderDao.findById(traderId);
        TraderAccountView traderAccountView = new TraderAccountView();
        traderAccountView.setAccount(account);
        traderAccountView.setTrader(trader);
        return traderAccountView;
    }

    /**
     * Create and return portfolioView by trader ID
     *
     * @param traderId
     * @return portfolioView
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException                    for invalid input
     */
    public PortfolioView getProfileViewByTraderId(Integer traderId) {
        Account account = accountDao.findByTraderId(traderId);
        List<Position> positions = positionDao.findAllByAccountId(account.getID());
        PortfolioView portfolioView = new PortfolioView();
        for (Position p : positions) {
            String ticker = p.getTicker();
            Qoute qoute = quoteDao.findById(ticker);
            portfolioView.addSecurityRow(p, qoute, ticker);
        }
        return portfolioView;
    }
}
