package com.jun.plugin.sql.node;

import java.util.Set;

import com.jun.plugin.sql.context.Context;
import com.jun.plugin.sql.token.TokenHandler;
import com.jun.plugin.sql.token.TokenParser;


public class TextSqlNode implements SqlNode {

    String text;

    public TextSqlNode(String text) {
        this.text = text;
    }

    @Override
    public void apply(Context context) {
        //解析常量值 ${xxx}
        TokenParser tokenParser = new TokenParser("${", "}", new TokenHandler() {
            @Override
            public String handleToken(String paramName) {
                Object value = context.getOgnlValue(paramName);
                return value == null ? "" : value.toString();
            }
        });
        String s = tokenParser.parse(text);


        context.appendSql(s);

    }

    @Override
    public void applyParameter(Set<String> set) {
        TokenParser tokenParser = new TokenParser("${", "}", new TokenHandler() {
            @Override
            public String handleToken(String paramName) {
                set.add(paramName);
                return paramName;
            }
        });
        String s = tokenParser.parse(text);

        TokenParser tokenParser2 = new TokenParser("#{", "}", new TokenHandler() {
            @Override
            public String handleToken(String paramName) {
                set.add(paramName);
                return paramName;
            }
        });
        tokenParser2.parse(s);
    }
}
