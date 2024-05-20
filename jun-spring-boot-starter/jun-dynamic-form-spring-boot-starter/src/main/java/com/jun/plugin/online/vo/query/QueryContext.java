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
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.jun.plugin.online.entity.OnlineTableColumnEntity;

public class QueryContext {
    private /* synthetic */ AbstractQuery query;
    private static /* synthetic */ int[] ud;
    private static /* synthetic */ String[] Rd;

    private static boolean wJ(int n, int n2) {
        return n < n2;
    }

    static {
        QueryContext.TJ();
        QueryContext.NJ();
    }

    public Map<String, Object> getQuery() {
        QueryContext yhub = null;
        return yhub.query.getQuery();
    }

    private static String mJ(String wGub, String VGub) {
        wGub = new String(Base64.getDecoder().decode(wGub.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder bhub = new StringBuilder();
        char[] Ahub = VGub.toCharArray();
        int yGub = ud[0];
        char[] PGub = wGub.toCharArray();
        int NGub = PGub.length;
        int mGub = ud[0];
        while (QueryContext.wJ(mGub, NGub)) {
            char Ehub = PGub[mGub];
            bhub.append((char)(Ehub ^ Ahub[yGub % Ahub.length]));
            "".length();
            ++yGub;
            ++mGub;
            "".length();
            if (null == null) continue;
            return null;
        }
        return String.valueOf(bhub);
    }

    private static String LJ(String sfub, String ofub) {
        try {
            SecretKeySpec Vfub = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(ofub.getBytes(StandardCharsets.UTF_8)), ud[5]), "DES");
            Cipher ufub = Cipher.getInstance("DES");
            ufub.init(ud[2], Vfub);
            return new String(ufub.doFinal(Base64.getDecoder().decode(sfub.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception Tfub) {
            Tfub.printStackTrace();
            return null;
        }
    }

    private static boolean uJ(int n) {
        return n != 0;
    }

    private static String kJ(String kEub, String JEub) {
        try {
            SecretKeySpec qEub = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(JEub.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher PEub = Cipher.getInstance("Blowfish");
            PEub.init(ud[2], qEub);
            return new String(PEub.doFinal(Base64.getDecoder().decode(kEub.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception oEub) {
            oEub.printStackTrace();
            return null;
        }
    }

    private static void TJ() {
        ud = new int[6];
        QueryContext.ud[0] = (0xA8 ^ 0xA3) & ~(0x2D ^ 0x26);
        QueryContext.ud[1] = " ".length();
        QueryContext.ud[2] = "  ".length();
        QueryContext.ud[3] = "   ".length();
        QueryContext.ud[4] = 0x5C ^ 0x58;
        QueryContext.ud[5] = 42 + 33 - -61 + 68 ^ 140 + 98 - 116 + 74;
    }

    private static void NJ() {
        Rd = new String[ud[4]];
        QueryContext.Rd[QueryContext.ud[0]] = QueryContext.kJ("uuz55rf/ZUY=", "VMnhT");
        QueryContext.Rd[QueryContext.ud[1]] = QueryContext.LJ("xpQHT6V06Q0=", "oDNnV");
        QueryContext.Rd[QueryContext.ud[2]] = QueryContext.LJ("e+EVHOfY+9c=", "hNZMz");
        QueryContext.Rd[QueryContext.ud[3]] = QueryContext.mJ("ICY2EgwtKic=", "DGBwx");
    }

    public QueryContext(OnlineTableColumnEntity GJub) {
        QueryContext EJub = null;
        if (QueryContext.uJ(StrUtil.equalsIgnoreCase((CharSequence)GJub.getQueryInput(), (CharSequence)Rd[ud[0]]) ? 1 : 0)) {
            EJub.query = new InputQuery(GJub);
            "".length();
            if ("  ".length() == 0) {
                throw null;
            }
        } else if (QueryContext.uJ(StrUtil.equalsIgnoreCase((CharSequence)GJub.getQueryInput(), (CharSequence)Rd[ud[1]]) ? 1 : 0)) {
            EJub.query = new SelectQuery(GJub);
            "".length();
            if (-"   ".length() >= 0) {
                throw null;
            }
        } else if (QueryContext.uJ(StrUtil.equalsIgnoreCase((CharSequence)GJub.getQueryInput(), (CharSequence)Rd[ud[2]]) ? 1 : 0)) {
            EJub.query = new DateQuery(GJub);
            "".length();
            if ("   ".length() != "   ".length()) {
                throw null;
            }
        } else if (QueryContext.uJ(StrUtil.equalsIgnoreCase((CharSequence)GJub.getQueryInput(), (CharSequence)Rd[ud[3]]) ? 1 : 0)) {
            EJub.query = new DateTimeQuery(GJub);
            "".length();
            if (-"   ".length() >= 0) {
                throw null;
            }
        } else {
            EJub.query = new InputQuery(GJub);
        }
    }
}

