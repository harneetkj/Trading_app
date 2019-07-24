package ca.jrvs.apps.trading.Controller;


import ca.jrvs.apps.trading.dao.MarketDataDao;

import ca.jrvs.apps.trading.dao.QouteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;

import ca.jrvs.apps.trading.model.domain.Qoute;
import ca.jrvs.apps.trading.service.QouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;


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

    @GetMapping(path="/dailylist")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Qoute> GetDailyList(){

          return   qouteService.getAllQuotes();


    }


}