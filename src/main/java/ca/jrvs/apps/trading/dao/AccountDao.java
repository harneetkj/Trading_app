package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;


public class AccountDao extends JdbcCrudDao<Account, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);
    private final static String Table_name = "account";
    private final static String ID_Name = "id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public AccountDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(Table_name).usingGeneratedKeyColumns(ID_Name);
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
        return Table_name;
    }

    @Override
    public String getIdName() {
        return ID_Name;
    }

    @Override
    Class getEntityClass() {
        return Account.class;
    }

    @Override
    public Account create(Account entity) {
        return super.create(entity);
    }

    @Override
    public Account update(Account entity) {
        return super.update(entity);
    }


    public Account findByTraderId(Integer TraderID) {
        return super.findById(ID_Name, TraderID, false, getEntityClass());
    }

    public Account findByIdwithUpdate(Integer TraderID) {
        return super.findById(ID_Name, TraderID, true, getEntityClass());
    }

    /* return updated account*/
    public Account UpdateAccount(Integer ID, Double Amount) {
        if (super.existsById(ID)) {
            System.out.println("Account does not exist");

            String sql = "UPDATE" + Table_name + "SET amount=? WHERE id=?";
            int row = jdbcTemplate.update(sql, ID, Amount);
            logger.debug("Updates rows" + row);
            if (row != 1) {
                throw new IncorrectResultSizeDataAccessException(1, row);
            }
            return findById(ID);
        }
        return null;
    }
}
