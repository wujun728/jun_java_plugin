package com.yisin.dbc.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;

public class SQLFormatter {
    private static final Set<String> BEGIN_CLAUSES = new HashSet<String>();
    private static final Set<String> END_CLAUSES = new HashSet<String>();
    private static final Set<String> LOGICAL = new HashSet<String>();
    private static final Set<String> QUANTIFIERS = new HashSet<String>();
    private static final Set<String> DML = new HashSet<String>();
    private static final Set<String> MISC = new HashSet<String>();
    public static final String WHITESPACE = " \n\r\f\t";

    static {
        BEGIN_CLAUSES.add("left");
        BEGIN_CLAUSES.add("right");
        BEGIN_CLAUSES.add("inner");
        BEGIN_CLAUSES.add("outer");
        BEGIN_CLAUSES.add("group");
        BEGIN_CLAUSES.add("order");

        END_CLAUSES.add("where");
        END_CLAUSES.add("set");
        END_CLAUSES.add("having");
        END_CLAUSES.add("join");
        END_CLAUSES.add("from");
        END_CLAUSES.add("by");
        END_CLAUSES.add("join");
        END_CLAUSES.add("into");
        END_CLAUSES.add("union");

        LOGICAL.add("and");
        LOGICAL.add("or");
        LOGICAL.add("when");
        LOGICAL.add("else");
        LOGICAL.add("end");

        QUANTIFIERS.add("in");
        QUANTIFIERS.add("all");
        QUANTIFIERS.add("exists");
        QUANTIFIERS.add("some");
        QUANTIFIERS.add("any");

        DML.add("insert");
        DML.add("update");
        DML.add("delete");

        MISC.add("select");
        MISC.add("on");
    }

    static final String indentString = "    ";
    static final String initial = "\n    ";

    public String format(String source) {
        String sql = new FormatProcess(replaceKeyword(" " + source + " ")).perform();
        return sql;
    }

    public String replaceKeyword(String sql) {
        if(sql != null){
            Iterator<String> ite1 = BEGIN_CLAUSES.iterator();
            Iterator<String> ite2 = END_CLAUSES.iterator();
            Iterator<String> ite3 = LOGICAL.iterator();
            Iterator<String> ite4 = QUANTIFIERS.iterator();
            Iterator<String> ite5 = DML.iterator();
            Iterator<String> ite6 = MISC.iterator();
            while (ite1.hasNext()) {
                sql = replaceKey(sql, ite1.next());
            }
            while (ite2.hasNext()) {
                sql = replaceKey(sql, ite2.next());
            }
            while (ite3.hasNext()) {
                sql = replaceKey(sql, ite3.next());
            }
            while (ite4.hasNext()) {
                sql = replaceKey(sql, ite4.next());
            }
            while (ite5.hasNext()) {
                sql = replaceKey(sql, ite5.next());
            }
            while (ite6.hasNext()) {
                sql = replaceKey(sql, ite6.next());
            }
        }
        return sql;
    }
    
    private String replaceKey(String sql, String key){
        String value = " " + key.toUpperCase() + " ";
        sql = sql.replaceAll("[\t]+" + key + "[\t]", value);
        sql = sql.replaceAll("[\t]+" + key + "[\n]", value);
        sql = sql.replaceAll("[\t]+" + key + "[ ]", value);
        
        sql = sql.replaceAll("[\n]+" + key + "[\t]", value);
        sql = sql.replaceAll("[\n]+" + key + "[\n]", value);
        sql = sql.replaceAll("[\n]+" + key + "[ ]", value);
        
        sql = sql.replaceAll("[ ]+" + key + "[\t]", value);
        sql = sql.replaceAll("[ ]+" + key + "[\n]", value);
        sql = sql.replaceAll("[ ]+" + key + "[ ]", value);
        return sql;
    }

    private static class FormatProcess {
        boolean beginLine = true;
        boolean afterBeginBeforeEnd = false;
        boolean afterByOrSetOrFromOrSelect = false;
        boolean afterValues = false;
        boolean afterOn = false;
        boolean afterBetween = false;
        boolean afterInsert = false;
        int inFunction = 0;
        int parensSinceSelect = 0;
        private LinkedList<Integer> parenCounts = new LinkedList<Integer>();
        private LinkedList<Boolean> afterByOrFromOrSelects = new LinkedList<Boolean>();

        int indent = 1;

        StringBuilder result = new StringBuilder();
        StringTokenizer tokens;
        String lastToken;
        String token;
        String lcToken;

        public FormatProcess(String sql) {
            tokens = new StringTokenizer(sql, "()+*/-=<>'`\"[]," + WHITESPACE, true);
        }

        public String perform() {

            result.append(initial);

            while (tokens.hasMoreTokens()) {
                token = tokens.nextToken();
                lcToken = token.toLowerCase();

                if ("'".equals(token)) {
                    String t;
                    do {
                        t = tokens.nextToken();
                        token += t;
                    } while (!"'".equals(t) && tokens.hasMoreTokens()); // cannot
                                                                        // handle
                                                                        // single
                                                                        // quotes
                } else if ("\"".equals(token)) {
                    String t;
                    do {
                        t = tokens.nextToken();
                        token += t;
                    } while (!"\"".equals(t));
                }

                if (afterByOrSetOrFromOrSelect && ",".equals(token)) {
                    commaAfterByOrFromOrSelect();
                } else if (afterOn && ",".equals(token)) {
                    commaAfterOn();
                }

                else if ("(".equals(token)) {
                    openParen();
                } else if (")".equals(token)) {
                    closeParen();
                }

                else if (BEGIN_CLAUSES.contains(lcToken)) {
                    beginNewClause();
                }

                else if (END_CLAUSES.contains(lcToken)) {
                    endNewClause();
                }

                else if ("select".equals(lcToken)) {
                    select();
                }

                else if (DML.contains(lcToken)) {
                    updateOrInsertOrDelete();
                }

                else if ("values".equals(lcToken)) {
                    values();
                }

                else if ("on".equals(lcToken)) {
                    on();
                }

                else if (afterBetween && lcToken.equals("and")) {
                    misc();
                    afterBetween = false;
                }

                else if (LOGICAL.contains(lcToken)) {
                    logical();
                }

                else if (isWhitespace(token)) {
                    white();
                }

                else {
                    misc();
                }

                if (!isWhitespace(token)) {
                    lastToken = lcToken;
                }

            }
            return result.toString();
        }

        private void commaAfterOn() {
            out();
            indent--;
            newline();
            afterOn = false;
            afterByOrSetOrFromOrSelect = true;
        }

        private void commaAfterByOrFromOrSelect() {
            out();
            newline();
        }

        private void logical() {
            if ("end".equals(lcToken)) {
                indent--;
            }
            newline();
            out();
            beginLine = false;
        }

        private void on() {
            indent++;
            afterOn = true;
            newline();
            out();
            beginLine = false;
        }

        private void misc() {
            out();
            if ("between".equals(lcToken)) {
                afterBetween = true;
            }
            if (afterInsert) {
                newline();
                afterInsert = false;
            } else {
                beginLine = false;
                if ("case".equals(lcToken)) {
                    indent++;
                }
            }
        }

        private void white() {
            if (!beginLine) {
                result.append(" ");
            }
        }

        private void updateOrInsertOrDelete() {
            out();
            indent++;
            beginLine = false;
            if ("update".equals(lcToken)) {
                newline();
            }
            if ("insert".equals(lcToken)) {
                afterInsert = true;
            }
        }

        private void select() {
            out();
            indent++;
            newline();
            parenCounts.addLast(Integer.valueOf(parensSinceSelect));
            afterByOrFromOrSelects.addLast(Boolean.valueOf(afterByOrSetOrFromOrSelect));
            parensSinceSelect = 0;
            afterByOrSetOrFromOrSelect = true;
        }

        private void out() {
            result.append(token);
        }

        private void endNewClause() {
            if (!afterBeginBeforeEnd) {
                indent--;
                if (afterOn) {
                    indent--;
                    afterOn = false;
                }
                newline();
            }
            out();
            if (!"union".equals(lcToken)) {
                indent++;
            }
            newline();
            afterBeginBeforeEnd = false;
            afterByOrSetOrFromOrSelect = "by".equals(lcToken) || "set".equals(lcToken) || "from".equals(lcToken);
        }

        private void beginNewClause() {
            if (!afterBeginBeforeEnd) {
                if (afterOn) {
                    indent--;
                    afterOn = false;
                }
                indent--;
                newline();
            }
            out();
            beginLine = false;
            afterBeginBeforeEnd = true;
        }

        private void values() {
            indent--;
            newline();
            out();
            indent++;
            newline();
            afterValues = true;
        }

        private void closeParen() {
            parensSinceSelect--;
            if (parensSinceSelect < 0) {
                indent--;
                parensSinceSelect = parenCounts.removeLast().intValue();
                afterByOrSetOrFromOrSelect = afterByOrFromOrSelects.removeLast().booleanValue();
            }
            if (inFunction > 0) {
                inFunction--;
                out();
            } else {
                if (!afterByOrSetOrFromOrSelect) {
                    indent--;
                    newline();
                }
                out();
            }
            beginLine = false;
        }

        private void openParen() {
            if (isFunctionName(lastToken) || inFunction > 0) {
                inFunction++;
            }
            beginLine = false;
            if (inFunction > 0) {
                out();
            } else {
                out();
                if (!afterByOrSetOrFromOrSelect) {
                    indent++;
                    newline();
                    beginLine = true;
                }
            }
            parensSinceSelect++;
        }

        private static boolean isFunctionName(String tok) {
            final char begin = tok.charAt(0);
            final boolean isIdentifier = Character.isJavaIdentifierStart(begin) || '"' == begin;
            return isIdentifier && !LOGICAL.contains(tok) && !END_CLAUSES.contains(tok) && !QUANTIFIERS.contains(tok)
                    && !DML.contains(tok) && !MISC.contains(tok);
        }

        private static boolean isWhitespace(String token) {
            return WHITESPACE.indexOf(token) >= 0;
        }

        private void newline() {
            result.append("\n");
            for (int i = 0; i < indent; i++) {
                result.append(indentString);
            }
            beginLine = true;
        }
    }

    public static void main(String[] args) {
        String sql = new SQLFormatter().format("select * from user_info ui, cmu_user_info_ext ue where ui.user_id = ue.user_id");
        System.out.println(sql);
    }
}
