package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<E extends Entity, ID> implements CrudRepository<E, ID> {
    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    abstract public JdbcTemplate getJdbcTemplate();

    abstract public SimpleJdbcInsert getSimpleJdbcInsert();

    abstract public String getTableName();

    abstract public String getIdName();

    abstract Class getEntityClass();

    @Override
    public E create(E entity) {

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);

        Number newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
        entity.setId(newId.intValue());
        return entity;
    }

    @Override
    public E update(E entity) {

        return null;
    }

    @Override
    public E findById(ID id) {
        return findById(getIdName(), id, false, getEntityClass());
    }

    @Override
    public boolean existsById(ID id) {
        return existsById(getIdName(), id);
    }

    public boolean existsById(String IDName, ID id) {
        if (id == null) {
            System.out.println("ID cant be null");
        }
        String Exist_SQL = "SELECT * FROM" + getTableName() + "WHERE" + IDName + "= ? ";
        logger.info(Exist_SQL);
        Integer count = getJdbcTemplate().queryForObject(Exist_SQL,
                Integer.class, id);
        return count != 0;
    }

    @Override
    public E deleteById(ID id) {
        deleteById(getIdName(), id);
        return null;
    }

    public boolean deleteById(String Idname, ID id) {
        String delete_query = "DELETE FROM" + getTableName() + "WHERE " + Idname + " =?";
        logger.info(delete_query);
        getJdbcTemplate().update(delete_query, id);
        return false;
    }

    public E findById(String idName, ID id, boolean forUpdate, Class clazz) {
        E t = null;
        String Sql_select = "SELECT *" + getTableName() + "WHERE" + idName + "= ?";
        if (forUpdate) {
            Sql_select += "for update";
        }

        logger.info(Sql_select);

        try {
            t = (E) getJdbcTemplate()
                    .queryForObject(Sql_select, BeanPropertyRowMapper.newInstance(clazz), id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Can't find a entry with this id:" + id, e);
        }
        if (t == null) {
            System.out.println("Resource not found");

        }
        return t;

    }

}

