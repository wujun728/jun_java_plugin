/*   
 * Project: OSMP
 * FileName: SQLBlock.java
 * version: V1.0
 */
package com.osmp.jdbc.parse.sqlblock;

import java.util.Map;
/**
 * sql块
 * @author heyu
 *
 */
public interface SQLBlock {
    String sql(Map<String, Object> params);
}
