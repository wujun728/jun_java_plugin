/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.StrUtil
 */
package com.jun.plugin.online.vo.query;

import cn.hutool.core.util.StrUtil;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.jun.plugin.online.entity.OnlineTableColumnEntity;

public class SelectQuery
extends AbstractQuery {
    private static /* synthetic */ String[] Lf;
    private static /* synthetic */ int[] of;

    private static String RN(String mkRb, String LkRb) {
        try {
            SecretKeySpec RkRb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(LkRb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher qkRb = Cipher.getInstance("Blowfish");
            qkRb.init(of[2], RkRb);
            return new String(qkRb.doFinal(Base64.getDecoder().decode(mkRb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception PkRb) {
            PkRb.printStackTrace();
            return null;
        }
    }

    private static String sN(String AmRb, String XLRb) {
        try {
            SecretKeySpec dmRb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(XLRb.getBytes(StandardCharsets.UTF_8)), of[8]), "DES");
            Cipher CmRb = Cipher.getInstance("DES");
            CmRb.init(of[2], dmRb);
            return new String(CmRb.doFinal(Base64.getDecoder().decode(AmRb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception bmRb) {
            bmRb.printStackTrace();
            return null;
        }
    }

    private static boolean VN(int n) {
        return n != 0;
    }

    private static void TN() {
        Lf = new String[of[18]];
        SelectQuery.Lf[SelectQuery.of[0]] = SelectQuery.qN("NQ0mDD4=", "BdBxV");
        SelectQuery.Lf[SelectQuery.of[1]] = SelectQuery.RN("gBQ2qpkYxIE=", "woRMg");
        SelectQuery.Lf[SelectQuery.of[2]] = SelectQuery.sN("1NdOTEozNQ9SO4lGkLPmNQ==", "CQomh");
        SelectQuery.Lf[SelectQuery.of[3]] = SelectQuery.qN("PAohMiA9GwU1KQ==", "NoPGE");
        SelectQuery.Lf[SelectQuery.of[4]] = SelectQuery.qN("EDsgJT4VPyY=", "tRCQp");
        SelectQuery.Lf[SelectQuery.of[5]] = SelectQuery.sN("gaids6ng4aMTq786fKwgTQ==", "WBRHN");
        SelectQuery.Lf[SelectQuery.of[6]] = SelectQuery.sN("1ldMStIOaY+/ZcM/Kj2Jvg==", "pwDeI");
        SelectQuery.Lf[SelectQuery.of[7]] = SelectQuery.sN("pqGplRZEP1Q=", "eRxBU");
        SelectQuery.Lf[SelectQuery.of[8]] = SelectQuery.sN("7hyUbEONvcgVq5a7HS5XCA==", "enmgp");
        SelectQuery.Lf[SelectQuery.of[9]] = SelectQuery.qN("PS00Mw==", "YDWGa");
        SelectQuery.Lf[SelectQuery.of[10]] = SelectQuery.sN("zqkxecizNnI=", "oXJuG");
        SelectQuery.Lf[SelectQuery.of[11]] = SelectQuery.qN("IxoxOxkmETcj", "GsROU");
        SelectQuery.Lf[SelectQuery.of[12]] = SelectQuery.sN("9Oo7nl3Qmh4=", "qlsOP");
        SelectQuery.Lf[SelectQuery.of[13]] = SelectQuery.qN("JwAzOjsiBSUr", "CiPNm");
        SelectQuery.Lf[SelectQuery.of[14]] = SelectQuery.RN("YxHw6mJPkTg=", "WhgUE");
        SelectQuery.Lf[SelectQuery.of[15]] = SelectQuery.sN("LmvCO7prZbk=", "eLxtG");
        SelectQuery.Lf[SelectQuery.of[16]] = SelectQuery.qN("JgIMEA==", "HcauO");
        SelectQuery.Lf[SelectQuery.of[17]] = SelectQuery.sN("VZPUD4PqD8k=", "EVZhJ");
    }

    static {
        SelectQuery.uN();
        SelectQuery.TN();
    }

    private static boolean yN(int n, int n2) {
        return n < n2;
    }

    public SelectQuery(OnlineTableColumnEntity VmRb) {
        SelectQuery wmRb = null;
        wmRb.column = VmRb;
    }

    private static void uN() {
        of = new int[19];
        SelectQuery.of[0] = (0x7B ^ 0xB ^ (0xA5 ^ 0x91)) & (0xEF ^ 0x98 ^ (0x47 ^ 0x74) ^ -" ".length());
        SelectQuery.of[1] = " ".length();
        SelectQuery.of[2] = "  ".length();
        SelectQuery.of[3] = "   ".length();
        SelectQuery.of[4] = 0x29 ^ 0x2D;
        SelectQuery.of[5] = 0x9F ^ 0x9A;
        SelectQuery.of[6] = 0xC7 ^ 0xC1;
        SelectQuery.of[7] = 0x14 ^ 0x1F ^ (0x9B ^ 0x97);
        SelectQuery.of[8] = 0x40 ^ 0x48;
        SelectQuery.of[9] = 1 ^ 0x30 ^ (0xB0 ^ 0x88);
        SelectQuery.of[10] = 0x81 ^ 0x8B ^ "   ".length() & ~"   ".length();
        SelectQuery.of[11] = 0x41 ^ 0x4A;
        SelectQuery.of[12] = 196 + 71 - 228 + 167 ^ 129 + 170 - 128 + 23;
        SelectQuery.of[13] = 0xA3 ^ 0xAE;
        SelectQuery.of[14] = 0x8F ^ 0x81;
        SelectQuery.of[15] = 0x4E ^ 0x41;
        SelectQuery.of[16] = 188 + 149 - 206 + 77 ^ 23 + 188 - 174 + 155;
        SelectQuery.of[17] = 0x52 ^ 0x36 ^ (0x64 ^ 0x11);
        SelectQuery.of[18] = 0xD9 ^ 0xC1 ^ (0xB4 ^ 0xBE);
    }

    @Override
    public Map<String, Object> getQuery() {
        SelectQuery qmRb = null;
        HashMap<String, Object> PmRb = new HashMap<String, Object>();
        PmRb.put(Lf[of[0]], Lf[of[1]]);
        "".length();
        PmRb.put(Lf[of[2]], of[1]);
        "".length();
        PmRb.put(Lf[of[3]], of[0]);
        "".length();
        PmRb.put(Lf[of[4]], qmRb.column.getFormDict());
        "".length();
        PmRb.put(Lf[of[5]], qmRb.column.getComments());
        "".length();
        if (SelectQuery.VN(StrUtil.isBlank((CharSequence)qmRb.column.getFormDict()) ? 1 : 0)) {
            PmRb.put(Lf[of[6]], Lf[of[7]]);
            "".length();
            "".length();
            if (-" ".length() != -" ".length()) {
                return null;
            }
        } else {
            PmRb.put(Lf[of[8]], Lf[of[9]]);
            "".length();
        }
        HashMap<String, String> omRb = new HashMap<String, String>();
        omRb.put(Lf[of[10]], Lf[of[11]]);
        "".length();
        omRb.put(Lf[of[12]], Lf[of[13]]);
        "".length();
        PmRb.put(Lf[of[14]], omRb);
        "".length();
        HashMap<String, Object> NmRb = new HashMap<String, Object>();
        NmRb.put(Lf[of[15]], qmRb.column.getQueryInput());
        "".length();
        NmRb.put(Lf[of[16]], qmRb.column.getName());
        "".length();
        NmRb.put(Lf[of[17]], PmRb);
        "".length();
        return NmRb;
    }

    private static String qN(String ELRb, String dLRb) {
        ELRb = new String(Base64.getDecoder().decode(ELRb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder hLRb = new StringBuilder();
        char[] GLRb = dLRb.toCharArray();
        int fLRb = of[0];
        char[] ZkRb = ELRb.toCharArray();
        int ykRb = ZkRb.length;
        int XkRb = of[0];
        while (SelectQuery.yN(XkRb, ykRb)) {
            char LLRb = ZkRb[XkRb];
            hLRb.append((char)(LLRb ^ GLRb[fLRb % GLRb.length]));
            "".length();
            ++fLRb;
            ++XkRb;
            "".length();
            if (((0x8B ^ 0xA7) & ~(0x4E ^ 0x62)) == 0) continue;
            return null;
        }
        return String.valueOf(hLRb);
    }
}

