/*   
 * Project: OSMP
 * FileName: AdvancedBlock.java
 * version: V1.0
 */
package com.osmp.jdbc.parse.sqlblock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 高级查询块(暂只支持作为where下子块)
 * @author heyu
 *
 */
public class AdvancedBlock implements SQLBlock {
    //拼接的sql属性值
    private String filterProperty;
    //条件分隔符
    private String separator;
    
    
    private static final String specialCondRegex = "(i|I)(S|s)\\s+(((N|n)(O|o)(T|t))?\\s+)?(N|n)(U|u)(L|l)(L|l)";
    private static final String normalCondRegex = "(!=)|(>=)|(<=)|(>)|(<)|(=)|( (L|l)(I|i)(K|k)(E|e) )|( (N|n)(O|o)(T|t)\\s+(L|l)(I|i)(K|k)(E|e) )|( (I|i)(N|n) )|( (N|n)(O|o)(T|t)\\s+(I|i)(N|n) )";
    private static final String bettwenCondRegex = " ((B|b)(E|e)(T|t)(W|w)(E|e)(E|e)(N|n))[\\w\\W]*((A|a)(N|n)(D|d)) ";
    private static final String bettwenRegex = " (B|b)(E|e)(T|t)(W|w)(E|e)(E|e)(N|n) ";
    private static final String andRegex = " (A|a)(N|n)(D|d) ";
    private static Pattern normalpattern = Pattern.compile(normalCondRegex);
    private static Pattern specialPattern = Pattern.compile(specialCondRegex);
    private static Pattern betweenPattern = Pattern.compile(bettwenCondRegex);
    private static Pattern betweenKeyPattern = Pattern.compile(bettwenRegex);
    public AdvancedBlock(String filterProperty, String separator) {
        this.filterProperty = filterProperty;
        this.separator = separator;
    }
    
    @Override
    public String sql(Map<String, Object> params) {
        if(filterProperty == null || separator == null || "".equals(separator) ||
                params == null || params.get(filterProperty) == null) return "";
        StringBuffer sb = new StringBuffer();
        String filterStr = params.get(filterProperty).toString();
        //如果分隔符是and，替换between条件后and
        if("and".equalsIgnoreCase(separator.trim())){
            String replaceAfterBetweenAndStr = ("a".equals(String.valueOf(separator.trim().charAt(0)))?" A":" a") + "nd ";
            Matcher m = betweenKeyPattern.matcher(filterStr);
            while(m.find()){
                int index = m.start();
                filterStr = filterStr.substring(0,index) + filterStr.substring(index).replaceFirst(andRegex, replaceAfterBetweenAndStr);
            }
        }
        String[] allExp = filterStr.split(separator);
        for(String exp : allExp) {
            String expTrim = exp.trim();
            StringBuffer sc = new StringBuffer();
            //处理并列条件and 或者 or
            if(expTrim.startsWith("or ")){
                sc.append("or ");
                expTrim = expTrim.substring(2).trim();
            }else if(expTrim.startsWith("and ")){
                sc.append("and ");
                expTrim = expTrim.substring(3).trim();
            }else{
                sc.append("and ");
            }
            //处理特殊条件 如 xx is null 或者 xx is not null
            String[] specialArr = specialPattern.split(expTrim,2);
            if(specialArr.length > 1){
                if(!specialArr[1].trim().isEmpty()){
                    continue;
                }
                sc.append(specialArr[0].replaceAll(" ", "")).append(expTrim.replaceFirst(specialArr[0], " "));
                sb.append(sc).append(" ");
                continue;
            }
            //普通条件 如 xx>1
            String[] normalArr = normalpattern.split(expTrim, 2);
            //如果无匹配普通条件
            if(normalArr.length < 2){
                //判断是否为between and表达式
                String[] betweenArr = betweenPattern.split(expTrim,2);
                if(betweenArr.length < 2){
                    continue;
                }
                String betweenKey = betweenArr[0].replaceAll(" ", "");
                String betweenVal2 = betweenArr[1].trim();
                String betweenVal1 = expTrim.replaceFirst(betweenArr[0], "").replaceFirst(" (B|b)(E|e)(T|t)(W|w)(E|e)(E|e)(N|n) ", "")
                            .replaceFirst(" (A|a)(N|n)(D|d) ", "").replaceFirst(betweenArr[1], "").trim();
                String startKey = generateKey(betweenKey+"_start",params);
                String endKey = generateKey(betweenKey+"_end",params);
                sc.append(betweenKey).append(" between ").append(":"+startKey)
                    .append(" and ").append(":"+endKey);
                params.put(startKey, betweenVal1);
                params.put(endKey, betweenVal2);
                sb.append(sc).append(" ");
                continue;
            }
            
            String columnKey = normalArr[0].replaceAll(" ", "");
            String cond = expTrim;
            int firstIndex = cond.indexOf(normalArr[0]);
            if(firstIndex > -1){
                cond = cond.substring(firstIndex+normalArr[0].length());
            }
            int lastIndex = cond.indexOf(normalArr[1]);
            if(lastIndex > -1){
                cond = cond.substring(0,lastIndex);
            }
            cond = cond.replaceAll("\\(|\\)", "").trim();
            String valKey = generateKey(columnKey,params);
            sc.append(columnKey).append(" ").append(cond).append(" ");
            String columnVal = normalArr[1].trim();
            if("in".equalsIgnoreCase(cond) || "not in".equalsIgnoreCase(cond)){
                if(columnVal.startsWith("(") && columnVal.endsWith(")")){
                    columnVal = columnVal.substring(1,columnVal.length()-1);
                }
                List<String> val = Arrays.asList(columnVal.split(","));
                sc.append("(").append(":"+valKey).append(")");
                params.put(valKey, val);
            }else{
                sc.append(":"+valKey);
                params.put(valKey, columnVal);
            }
            sb.append(sc).append(" ");
        }
        return sb.toString();
    }
    
    //生成参数键
    private String generateKey(String key,Map<String, Object> params){
        if(params.containsKey(key)){
            return generateKey(key+"_"+1,params);
        }
        return key;
    }
    
}
