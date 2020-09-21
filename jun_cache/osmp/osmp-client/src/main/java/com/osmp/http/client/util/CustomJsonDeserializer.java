/*   
 * Project: OSMP
 * FileName: CustomJsonDeserializer.java
 * version: V1.0
 */
package com.osmp.http.client.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

/**
 * 自定义json解析器,针对jdk反射设值 如(setiFun 与  setIFun)的问题
 * @author heyu
 *
 */
public class CustomJsonDeserializer implements ObjectDeserializer{
    public final static CustomJsonDeserializer instance = new CustomJsonDeserializer();
    
    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        final JSONLexer lexer = parser.getLexer();
        if (lexer.token() == JSONToken.NULL) {
            lexer.nextToken(JSONToken.COMMA);
            return null;
        }
        T model = null;
        Map<String, Object> map = null;
        ParseContext context = parser.getContext();
        Class<?> clazz = null;
        Map<String, CustomJsonFieldDesc> jfd = null;
        try {
            clazz = (Class<?>) type;
            jfd = new HashMap<String, CustomJsonFieldDesc>();
            _scanField(clazz, jfd);
 
            model = (T) clazz.newInstance();
            parser.setContext(context, map, fieldName);
            deserialze(parser, fieldName, map, model, jfd);
            return model;
        } catch (InstantiationException e) {
            throw new JSONException("init model exceptopn:" + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new JSONException("init model exceptopn:" + e.getMessage());
        } catch (NumberFormatException e) {
            throw e;
        } finally {
            parser.setContext(context);
            clazz = null;
            jfd.clear();
        }
    }
 
    private void _scanField(Class<?> clazz, Map<String, CustomJsonFieldDesc> jfd) {
        for (Field f : clazz.getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            JSONField jf = f.getAnnotation(JSONField.class);
            if (jf != null && jf.name() != null && jf.name().trim().length() != 0) {
                jfd.put(jf.name().trim(), new CustomJsonFieldDesc(f, jf));
            }else{
                
                jfd.put(f.getName(),new CustomJsonFieldDesc(f, null));
            }
        }
    }
 
    private <T> Map<String, Object> deserialze(DefaultJSONParser parser, Object fieldName, Map<String, Object> map, T model,
            Map<String, CustomJsonFieldDesc> jfd) throws NumberFormatException {
        JSONLexer lexer = parser.getLexer();
 
        if (lexer.token() != JSONToken.LBRACE) {
            throw new JSONException("syntax error, expect {, actual " + lexer.token());
        }
        ParseContext context = parser.getContext();
        try {
            for (;;) {
                lexer.skipWhitespace();
                char ch = lexer.getCurrent();
                if (parser.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (ch == ',') {
                        lexer.next();
                        lexer.skipWhitespace();
                        ch = lexer.getCurrent();
                    }
                }
                String key;
                if (ch == '"') {
                    key = lexer.scanSymbol(parser.getSymbolTable(), '"');
                    lexer.skipWhitespace();
                    ch = lexer.getCurrent();
                    if (ch != ':') {
                        throw new JSONException("expect ':' at " + lexer.pos());
                    }
                } else if (ch == '}') {
                    lexer.next();
                    lexer.resetStringPosition();
                    lexer.nextToken(JSONToken.COMMA);
                    return map;
                } else if (ch == '\'') {
                    if (!parser.isEnabled(Feature.AllowSingleQuotes)) {
                        throw new JSONException("syntax error");
                    }
 
                    key = lexer.scanSymbol(parser.getSymbolTable(), '\'');
                    lexer.skipWhitespace();
                    ch = lexer.getCurrent();
                    if (ch != ':') {
                        throw new JSONException("expect ':' at " + lexer.pos());
                    }
                } else {
                    if (!parser.isEnabled(Feature.AllowUnQuotedFieldNames)) {
                        throw new JSONException("syntax error");
                    }
 
                    key = lexer.scanSymbolUnQuoted(parser.getSymbolTable());
                    lexer.skipWhitespace();
                    ch = lexer.getCurrent();
                    if (ch != ':') {
                        throw new JSONException("expect ':' at " + lexer.pos() + ", actual " + ch);
                    }
                }
 
                lexer.next();
                lexer.skipWhitespace();
                ch = lexer.getCurrent();
 
                lexer.resetStringPosition();
 
                CustomJsonFieldDesc jsonfdes = null;
                Object value;
                Type valueType = Object.class;
                Class<?> valueClass = null;
                lexer.nextToken();
 
                if (lexer.token() == JSONToken.NULL) {
                    value = null;
                    lexer.nextToken();
                } else {
                    if (jfd.containsKey(key)) {
                        jsonfdes = jfd.get(key);
                        valueType = jsonfdes.getField().getGenericType();
                        valueClass = jsonfdes.getField().getType();
                        key = jsonfdes.getField().getName();
                        jsonfdes = null;
                    }
                    value = parser.parseObject(valueType);
                }
                if(valueClass != null){
                    Method m = null;
                    try {
                        m = model.getClass().getDeclaredMethod("set"+((key.charAt(0) + "").toUpperCase() + key.substring(1)),valueClass);
                        if(m != null){
                            m.invoke(model, value);
                        }
                    } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                    }
                    
                    if(m == null){
                        try {
                            m = model.getClass().getDeclaredMethod("set"+key,valueClass);
                            if(m != null){
                                m.invoke(model, value);
                            }
                            
                        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
                
                valueType = null;
                valueClass = null;
                
                parser.checkMapResolve(map, key);
                parser.setContext(context, value, key);
 
                final int tok = lexer.token();
                if (tok == JSONToken.EOF || tok == JSONToken.RBRACKET) {
                    return map;
                }
                if (tok == JSONToken.RBRACE) {
                    lexer.nextToken();
                    return map;
                }
            }
        } finally {
            parser.setContext(context);
        }
    }
 
    public int getFastMatchToken() {
        return JSONToken.LBRACE;
    }
}
