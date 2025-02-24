package com.lianqu1990.springboot.web.version.mapping.condition;

import com.lianqu1990.springboot.web.version.mapping.annotation.TerminalVersion;
import com.lianqu1990.springboot.web.version.mapping.core.VersionOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hanchao
 * @date 2018/3/9 13:29
 */
public class ClientVersionRequestCondition extends AbstractRequestCondition<ClientVersionRequestCondition> {
    //consts
    public static final char SEP = ',';


    private final Set<TerminalVersionExpression> expressions;

    protected ClientVersionRequestCondition(Set<TerminalVersionExpression> expressions) {
        this.expressions = expressions;
    }

    public ClientVersionRequestCondition(String[] stringExpressions) {
        //待实现，适配string，可以使用正则快速匹配
        expressions = Collections.unmodifiableSet(parseByExpression(stringExpressions));
    }

    public ClientVersionRequestCondition(TerminalVersion[] terminalVersions) {
        expressions = Collections.unmodifiableSet(parseByTerminalVersion(terminalVersions));
    }

    private static Set<TerminalVersionExpression> parseByTerminalVersion(TerminalVersion[] terminalVersions) {
        Set<TerminalVersionExpression> expressions = new LinkedHashSet<TerminalVersionExpression>();
        for (TerminalVersion terminalVersion : terminalVersions) {
            expressions.add(new TerminalVersionExpression(terminalVersion.terminals(), terminalVersion.version(), terminalVersion.op()));
        }
        return expressions;
    }

    private static Set<TerminalVersionExpression> parseByExpression(String[] stringExpressions) {
        Set<TerminalVersionExpression> terminalExpressions = new LinkedHashSet<TerminalVersionExpression>();
        for (String expression : stringExpressions) {
            String regex = "([\\d,*]+)([!=<>]*)([\\d\\.]*)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(expression);
            while (matcher.find()) {
                int[] terminals = new int[]{};
                String version = "";
                VersionOperator operator = VersionOperator.NIL;
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    String content = matcher.group(i);
                    if (i == 1) {
                        if (StringUtils.isNotBlank(content) && !content.equalsIgnoreCase("*")) {
                            String[] split = content.split(String.valueOf(SEP));
                            terminals = new int[split.length];
                            for (int j = 0; j < split.length; j++) {
                                try {
                                    terminals[j] = Integer.parseInt(split[j]);
                                } catch (Exception e) {
                                    throw new IllegalArgumentException("is there a wrong number for terminal type?");
                                }
                            }
                        }
                    } else if (i == 2) {
                        operator = VersionOperator.parse(content);
                        if (operator == null) {
                            throw new IllegalArgumentException("check the versionOperator!!!");
                        }
                    } else if (i == 3) {
                        version = content;
                    }
                }
                terminalExpressions.add(new TerminalVersionExpression(terminals, version, operator));
                break;
            }
        }
        return terminalExpressions;
    }


    @Override
    protected Collection<?> getContent() {
        return this.expressions;
    }

    //同param,一项不满足，即匹配失败
    @Override
    protected String getToStringInfix() {
        return " && ";
    }

    @Override
    public ClientVersionRequestCondition combine(ClientVersionRequestCondition other) {
        Set<TerminalVersionExpression> set = new LinkedHashSet<TerminalVersionExpression>(this.expressions);
        set.addAll(other.expressions);
        return new ClientVersionRequestCondition(set);
    }

    @Override
    public ClientVersionRequestCondition getMatchingCondition(HttpServletRequest request) {
        for (TerminalVersionExpression expression : expressions) {
            if(!expression.match(request)){//同param condition,任意一个失败则失败
                return null;
            }
        }
        return this;
    }

    @Override
    public int compareTo(ClientVersionRequestCondition other, HttpServletRequest request) {
        return 0;
    }


    static class TerminalVersionExpression {
        public static final String HEADER_VERSION = "cv";
        public static final String HEADER_TERMINAL = "terminal";


        private int[] terminals;
        private String version;
        private VersionOperator operator;

        public int[] getTerminals() {
            return terminals;
        }

        public void setTerminals(int[] terminals) {
            this.terminals = terminals;
        }

        public String getVersion() {
            return version;
        }

        public TerminalVersionExpression(int[] terminals, String version, VersionOperator operator) {
            Arrays.sort(terminals);
            if (StringUtils.isNotBlank(version) && operator == VersionOperator.NIL) {
                throw new IllegalArgumentException("opetator cant be nil when version is existing...");
            }
            this.terminals = terminals;
            this.version = version;
            this.operator = operator;
        }


        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (terminals != null && terminals.length != 0) {
                builder.append(StringUtils.join(terminals, SEP, 0, terminals.length));
            } else {
                builder.append("*");
            }
            builder.append(operator.getCode());
            if (StringUtils.isNotBlank(version)) {
                builder.append(version);
            }
            return builder.toString();
        }


        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && obj instanceof TerminalVersionExpression) {
                //暂定最终的表达式结果一致确定唯一性，后期有需要调整
                return this.toString().equalsIgnoreCase(obj.toString());
            }
            return false;
        }

        //同上，如果需要复杂判定，则需要自己实现hashcode
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }

        public final boolean match(HttpServletRequest request) {
            //匹配客户端类型
            if (this.terminals != null && this.terminals.length > 0) {
                int terminal = getTerminal(request);
                int i = Arrays.binarySearch(terminals, terminal);
                //未找到则匹配失败
                if (i < 0) {
                    return false;
                }
            }
            if (this.operator != null && this.operator != VersionOperator.NIL) {
                String clientVersion = getVersion(request);
                String checkVersion = getVersion();
                if (StringUtils.isBlank(clientVersion)) {
                    //尽量保证快速失败
                    return false;
                }
                int i = clientVersion.compareToIgnoreCase(checkVersion);
                switch (operator) {
                    case GT:
                        return i > 0;
                    case GTE:
                        return i >= 0;
                    case LT:
                        return i < 0;
                    case LTE:
                        return i <= 0;
                    case EQ:
                        return i == 0;
                    case NE:
                        return i != 0;
                    default:
                        break;
                }
            }
            return true;
        }

        private int getTerminal(HttpServletRequest request) {
            String value = request.getHeader(HEADER_TERMINAL);
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
                return -1;
            }
        }

        private String getVersion(HttpServletRequest request) {
            return request.getHeader(HEADER_VERSION);
        }

    }
}
