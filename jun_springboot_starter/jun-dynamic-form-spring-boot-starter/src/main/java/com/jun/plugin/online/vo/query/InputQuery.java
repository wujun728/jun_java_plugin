/*
 * Decompiled with CFR 0.152.
 */
package com.jun.plugin.online.vo.query;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.jun.plugin.online.entity.OnlineTableColumnEntity;

public class InputQuery
extends AbstractQuery {
    private static /* synthetic */ int[] Jf;
    private static /* synthetic */ String[] wE;

    private static void km() {
        wE = new String[Jf[5]];
        InputQuery.wE[InputQuery.Jf[0]] = InputQuery.ZL("/vnJ9fO69EUCILEaTvo2AQ==", "LdDIV");
        InputQuery.wE[InputQuery.Jf[1]] = InputQuery.dm("IjMBKw==", "VJqNk");
        InputQuery.wE[InputQuery.Jf[2]] = InputQuery.ZL("/Hp24sIIZEk=", "ndDhH");
        InputQuery.wE[InputQuery.Jf[3]] = InputQuery.ZL("vi6nTeEc6D4=", "ywjWT");
        InputQuery.wE[InputQuery.Jf[4]] = InputQuery.dm("AiIaOAsDIQ==", "mRnQd");
    }

    private static String ZL(String GRRb, String fRRb) {
        try {
            SecretKeySpec mRRb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(fRRb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher LRRb = Cipher.getInstance("Blowfish");
            LRRb.init(Jf[2], mRRb);
            return new String(LRRb.doFinal(Base64.getDecoder().decode(GRRb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception kRRb) {
            kRRb.printStackTrace();
            return null;
        }
    }

    static {
        InputQuery.mN();
        InputQuery.km();
    }

    private static String dm(String sqRb, String RqRb) {
        sqRb = new String(Base64.getDecoder().decode(sqRb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder qqRb = new StringBuilder();
        char[] PqRb = RqRb.toCharArray();
        int oqRb = Jf[0];
        char[] hqRb = sqRb.toCharArray();
        int GqRb = hqRb.length;
        int fqRb = Jf[0];
        while (InputQuery.NN(fqRb, GqRb)) {
            char TqRb = hqRb[fqRb];
            qqRb.append((char)(TqRb ^ PqRb[oqRb % PqRb.length]));
            "".length();
            ++oqRb;
            ++fqRb;
            "".length();
            if ((0x93 ^ 0x97) >= ((0x70 ^ 0x51) & ~(0 ^ 0x21))) continue;
            return null;
        }
        return String.valueOf(qqRb);
    }

    public InputQuery(OnlineTableColumnEntity CsRb) {
        InputQuery dsRb = null;
        dsRb.column = CsRb;
    }

    private static void mN() {
        Jf = new int[6];
        InputQuery.Jf[0] = (145 + 87 - 163 + 113 ^ 21 + 102 - 93 + 105) & (0x1E ^ 0x30 ^ (7 ^ 0x18) ^ -" ".length());
        InputQuery.Jf[1] = " ".length();
        InputQuery.Jf[2] = "  ".length();
        InputQuery.Jf[3] = "   ".length();
        InputQuery.Jf[4] = 0xA6 ^ 0xA2;
        InputQuery.Jf[5] = 0x86 ^ 0x8A ^ (0x81 ^ 0x88);
    }

    @Override
    public Map<String, Object> getQuery() {
        InputQuery TRRb = null;
        HashMap<String, String> VRRb = new HashMap<String, String>();
        VRRb.put(wE[Jf[0]], TRRb.column.getComments());
        "".length();
        HashMap<String, Object> uRRb = new HashMap<String, Object>();
        uRRb.put(wE[Jf[1]], wE[Jf[2]]);
        "".length();
        uRRb.put(wE[Jf[3]], TRRb.column.getName());
        "".length();
        uRRb.put(wE[Jf[4]], VRRb);
        "".length();
        return uRRb;
    }

    private static boolean NN(int n, int n2) {
        return n < n2;
    }
}

