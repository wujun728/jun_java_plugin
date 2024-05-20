/*
 * Decompiled with CFR 0.152.
 */
package com.jun.plugin.online.vo.query;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.jun.plugin.online.entity.OnlineTableColumnEntity;

public class DateQuery
extends AbstractQuery {
    private static /* synthetic */ int[] yE;
    private static /* synthetic */ String[] TE;

    private static String wL(String Ahsb, String bhsb) {
        try {
            SecretKeySpec fhsb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(bhsb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher Ehsb = Cipher.getInstance("Blowfish");
            Ehsb.init(yE[2], fhsb);
            return new String(Ehsb.doFinal(Base64.getDecoder().decode(Ahsb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception dhsb) {
            dhsb.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> getQuery() {
        DateQuery ZJsb = null;
        HashMap<String, Object> bksb = new HashMap<String, Object>();
        bksb.put(TE[yE[0]], ZJsb.column.getFormDefault());
        "".length();
        bksb.put(TE[yE[1]], TE[yE[2]]);
        "".length();
        bksb.put(TE[yE[3]], yE[1]);
        "".length();
        if (DateQuery.Nm(ZJsb.column.getQueryType().equals(TE[yE[4]]) ? 1 : 0)) {
            bksb.put(TE[yE[5]], TE[yE[6]]);
            "".length();
            bksb.put(TE[yE[7]], ZJsb.column.getComments());
            "".length();
            "".length();
            if (null != null) {
                return null;
            }
        } else {
            bksb.put(TE[yE[8]], TE[yE[9]]);
            "".length();
            bksb.put(TE[yE[10]], ZJsb.column.getComments() + " | \u5f00\u59cb");
            "".length();
            bksb.put(TE[yE[11]], TE[yE[12]]);
            "".length();
        }
        HashMap<String, Object> Aksb = new HashMap<String, Object>();
        Aksb.put(TE[yE[13]], ZJsb.column.getQueryInput());
        "".length();
        Aksb.put(TE[yE[14]], ZJsb.column.getName());
        "".length();
        Aksb.put(TE[yE[15]], bksb);
        "".length();
        return Aksb;
    }

    private static String yL(String PJsb, String mJsb) {
        try {
            SecretKeySpec sJsb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(mJsb.getBytes(StandardCharsets.UTF_8)), yE[8]), "DES");
            Cipher RJsb = Cipher.getInstance("DES");
            RJsb.init(yE[2], sJsb);
            return new String(RJsb.doFinal(Base64.getDecoder().decode(PJsb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception qJsb) {
            qJsb.printStackTrace();
            return null;
        }
    }

    static {
        DateQuery.mm();
        DateQuery.Cm();
    }

    private static boolean om(int n, int n2) {
        return n < n2;
    }

    public DateQuery(OnlineTableColumnEntity Jksb) {
        DateQuery hksb = null;
        hksb.column = Jksb;
    }

    private static void Cm() {
        TE = new String[yE[16]];
        DateQuery.TE[DateQuery.yE[0]] = DateQuery.wL("4FeiuOPwFbzGk9Vbjr3jNg==", "fSJcv");
        DateQuery.TE[DateQuery.yE[1]] = DateQuery.XL("Jw0gHCA1", "AbRqA");
        DateQuery.TE[DateQuery.yE[2]] = DateQuery.XL("PC8YE0woO2wOJQ==", "evAJa");
        DateQuery.TE[DateQuery.yE[3]] = DateQuery.wL("Z5ylG+PlbR0jD0F9DhLpBw==", "kaZlf");
        DateQuery.TE[DateQuery.yE[4]] = DateQuery.wL("kaT0Vmo8fB4=", "XtTmW");
        DateQuery.TE[DateQuery.yE[5]] = DateQuery.wL("4lKVyv1kjNg=", "JFqmD");
        DateQuery.TE[DateQuery.yE[6]] = DateQuery.XL("KgsgIg==", "NjTGx");
        DateQuery.TE[DateQuery.yE[7]] = DateQuery.XL("PAglCyQkCygMJD4=", "LdDhA");
        DateQuery.TE[DateQuery.yE[8]] = DateQuery.XL("PQMdBw==", "Izmbv");
        DateQuery.TE[DateQuery.yE[9]] = DateQuery.XL("FyQmNTYSKzU1", "sERPD");
        DateQuery.TE[DateQuery.yE[10]] = DateQuery.XL("OjU5GBcZLTkJBiEuNA4GOw==", "IAXjc");
        DateQuery.TE[DateQuery.yE[11]] = DateQuery.XL("FQEAEwkRDAErChwLATE=", "podCe");
        DateQuery.TE[DateQuery.yE[12]] = DateQuery.XL("57qf5pyL", "LTTPA");
        DateQuery.TE[DateQuery.yE[13]] = DateQuery.yL("v83GmX/miWo=", "nBbQA");
        DateQuery.TE[DateQuery.yE[14]] = DateQuery.yL("w/XNL6B06g8=", "oDfEl");
        DateQuery.TE[DateQuery.yE[15]] = DateQuery.XL("KhIaHhcrEQ==", "Ebnwx");
    }

    private static void mm() {
        yE = new int[17];
        DateQuery.yE[0] = (0x65 ^ 0x44 ^ (0xE ^ 0x33)) & (2 + 53 - 40 + 134 ^ 60 + 32 - 67 + 112 ^ -" ".length());
        DateQuery.yE[1] = " ".length();
        DateQuery.yE[2] = "  ".length();
        DateQuery.yE[3] = "   ".length();
        DateQuery.yE[4] = 0x77 ^ 0x73;
        DateQuery.yE[5] = 0xE ^ 0x30 ^ (0x4C ^ 0x77);
        DateQuery.yE[6] = 120 + 39 - 119 + 92 ^ 114 + 94 - 120 + 42;
        DateQuery.yE[7] = 0xE ^ 9;
        DateQuery.yE[8] = 0x6E ^ 0x66;
        DateQuery.yE[9] = 87 + 76 - 111 + 124 ^ 86 + 91 - 109 + 117;
        DateQuery.yE[10] = 0xAA ^ 0xA0;
        DateQuery.yE[11] = 0x28 ^ 0x7E ^ (0x2C ^ 0x71);
        DateQuery.yE[12] = 0x76 ^ 0x7A;
        DateQuery.yE[13] = 0xE0 ^ 0xB5 ^ (0xCB ^ 0x93);
        DateQuery.yE[14] = 0x2C ^ 3 ^ (0xAA ^ 0x8B);
        DateQuery.yE[15] = 0x63 ^ 0x76 ^ (0x6F ^ 0x75);
        DateQuery.yE[16] = 0x5E ^ 0x4E;
    }

    private static boolean Nm(int n) {
        return n != 0;
    }

    private static String XL(String Thsb, String Xhsb) {
        Thsb = new String(Base64.getDecoder().decode(Thsb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder whsb = new StringBuilder();
        char[] Vhsb = Xhsb.toCharArray();
        int uhsb = yE[0];
        char[] ohsb = Thsb.toCharArray();
        int Nhsb = ohsb.length;
        int mhsb = yE[0];
        while (DateQuery.om(mhsb, Nhsb)) {
            char Zhsb = ohsb[mhsb];
            whsb.append((char)(Zhsb ^ Vhsb[uhsb % Vhsb.length]));
            "".length();
            ++uhsb;
            ++mhsb;
            "".length();
            if ("   ".length() != 0) continue;
            return null;
        }
        return String.valueOf(whsb);
    }
}

