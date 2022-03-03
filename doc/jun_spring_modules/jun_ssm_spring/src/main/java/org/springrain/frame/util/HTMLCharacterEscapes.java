package org.springrain.frame.util;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;


/**
 * HTML特殊字符过滤
 * @author caomei
 *
 */

public class HTMLCharacterEscapes extends CharacterEscapes {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final int[] asciiEscapes;

    // < \u003C
    private static final SerializedString escapeFor003C = new SerializedString("&lt;");

    // > \u003E
    private static final SerializedString escapeFor003E = new SerializedString("&gt;");

    // / \u005C
    // private static final SerializedString escapeFor005C = new
    // SerializedString("\\");
    // & \u0026
    // private static final SerializedString escapeFor0026 = new
    // SerializedString("&amp;");

    public HTMLCharacterEscapes() {
        // start with set of characters known to require escaping (double-quote,
        // backslash etc)
        int[] esc = CharacterEscapes.standardAsciiEscapesForJSON();
        // and force escaping of a few others:
        esc['<'] = CharacterEscapes.ESCAPE_CUSTOM;
        esc['>'] = CharacterEscapes.ESCAPE_CUSTOM;
        // esc['&'] = CharacterEscapes.ESCAPE_CUSTOM;
        // esc['\''] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes = esc;
    }

    // this method gets called for character codes 0 - 127
    @Override
    public int[] getEscapeCodesForAscii() {
        return asciiEscapes;
    }

    // and this for others; we don't need anything special here
    @Override
    public SerializableString getEscapeSequence(int ch) {
        // no further escaping (beyond ASCII chars) needed:
        switch (ch) {
        case 0x003C:
            return escapeFor003C;
        case 0x003E:
            return escapeFor003E;
        // case 0x005C:
        // return escapeFor005C;
        // case 0x0026:
        // return escapeFor0026;
        default:
            return null;
        }
    }
}
