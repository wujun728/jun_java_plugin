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

public class DateTimeQuery
extends AbstractQuery {
    private static /* synthetic */ int[] fd;
    private static /* synthetic */ String[] Ad;

    private static void Eh() {
        fd = new int[18];
        DateTimeQuery.fd[0] = (0x83 ^ 0x98 ^ (0x11 ^ 0x49)) & (0xC9 ^ 0xA0 ^ (0xB3 ^ 0x99) ^ -" ".length());
        DateTimeQuery.fd[1] = " ".length();
        DateTimeQuery.fd[2] = "  ".length();
        DateTimeQuery.fd[3] = "   ".length();
        DateTimeQuery.fd[4] = 176 + 99 - 252 + 158 ^ 162 + 116 - 161 + 60;
        DateTimeQuery.fd[5] = 0xA9 ^ 0xAC;
        DateTimeQuery.fd[6] = 0x29 ^ 0x2F;
        DateTimeQuery.fd[7] = 0x40 ^ 0x52 ^ (0x28 ^ 0x3D);
        DateTimeQuery.fd[8] = 0xF8 ^ 0x8E ^ (0xDA ^ 0xA4);
        DateTimeQuery.fd[9] = 0x5F ^ 0x78 ^ (0xF ^ 0x21);
        DateTimeQuery.fd[10] = 0xBC ^ 0xB6;
        DateTimeQuery.fd[11] = 0x28 ^ 0x53 ^ (0x17 ^ 0x67);
        DateTimeQuery.fd[12] = 2 + 154 - 132 + 151 ^ 10 + 21 - -38 + 94;
        DateTimeQuery.fd[13] = 5 + 62 - 52 + 186 ^ 91 + 174 - 80 + 11;
        DateTimeQuery.fd[14] = " ".length() ^ (0x6A ^ 0x65);
        DateTimeQuery.fd[15] = 129 + 116 - 170 + 57 ^ 4 + 41 - -53 + 41;
        DateTimeQuery.fd[16] = 0xD6 ^ 0xA8 ^ (0x77 ^ 0x19);
        DateTimeQuery.fd[17] = 0x33 ^ 0x22;
    }

    static {
        DateTimeQuery.Eh();
        DateTimeQuery.bh();
    }

    @Override
    public Map<String, Object> getQuery() {
        DateTimeQuery oRwb = null;
        HashMap<String, Object> qRwb = new HashMap<String, Object>();
        qRwb.put(Ad[fd[0]], oRwb.column.getFormDefault());
        "".length();
        qRwb.put(Ad[fd[1]], Ad[fd[2]]);
        "".length();
        qRwb.put(Ad[fd[3]], fd[1]);
        "".length();
        if (DateTimeQuery.fh(oRwb.column.getQueryType().equals(Ad[fd[4]]) ? 1 : 0)) {
            qRwb.put(Ad[fd[5]], Ad[fd[6]]);
            "".length();
            qRwb.put(Ad[fd[7]], oRwb.column.getComments());
            "".length();
            "".length();
            if ((1 ^ 5) != (0x43 ^ 0x47)) {
                return null;
            }
        } else {
            qRwb.put(Ad[fd[8]], Ad[fd[9]]);
            "".length();
            qRwb.put(Ad[fd[10]], oRwb.column.getComments() + " | \u5f00\u59cb");
            "".length();
            qRwb.put(Ad[fd[11]], Ad[fd[12]]);
            "".length();
        }
        HashMap<String, Object> PRwb = new HashMap<String, Object>();
        PRwb.put(Ad[fd[13]], Ad[fd[14]]);
        "".length();
        PRwb.put(Ad[fd[15]], oRwb.column.getName());
        "".length();
        PRwb.put(Ad[fd[16]], qRwb);
        "".length();
        return PRwb;
    }

    private static boolean hh(int n, int n2) {
        return n < n2;
    }

    private static boolean fh(int n) {
        return n != 0;
    }

    private static String VG(String ARwb, String uqwb) {
        ARwb = new String(Base64.getDecoder().decode(ARwb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder yqwb = new StringBuilder();
        char[] Xqwb = uqwb.toCharArray();
        int wqwb = fd[0];
        char[] qqwb = ARwb.toCharArray();
        int Pqwb = qqwb.length;
        int oqwb = fd[0];
        while (DateTimeQuery.hh(oqwb, Pqwb)) {
            char bRwb = qqwb[oqwb];
            yqwb.append((char)(bRwb ^ Xqwb[wqwb % Xqwb.length]));
            "".length();
            ++wqwb;
            ++oqwb;
            "".length();
            if (((0x6F ^ 0x73) & ~(0xB2 ^ 0xAE)) >= 0) continue;
            return null;
        }
        return String.valueOf(yqwb);
    }

    public DateTimeQuery(OnlineTableColumnEntity VRwb) {
        DateTimeQuery wRwb = null;
        wRwb.column = VRwb;
    }

    private static String mG(String PPwb, String qPwb) {
        try {
            SecretKeySpec uPwb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(qPwb.getBytes(StandardCharsets.UTF_8)), fd[8]), "DES");
            Cipher TPwb = Cipher.getInstance("DES");
            TPwb.init(fd[2], uPwb);
            return new String(TPwb.doFinal(Base64.getDecoder().decode(PPwb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception sPwb) {
            sPwb.printStackTrace();
            return null;
        }
    }

    private static void bh() {
        Ad = new String[fd[17]];
        DateTimeQuery.Ad[DateTimeQuery.fd[0]] = DateTimeQuery.LG("/3bt1HrMvtfJ0BWuEcn8zw==", "GKTzh");
        DateTimeQuery.Ad[DateTimeQuery.fd[1]] = DateTimeQuery.mG("Q41XixZ/Czs=", "nGRud");
        DateTimeQuery.Ad[DateTimeQuery.fd[2]] = DateTimeQuery.LG("Bhkx0XVb1qNJx2wLhkbDvPtJ7l5CBcBQ", "hVbwv");
        DateTimeQuery.Ad[DateTimeQuery.fd[3]] = DateTimeQuery.mG("4mJ7QXESgJyKqwKOkXZgPQ==", "SuMzZ");
        DateTimeQuery.Ad[DateTimeQuery.fd[4]] = DateTimeQuery.mG("X4Ia1y2jgOM=", "NWPeo");
        DateTimeQuery.Ad[DateTimeQuery.fd[5]] = DateTimeQuery.mG("a7gbdUHnXhY=", "GLyBQ");
        DateTimeQuery.Ad[DateTimeQuery.fd[6]] = DateTimeQuery.mG("A2IJpN4ZZQ4Mdq+UHThhIw==", "lZzQG");
        DateTimeQuery.Ad[DateTimeQuery.fd[7]] = DateTimeQuery.VG("AhsqKCwaGCcvLAA=", "rwKKI");
        DateTimeQuery.Ad[DateTimeQuery.fd[8]] = DateTimeQuery.LG("pj2WMPuZ9LE=", "LQMyi");
        DateTimeQuery.Ad[DateTimeQuery.fd[9]] = DateTimeQuery.LG("6NWp/drFUfOmtvJMD3HmdA==", "FCybV");
        DateTimeQuery.Ad[DateTimeQuery.fd[10]] = DateTimeQuery.mG("K0auo0TkWw31W0jGZ1r8uFeBQdLjMMeL", "hsBYA");
        DateTimeQuery.Ad[DateTimeQuery.fd[11]] = DateTimeQuery.LG("XPV++H8V5shydojUmR1eWg==", "zOeHq");
        DateTimeQuery.Ad[DateTimeQuery.fd[12]] = DateTimeQuery.LG("5gggYJnWaMQ=", "zxbcg");
        DateTimeQuery.Ad[DateTimeQuery.fd[13]] = DateTimeQuery.mG("ELgyzUzoHfA=", "HAOzG");
        DateTimeQuery.Ad[DateTimeQuery.fd[14]] = DateTimeQuery.mG("xHc8PKG2bS8=", "YQUVb");
        DateTimeQuery.Ad[DateTimeQuery.fd[15]] = DateTimeQuery.mG("yUmo85t+gPw=", "Guasb");
        DateTimeQuery.Ad[DateTimeQuery.fd[16]] = DateTimeQuery.LG("QjssLq484Ys=", "IicfL");
    }

    private static String LG(String Eqwb, String bqwb) {
        try {
            SecretKeySpec hqwb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(bqwb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher Gqwb = Cipher.getInstance("Blowfish");
            Gqwb.init(fd[2], hqwb);
            return new String(Gqwb.doFinal(Base64.getDecoder().decode(Eqwb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception fqwb) {
            fqwb.printStackTrace();
            return null;
        }
    }
}

