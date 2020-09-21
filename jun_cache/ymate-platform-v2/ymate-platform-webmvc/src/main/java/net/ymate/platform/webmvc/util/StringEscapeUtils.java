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
package net.ymate.platform.webmvc.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/5 下午4:35
 * @version 1.0
 */
public class StringEscapeUtils {

    private static final Log __LOG = LogFactory.getLog(StringEscapeUtils.class);

    private static final Map<String, Character> __HTML_TO_CHAR_MAPS = new HashMap<String, Character>();
    private static final String[] __CHAR_TO_HTML_ARRAY = new String[3000];
    private static final char REFERENCE_START = '&';
    private static final String DECIMAL_REFERENCE_START = "&#";
    private static final String HEX_REFERENCE_START = "&#x";
    private static final char REFERENCE_END = ';';
    private static final char CHAR_NULL = (char) -1;

    private static final char CSV_DELIMITER = ',';
    private static final char CSV_QUOTE = '"';
    private static final String CSV_QUOTE_STR = String.valueOf(CSV_QUOTE);
    private static final char CR = '\r';
    private static final char LF = '\n';
    private static final char[] CSV_SEARCH_CHARS = new char[]{CSV_DELIMITER, CSV_QUOTE, CR, LF};

    static {
        Properties _configs = new Properties();
        InputStream _in = MimeTypeUtils.class.getClassLoader().getResourceAsStream("htmlchars-conf.properties");
        if (_in == null) {
            _in = MimeTypeUtils.class.getClassLoader().getResourceAsStream("META-INF/htmlchars-default-conf.properties");
        }
        if (_in != null) {
            try {
                _configs.load(_in);
            } catch (Exception e) {
                __LOG.warn("", e);
            } finally {
                try {
                    _in.close();
                } catch (IOException e) {
                    __LOG.warn("", e);
                }
            }
        }
        //
        Enumeration _names = _configs.propertyNames();
        while (_names.hasMoreElements()) {
            String _name = (String) _names.nextElement();
            int _char = Integer.parseInt(_name);
            int _idx = (_char < 1000 ? _char : _char - 7000);
            String _ref = _configs.getProperty(_name);
            __CHAR_TO_HTML_ARRAY[_idx] = REFERENCE_START + _ref + REFERENCE_END;
            __HTML_TO_CHAR_MAPS.put(_ref, (char) _char);
        }
    }

    private static String __doConvertToReference(char character) {
        if (character < 1000 || (character >= 8000 && character < 10000)) {
            int _idx = (character < 1000 ? character : character - 7000);
            String _html = __CHAR_TO_HTML_ARRAY[_idx];
            if (_html != null) {
                return _html;
            }
        }
        return null;
    }

    private static char __doConvertToCharacter(String entityReference) {
        Character _char = __HTML_TO_CHAR_MAPS.get(entityReference);
        if (_char != null) {
            return _char;
        }
        return CHAR_NULL;
    }

    private static int __doGetSupportedReferenceCount() {
        return __HTML_TO_CHAR_MAPS.size();
    }

    private static boolean __isMappedToReference(char character) {
        return (__doConvertToReference(character) != null);
    }

    public static String escapeHtml(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder escaped = new StringBuilder(input.length() * 2);
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            String reference = __doConvertToReference(character);
            if (reference != null) {
                escaped.append(reference);
            } else {
                escaped.append(character);
            }
        }
        return escaped.toString();
    }

    public static String escapeHtmlDecimal(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder escaped = new StringBuilder(input.length() * 2);
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            if (__isMappedToReference(character)) {
                escaped.append(DECIMAL_REFERENCE_START);
                escaped.append((int) character);
                escaped.append(REFERENCE_END);
            } else {
                escaped.append(character);
            }
        }
        return escaped.toString();
    }

    public static String escapeHtmlHex(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder escaped = new StringBuilder(input.length() * 2);
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            if (__isMappedToReference(character)) {
                escaped.append(HEX_REFERENCE_START);
                escaped.append(Integer.toString((int) character, 16));
                escaped.append(REFERENCE_END);
            } else {
                escaped.append(character);
            }
        }
        return escaped.toString();
    }

    public static String escapeXml(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder escaped = new StringBuilder(input.length() * 2);
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            switch (character) {
                case 34:
                case 38:
                case 60:
                case 62:
                case 39:
                    String _char = __doConvertToReference(character);
                    if (_char != null) {
                        escaped.append(_char);
                    } else {
                        escaped.append(character);
                    }
                    break;
                default:
                    escaped.append(character);
            }
        }
        return escaped.toString();
    }

    public static String unescapeHtml(String input) {
        if (input == null) {
            return null;
        }
        return new HtmlCharacterEntityDecoder(input).decode();
    }

    public static String unescapeXml(String input) {
        if (input == null) {
            return null;
        }
        return new HtmlCharacterEntityDecoder(input).decode();
    }

    public static String escapeJavaScript(String input) {
        StringBuilder writer = new StringBuilder();
        escapeJavaStyleString(writer, input, true, true);
        return writer.toString();
    }

    public static String escapeJava(String input) {
        StringBuilder writer = new StringBuilder();
        escapeJavaStyleString(writer, input, false, false);
        return writer.toString();
    }

    private static String hex(char ch) {
        return Integer.toHexString(ch).toUpperCase(Locale.ENGLISH);
    }

    private static void escapeJavaStyleString(StringBuilder out, String str, boolean escapeSingleQuote, boolean escapeForwardSlash) {
        if (out == null) {
            throw new IllegalArgumentException("The Writer must not be null");
        }
        if (str == null) {
            return;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);
            if (ch > 0xfff) {
                out.append("\\u").append(hex(ch));
            } else if (ch > 0xff) {
                out.append("\\u0").append(hex(ch));
            } else if (ch > 0x7f) {
                out.append("\\u00").append(hex(ch));
            } else if (ch < 32) {
                switch (ch) {
                    case '\b':
                        out.append('\\');
                        out.append('b');
                        break;
                    case '\n':
                        out.append('\\');
                        out.append('n');
                        break;
                    case '\t':
                        out.append('\\');
                        out.append('t');
                        break;
                    case '\f':
                        out.append('\\');
                        out.append('f');
                        break;
                    case '\r':
                        out.append('\\');
                        out.append('r');
                        break;
                    default:
                        if (ch > 0xf) {
                            out.append("\\u00").append(hex(ch));
                        } else {
                            out.append("\\u000").append(hex(ch));
                        }
                        break;
                }
            } else {
                switch (ch) {
                    case '\'':
                        if (escapeSingleQuote) {
                            out.append('\\');
                        }
                        out.append('\'');
                        break;
                    case '"':
                        out.append('\\');
                        out.append('"');
                        break;
                    case '\\':
                        out.append('\\');
                        out.append('\\');
                        break;
                    case '/':
                        if (escapeForwardSlash) {
                            out.append('\\');
                        }
                        out.append('/');
                        break;
                    default:
                        out.append(ch);
                        break;
                }
            }
        }
    }

    public static String unescapeJava(String str) {
        if (str == null) {
            return null;
        }
        int sz = str.length();
        StringBuilder out = new StringBuilder();
        StrBuilder unicode = new StrBuilder(4);
        boolean hadSlash = false;
        boolean inUnicode = false;
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);
            if (inUnicode) {
                unicode.append(ch);
                if (unicode.length() == 4) {
                    try {
                        int value = Integer.parseInt(unicode.toString(), 16);
                        out.append((char) value);
                        unicode.setLength(0);
                        inUnicode = false;
                        hadSlash = false;
                    } catch (NumberFormatException nfe) {
                        throw new NestableRuntimeException("Unable to parse unicode value: " + unicode, nfe);
                    }
                }
                continue;
            }
            if (hadSlash) {
                hadSlash = false;
                switch (ch) {
                    case '\\':
                        out.append('\\');
                        break;
                    case '\'':
                        out.append('\'');
                        break;
                    case '\"':
                        out.append('"');
                        break;
                    case 'r':
                        out.append('\r');
                        break;
                    case 'f':
                        out.append('\f');
                        break;
                    case 't':
                        out.append('\t');
                        break;
                    case 'n':
                        out.append('\n');
                        break;
                    case 'b':
                        out.append('\b');
                        break;
                    case 'u': {
                        inUnicode = true;
                        break;
                    }
                    default:
                        out.append(ch);
                        break;
                }
                continue;
            } else if (ch == '\\') {
                hadSlash = true;
                continue;
            }
            out.append(ch);
        }
        if (hadSlash) {
            out.append('\\');
        }
        return out.toString();
    }

    public static String unescapeJavaScript(String str) {
        return unescapeJava(str);
    }

    public static String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        return StringUtils.replace(str, "'", "''");
    }

    public static String escapeCsv(String str) {
        if (StringUtils.containsNone(str, CSV_SEARCH_CHARS)) {
            if (str != null) {
                return str;
            }
            return null;
        }
        StringBuilder _out = new StringBuilder();
        _out.append(CSV_QUOTE);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == CSV_QUOTE) {
                _out.append(CSV_QUOTE);
            }
            _out.append(c);
        }
        return _out.append(CSV_QUOTE).toString();
    }

    public static String unescapeCsv(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() < 2) {
            return str;
        }
        if (str.charAt(0) != CSV_QUOTE || str.charAt(str.length() - 1) != CSV_QUOTE) {
            return str;
        }
        String quoteless = str.substring(1, str.length() - 1);
        if (StringUtils.containsAny(quoteless, CSV_SEARCH_CHARS)) {
            str = StringUtils.replace(quoteless, CSV_QUOTE_STR + CSV_QUOTE_STR, CSV_QUOTE_STR);
        }
        return str;
    }

    static class HtmlCharacterEntityDecoder {

        private static final int MAX_REFERENCE_SIZE = 10;


        private final String originalMessage;

        private final StringBuilder decodedMessage;

        private int currentPosition = 0;

        private int nextPotentialReferencePosition = -1;

        private int nextSemicolonPosition = -2;


        public HtmlCharacterEntityDecoder(String original) {
            this.originalMessage = original;
            this.decodedMessage = new StringBuilder(originalMessage.length());
        }

        public String decode() {
            while (currentPosition < originalMessage.length()) {
                findNextPotentialReference(currentPosition);
                copyCharactersTillPotentialReference();
                processPossibleReference();
            }
            return decodedMessage.toString();
        }

        private void findNextPotentialReference(int startPosition) {
            nextPotentialReferencePosition = Math.max(startPosition, nextSemicolonPosition - MAX_REFERENCE_SIZE);

            do {
                nextPotentialReferencePosition =
                        originalMessage.indexOf('&', nextPotentialReferencePosition);

                if (nextSemicolonPosition != -1 &&
                        nextSemicolonPosition < nextPotentialReferencePosition)
                    nextSemicolonPosition = originalMessage.indexOf(';', nextPotentialReferencePosition + 1);

                boolean isPotentialReference =
                        nextPotentialReferencePosition != -1
                                && nextSemicolonPosition != -1
                                && nextPotentialReferencePosition - nextSemicolonPosition < MAX_REFERENCE_SIZE;

                if (isPotentialReference) {
                    break;
                }
                if (nextPotentialReferencePosition == -1) {
                    break;
                }
                if (nextSemicolonPosition == -1) {
                    nextPotentialReferencePosition = -1;
                    break;
                }

                nextPotentialReferencePosition = nextPotentialReferencePosition + 1;
            }
            while (nextPotentialReferencePosition != -1);
        }


        private void copyCharactersTillPotentialReference() {
            if (nextPotentialReferencePosition != currentPosition) {
                int skipUntilIndex = nextPotentialReferencePosition != -1 ?
                        nextPotentialReferencePosition : originalMessage.length();
                if (skipUntilIndex - currentPosition > 3) {
                    decodedMessage.append(originalMessage.substring(currentPosition, skipUntilIndex));
                    currentPosition = skipUntilIndex;
                } else {
                    while (currentPosition < skipUntilIndex)
                        decodedMessage.append(originalMessage.charAt(currentPosition++));
                }
            }
        }

        private void processPossibleReference() {
            if (nextPotentialReferencePosition != -1) {
                boolean isNumberedReference = originalMessage.charAt(currentPosition + 1) == '#';
                boolean wasProcessable = isNumberedReference ? processNumberedReference() : processNamedReference();
                if (wasProcessable) {
                    currentPosition = nextSemicolonPosition + 1;
                } else {
                    char currentChar = originalMessage.charAt(currentPosition);
                    decodedMessage.append(currentChar);
                    currentPosition++;
                }
            }
        }

        private boolean processNumberedReference() {
            boolean isHexNumberedReference =
                    originalMessage.charAt(nextPotentialReferencePosition + 2) == 'x' ||
                            originalMessage.charAt(nextPotentialReferencePosition + 2) == 'X';
            try {
                int value = (!isHexNumberedReference) ?
                        Integer.parseInt(getReferenceSubstring(2)) :
                        Integer.parseInt(getReferenceSubstring(3), 16);
                decodedMessage.append((char) value);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }

        private boolean processNamedReference() {
            String referenceName = getReferenceSubstring(1);
            char mappedCharacter = __doConvertToCharacter(referenceName);
            if (mappedCharacter != CHAR_NULL) {
                decodedMessage.append(mappedCharacter);
                return true;
            }
            return false;
        }

        private String getReferenceSubstring(int referenceOffset) {
            return originalMessage.substring(nextPotentialReferencePosition + referenceOffset, nextSemicolonPosition);
        }

    }
}
