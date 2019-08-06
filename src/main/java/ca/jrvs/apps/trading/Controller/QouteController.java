package ca.jrvs.apps.trading.Controller;


import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QouteDao;
import ca.jrvs.apps.trading.model.domain.Qoute;
import ca.jrvs.apps.trading.service.QouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/qoute")
public class QouteController {
    private MarketDataDao marketDataDao;
    private QouteDao qouteDao;
    private QouteService qouteService;

    @Autowired
    public QouteController(MarketDataDao marketDataDao, QouteDao qouteDao, QouteService qouteService) {
        this.marketDataDao = marketDataDao;
        this.qouteDao = qouteDao;
        this.qouteService = qouteService;
    }

    @PutMapping(path = "/iexMarketData")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Qoute> updateMarketData() {
        return null;
    }

    @GetMapping(path = "/dailylist")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Qoute> GetDailyList() {

        return qouteService.getAllQuotes();


    }


}