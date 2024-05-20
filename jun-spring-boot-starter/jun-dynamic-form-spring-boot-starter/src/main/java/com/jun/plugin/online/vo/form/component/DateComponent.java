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

public class DateComponent
extends AbstractComponent {
    private static /* synthetic */ int[] Jd;
    private static /* synthetic */ String[] uC;

    private static void hG() {
        uC = new String[Jd[18]];
        DateComponent.uC[DateComponent.Jd[0]] = DateComponent.wf("kIbGdFO0NpX+y8SqlRx6jw==", "XUlrv");
        DateComponent.uC[DateComponent.Jd[1]] = DateComponent.yf("ES0DBSQ=", "fDgqL");
        DateComponent.uC[DateComponent.Jd[2]] = DateComponent.Zf("WxFWB3b1dJI=", "dFoBI");
        DateComponent.uC[DateComponent.Jd[3]] = DateComponent.yf("DC0XKw==", "xTgNb");
        DateComponent.uC[DateComponent.Jd[4]] = DateComponent.wf("NLB24JwKVug=", "KWlZl");
        DateComponent.uC[DateComponent.Jd[5]] = DateComponent.wf("uoqJZIZR7yY=", "dxHMX");
        DateComponent.uC[DateComponent.Jd[6]] = DateComponent.Zf("exbHd8xBuk0nWbw3LlRTpA==", "eAamU");
        DateComponent.uC[DateComponent.Jd[7]] = DateComponent.yf("MiMxJB0qIDwjHTA=", "BOPGx");
        DateComponent.uC[DateComponent.Jd[8]] = DateComponent.Zf("bw0DUlplK/zWoZDm0Wf4DKk2mDsRxbni", "hPqBf");
        DateComponent.uC[DateComponent.Jd[9]] = DateComponent.yf("CSgAGD0NJQEgPgAiATo=", "lFdHQ");
        DateComponent.uC[DateComponent.Jd[10]] = DateComponent.Zf("T8ijL+P7S9ZEFQ/4Zy//pw==", "fDaIx");
        DateComponent.uC[DateComponent.Jd[11]] = DateComponent.wf("sjXkDdfavEp+UK+8/Q/KGw==", "hvnQd");
        DateComponent.uC[DateComponent.Jd[12]] = DateComponent.wf("ns5+5RIN7mWdgRZX/EwKLg==", "dRuZq");
        DateComponent.uC[DateComponent.Jd[13]] = DateComponent.Zf("OHHqwUrjUWo=", "WdVZP");
        DateComponent.uC[DateComponent.Jd[14]] = DateComponent.wf("tmFhvJw3BKI=", "dOsFe");
        DateComponent.uC[DateComponent.Jd[15]] = DateComponent.yf("JiU2Mhs=", "JDTWw");
        DateComponent.uC[DateComponent.Jd[16]] = DateComponent.wf("9Kbj0XG7iEQ=", "QTWwc");
        DateComponent.uC[DateComponent.Jd[17]] = DateComponent.Zf("Q6BwUtASZjY=", "kOvdb");
    }

    private static boolean qh(int n, int n2) {
        return n < n2;
    }

    private static String Zf(String ZNVb, String yNVb) {
        try {
            SecretKeySpec CoVb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(yNVb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher boVb = Cipher.getInstance("Blowfish");
            boVb.init(Jd[2], CoVb);
            return new String(boVb.doFinal(Base64.getDecoder().decode(ZNVb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception AoVb) {
            AoVb.printStackTrace();
            return null;
        }
    }

    static {
        DateComponent.oh();
        DateComponent.hG();
    }

    private static void oh() {
        Jd = new int[19];
        DateComponent.Jd[0] = (62 + 131 - 121 + 80 ^ 62 + 133 - 149 + 141) & (0xE6 ^ 0x98 ^ (0x40 ^ 0x1D) ^ -" ".length());
        DateComponent.Jd[1] = " ".length();
        DateComponent.Jd[2] = "  ".length();
        DateComponent.Jd[3] = "   ".length();
        DateComponent.Jd[4] = 30 + 13 - 32 + 139 ^ 80 + 32 - 92 + 126;
        DateComponent.Jd[5] = 35 + 31 - 1 + 129 ^ 144 + 71 - 171 + 155;
        DateComponent.Jd[6] = 0x64 ^ 0x62;
        DateComponent.Jd[7] = 0x9E ^ 0xB1 ^ (0x7A ^ 0x52);
        DateComponent.Jd[8] = 134 + 35 - 160 + 150 ^ 39 + 67 - 9 + 54;
        DateComponent.Jd[9] = 38 + 165 - 49 + 19 ^ 108 + 116 - 64 + 4;
        DateComponent.Jd[10] = 0x63 ^ 0x69;
        DateComponent.Jd[11] = 54 + 69 - 4 + 29 ^ 77 + 17 - -21 + 44;
        DateComponent.Jd[12] = 0xAD ^ 0xA1;
        DateComponent.Jd[13] = 68 + 105 - 132 + 98 ^ 94 + 100 - 176 + 116;
        DateComponent.Jd[14] = 0xBE ^ 0xB0;
        DateComponent.Jd[15] = 0xAC ^ 0xA3;
        DateComponent.Jd[16] = 0x79 ^ 0x69;
        DateComponent.Jd[17] = 0x90 ^ 0x81;
        DateComponent.Jd[18] = 93 + 108 - 58 + 3 ^ 65 + 107 - 127 + 83;
    }

    @Override
    public Map<String, Object> getComponent() {
        DateComponent ZPVb = null;
        HashMap<String, Object> yPVb = new HashMap<String, Object>();
        yPVb.put(uC[Jd[0]], ZPVb.column.getFormDefault());
        "".length();
        yPVb.put(uC[Jd[1]], uC[Jd[2]]);
        "".length();
        yPVb.put(uC[Jd[3]], uC[Jd[4]]);
        "".length();
        yPVb.put(uC[Jd[5]], uC[Jd[6]]);
        "".length();
        yPVb.put(uC[Jd[7]], null);
        "".length();
        yPVb.put(uC[Jd[8]], null);
        "".length();
        yPVb.put(uC[Jd[9]], null);
        "".length();
        yPVb.put(uC[Jd[10]], Jd[0]);
        "".length();
        yPVb.put(uC[Jd[11]], Jd[0]);
        "".length();
        yPVb.put(uC[Jd[12]], Jd[0]);
        "".length();
        yPVb.put(uC[Jd[13]], ZPVb.getRules());
        "".length();
        HashMap<String, Object> XPVb = new HashMap<String, Object>();
        XPVb.put(uC[Jd[14]], ZPVb.column.getFormInput());
        "".length();
        XPVb.put(uC[Jd[15]], ZPVb.column.getComments());
        "".length();
        XPVb.put(uC[Jd[16]], ZPVb.column.getName());
        "".length();
        XPVb.put(uC[Jd[17]], yPVb);
        "".length();
        return XPVb;
    }

    private static String yf(String VoVb, String uoVb) {
        VoVb = new String(Base64.getDecoder().decode(VoVb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder ToVb = new StringBuilder();
        char[] soVb = uoVb.toCharArray();
        int RoVb = Jd[0];
        char[] LoVb = VoVb.toCharArray();
        int koVb = LoVb.length;
        int JoVb = Jd[0];
        while (DateComponent.qh(JoVb, koVb)) {
            char woVb = LoVb[JoVb];
            ToVb.append((char)(woVb ^ soVb[RoVb % soVb.length]));
            "".length();
            ++RoVb;
            ++JoVb;
            "".length();
            if ((0x64 ^ 0x60) >= -" ".length()) continue;
            return null;
        }
        return String.valueOf(ToVb);
    }

    public DateComponent(OnlineTableColumnEntity fqVb) {
        DateComponent EqVb = null;
        EqVb.column = fqVb;
    }

    private static String wf(String kPVb, String JPVb) {
        try {
            SecretKeySpec PPVb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(JPVb.getBytes(StandardCharsets.UTF_8)), Jd[8]), "DES");
            Cipher oPVb = Cipher.getInstance("DES");
            oPVb.init(Jd[2], PPVb);
            return new String(oPVb.doFinal(Base64.getDecoder().decode(kPVb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception NPVb) {
            NPVb.printStackTrace();
            return null;
        }
    }
}

