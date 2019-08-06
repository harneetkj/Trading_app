package ca.jrvs.apps.trading.dao;

public interface CrudRepository<T, ID> {

    T create(T entity);

    T update(T entity);

    T findById(ID id);

    boolean existsById(ID id);

    T deleteById(ID id);
}
