/*   
 * Project: OSMP
 * FileName: WhereBlock.java
 * version: V1.0
 */
package com.osmp.jdbc.parse.sqlblock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * where块
 * @author heyu
 *
 */
public class WhereBlock implements SQLBlock {
    private static final String linkWorldRegex = "((A|a)(N|n)(D|d))|((O|o)(R|r))|((O|o)(R|r)(D|d)(E|e)(R|r))";
    private static final String linkWorldRegex_1 =  "((A|a)(N|n)(D|d))|((O|o)(R|r))|((W|w)(H|h)(E|e)(R|r)(E|e))";
    private static final Pattern linkWorldPattern = Pattern.compile(linkWorldRegex);
    private static final Pattern linkWorldPattern_1 = Pattern.compile(linkWorldRegex_1);

    private List<SQLBlock> sqlBlocks;

    public WhereBlock() {
        this.sqlBlocks = new ArrayList<SQLBlock>();
    }

    public void addSqlBlock(SQLBlock sqlBlock){
        if(sqlBlock == null) {
            return;
        }
        this.sqlBlocks.add(sqlBlock);
    }

    public void addSqlBlocks(List<SQLBlock> sqlBlocks){
        if(sqlBlocks == null) {
            return;
        }
        this.sqlBlocks.addAll(sqlBlocks);
    }

    @Override
    public String sql(Map<String, Object> params) {
        if(sqlBlocks.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for(SQLBlock block : sqlBlocks){
            String sql = block.sql(params);
            if(sql == null || "".equals(sql.trim())) {
                continue;
            }
            String prefix = sql.substring(0,sql.indexOf(" ")).trim();
            Matcher matcher = linkWorldPattern.matcher(prefix);
            if(matcher.find()){
                String mGroup = matcher.group();
                if(mGroup.length() != prefix.length()){
                    sb.append("and ");
                }
            }else {
                sb.append("and ");
            }
            sb.append(sql);
        }
        String sqlAll = sb.toString().trim();
        if(sqlAll.isEmpty()) {
            return "";
        }

        String prefix_1 = sqlAll.substring(0,sqlAll.indexOf(" ")).trim();
        Matcher matcher_1 = linkWorldPattern_1.matcher(prefix_1);
        if(matcher_1.find()){
            String mGroup_1 = matcher_1.group();
            if(mGroup_1.length() == prefix_1.length()){
                return "where " + sqlAll.replaceFirst(mGroup_1, "") + " ";
            }
        }

        return sqlAll + " ";
    }

}
