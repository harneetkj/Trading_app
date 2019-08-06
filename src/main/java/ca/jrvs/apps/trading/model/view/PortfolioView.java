package ca.jrvs.apps.trading.model.view;

import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Qoute;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.LinkedList;
import java.util.List;

public class PortfolioView {
    @JsonProperty("securityRows")
    private List<SecurityRow> securityRows;

    public PortfolioView() {
        securityRows = new LinkedList<>();
    }

    @JsonProperty("securityRows")
    public List<SecurityRow> getSecurityRows() {
        return securityRows;
    }

    @JsonProperty("securityRows")
    public void setSecurityRows(List<SecurityRow> securityRows) {
        this.securityRows = securityRows;
    }

    public void addSecurityRow(Position position, Qoute qoute, String ticker) {
        SecurityRow securityRow = new SecurityRow(position, qoute, ticker);
        securityRows.add(securityRow);
    }

    @JsonPropertyOrder({"ticker", "position", "quote"})
    class SecurityRow {

        @JsonProperty("ticker")
        private Position position;
        @JsonProperty("positon")
        private Qoute qoute;
        @JsonProperty("quote")
        private String ticker;

        public SecurityRow() {
        }

        public SecurityRow(Position position, Qoute qoute, String ticker) {
            this.position = position;
            this.qoute = qoute;
            this.ticker = ticker;
        }

        @JsonProperty("positon")
        public Position getPosition() {
            return position;
        }

        @JsonProperty("positon")
        public void setPosition(Position position) {
            this.position = position;
        }

        @JsonProperty("qoute")
        public Qoute getQoute() {
            return qoute;
        }

        @JsonProperty("qoute")
        public void setQoute(Qoute qoute) {
            this.qoute = qoute;
        }

        @JsonProperty("ticker")
        public String getTicker() {
            return ticker;
        }

        @JsonProperty("ticker")
        public void setTicker(String ticker) {
            this.ticker = ticker;
        }
    }
}
