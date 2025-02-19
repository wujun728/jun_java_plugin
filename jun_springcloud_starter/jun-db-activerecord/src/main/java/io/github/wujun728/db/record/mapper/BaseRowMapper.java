package io.github.wujun728.db.record.mapper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import io.github.wujun728.db.record.annotation.MapRow;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SpringJdbcTemplate字段映射类
 */
public class BaseRowMapper<T> implements RowMapper<T> {

	private Class<T> clazz;

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public BaseRowMapper(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		T t;
		try {
			t = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		//获得类中所有字段，包括其父类中的字段
		Field[] fields = ReflectUtil.getFields(clazz);

		String column = "";
		String str = null;
		RowType type = null;
		for(Field field : fields) {
			if(field.isAnnotationPresent(MapRow.class)) {
				column = field.getAnnotation(MapRow.class).column();
				type = field.getAnnotation(MapRow.class).type();

				try {
					field.setAccessible(true);

					if(type == RowType.STRING) {
						field.set(t, rs.getString(column));
					}
					else if(type == RowType.INTEGER) {
						field.set(t, rs.getInt(column));
					}
					else if(type == RowType.DOUBLE) {
						field.set(t, rs.getDouble(column));
					}
					else if(type == RowType.LONG) {
						field.set(t, rs.getLong(column));
					}
					else if(type == RowType.DATE) {
						str = rs.getString(column);
						if(StrUtil.isNotBlank(str)) {
							field.set(t, DateUtil.parse(str));
						}
					}
					else if(type == RowType.BOOLEAN) {
						field.set(t, rs.getBoolean(column));
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		return t;
	}
}