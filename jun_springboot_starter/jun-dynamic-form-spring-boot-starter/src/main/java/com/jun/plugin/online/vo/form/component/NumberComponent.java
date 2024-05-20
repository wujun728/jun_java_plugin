/*
 * Decompiled with CFR 0.152.
 */
package com.jun.plugin.online.vo.form.component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.jun.plugin.online.entity.OnlineTableColumnEntity;

public class NumberComponent
extends AbstractComponent {
    private static /* synthetic */ int[] Xd;
    private static /* synthetic */ String[] Td;

    private static String PJ(String yCub, String ZCub) {
        try {
            SecretKeySpec ddub = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(ZCub.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher Cdub = Cipher.getInstance("Blowfish");
            Cdub.init(Xd[2], ddub);
            return new String(Cdub.doFinal(Base64.getDecoder().decode(yCub.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception bdub) {
            bdub.printStackTrace();
            return null;
        }
    }

    public NumberComponent(OnlineTableColumnEntity udub) {
        NumberComponent Vdub = null;
        Vdub.column = udub;
    }

    private static String oJ(String wbub, String Vbub) {
        wbub = new String(Base64.getDecoder().decode(wbub.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder ubub = new StringBuilder();
        char[] Tbub = Vbub.toCharArray();
        int sbub = Xd[0];
        char[] mbub = wbub.toCharArray();
        int Lbub = mbub.length;
        int kbub = Xd[0];
        while (NumberComponent.Ak(kbub, Lbub)) {
            char Xbub = mbub[kbub];
            ubub.append((char)(Xbub ^ Tbub[sbub % Tbub.length]));
            "".length();
            ++sbub;
            ++kbub;
            "".length();
            if ("  ".length() >= 0) continue;
            return null;
        }
        return String.valueOf(ubub);
    }

    @Override
    public Map<String, Object> getComponent() {
        NumberComponent odub = null;
        HashMap<String, Object> Ndub = new HashMap<String, Object>();
        Ndub.put(Td[Xd[0]], odub.column.getFormDefault());
        "".length();
        Ndub.put(Td[Xd[1]], Td[Xd[2]]);
        "".length();
        Ndub.put(Td[Xd[3]], Xd[0]);
        "".length();
        Ndub.put(Td[Xd[4]], Xd[5]);
        "".length();
        Ndub.put(Td[Xd[6]], Xd[1]);
        "".length();
        Ndub.put(Td[Xd[7]], Xd[0]);
        "".length();
        Ndub.put(Td[Xd[8]], Td[Xd[9]]);
        "".length();
        Ndub.put(Td[Xd[10]], Xd[0]);
        "".length();
        Ndub.put(Td[Xd[11]], Xd[0]);
        "".length();
        Ndub.put(Td[Xd[12]], Xd[0]);
        "".length();
        Ndub.put(Td[Xd[13]], odub.getRules());
        "".length();
        HashMap<String, Object> mdub = new HashMap<String, Object>();
        mdub.put(Td[Xd[14]], odub.column.getFormInput());
        "".length();
        mdub.put(Td[Xd[15]], odub.column.getComments());
        "".length();
        mdub.put(Td[Xd[16]], odub.column.getName());
        "".length();
        mdub.put(Td[Xd[17]], Ndub);
        "".length();
        return mdub;
    }

    private static void ZJ() {
        Xd = new int[19];
        NumberComponent.Xd[0] = (0x19 ^ 0x77 ^ (0xDC ^ 0xB7)) & (0x93 ^ 0x83 ^ (0x69 ^ 0x7C) ^ -" ".length());
        NumberComponent.Xd[1] = " ".length();
        NumberComponent.Xd[2] = "  ".length();
        NumberComponent.Xd[3] = "   ".length();
        NumberComponent.Xd[4] = 67 + 83 - 66 + 51 ^ 69 + 129 - 67 + 0;
        NumberComponent.Xd[5] = 0xFFFFF753 & 0x2FBC;
        NumberComponent.Xd[6] = 164 + 66 - 87 + 37 ^ 163 + 83 - 235 + 166;
        NumberComponent.Xd[7] = 0x8C ^ 0x8A;
        NumberComponent.Xd[8] = 0x34 ^ 0x33;
        NumberComponent.Xd[9] = 0xF ^ 6 ^ " ".length();
        NumberComponent.Xd[10] = 26 + 67 - 35 + 94 ^ 131 + 94 - 131 + 51;
        NumberComponent.Xd[11] = 165 + 111 - 154 + 66 ^ 18 + 163 - 144 + 145;
        NumberComponent.Xd[12] = 0x7A ^ 0x71;
        NumberComponent.Xd[13] = 0xB3 ^ 0xC3 ^ (0xD5 ^ 0xA9);
        NumberComponent.Xd[14] = 0x6C ^ 0x61;
        NumberComponent.Xd[15] = 0x9A ^ 0x94;
        NumberComponent.Xd[16] = 0xDD ^ 0xBD ^ (0x5E ^ 0x31);
        NumberComponent.Xd[17] = 0xD3 ^ 0xC3;
        NumberComponent.Xd[18] = 0x4B ^ 0x5A;
    }

    private static void yJ() {
        Td = new String[Xd[18]];
        NumberComponent.Td[NumberComponent.Xd[0]] = NumberComponent.oJ("NTwwKTE9LQApKCQ8", "QYVHD");
        NumberComponent.Td[NumberComponent.Xd[1]] = NumberComponent.PJ("v5iGajdR/74=", "VuetK");
        NumberComponent.Td[NumberComponent.Xd[2]] = NumberComponent.RJ("7sGANYyIK9I=", "bEMhk");
        NumberComponent.Td[NumberComponent.Xd[3]] = NumberComponent.oJ("IAoe", "McpgF");
        NumberComponent.Td[NumberComponent.Xd[4]] = NumberComponent.oJ("HhI8", "ssDPd");
        NumberComponent.Td[NumberComponent.Xd[6]] = NumberComponent.PJ("YNyt1X4FPoI=", "CHGyC");
        NumberComponent.Td[NumberComponent.Xd[7]] = NumberComponent.PJ("7dP7PWQ36a1P4WMiqYbVHw==", "xTIgm");
        NumberComponent.Td[NumberComponent.Xd[8]] = NumberComponent.RJ("ZrFRREMY4BPfM3NOCCTwLY/PYwUXSE6k", "ZabsN");
        NumberComponent.Td[NumberComponent.Xd[9]] = NumberComponent.RJ("WSYsBLDgkvM=", "WMUje");
        NumberComponent.Td[NumberComponent.Xd[10]] = NumberComponent.oJ("OAktCR4cASsJ", "ThOlr");
        NumberComponent.Td[NumberComponent.Xd[11]] = NumberComponent.PJ("5EjADrajIQztRJ17GQxROw==", "uylCW");
        NumberComponent.Td[NumberComponent.Xd[12]] = NumberComponent.RJ("a1yn0mTQ9YFx+3QiE5wwkQ==", "kGOpG");
        NumberComponent.Td[NumberComponent.Xd[13]] = NumberComponent.PJ("R36XUEhDIGw=", "DDYNK");
        NumberComponent.Td[NumberComponent.Xd[14]] = NumberComponent.PJ("rFYoWobxYAQ=", "FitDu");
        NumberComponent.Td[NumberComponent.Xd[15]] = NumberComponent.PJ("pQVANooYDgs=", "NFVaQ");
        NumberComponent.Td[NumberComponent.Xd[16]] = NumberComponent.oJ("KC0PKQ==", "FLbLV");
        NumberComponent.Td[NumberComponent.Xd[17]] = NumberComponent.PJ("XTYbG8Y1hVs=", "BklfL");
    }

    private static boolean Ak(int n, int n2) {
        return n < n2;
    }

    private static String RJ(String NCub, String kCub) {
        try {
            SecretKeySpec qCub = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(kCub.getBytes(StandardCharsets.UTF_8)), Xd[9]), "DES");
            Cipher PCub = Cipher.getInstance("DES");
            PCub.init(Xd[2], qCub);
            return new String(PCub.doFinal(Base64.getDecoder().decode(NCub.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception oCub) {
            oCub.printStackTrace();
            return null;
        }
    }

    static {
        NumberComponent.ZJ();
        NumberComponent.yJ();
    }
}

