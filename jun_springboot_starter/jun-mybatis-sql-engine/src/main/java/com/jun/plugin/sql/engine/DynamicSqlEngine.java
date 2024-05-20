package com.jun.plugin.sql.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.jun.plugin.sql.SqlMeta;
import com.jun.plugin.sql.context.Context;
import com.jun.plugin.sql.node.SqlNode;
import com.jun.plugin.sql.tag.XmlParser;
import com.jun.plugin.sql.token.TokenHandler;
import com.jun.plugin.sql.token.TokenParser;


public class DynamicSqlEngine {

    Cache cache = new Cache();

    public SqlMeta parse(String text, Map<String, Object> params) {
        text = String.format("<root>%s</root>", text);
        SqlNode sqlNode = parseXml2SqlNode(text);
        Context context = new Context(params);
        parseSqlText(sqlNode, context);
        parseParameter(context);
        SqlMeta sqlMeta = new SqlMeta(context.getSql(), context.getJdbcParameters());
        return sqlMeta;
    }

    public Set<String> parseParameter(String text) {
        text = String.format("<root>%s</root>", text);
        SqlNode sqlNode = parseXml2SqlNode(text);
        HashSet<String> set = new HashSet<>();
        sqlNode.applyParameter(set);
        return set;
    }

    private SqlNode parseXml2SqlNode(String text) {
        SqlNode node = cache.getNodeCache().get(text);
        if (node == null) {
            node = XmlParser.parseXml2SqlNode(text);
            cache.getNodeCache().put(text, node);
        }
        return node;
    }

    /**
     * 解析标签，去除标签，替换 ${}为常量值, #{}保留不变
     *
     * @param sqlNode
     * @param context
     */
    private void parseSqlText(SqlNode sqlNode, Context context) {
        sqlNode.apply(context);
    }

    /**
     * #{}替换成?，并且将?对应的参数值按顺序保存起来
     *
     * @param context
     */
    private void parseParameter(Context context) {
        TokenParser tokenParser = new TokenParser("#{", "}", new TokenHandler() {
            @Override
            public String handleToken(String content) {
                Object value = context.getOgnlValue(content);
                if (value == null) {
                    throw new RuntimeException("could not found value : " + content);
                }
                context.addParameter(value);
                return "?";
            }
        });
        String sql = tokenParser.parse(context.getSql());
        context.setSql(sql);
    }

    public static void main(String[] args) {
        DynamicSqlEngine engine = new DynamicSqlEngine();
        String sql = ("<root>select <if test='minId != null'>id > ${minId} #{minId} <if test='maxId != null'> and id &lt; ${maxId} #{maxId}</if> </if></root>");
        Map<String, Object> map = new HashMap<>();
        map.put("minId", 100);
        map.put("maxId", 500);
        engine.parse(sql, map);
    }
}
