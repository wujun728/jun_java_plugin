/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.StrUtil
 */
package com.jun.plugin.online.vo.form.component;

import cn.hutool.core.util.StrUtil;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.jun.plugin.online.entity.OnlineTableColumnEntity;

public class ComponentContext {
    private static /* synthetic */ int[] VE;
    private static /* synthetic */ String[] RE;
    private   /* synthetic */ AbstractComponent component;

    static {
        ComponentContext.Am();
        ComponentContext.VL();
    }

    private static boolean bm(int n) {
        return n != 0;
    }

    private static String uL(String Tqsb, String uqsb) {
        try {
            SecretKeySpec yqsb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(uqsb.getBytes(StandardCharsets.UTF_8)), VE[8]), "DES");
            Cipher Xqsb = Cipher.getInstance("DES");
            Xqsb.init(VE[2], yqsb);
            return new String(Xqsb.doFinal(Base64.getDecoder().decode(Tqsb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception wqsb) {
            wqsb.printStackTrace();
            return null;
        }
    }

    public ComponentContext(OnlineTableColumnEntity JRsb) {
        ComponentContext kRsb = null;
        if (ComponentContext.bm(StrUtil.equalsIgnoreCase((CharSequence)JRsb.getFormInput(), (CharSequence)RE[VE[0]]) ? 1 : 0)) {
            kRsb.component = new InputComponent(JRsb);
            "".length();
            if ("   ".length() < "  ".length()) {
                throw null;
            }
        } else if (ComponentContext.bm(StrUtil.equalsIgnoreCase((CharSequence)JRsb.getFormInput(), (CharSequence)RE[VE[1]]) ? 1 : 0)) {
            kRsb.component = new NumberComponent(JRsb);
            "".length();
            if (" ".length() != " ".length()) {
                throw null;
            }
        } else if (ComponentContext.bm(StrUtil.equalsIgnoreCase((CharSequence)JRsb.getFormInput(), (CharSequence)RE[VE[2]]) ? 1 : 0)) {
            kRsb.component = new CheckboxComponent(JRsb);
            "".length();
            if (null != null) {
                throw null;
            }
        } else if (ComponentContext.bm(StrUtil.equalsIgnoreCase((CharSequence)JRsb.getFormInput(), (CharSequence)RE[VE[3]]) ? 1 : 0)) {
            kRsb.component = new DateComponent(JRsb);
            "".length();
            if ("  ".length() == " ".length()) {
                throw null;
            }
        } else if (ComponentContext.bm(StrUtil.equalsIgnoreCase((CharSequence)JRsb.getFormInput(), (CharSequence)RE[VE[4]]) ? 1 : 0)) {
            kRsb.component = new DateTimeComponent(JRsb);
            "".length();
            if ("   ".length() == 0) {
                throw null;
            }
        } else if (ComponentContext.bm(StrUtil.equalsIgnoreCase((CharSequence)JRsb.getFormInput(), (CharSequence)RE[VE[5]]) ? 1 : 0)) {
            kRsb.component = new RadioComponent(JRsb);
            "".length();
            if (((2 + 0 - -37 + 150 ^ 10 + 133 - 122 + 150) & (0x3A ^ 0x6E ^ (0xD1 ^ 0x93) ^ -" ".length())) != 0) {
                throw null;
            }
        } else if (ComponentContext.bm(StrUtil.equalsIgnoreCase((CharSequence)JRsb.getFormInput(), (CharSequence)RE[VE[6]]) ? 1 : 0)) {
            kRsb.component = new RateComponent(JRsb);
            "".length();
            if ("   ".length() >= (97 + 124 - 212 + 176 ^ 100 + 50 - -37 + 2)) {
                throw null;
            }
        } else if (ComponentContext.bm(StrUtil.equalsIgnoreCase((CharSequence)JRsb.getFormInput(), (CharSequence)RE[VE[7]]) ? 1 : 0)) {
            kRsb.component = new SelectComponent(JRsb);
            "".length();
            if ((0x3F ^ 0x3A) <= 0) {
                throw null;
            }
        } else if (ComponentContext.bm(StrUtil.equalsIgnoreCase((CharSequence)JRsb.getFormInput(), (CharSequence)RE[VE[8]]) ? 1 : 0)) {
            kRsb.component = new SliderComponent(JRsb);
            "".length();
            if (null != null) {
                throw null;
            }
        } else if (ComponentContext.bm(StrUtil.equalsIgnoreCase((CharSequence)JRsb.getFormInput(), (CharSequence)RE[VE[9]]) ? 1 : 0)) {
            kRsb.component = new SwitchComponent(JRsb);
            "".length();
            if (null != null) {
                throw null;
            }
        } else if (ComponentContext.bm(StrUtil.equalsIgnoreCase((CharSequence)JRsb.getFormInput(), (CharSequence)RE[VE[10]]) ? 1 : 0)) {
            kRsb.component = new TextareaComponent(JRsb);
            "".length();
            if (((0x79 ^ 0x50) & ~(0xC ^ 0x25)) != 0) {
                throw null;
            }
        } else if (ComponentContext.bm(StrUtil.equalsIgnoreCase((CharSequence)JRsb.getFormInput(), (CharSequence)RE[VE[11]]) ? 1 : 0)) {
            kRsb.component = new TimeComponent(JRsb);
            "".length();
            if (((0x88 ^ 0xAD ^ (0xC ^ 0x37)) & (0x78 ^ 0x6D ^ (0x3A ^ 0x31) ^ -" ".length())) > (0x60 ^ 0x36 ^ (0x2A ^ 0x78))) {
                throw null;
            }
        } else {
            kRsb.component = new InputComponent(JRsb);
        }
    }

    private static String TL(String Eqsb, String yPsb) {
        Eqsb = new String(Base64.getDecoder().decode(Eqsb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder Cqsb = new StringBuilder();
        char[] bqsb = yPsb.toCharArray();
        int Aqsb = VE[0];
        char[] uPsb = Eqsb.toCharArray();
        int TPsb = uPsb.length;
        int sPsb = VE[0];
        while (ComponentContext.Gm(sPsb, TPsb)) {
            char fqsb = uPsb[sPsb];
            Cqsb.append((char)(fqsb ^ bqsb[Aqsb % bqsb.length]));
            "".length();
            ++Aqsb;
            ++sPsb;
            "".length();
            if ("   ".length() != 0) continue;
            return null;
        }
        return String.valueOf(Cqsb);
    }

    private static void Am() {
        VE = new int[13];
        ComponentContext.VE[0] = (0x57 ^ 0x6E) & ~(0x5D ^ 0x64);
        ComponentContext.VE[1] = " ".length();
        ComponentContext.VE[2] = "  ".length();
        ComponentContext.VE[3] = "   ".length();
        ComponentContext.VE[4] = 0x2D ^ 0x29;
        ComponentContext.VE[5] = 0xB6 ^ 0x8A ^ (0xA9 ^ 0x90);
        ComponentContext.VE[6] = 0xA7 ^ 0xA1;
        ComponentContext.VE[7] = 0xDE ^ 0xAC ^ (0xF4 ^ 0x81);
        ComponentContext.VE[8] = 0x29 ^ 0x6F ^ (0x25 ^ 0x6B);
        ComponentContext.VE[9] = 0x28 ^ 0x44 ^ (0x5B ^ 0x3E);
        ComponentContext.VE[10] = 0x61 ^ 0x6B;
        ComponentContext.VE[11] = 0x80 ^ 0xBA ^ (0xF1 ^ 0xC0);
        ComponentContext.VE[12] = 0x20 ^ 0x4D ^ (0xF9 ^ 0x98);
    }

    public Map<String, Object> getComponent() {
        ComponentContext dRsb = null;
        return dRsb.component.getComponent();
    }

    private static String RL(String GPsb, String hPsb) {
        try {
            SecretKeySpec mPsb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(hPsb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher LPsb = Cipher.getInstance("Blowfish");
            LPsb.init(VE[2], mPsb);
            return new String(LPsb.doFinal(Base64.getDecoder().decode(GPsb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception kPsb) {
            kPsb.printStackTrace();
            return null;
        }
    }

    private static void VL() {
        RE = new String[VE[12]];
        ComponentContext.RE[ComponentContext.VE[0]] = ComponentContext.RL("aju9b3VpLZY=", "UqOJv");
        ComponentContext.RE[ComponentContext.VE[1]] = ComponentContext.RL("wAWd0PY68QI=", "RQYbj");
        ComponentContext.RE[ComponentContext.VE[2]] = ComponentContext.TL("OR4tLjs4GTA=", "ZvHMP");
        ComponentContext.RE[ComponentContext.VE[3]] = ComponentContext.TL("PC0sMQ==", "XLXTi");
        ComponentContext.RE[ComponentContext.VE[4]] = ComponentContext.TL("FSAHEhkYLBY=", "qAswm");
        ComponentContext.RE[ComponentContext.VE[5]] = ComponentContext.uL("pKbG/dlIRFA=", "YIBCa");
        ComponentContext.RE[ComponentContext.VE[6]] = ComponentContext.TL("Jg4RCw==", "Toenk");
        ComponentContext.RE[ComponentContext.VE[7]] = ComponentContext.uL("FZPe1snggRg=", "GAOot");
        ComponentContext.RE[ComponentContext.VE[8]] = ComponentContext.RL("efgGBtWoDKs=", "LqGYc");
        ComponentContext.RE[ComponentContext.VE[9]] = ComponentContext.TL("PB8sGTIn", "OhEmQ");
        ComponentContext.RE[ComponentContext.VE[10]] = ComponentContext.uL("TN4Y5H/jsIQXTPFhyu/jGQ==", "FLvlI");
        ComponentContext.RE[ComponentContext.VE[11]] = ComponentContext.uL("D/RnoXF2QgA=", "ZBNGp");
    }

    private static boolean Gm(int n, int n2) {
        return n < n2;
    }
}

