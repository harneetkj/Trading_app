package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Qoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QouteDao extends JdbcCrudDao<Qoute, String> {

    private final static String tableName = "quote";
    private final static String ID_Name = "ticker";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QouteDao(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(getTableName());


    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public String getTableName() {
        return "quote";
    }

    @Override
    public String getIdName() {
        return ID_Name;
    }

    @Override
    Class getEntityClass() {
        return Qoute.class;
    }

    public List<Qoute> FindAll() {
        List<Qoute> dtoList = new ArrayList<>();
        {
        }
        ;
        try {
            dtoList = jdbcTemplate.query("select * from " + getTableName(), BeanPropertyRowMapper.newInstance(getDtoClass()));
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return dtoList;
    }

    protected Class getDtoClass() {
        return Qoute.class;
    }

    public Qoute create(Qoute entity) {

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        simpleJdbcInsert.execute(parameterSource);
        return entity;
    }


    public Qoute update(Qoute entity) {

        return null;
    }


    public Qoute findById(String s) {
        return null;
    }


    public boolean existsById(String s) {
        return false;
    }


    public Qoute deleteById(String s) {
        return null;
    }


}


