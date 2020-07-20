package com.ic911.htools.service.hadoop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ic911.htools.entity.hadoop.NodeStatusReport;
/**
 * 自定义报表
 * @author changcheng
 */
@Service
@Transactional
public class ReportService {
	
	private JdbcTemplate template;

	@Autowired
	public void setDateSource(DataSource dataSource){
		template = new JdbcTemplate(dataSource);
	}

	public List<NodeStatusReport> findReport(String today, String end){
		
		StringBuffer  sb = new StringBuffer();
		sb.append("SELECT b.hours as hours, ");
		sb.append("       b.allnode as allnodes, ");
		sb.append("       b.allnode - Ifnull(a.badnode, 0) AS healthnodes, ");
		sb.append("       Ifnull(a.badnode, 0) AS badnodes ");
		sb.append("FROM   (SELECT Count(DISTINCT hostname) AS badnode, ");
		sb.append("               HOURS ");
		sb.append("        FROM   HDP_MONITOR_STATUS ");
		sb.append("        WHERE  MOITOR_DATE > ? ");
		sb.append("               AND MOITOR_DATE < ? ");
		sb.append("               AND status != 2 ");
		sb.append("        GROUP  BY HOURS) a ");
		sb.append("       RIGHT JOIN (SELECT Count(DISTINCT hostname) AS allnode, ");
		sb.append("                          HOURS ");
		sb.append("                   FROM   HDP_MONITOR_STATUS ");
		sb.append("                   WHERE  MOITOR_DATE > ? ");
		sb.append("                          AND MOITOR_DATE < ? ");
		sb.append("                   GROUP  BY HOURS) b ");
		sb.append("         ON a.HOURS = b.HOURS ");
		sb.append("ORDER BY b.hours");
		
		List<NodeStatusReport> list = template.query(sb.toString(), new ReportMap(),today,end,today,end);
		return list;
	}
	
	/**
	 * 自定义报表实体映射
	 * @author changcheng
	 */
	private class ReportMap implements RowMapper<NodeStatusReport>{
		@Override
		public NodeStatusReport mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			NodeStatusReport report = new NodeStatusReport();
			report.setHours(rs.getInt("hours"));
			report.setAllnodes(rs.getInt("allnodes"));
			report.setHealthnodes(rs.getInt("healthnodes"));
			report.setBadnodes(rs.getInt("badnodes"));
			
			return report;
		}  
	}
	
}
