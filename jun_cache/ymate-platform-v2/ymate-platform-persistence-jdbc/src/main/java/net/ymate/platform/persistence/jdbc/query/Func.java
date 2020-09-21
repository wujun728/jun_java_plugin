/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.persistence.jdbc.query;

import net.ymate.platform.persistence.IFunction;
import net.ymate.platform.persistence.impl.DefaultFunction;

/**
 * 数据库函数库(尝试!!! 暂未考虑不同数据库间的兼容问题)
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/22 上午10:50
 * @version 1.0
 */
public final class Func {

    public static IFunction create() {
        return new DefaultFunction();
    }

    public static IFunction create(String funcName) {
        return new DefaultFunction(funcName);
    }

    // ----------
    // Mathematical Functions
    // ----------

    // ------ 返回 X 的绝对值

    public static IFunction ABS(String x) {
        return create("ABS").param(x);
    }

    public static IFunction ABS(IFunction x) {
        return ABS(x.build());
    }

    // ------ 返回 X 反余弦, 即, 余弦是X的值

    public static IFunction ACOS(String x) {
        return create("ACOS").param(x);
    }

    public static IFunction ACOS(IFunction x) {
        return ACOS(x.build());
    }

    // ------ 返回 X 的反正弦, 即, 正弦为X的值

    public static IFunction ASIN(String x) {
        return create("ASIN").param(x);
    }

    public static IFunction ASIN(IFunction x) {
        return ASIN(x.build());
    }

    // ------ 返回 X 的反正切，即，正切为X的值。或两个变量 X 及 Y 的反正切

    public static IFunction ATAN(String x) {
        return create("ATAN").param(x);
    }

    public static IFunction ATAN(IFunction x) {
        return ATAN(x.build());
    }

    public static IFunction ATAN(String y, String x) {
        return create("ATAN").param(y).separator().param(x);
    }

    public static IFunction ATAN(IFunction y, IFunction x) {
        return ATAN(y.build(), x.build());
    }

    // ------ 返回不小于 X 的最小整数值

    public static IFunction CEILING(String x) {
        return create("CEILING").param(x);
    }

    // ------ 进制转换

    public static IFunction CONV(String x, int fromBase, int toBase) {
        return create("CONV").param(x).separator().param(fromBase).separator().param(toBase);
    }

    // ------ 返回 X 的余弦，其中 X 在弧度上已知

    public static IFunction COS(String x) {
        return create("COS").param(x);
    }

    // ------ 返回 X 的余切

    public static IFunction COT(String x) {
        return create("COT").param(x);
    }

    // ------ 计算循环冗余码校验值并返回一个32比特无符号值

    public static IFunction CRC32(String expr) {
        return create("CRC32").param(expr);
    }

    // ------ 返回参数 X, 该参数由弧度被转化为度

    public static IFunction DEGREES(String x) {
        return create("DEGREES").param(x);
    }

    // ------ 返回e的X乘方后的值(自然对数的底)

    public static IFunction EXP(String x) {
        return create("EXP").param(x);
    }

    // ----- 返回不大于X的最大整数值

    public static IFunction FLOOR(String x) {
        return create("FLOOR").param(x);
    }

    // ------ 返回 X 的自然对数,即, X 相对于基数e 的对数

    public static IFunction LN(String x) {
        return create("LN").param(x);
    }

    // ------ 若用一个参数调用，这个函数就会返回X 的自然对数。若用两个参数进行调用，这个函数会返回X 对于任意基数B 的对数。

    public static IFunction LOG(String x) {
        return create("LOG").param(x);
    }

    public static IFunction LOG(String b, String x) {
        return create("LOG").param(b).separator().param(x);
    }

    // ------

    public static IFunction LOG10(String x) {
        return create("LOG10").param(x);
    }

    // ------

    public static IFunction LOG2(String x) {
        return create("LOG2").param(x);
    }

    // ------ 模操作。返回N 被 M除后的余数。

    public static IFunction MOD(String n, String m) {
        return create("MOD").param(n).separator().param(m);
    }

    // ------ 返回 ϖ (pi)的值。默认的显示小数位数是7位

    public static IFunction PI() {
        return create("PI");
    }

    // ------ 返回X 的Y乘方的结果值。

    public static IFunction POW(String y, String x) {
        return create("POW").param(y).separator().param(x);
    }

    // ------

    public static IFunction POWER(String y, String x) {
        return create("POWER").param(y).separator().param(x);
    }

    // ------ 返回由度转化为弧度的参数 X, (注意 ϖ 弧度等于180度）。

    public static IFunction RADIANS(String x) {
        return create("RADIANS").param(x);
    }

    // ------ 返回一个随机浮点值 v ，范围在 0 到1 之间 (即, 其范围为 0 ≤ v ≤ 1.0)。若已指定一个整数参数 N ，则它被用作种子值，用来产生重复序列。

    public static IFunction RAND() {
        return create("RAND");
    }

    public static IFunction RAND(String n) {
        return create("RAND").param(n);
    }

    // ------ 返回参数X, 其值接近于最近似的整数。在有两个参数的情况下，返回 X ，其值保留到小数点后D位，而第D位的保留方式为四舍五入。若要接保留X值小数点左边的D 位，可将 D 设为负值。

    public static IFunction ROUND(String x) {
        return create("ROUND").param(x);
    }

    public static IFunction ROUND(String x, Number d) {
        return create("ROUND").param(x).separator().param(d);
    }

    // ------ 返回参数作为-1、 0或1的符号，该符号取决于X 的值为负、零或正。

    public static IFunction SIGN(String n) {
        return create("SIGN").param(n);
    }

    // ------ 返回X 正弦，其中 X 在弧度中被给定。

    public static IFunction SIN(String x) {
        return create("SIN").param(x);
    }

    // ------ 返回非负数X 的二次方根。

    public static IFunction SQRT(String x) {
        return create("SQRT").param(x);
    }

    // ------ 返回X 的正切，其中X 在弧度中被给定。

    public static IFunction TAN(String x) {
        return create("TAN").param(x);
    }

    // ------ 返回被舍去至小数点后D位的数字X。若D 的值为 0, 则结果不带有小数点或不带有小数部分。可以将D设为负数,若要截去(归零) X小数点左起第D位开始后面所有低位的值

    public static IFunction TRUNCATE(String x) {
        return create("TRUNCATE").param(x);
    }

    public static IFunction TRUNCATE(String x, Number d) {
        return create("TRUNCATE").param(x).separator().param(d);
    }

    // ----------
    // String Functions
    // ----------

    // ------ 返回值为字符串str 的最左字符的数值。假如str为空字符串，则返回值为 0 。假如str 为NULL，则返回值为 NULL。 ASCII()用于带有从 0到255的数值的字符。

    public static IFunction ASCII(String str) {
        return create("ASCII").param(str);
    }

    // ------ 返回值为N的二进制值的字符串表示，其中  N 为一个longlong (BIGINT) 数字。这等同于 CONV(N,10,2)。假如N 为NULL，则返回值为 NULL。

    public static IFunction BIN(String str) {
        return create("BIN").param(str);
    }

    // ------ 返回值为二进制的字符串str 长度。

    public static IFunction BIT_LENGTH(String str) {
        return create("BIT_LENGTH").param(str);
    }

    // ------ 将每个参数N理解为一个整数，其返回值为一个包含这些整数的代码值所给出的字符的字符串。NULL值被省略。

    public static IFunction CHAR(String... n) {
        return create("CHAR").param(n);
    }

    // ------ 返回值为字符串str 的长度，长度的单位为字符。一个多字节字符算作一个单字符。对于一个包含五个二字节字符集, LENGTH()返回值为 10, 而CHAR_LENGTH()的返回值为5。

    public static IFunction CHAR_LENGTH(String str) {
        return create("CHAR_LENGTH").param(str);
    }

    // ------

    public static IFunction CHARACTER_LENGTH(String str) {
        return create("CHARACTER_LENGTH").param(str);
    }

    // ------ 返回结果为连接参数产生的字符串。

    public static IFunction CONCAT(String... n) {
        return create("CONCAT").param(n);
    }

    // ------

    public static IFunction CONCAT_WS(String separator, String... n) {
        return create("CONCAT_WS").param(separator).separator().param(n);
    }

    // ------ 若N = 1，则返回值为  str1 ，若N = 2，则返回值为 str2 ，以此类推。   若N 小于1或大于参数的数目，则返回值为 NULL 。 ELT() 是  FIELD()的补数。

    public static IFunction ELT(String n, String... strs) {
        return create("ELT").param(n).separator().param(strs);
    }

    // ------ 返回值为str1, str2, str3,……列表中的str 指数。在找不到str 的情况下，返回值为 0 。

    public static IFunction FIELD(String str, String... n) {
        return create("FIELD").param(str).separator().param(n);
    }

    // ------ 假如字符串str 在由N 子链组成的字符串列表strlist 中， 则返回值的范围在 1 到 N 之间 。一个字符串列表就是一个由一些被‘,’符号分开的自链组成的字符串。

    public static IFunction FIND_IN_SET(String x, String strlist) {
        return create("FIND_IN_SET").param(x).separator().param(strlist);
    }

    // ------

    public static IFunction FORMAT(String x, Number d) {
        return create("FORMAT").param(x).separator().param(d);
    }

    public static IFunction FORMAT(String x, Number d, String locale) {
        return create("FORMAT").param(x).separator().param(d).separator().param(locale);
    }

    // ------

    public static IFunction HEX(String str) {
        return create("HEX").param(str);
    }

    // ------

    public static IFunction FORM_BASE64(String str) {
        return create("FORM_BASE64").param(str);
    }

    // ------

    public static IFunction TO_BASE64(String str) {
        return create("TO_BASE64").param(str);
    }

    // ------ 返回字符串 str, 其子字符串起始于 pos 位置和长期被字符串 newstr取代的len 字符。  如果pos 超过字符串长度，则返回值为原始字符串。 假如len的长度大于其它字符串的长度，则从位置pos开始替换。若任何一个参数为null，则返回值为NULL。

    public static IFunction INSERT(String str, Number pos, Number len, String newstr) {
        return create("INSERT").paramWS(str, pos, len, newstr);
    }

    // ------ 返回字符串 str 中子字符串的第一个出现位置。

    public static IFunction INSTR(String str, String substr) {
        return create("INSTR").paramWS(str, substr);
    }

    // ------ 返回从字符串str 开始的len 最左字符。

    public static IFunction LEFT(String str, Number len) {
        return create("LEFT").paramWS(str, len);
    }

    // ------ 返回值为字符串str 的长度，单位为字节。一个多字节字符算作多字节。这意味着 对于一个包含5个2字节字符的字符串， LENGTH() 的返回值为 10, 而 CHAR_LENGTH()的返回值则为5。

    public static IFunction LENGTH(String str) {
        return create("LEHGTH").param(str);
    }

    // ------ 读取文件并将这一文件按照字符串的格式返回。 文件的位置必须在服务器上,你必须为文件制定路径全名，而且你还必须拥有FILE 特许权。文件必须可读取，文件容量必须小于 max_allowed_packet字节。

    public static IFunction LOAD_FILE(String str) {
        return create("LOAD_FILE").param(str);
    }

    // ------ 第一个语法返回字符串 str中子字符串substr的第一个出现位置。第二个语法返回字符串 str中子字符串substr的第一个出现位置, 起始位置在pos。如若substr 不在str中，则返回值为0。

    public static IFunction LOCATE(String substr, String str) {
        return create("LOCATE").paramWS(substr, str);
    }

    public static IFunction LOCATE(String substr, String str, String pos) {
        return create("LOCATE").paramWS(substr, str, pos);
    }

    // ------ 返回字符串 str 以及所有根据最新的字符集映射表变为小写字母的字符

    public static IFunction LOWER(String str) {
        return create("LOWER").param(str);
    }

    // ------ 返回字符串 str, 其左边由字符串padstr 填补到len 字符长度。假如str 的长度大于len, 则返回值被缩短至 len 字符。

    public static IFunction LPAD(String str, String len, String padstr) {
        return create("LPAD").paramWS(str, len, padstr);
    }

    // ------ 返回字符串 str ，其引导空格字符被删除

    public static IFunction LTRIM(String str) {
        return create("LTRIM").param(str);
    }

    // ------ 返回一个 N的八进制值的字符串表示，其中 N 是一个longlong (BIGINT)数。这等同于CONV(N,10,8)。若N 为 NULL ，则返回值为NULL。

    public static IFunction OCT(String str) {
        return create("OCT").param(str);
    }

    // ------ 若字符串str 的最左字符是一个多字节字符，则返回该字符的代码

    public static IFunction ORD(String str) {
        return create("ORD").param(str);
    }

    // ------ 引证一个字符串，由此产生一个在SQL语句中可用作完全转义数据值的结果。

    public static IFunction QUOTE(String str) {
        return create("QUOTE").param(str);
    }

    // ------ 返回一个由重复的字符串str 组成的字符串，字符串str的数目等于count 。 若 count <= 0,则返回一个空字符串。若str 或 count 为 NULL，则返回 NULL 。

    public static IFunction REPEAT(String str, Number count) {
        return create("REPEAT").paramWS(str, count);
    }

    // ------ 返回字符串str 以及所有被字符串to_str替代的字符串from_str 。

    public static IFunction REPLACE(String str, String fromStr, String toStr) {
        return create("REPLACE").paramWS(str, fromStr, toStr);
    }

    // ------ 返回字符串 str ，顺序和字符顺序相反。

    public static IFunction REVERSE(String str) {
        return create("REVERSE").param(str);
    }

    // ------ 从字符串str 开始，返回最右len 字符。

    public static IFunction RIGHT(String str, Number len) {
        return create("RIGHT").paramWS(str, len);
    }

    // ------ 返回字符串str, 其右边被字符串 padstr填补至len 字符长度。假如字符串str 的长度大于 len,则返回值被缩短到与 len 字符相同长度。

    public static IFunction RPAD(String str, Number len, String padstr) {
        return create("RPAD").paramWS(str, len, padstr);
    }

    // ------ 返回字符串 str ，结尾空格字符被删去

    public static IFunction RTRIM(String str) {
        return create("RTRIM").param(str);
    }

    // ------ 从str返回一个soundex字符串。

    public static IFunction SOUNDEX(String str) {
        return create("SOUNDEX").param(str);
    }

    // ------ 返回一个由N 间隔符号组成的字符串。

    public static IFunction SPACE(Number n) {
        return create("SPACE").param(n);
    }

    // ------ 若所有的字符串均相同，则返回STRCMP()，若根据当前分类次序，第一个参数小于第二个，则返回  -1，其它情况返回 1

    public static IFunction STRCMP(String expr1, String expr2) {
        return create("STRCMP").paramWS(expr1, expr2);
    }

    // ------ 不带有len 参数的格式从字符串str返回一个子字符串，起始于位置 pos。带有len参数的格式从字符串str返回一个长度同len字符相同的子字符串，起始于位置 pos。

    public static IFunction SUBSTRING(String str, Number pos) {
        return create("SUBSTRING").paramWS(str, pos);
    }

    public static IFunction SUBSTRING(String str, Number pos, Number len) {
        return create("SUBSTRING").paramWS(str, pos, len);
    }

    // ------ 在定界符 delim 以及count 出现前，从字符串str返回自字符串。若count为正值,则返回最终定界符(从左边开始)左边的一切内容。若count为负值，则返回定界符（从右边开始）右边的一切内容。

    public static IFunction SUBSTRING_INDEX(String str, String delim, Number count) {
        return create("SUBSTRING_INDEX").paramWS(str, delim, count);
    }

    // ------ 返回字符串 str ， 其中所有remstr 前缀和/或后缀都已被删除。若分类符BOTH、LEADIN或TRAILING中没有一个是给定的,则假设为BOTH 。 remstr 为可选项，在未指定情况下，可删除空格。

    public static IFunction TRIM(String str) {
        return create("TRIM").param(str);
    }

    public static IFunction TRIM_BOTH(String remstr, String str) {
        return create("TRIM").param("BOTH " + remstr + " FROM " + str);
    }

    public static IFunction TRIM_LEADIN(String remstr, String str) {
        return create("TRIM").param("LEADIN " + remstr + " FROM " + str);
    }

    public static IFunction TRIM_TRAILING(String remstr, String str) {
        return create("TRIM").param("TRAILING " + remstr + " FROM " + str);
    }

    // ------ 执行从HEX(str)的反向操作。就是说，它将参数中的每一对十六进制数字理解为一个数字，并将其转化为该数字代表的字符。结果字符以二进制字符串的形式返回。

    public static IFunction UNHEX(String str) {
        return create("UNHEX").param(str);
    }

    // ------ 返回字符串str， 以及根据最新字符集映射转化为大写字母的字符

    public static IFunction UPPER(String str) {
        return create("UPPER").param(str);
    }

    // ----------
    // Date and Time Functions
    // ----------

    // ------ 若 days 参数只是整数值，将其作为天数值添加至 expr。

    public static IFunction ADDDATE(String expr, Number days) {
        return create("ADDDATE").paramWS(expr, days);
    }

    // ------ 将 expr2添加至expr 然后返回结果。 expr 是一个时间或时间日期表达式，而expr2 是一个时间表达式。

    public static IFunction ADDTIME(String expr, String expr2) {
        return create("ADDTIME").paramWS(expr, expr2);
    }

    // ------ 将时间日期值dt 从from_tz 给出的时区转到to_tz给出的时区，然后返回结果值。

    public static IFunction CONVERT_TZ(String dt, String fromTz, String toTz) {
        return create("CONVERT_TZ").paramWS(dt, fromTz, toTz);
    }

    // ------ 将当前日期按照'YYYY-MM-DD' 或YYYYMMDD 格式的值返回，具体格式根据函数用在字符串或是数字语境中而定。

    public static IFunction CURDATE() {
        return create("CURDATE");
    }

    // ------ 将当前时间以'HH:MM:SS'或 HHMMSS 的格式返回， 具体格式根据函数用在字符串或是数字语境中而定。

    public static IFunction CURTIME() {
        return create("CURTIME");
    }

    // ------ 提取日期或时间日期表达式expr中的日期部分

    public static IFunction DATE(String expr) {
        return create("DATE").param(expr);
    }

    // ------ 根据format 字符串安排date 值的格式。

    public static IFunction DATE_FORMAT(String date, String format) {
        return create("DATE_FORMAT").paramWS(date, format);
    }

    // ------ 返回起始时间 expr和结束时间expr2之间的天数。Expr和expr2 为日期或 date-and-time 表达式。计算中只用到这些值的日期部分。

    public static IFunction DATEDIFF(String expr, String expr2) {
        return create("DATEDIFF").paramWS(expr, expr2);
    }

    // ------ 返回date 对应的工作日名称。

    public static IFunction DAYNAME(String date) {
        return create("DAYNAME").param(date);
    }

    // ------ 返回date 对应的该月日期，范围是从 1到31。

    public static IFunction DAYOFMONTH(String date) {
        return create("DAYOFMONTH").param(date);
    }

    // ------ 返回date (1 = 周日, 2 = 周一, ..., 7 = 周六)对应的工作日索引。这些索引值符合 ODBC标准。

    public static IFunction DAYOFWEEK(String date) {
        return create("DAYOFWEEK").param(date);
    }

    // ------ 返回date 对应的一年中的天数，范围是从 1到366。

    public static IFunction DAYOFYEAR(String date) {
        return create("DAYOFYEAR").param(date);
    }

    // ------ 返回'YYYY-MM-DD HH:MM:SS'或YYYYMMDDHHMMSS 格式值的unix_timestamp参数表示，具体格式取决于该函数是否用在字符串中或是数字语境中。

    public static IFunction FROM_UNIXTIME(String timestamp) {
        return create("FROM_UNIXTIME").param(timestamp);
    }

    // ------ 若无参数调用，则返回一个Unix timestamp ('1970-01-01 00:00:00' GMT 之后的秒数) 作为无符号整数。
    // ------ 若用date 来调用UNIX_TIMESTAMP()，它会将参数值以'1970-01-01 00:00:00' GMT后的秒数的形式返回。date 可以是一个DATE 字符串、一个 DATETIME字符串、一个 TIMESTAMP或一个当地时间的YYMMDD 或YYYMMDD格式的数字。

    public static IFunction UNIX_TIMESTAMP() {
        return create("UNIX_TIMESTAMP");
    }

    public static IFunction UNIX_TIMESTAMP(String date) {
        return create("UNIX_TIMESTAMP").param(date);
    }

    // ------ 返回一个格式字符串。

    public static IFunction GET_FORMAT(String date, String type) {
        return create("GET_FORMAT").paramWS(date, type);
    }

    // ------ 返回time 对应的小时数。对于日时值的返回值范围是从 0 到 23 。

    public static IFunction HOUR(String time) {
        return create("HOUR").param(time);
    }

    // ------ 获取一个日期或日期时间值，返回该月最后一天对应的值。若参数无效，则返回NULL。

    public static IFunction LAST_DAY(String date) {
        return create("LAST_DAY").param(date);
    }

    // ------ 给出年份值和一年中的天数值，返回一个日期。dayofyear 必须大于 0 ，否则结果为 NULL。

    public static IFunction MAKEDATE(String year, String dayOfYear) {
        return create("MAKEDATE").paramWS(year, dayOfYear);
    }

    // ------ 返回由hour、 minute和second 参数计算得出的时间值

    public static IFunction MAKETIME(String hour, String minute, String second) {
        return create("MAKETIME").paramWS(hour, minute, second);
    }

    // ------ 从时间或日期时间表达式expr返回微秒值，其数字范围从 0到 999999。

    public static IFunction MICROSECOND(String expr) {
        return create("MICROSECOND").param(expr);
    }

    // ------ 返回 time 对应的分钟数,范围是从 0 到 59。

    public static IFunction MINUTE(String time) {
        return create("MINUTE").param(time);
    }

    // ------ 返回date 对应的月份，范围时从 1 到 12。

    public static IFunction MONTH(String date) {
        return create("MONTH").param(date);
    }

    // ------ 返回date 对应月份的全名。

    public static IFunction MONTHNAME(String date) {
        return create("MONTHNAME").param(date);
    }

    // ------ 返回当前日期和时间值，其格式为 'YYYY-MM-DD HH:MM:SS' 或YYYYMMDDHHMMSS ， 具体格式取决于该函数是否用在字符串中或数字语境中。

    public static IFunction NOW() {
        return create("NOW");
    }

    // ------ 添加 N 个月至周期P (格式为YYMM 或YYYYMM)，返回值的格式为 YYYYMM。注意周期参数 P 不是日期值。

    public static IFunction PERIOD_ADD(String p, String n) {
        return create("PERIOD_ADD").paramWS(p, n);
    }

    // ------ 返回周期P1和 P2 之间的月份数。P1 和P2 的格式应该为YYMM或YYYYMM。注意周期参数 P1和P2 不是日期值。

    public static IFunction PERIOD_DIFF(String p1, String p2) {
        return create("PERIOD_DIFF").paramWS(p1, p2);
    }

    // ------ 返回date 对应的一年中的季度值，范围是从 1到 4。

    public static IFunction QUARTER(String date) {
        return create("QUARTER").param(date);
    }

    // ------ 返回被转化为小时、 分钟和秒数的seconds参数值, 其格式为 'HH:MM:SS' 或HHMMSS，具体格式根据该函数是否用在字符串或数字语境中而定。

    public static IFunction SEC_TO_TIME(String seconds) {
        return create("SEC_TO_TIME").param(seconds);
    }

    // ------ 返回time 对应的秒数, 范围是从 0到59。

    public static IFunction SECOND(String date) {
        return create("SECOND").param(date);
    }

    // ------ 这是DATE_FORMAT() 函数的倒转。它获取一个字符串 str 和一个格式字符串format。若格式字符串包含日期和时间部分，则 STR_TO_DATE()返回一个 DATETIME 值， 若该字符串只包含日期部分或时间部分，则返回一个 DATE 或TIME值。

    public static IFunction STR_TO_DATE(String str, String format) {
        return create("STR_TO_DATE").paramWS(str, format);
    }

    // ------ 返回当前日期和时间值，格式为'YYYY-MM-DD HH:MM:SS' 或YYYYMMDDHHMMSS， 具体格式根据函数是否用在字符串或数字语境而定。

    public static IFunction SYSDATE() {
        return create("SYSDATE");
    }

    // ------ 提取一个时间或日期时间表达式的时间部分，并将其以字符串形式返回。

    public static IFunction TIME(String expr) {
        return create("TIME").param(expr);
    }

    // ------ 其使用和 DATE_FORMAT()函数相同, 然而format 字符串可能仅会包含处理小时、分钟和秒的格式说明符。其它说明符产生一个NULL值或0。

    public static IFunction TIME_FORMAT(String time, String format) {
        return create("TIME_FORMAT").paramWS(time, format);
    }

    // ------ 返回已转化为秒的time参数。

    public static IFunction TIME_TO_SEC(String time) {
        return create("TIME_TO_SEC").param(time);
    }

    // ------ 返回起始时间 expr 和结束时间expr2 之间的时间。 expr 和expr2 为时间或 date-and-time 表达式,两个的类型必须一样。

    public static IFunction TIMEDIFF(String expr, String expr2) {
        return create("TIMEDIFF").paramWS(expr, expr2);
    }

    // ------ 对于一个单参数,该函数将日期或日期时间表达式 expr 作为日期时间值返回.对于两个参数, 它将时间表达式 expr2 添加到日期或日期时间表达式 expr 中，将theresult作为日期时间值返回。

    public static IFunction TIMESTAMP(String expr) {
        return create("TIMESTAMP").param(expr);
    }

    public static IFunction TIMESTAMP(String expr, String expr2) {
        return create("TIMESTAMP").paramWS(expr, expr2);
    }

    // ------ 返回日期或日期时间表达式datetime_expr1 和datetime_expr2the 之间的整数差。其结果的单位由interval 参数给出。

    public static IFunction TIMESTAMPDIFF(String interval, String datetimeExpr1, String datetimeExpr2) {
        return create("TIMESTAMPDIFF").paramWS(interval, datetimeExpr1, datetimeExpr2);
    }

    // ------ 给定一个日期date, 返回一个天数 (从年份0开始的天数 )。

    public static IFunction TO_DAYS(String date) {
        return create("TO_DAYS").param(date);
    }

//    public abstract Func TO_SECONDS();

    // ------ 返回当前 UTC日期值，其格式为 'YYYY-MM-DD' 或 YYYYMMDD，具体格式取决于函数是否用在字符串或数字语境中。

    public static IFunction UTC_DATE() {
        return create("UTC_DATE");
    }

    // ------ 返回当前 UTC 值，其格式为  'HH:MM:SS' 或HHMMSS，具体格式根据该函数是否用在字符串或数字语境而定。

    public static IFunction UTC_TIME() {
        return create("UTC_TIME");
    }

    // ------ 返回当前UTC日期及时间值，格式为 'YYYY-MM-DD HH:MM:SS' 或YYYYMMDDHHMMSS，具体格式根据该函数是否用在字符串或数字语境而定。

    public static IFunction UTC_TIMESTAMP() {
        return create("UTC_TIMESTAMP");
    }

    // ------ 该函数返回date 对应的星期数。WEEK() 的双参数形式允许你指定该星期是否起始于周日或周一， 以及返回值的范围是否为从0 到53 或从1 到53。若 mode参数被省略，则使用default_week_format系统自变量的值。

    public static IFunction WEEK(String date) {
        return create("WEEK").param(date);
    }

    public static IFunction WEEK(String date, Number mode) {
        return create("WEEK").paramWS(date, mode);
    }

    // ------ 返回date (0 = 周一, 1 = 周二, ... 6 = 周日)对应的工作日索引

    public static IFunction WEEKDAY(String date) {
        return create("WEEKDAY").param(date);
    }

    // ------ 将该日期的阳历周以数字形式返回，范围是从1到53。它是一个兼容度函数，相当于WEEK(date,3)。

    public static IFunction WEEKOFYEAR(String date) {
        return create("WEEKOFYEAR").param(date);
    }

    // ------ 返回date 对应的年份,范围是从1000到9999。

    public static IFunction YEAR(String date) {
        return create("YEAR").param(date);
    }

    // ------ 返回一个日期对应的年或周。start参数的工作同 start参数对 WEEK()的工作相同。结果中的年份可以和该年的第一周和最后一周对应的日期参数有所不同。

    public static IFunction YEARWEEK(String date) {
        return create("YEARWEEK").param(date);
    }

    public static IFunction YEARWEEK(String date, String start) {
        return create("YEARWEEK").paramWS(date, start);
    }

    // ----------
    // Aggregate (GROUP BY) Function Descriptions
    // ----------

    // ------ 返回expr 的平均值。 DISTINCT 选项可用于返回 expr的不同值的平均值。

    public static IFunction AVG(String expr) {
        return create("AVG").param(expr);
    }

    public static IFunction AVG(String expr, boolean distinct) {
        return create("AVG").param(distinct ? "DISTINCT " : "").param(expr);
    }

    // ------ 返回expr中所有比特的 bitwise AND 。计算执行的精确度为64比特(BIGINT) 。

    public static IFunction BIT_AND(String expr) {
        return create("BIT_AND").param(expr);
    }

    // ------ 返回expr 中所有比特的bitwise OR。计算执行的精确度为64比特(BIGINT) 。

    public static IFunction BIT_OR(String expr) {
        return create("BIT_OR").param(expr);
    }

    // ------ 返回expr 中所有比特的bitwise XOR。计算执行的精确度为64比特(BIGINT) 。

    public static IFunction BIT_XOR(String expr) {
        return create("BIT_XOR").param(expr);
    }

    // ------ 返回SELECT语句检索到的行中非NULL值的数目。

    public static IFunction COUNT(String expr) {
        return create("COUNT").param(expr);
    }

    public static IFunction COUNT(String expr, boolean distinct) {
        return create("COUNT").param(distinct ? "DISTINCT " : "").param(expr);
    }

    // ------

    public static IFunction GROUP_CONCAT(String expr) {
        return create("GROUP_CONCAT").param(expr);
    }

    // ------ 返回expr 的最小值和最大值。

    public static IFunction MAX(String expr) {
        return create("MAX").param(expr);
    }

    public static IFunction MIN(String expr) {
        return create("MIN").param(expr);
    }

    // ------ 返回expr 的总数。 若返回集合中无任何行，则 SUM() 返回NULL。

    public static IFunction SUM(String expr) {
        return create("SUM").param(expr);
    }

    // ----------
    // Control Flow Functions
    // ----------

//    public abstract Func CASE();

//    public abstract Func IF();

//    public abstract Func IFNULL();

//    public abstract Func NULLIF();
}
