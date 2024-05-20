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

public class TimeComponent
extends AbstractComponent {
    private static /* synthetic */ int[] Cd;
    private static /* synthetic */ String[] hC;

    private static boolean dh(int n, int n2) {
        return n < n2;
    }

    private static String TE(String kswb, String Jswb) {
        try {
            SecretKeySpec Pswb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(Jswb.getBytes(StandardCharsets.UTF_8)), Cd[8]), "DES");
            Cipher oswb = Cipher.getInstance("DES");
            oswb.init(Cd[2], Pswb);
            return new String(oswb.doFinal(Base64.getDecoder().decode(kswb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception Nswb) {
            Nswb.printStackTrace();
            return null;
        }
    }

    private static String GG(String yTwb, String XTwb) {
        try {
            SecretKeySpec buwb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(XTwb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher Auwb = Cipher.getInstance("Blowfish");
            Auwb.init(Cd[2], buwb);
            return new String(Auwb.doFinal(Base64.getDecoder().decode(yTwb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ZTwb) {
            ZTwb.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> getComponent() {
        TimeComponent muwb = null;
        HashMap<String, Object> Luwb = new HashMap<String, Object>();
        Luwb.put(hC[Cd[0]], muwb.column.getFormDefault());
        "".length();
        Luwb.put(hC[Cd[1]], hC[Cd[2]]);
        "".length();
        Luwb.put(hC[Cd[3]], hC[Cd[4]]);
        "".length();
        Luwb.put(hC[Cd[5]], Cd[0]);
        "".length();
        Luwb.put(hC[Cd[6]], null);
        "".length();
        Luwb.put(hC[Cd[7]], null);
        "".length();
        Luwb.put(hC[Cd[8]], null);
        "".length();
        Luwb.put(hC[Cd[9]], Cd[0]);
        "".length();
        Luwb.put(hC[Cd[10]], Cd[0]);
        "".length();
        Luwb.put(hC[Cd[11]], Cd[0]);
        "".length();
        Luwb.put(hC[Cd[12]], muwb.getRules());
        "".length();
        HashMap<String, Object> kuwb = new HashMap<String, Object>();
        kuwb.put(hC[Cd[13]], muwb.column.getFormInput());
        "".length();
        kuwb.put(hC[Cd[14]], muwb.column.getComments());
        "".length();
        kuwb.put(hC[Cd[15]], muwb.column.getName());
        "".length();
        kuwb.put(hC[Cd[16]], Luwb);
        "".length();
        return kuwb;
    }

    private static void JG() {
        hC = new String[Cd[17]];
        TimeComponent.hC[TimeComponent.Cd[0]] = TimeComponent.TE("ooUkFD7TBS8Oqb2Xfx8g7g==", "bfPTy");
        TimeComponent.hC[TimeComponent.Cd[1]] = TimeComponent.TE("e/r/TcSRRqs=", "IYRuj");
        TimeComponent.hC[TimeComponent.Cd[2]] = TimeComponent.TE("5dusNU3CzdU=", "qyQpk");
        TimeComponent.hC[TimeComponent.Cd[3]] = TimeComponent.fG("LSs0GBU/", "KDFut");
        TimeComponent.hC[TimeComponent.Cd[4]] = TimeComponent.TE("WpdihUe1Hvi9SZrn4APu2g==", "XQQvu");
        TimeComponent.hC[TimeComponent.Cd[5]] = TimeComponent.fG("AjE9KTwMJw==", "kBoHR");
        TimeComponent.hC[TimeComponent.Cd[6]] = TimeComponent.fG("HRkzOTIFGj4+Mh8=", "muRZW");
        TimeComponent.hC[TimeComponent.Cd[7]] = TimeComponent.GG("x34iTEXHK4fM2lSRNZkat4yUQKe9zOzy", "foQKd");
        TimeComponent.hC[TimeComponent.Cd[8]] = TimeComponent.fG("Cz4HBysPMwY/KAI0BiU=", "nPcWG");
        TimeComponent.hC[TimeComponent.Cd[9]] = TimeComponent.TE("g02xaQ3KMA/TV5t2kE/1jg==", "TNjZe");
        TimeComponent.hC[TimeComponent.Cd[10]] = TimeComponent.GG("/8hr2IUjMRJDziiCcjZyeQ==", "DNEOi");
        TimeComponent.hC[TimeComponent.Cd[11]] = TimeComponent.GG("/44pKxN6O4uj1EML/4nCdg==", "ICWSc");
        TimeComponent.hC[TimeComponent.Cd[12]] = TimeComponent.GG("aizccuA/25g=", "INwPR");
        TimeComponent.hC[TimeComponent.Cd[13]] = TimeComponent.TE("etwlfVS+t5I=", "TOVzQ");
        TimeComponent.hC[TimeComponent.Cd[14]] = TimeComponent.GG("yyRzRRm4fiw=", "cszSz");
        TimeComponent.hC[TimeComponent.Cd[15]] = TimeComponent.fG("HBQlCw==", "ruHnN");
        TimeComponent.hC[TimeComponent.Cd[16]] = TimeComponent.GG("3dXL9MDE3+o=", "eHouH");
    }

    private static String fG(String CTwb, String bTwb) {
        CTwb = new String(Base64.getDecoder().decode(CTwb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder fTwb = new StringBuilder();
        char[] ETwb = bTwb.toCharArray();
        int dTwb = Cd[0];
        char[] Xswb = CTwb.toCharArray();
        int wswb = Xswb.length;
        int Vswb = Cd[0];
        while (TimeComponent.dh(Vswb, wswb)) {
            char JTwb = Xswb[Vswb];
            fTwb.append((char)(JTwb ^ ETwb[dTwb % ETwb.length]));
            "".length();
            ++dTwb;
            ++Vswb;
            "".length();
            if (" ".length() >= 0) continue;
            return null;
        }
        return String.valueOf(fTwb);
    }

    static {
        TimeComponent.Ch();
        TimeComponent.JG();
    }

    private static void Ch() {
        Cd = new int[18];
        TimeComponent.Cd[0] = (0x1A ^ 0x56) & ~(0xCA ^ 0x86);
        TimeComponent.Cd[1] = " ".length();
        TimeComponent.Cd[2] = "  ".length();
        TimeComponent.Cd[3] = "   ".length();
        TimeComponent.Cd[4] = 98 + 130 - 128 + 36 ^ 13 + 101 - 103 + 129;
        TimeComponent.Cd[5] = 0x8A ^ 0x8F;
        TimeComponent.Cd[6] = 0xFF ^ 0xAD ^ (0xD5 ^ 0x81);
        TimeComponent.Cd[7] = 0x7C ^ 0x25 ^ (0x35 ^ 0x6B);
        TimeComponent.Cd[8] = 0x12 ^ 0x1A;
        TimeComponent.Cd[9] = 0x63 ^ 0x10 ^ (0xBA ^ 0xC0);
        TimeComponent.Cd[10] = 9 ^ 0x3F ^ (0xFA ^ 0xC6);
        TimeComponent.Cd[11] = 0xA5 ^ 0xAE;
        TimeComponent.Cd[12] = 180 + 93 - 190 + 108 ^ 6 + 159 - 133 + 147;
        TimeComponent.Cd[13] = 0x55 ^ 0x30 ^ (0x1F ^ 0x77);
        TimeComponent.Cd[14] = 0x8F ^ 0x81;
        TimeComponent.Cd[15] = 0x33 ^ 0xB ^ (0x5D ^ 0x6A);
        TimeComponent.Cd[16] = 0x6E ^ 0x7E;
        TimeComponent.Cd[17] = 0xB3 ^ 0xBD ^ (0x3B ^ 0x24);
    }

    public TimeComponent(OnlineTableColumnEntity suwb) {
        TimeComponent Tuwb = null;
        Tuwb.column = suwb;
    }
}

