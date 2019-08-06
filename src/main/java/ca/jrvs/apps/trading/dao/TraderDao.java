package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Trader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class TraderDao implements CrudRepository<Trader, Integer> {

    private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);
    private final String TABLE_NAME = "trader";
    private final String ID_COLUMN = "id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;


    @Autowired
    public TraderDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_COLUMN);

    }

    @Override
    public Trader create(Trader entity) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(entity);
        Number ID = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
        entity.setId((Integer) ID);
        return entity;
    }

    @Override
    public Trader update(Trader entity) {
        return null;
    }

    @Override
    public Trader findById(Integer id) {
        if (id == null) {
            System.out.println("ID can't be null");

        }
        Trader trader = null;
        try {
            trader = jdbcTemplate.queryForObject("SELECT * FROM" + TABLE_NAME + "where id = ?", BeanPropertyRowMapper.newInstance(Trader.class), id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("cant find trader id" + id, e);
        }
        return trader;
    }


    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Trader deleteById(Integer integer) {
        return null;
    }
}
