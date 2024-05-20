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

public class RateComponent
extends AbstractComponent {
    private static /* synthetic */ int[] sf;
    private static /* synthetic */ String[] Rf;

    private static void ho() {
        Rf = new String[sf[13]];
        RateComponent.Rf[RateComponent.sf[0]] = RateComponent.bo("racJWIfC1Tr0xsr6DEm8yA==", "zRsWe");
        RateComponent.Rf[RateComponent.sf[1]] = RateComponent.Co("NxaOPCdRBwM=", "MlyuA");
        RateComponent.Rf[RateComponent.sf[2]] = RateComponent.bo("YAdzlC4naAI=", "iVStb");
        RateComponent.Rf[RateComponent.sf[3]] = RateComponent.Co("LG/YrwcXj6Q=", "pGPFJ");
        RateComponent.Rf[RateComponent.sf[5]] = RateComponent.fo("AzgZGT8qNRkQ", "bTuvH");
        RateComponent.Rf[RateComponent.sf[4]] = RateComponent.fo("JSAvFjQBKCkW", "IAMsX");
        RateComponent.Rf[RateComponent.sf[6]] = RateComponent.bo("YBcU8kzIXDzLJ+ry7h8wrQ==", "joMQc");
        RateComponent.Rf[RateComponent.sf[7]] = RateComponent.bo("cDefxuHgGfwOeu2YSVp+UA==", "MJzWy");
        RateComponent.Rf[RateComponent.sf[8]] = RateComponent.Co("GihYGG6ObM4=", "fXfJy");
        RateComponent.Rf[RateComponent.sf[9]] = RateComponent.bo("LaYW4sK3SBg=", "kpWGG");
        RateComponent.Rf[RateComponent.sf[10]] = RateComponent.fo("ACk3IDw=", "lHUEP");
        RateComponent.Rf[RateComponent.sf[11]] = RateComponent.bo("UOisWpKvk70=", "NpGox");
        RateComponent.Rf[RateComponent.sf[12]] = RateComponent.fo("IyoxOx8iKQ==", "LZERp");
    }

    private static String fo(String ZGRb, String TGRb) {
        ZGRb = new String(Base64.getDecoder().decode(ZGRb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder XGRb = new StringBuilder();
        char[] wGRb = TGRb.toCharArray();
        int VGRb = sf[0];
        char[] PGRb = ZGRb.toCharArray();
        int oGRb = PGRb.length;
        int NGRb = sf[0];
        while (RateComponent.mo(NGRb, oGRb)) {
            char AhRb = PGRb[NGRb];
            XGRb.append((char)(AhRb ^ wGRb[VGRb % wGRb.length]));
            "".length();
            ++VGRb;
            ++NGRb;
            "".length();
            if (-" ".length() < ((0x68 ^ 0x46 ^ (0x7F ^ 0x1C)) & (0x8D ^ 0x89 ^ (0x1A ^ 0x53) ^ -" ".length()))) continue;
            return null;
        }
        return String.valueOf(XGRb);
    }

    private static boolean mo(int n, int n2) {
        return n < n2;
    }

    private static String Co(String ohRb, String NhRb) {
        try {
            SecretKeySpec ThRb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(NhRb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher shRb = Cipher.getInstance("Blowfish");
            shRb.init(sf[2], ThRb);
            return new String(shRb.doFinal(Base64.getDecoder().decode(ohRb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception RhRb) {
            RhRb.printStackTrace();
            return null;
        }
    }

    public RateComponent(OnlineTableColumnEntity VJRb) {
        RateComponent yJRb = null;
        yJRb.column = VJRb;
    }

    static {
        RateComponent.Lo();
        RateComponent.ho();
    }

    private static void Lo() {
        sf = new int[14];
        RateComponent.sf[0] = (175 + 7 - 151 + 175 ^ 145 + 54 - 131 + 85) & (0x5E ^ 0x17 ^ (0x26 ^ 0x38) ^ -" ".length());
        RateComponent.sf[1] = " ".length();
        RateComponent.sf[2] = "  ".length();
        RateComponent.sf[3] = "   ".length();
        RateComponent.sf[4] = 0xB3 ^ 0xB6;
        RateComponent.sf[5] = 0xD5 ^ 0x86 ^ (7 ^ 0x50);
        RateComponent.sf[6] = 0x26 ^ 1 ^ (0x8C ^ 0xAD);
        RateComponent.sf[7] = 6 ^ 1;
        RateComponent.sf[8] = 51 + 82 - 41 + 55 ^ 56 + 43 - 42 + 98;
        RateComponent.sf[9] = 39 + 95 - 70 + 73 ^ 65 + 75 - 23 + 11;
        RateComponent.sf[10] = 0xEA ^ 0x89 ^ (0xD4 ^ 0xBD);
        RateComponent.sf[11] = 0x51 ^ 0x5A;
        RateComponent.sf[12] = 0x9F ^ 0x93;
        RateComponent.sf[13] = 179 + 112 - 148 + 48 ^ 4 + 160 - 72 + 86;
    }

    @Override
    public Map<String, Object> getComponent() {
        RateComponent RJRb = null;
        HashMap<String, Object> qJRb = new HashMap<String, Object>();
        qJRb.put(Rf[sf[0]], RJRb.column.getFormDefault());
        "".length();
        qJRb.put(Rf[sf[1]], Rf[sf[2]]);
        "".length();
        qJRb.put(Rf[sf[3]], sf[4]);
        "".length();
        qJRb.put(Rf[sf[5]], sf[0]);
        "".length();
        qJRb.put(Rf[sf[4]], sf[0]);
        "".length();
        qJRb.put(Rf[sf[6]], sf[0]);
        "".length();
        qJRb.put(Rf[sf[7]], sf[0]);
        "".length();
        qJRb.put(Rf[sf[8]], RJRb.getRules());
        "".length();
        HashMap<String, Object> PJRb = new HashMap<String, Object>();
        PJRb.put(Rf[sf[9]], RJRb.column.getFormInput());
        "".length();
        PJRb.put(Rf[sf[10]], RJRb.column.getComments());
        "".length();
        PJRb.put(Rf[sf[11]], RJRb.column.getName());
        "".length();
        PJRb.put(Rf[sf[12]], qJRb);
        "".length();
        return PJRb;
    }

    private static String bo(String dJRb, String CJRb) {
        try {
            SecretKeySpec GJRb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(CJRb.getBytes(StandardCharsets.UTF_8)), sf[8]), "DES");
            Cipher fJRb = Cipher.getInstance("DES");
            fJRb.init(sf[2], GJRb);
            return new String(fJRb.doFinal(Base64.getDecoder().decode(dJRb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception EJRb) {
            EJRb.printStackTrace();
            return null;
        }
    }
}

