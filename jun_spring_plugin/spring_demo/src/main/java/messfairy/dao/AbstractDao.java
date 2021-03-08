package messfairy.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by hasee-pc on 2015/1/19.
 */
public abstract class AbstractDao<T> {
    protected RowMapper<T> mapper = mapper();
    protected JdbcTemplate template;
    protected NamedParameterJdbcTemplate jdbcTemplate;
    protected RowMapper<T> mapper(){
        return new RowMapper<T>() {
            public T mapRow(final ResultSet rs, int rowNum) throws SQLException {
                return map(rs);
            }
        };
    }
    public List<T> queryAll(final String abstractSql) throws DataAccessException {
        return this.jdbcTemplate.query(abstractSql, mapper());
    }
    protected abstract T map(final ResultSet rs) throws SQLException;
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.template = new JdbcTemplate(dataSource);
    }

}
