package ca.jrvs.apps.trading.model.domain;

public interface Entity<ID> {
    ID getID();

    void setId(ID id);

}
