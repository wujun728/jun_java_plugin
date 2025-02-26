/*
 * Copyright (c) 2019-2029, Dreamlu 卢春梦 (596392912@qq.com & www.dreamlu.net).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.wujun728.activerecord.logger;

import com.alibaba.druid.DbType;
import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.github.wujun728.activerecord.config.JunDruidProperties;
//import net.dreamlu.mica.core.utils.StringUtil;

import java.sql.SQLException;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 打印可执行的 sql 日志
 *
 * <p>
 * 参考：<a href="https://jfinal.com/share/2204">https://jfinal.com/share/2204</a>
 * </p>
 *
 * 
 */
@Slf4j
@RequiredArgsConstructor
public class SqlLogFilter extends FilterEventAdapter {
	private static final SQLUtils.FormatOption FORMAT_OPTION = new SQLUtils.FormatOption(false, false);
	private final JunDruidProperties properties;
	private final List<Pattern> showSqlPatternList;

	@Override
	protected void statementExecuteBefore(StatementProxy statement, String sql) {
		statement.setLastExecuteStartNano();
	}

	@Override
	protected void statementExecuteBatchBefore(StatementProxy statement) {
		statement.setLastExecuteStartNano();
	}

	@Override
	protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
		statement.setLastExecuteStartNano();
	}

	@Override
	protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
		statement.setLastExecuteStartNano();
	}

	@Override
	protected void statementExecuteAfter(StatementProxy statement, String sql, boolean firstResult) {
		statement.setLastExecuteTimeNano();
	}

	@Override
	protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
		statement.setLastExecuteTimeNano();
	}

	@Override
	protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
		statement.setLastExecuteTimeNano();
	}

	@Override
	protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
		statement.setLastExecuteTimeNano();
	}

	@Override
	public void statement_close(FilterChain chain, StatementProxy statement) throws SQLException {
		// 先调用父类关闭 statement
		super.statement_close(chain, statement);
		// 支持动态关闭
		if (!properties.isShowSql()) {
			return;
		}
		// 是否开启调试
		if (!log.isInfoEnabled()) {
			return;
		}
		// 打印可执行的 sql
		String sql = statement.getBatchSql();
		// sql 为空直接返回
		if (StringUtils.isEmpty(sql)) {
			return;
		}
		boolean isSqlMatch = showSqlPatternList.stream()
			.anyMatch(pattern -> pattern.matcher(sql).matches());
		if (!isSqlMatch) {
			log.debug("sql:{} not match in SqlPatternList:{}", sql, properties.getShowSqlPatterns());
			return;
		}
		int parametersSize = statement.getParametersSize();
		List<Object> parameters = new ArrayList<>(parametersSize);
		for (int i = 0; i < parametersSize; ++i) {
			// 转换参数，处理 java8 时间
			parameters.add(getJdbcParameter(statement.getParameter(i)));
		}
		String dbType = statement.getConnectionProxy().getDirectDataSource().getDbType();
		String formattedSql = SQLUtils.format(sql, DbType.of(dbType), parameters, FORMAT_OPTION);
		printSql(formattedSql, statement);
	}

	private static Object getJdbcParameter(JdbcParameter jdbcParam) {
		if (jdbcParam == null) {
			return null;
		}
		Object value = jdbcParam.getValue();
		// 处理 java8 时间
		if (value instanceof TemporalAccessor) {
			return value.toString();
		}
		return value;
	}

	private static void printSql(String sql, StatementProxy statement) {
		// 打印 sql
		String sqlLogger = "\n\n======= Sql Logger ======================" +
			"\n{}" +
			"\n======= Sql Execute Time: {} =======\n";
		log.info(sqlLogger, sql.trim()/*, StringUtil.format(statement.getLastExecuteTimeNano())*/);
	}

}
