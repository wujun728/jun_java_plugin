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

public class TextareaComponent
extends AbstractComponent {
    private static /* synthetic */ String[] sC;
    private static /* synthetic */ int[] sE;

    private static String Rf(String Lssb, String Essb) {
        Lssb = new String(Base64.getDecoder().decode(Lssb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder Jssb = new StringBuilder();
        char[] hssb = Essb.toCharArray();
        int Gssb = sE[0];
        char[] Assb = Lssb.toCharArray();
        int ZRsb = Assb.length;
        int yRsb = sE[0];
        while (TextareaComponent.sL(yRsb, ZRsb)) {
            char mssb = Assb[yRsb];
            Jssb.append((char)(mssb ^ hssb[Gssb % hssb.length]));
            "".length();
            ++Gssb;
            ++yRsb;
            "".length();
            if ((0x8A ^ 0x8E) > "   ".length()) continue;
            return null;
        }
        return String.valueOf(Jssb);
    }

    @Override
    public Map<String, Object> getComponent() {
        TextareaComponent ZTsb = null;
        HashMap<String, Object> busb = new HashMap<String, Object>();
        busb.put(sC[sE[0]], ZTsb.column.getFormDefault());
        "".length();
        busb.put(sC[sE[1]], sC[sE[2]]);
        "".length();
        busb.put(sC[sE[3]], null);
        "".length();
        busb.put(sC[sE[4]], null);
        "".length();
        busb.put(sC[sE[5]], sE[2]);
        "".length();
        busb.put(sC[sE[6]], sE[0]);
        "".length();
        busb.put(sC[sE[7]], sE[0]);
        "".length();
        busb.put(sC[sE[8]], sE[0]);
        "".length();
        busb.put(sC[sE[9]], sE[0]);
        "".length();
        busb.put(sC[sE[10]], sE[0]);
        "".length();
        busb.put(sC[sE[11]], ZTsb.getRules());
        "".length();
        HashMap<String, Object> Ausb = new HashMap<String, Object>();
        Ausb.put(sC[sE[12]], ZTsb.column.getFormInput());
        "".length();
        Ausb.put(sC[sE[13]], ZTsb.column.getComments());
        "".length();
        Ausb.put(sC[sE[14]], ZTsb.column.getName());
        "".length();
        Ausb.put(sC[sE[15]], busb);
        "".length();
        return Ausb;
    }

    public TextareaComponent(OnlineTableColumnEntity Jusb) {
        TextareaComponent husb = null;
        husb.column = Jusb;
    }

    private static boolean sL(int n, int n2) {
        return n < n2;
    }

    private static String sf(String NTsb, String oTsb) {
        try {
            SecretKeySpec sTsb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(oTsb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher RTsb = Cipher.getInstance("Blowfish");
            RTsb.init(sE[2], sTsb);
            return new String(RTsb.doFinal(Base64.getDecoder().decode(NTsb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception qTsb) {
            qTsb.printStackTrace();
            return null;
        }
    }

    private static String qf(String bTsb, String yssb) {
        try {
            SecretKeySpec ETsb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(yssb.getBytes(StandardCharsets.UTF_8)), sE[8]), "DES");
            Cipher dTsb = Cipher.getInstance("DES");
            dTsb.init(sE[2], ETsb);
            return new String(dTsb.doFinal(Base64.getDecoder().decode(bTsb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception CTsb) {
            CTsb.printStackTrace();
            return null;
        }
    }

    static {
        TextareaComponent.qL();
        TextareaComponent.CG();
    }

    private static void qL() {
        sE = new int[17];
        TextareaComponent.sE[0] = (0x9F ^ 0x91) & ~(0xA5 ^ 0xAB);
        TextareaComponent.sE[1] = " ".length();
        TextareaComponent.sE[2] = "  ".length();
        TextareaComponent.sE[3] = "   ".length();
        TextareaComponent.sE[4] = 82 + 130 - 90 + 35 ^ 89 + 5 - 7 + 66;
        TextareaComponent.sE[5] = 0x1D ^ 0x18;
        TextareaComponent.sE[6] = 0xE1 ^ 0xB3 ^ (0xC6 ^ 0x92);
        TextareaComponent.sE[7] = 0x29 ^ 0x6E ^ (0xCA ^ 0x8A);
        TextareaComponent.sE[8] = 0xC ^ 0x1C ^ (0xB4 ^ 0xAC);
        TextareaComponent.sE[9] = 0x87 ^ 0x8E;
        TextareaComponent.sE[10] = 119 + 88 - 194 + 153 ^ 104 + 115 - 185 + 138;
        TextareaComponent.sE[11] = 0x57 ^ 0x5C;
        TextareaComponent.sE[12] = 8 + 101 - 51 + 80 ^ 20 + 42 - 52 + 124;
        TextareaComponent.sE[13] = "  ".length() ^ (0x68 ^ 0x67);
        TextareaComponent.sE[14] = 11 + 2 - -120 + 28 ^ 75 + 120 - 54 + 34;
        TextareaComponent.sE[15] = 0xFB ^ 0x95 ^ (0xD7 ^ 0xB6);
        TextareaComponent.sE[16] = 77 + 4 - -17 + 38 ^ 80 + 134 - 181 + 119;
    }

    private static void CG() {
        sC = new String[sE[16]];
        TextareaComponent.sC[TextareaComponent.sE[0]] = TextareaComponent.qf("IiA9EnDChXs1wNTkfENtOg==", "jQIHD");
        TextareaComponent.sC[TextareaComponent.sE[1]] = TextareaComponent.Rf("HAwREwI=", "keugj");
        TextareaComponent.sC[TextareaComponent.sE[2]] = TextareaComponent.Rf("", "HFTSy");
        TextareaComponent.sC[TextareaComponent.sE[3]] = TextareaComponent.sf("aDK8cxLe74ARlUf5SxQC2A==", "gZHvP");
        TextareaComponent.sC[TextareaComponent.sE[4]] = TextareaComponent.Rf("FygoMAQPKyU3BBU=", "gDISa");
        TextareaComponent.sC[TextareaComponent.sE[5]] = TextareaComponent.qf("NCc+S8l28rM=", "vjWHm");
        TextareaComponent.sC[TextareaComponent.sE[6]] = TextareaComponent.Rf("DQAaCQQpCBwJ", "aaxlh");
        TextareaComponent.sC[TextareaComponent.sE[7]] = TextareaComponent.sf("Q85Xbx+tiHAl7Eh07RLKTQ==", "eexjm");
        TextareaComponent.sC[TextareaComponent.sE[8]] = TextareaComponent.Rf("OBMENCckGhw=", "JvePH");
        TextareaComponent.sC[TextareaComponent.sE[9]] = TextareaComponent.sf("SvbmOPJGiZQs2AcqOnD0tQ==", "ZCsqP");
        TextareaComponent.sC[TextareaComponent.sE[10]] = TextareaComponent.Rf("GiouDQIGMCU2PAQrNQ==", "iBAzU");
        TextareaComponent.sC[TextareaComponent.sE[11]] = TextareaComponent.Rf("PjIYCD8=", "LGtmL");
        TextareaComponent.sC[TextareaComponent.sE[12]] = TextareaComponent.qf("qIorf674bvA=", "ViZGK");
        TextareaComponent.sC[TextareaComponent.sE[13]] = TextareaComponent.sf("2pi+lGH8RfY=", "BXBBo");
        TextareaComponent.sC[TextareaComponent.sE[14]] = TextareaComponent.sf("61hp3VqW7Tk=", "avohX");
        TextareaComponent.sC[TextareaComponent.sE[15]] = TextareaComponent.sf("ZFDZgtoB1Oc=", "kBkBV");
    }
}

