package io.github.wujun728.uidgenerator.worker.repository;

import io.github.wujun728.uidgenerator.worker.entity.WorkerNodeEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class DbWorkerNodeResposity implements WorkerNodeResposity {
    private static final String GET_WORKER_NODE_BY_HOST_PORT_SQL = "SELECT ID,HOST_NAME,PORT,TYPE,LAUNCH_DATE,MODIFIED,CREATED FROM worker_node WHERE HOST_NAME = ? AND PORT = ?";
    private static final String ADD_WORKER_NODE_SQL = "INSERT INTO worker_node (HOST_NAME,PORT,TYPE,LAUNCH_DATE,MODIFIED,CREATED) VALUES (?,?,?,?,NOW(),NOW())";
    private static final String DROP_WORKER_NODE_SQL = "DROP TABLE IF EXISTS `worker_node`";
    private static final String CREATE_WORKER_NODE_SQL = "CREATE TABLE `worker_node` (\n" +
            "  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'auto increment id',\n" +
            "  `HOST_NAME` varchar(64) COLLATE utf8mb4_german2_ci NOT NULL COMMENT 'host name',\n" +
            "  `PORT` varchar(64) COLLATE utf8mb4_german2_ci NOT NULL COMMENT 'port',\n" +
            "  `TYPE` int(11) NOT NULL COMMENT 'node type: ACTUAL or CONTAINER',\n" +
            "  `LAUNCH_DATE` date NOT NULL COMMENT 'launch date',\n" +
            "  `MODIFIED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'modified time',\n" +
            "  `CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',\n" +
            "  PRIMARY KEY (`ID`) USING BTREE\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=269 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci COMMENT='DB WorkerID Assigner for UID Generator'";

    private final JdbcTemplate jdbcTemplate;

    public DbWorkerNodeResposity(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @param host
     * @param port
     * @return
     */
    @Override
    public WorkerNodeEntity getWorkerNodeByHostPort(String host, String port) {
        return this.jdbcTemplate.queryForObject(GET_WORKER_NODE_BY_HOST_PORT_SQL, (rs, rowNum) -> {
            WorkerNodeEntity entity = new WorkerNodeEntity();
            entity.setId(rs.getLong("ID"));
            entity.setHostName(rs.getString("HOST_NAME"));
            entity.setPort(rs.getString("PORT"));
            entity.setType(rs.getInt("TYPE"));
            entity.setLaunchDateDate(rs.getDate("LAUNCH_DATE"));
            entity.setModified(rs.getTimestamp("MODIFIED"));
            entity.setCreated(rs.getTime("CREATED"));
            return entity;
        }, new String[]{host, port});
    }

    /**
     * Add {@link WorkerNodeEntity}
     *
     * @param entity
     */
    @Override
    public void addWorkerNode(WorkerNodeEntity entity) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            PreparedStatementCreator preparedStatementCreator = con -> {
                PreparedStatement ps = con.prepareStatement(ADD_WORKER_NODE_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, entity.getHostName());
                ps.setString(2, entity.getPort());
                ps.setInt(3, entity.getType());
                ps.setObject(4, entity.getLaunchDate());
                return ps;
            };
            this.jdbcTemplate.update(preparedStatementCreator, keyHolder);
            entity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        } catch (BadSqlGrammarException e) {
            if(e.getMessage().contains("doesn't exist")){
                this.jdbcTemplate.update(DROP_WORKER_NODE_SQL);
                this.jdbcTemplate.update(CREATE_WORKER_NODE_SQL);
                this.addWorkerNode(entity);//如果表没有，见建表
            }else{
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
