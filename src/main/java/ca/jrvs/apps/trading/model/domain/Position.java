package ca.jrvs.apps.trading.model.domain;

public class Position implements Entity<Integer> {
    private Integer accountId;
    private long position;
    private String ticker;

    @Override
    public void setId(Integer integer) {

    }

    public long getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }


    public Integer getID() {
        return accountId;
    }


    @Override
    public String toString() {
        return "Position{" +
                "accountId=" + accountId +
                ", position=" + position +
                ", ticker='" + ticker + '\'' +
                '}';
    }
}
