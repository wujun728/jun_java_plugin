package com.holder.dbhelper;

import java.sql.PreparedStatement;

public interface ParameterBinder {
	void bindPreparedStatement(PreparedStatement pstmt);
}
