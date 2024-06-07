/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.StrUtil
 */
package com.jun.plugin.online.vo.form.component;

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

public class RadioComponent
extends AbstractComponent {
    private static /* synthetic */ String[] bE;
    private static /* synthetic */ int[] NE;

    @Override
    public Map<String, Object> getComponent() {
        RadioComponent bATb = null;
        HashMap<String, Object> AATb = new HashMap<String, Object>();
        AATb.put(bE[NE[0]], bATb.column.getFormDefault());
        "".length();
        AATb.put(bE[NE[1]], bE[NE[2]]);
        "".length();
        AATb.put(bE[NE[3]], NE[0]);
        "".length();
        AATb.put(bE[NE[4]], NE[0]);
        "".length();
        AATb.put(bE[NE[5]], NE[0]);
        "".length();
        AATb.put(bE[NE[6]], NE[0]);
        "".length();
        AATb.put(bE[NE[7]], bATb.column.getFormDict());
        "".length();
        AATb.put(bE[NE[8]], bATb.getRules());
        "".length();
        if (RadioComponent.kL(StrUtil.isBlank((CharSequence)bATb.column.getFormDict()) ? 1 : 0)) {
            AATb.put(bE[NE[9]], bE[NE[10]]);
            "".length();
            "".length();
            if (((0xBC ^ 0x88) & ~(0x32 ^ 6)) != 0) {
                return null;
            }
        } else {
            AATb.put(bE[NE[11]], bE[NE[12]]);
            "".length();
        }
        HashMap<String, String> ZZsb = new HashMap<String, String>();
        ZZsb.put(bE[NE[13]], bE[NE[14]]);
        "".length();
        ZZsb.put(bE[NE[15]], bE[NE[16]]);
        "".length();
        AATb.put(bE[NE[17]], ZZsb);
        "".length();
        HashMap<String, Object> yZsb = new HashMap<String, Object>();
        yZsb.put(bE[NE[18]], bATb.column.getFormInput());
        "".length();
        yZsb.put(bE[NE[19]], bATb.column.getComments());
        "".length();
        yZsb.put(bE[NE[20]], bATb.column.getName());
        "".length();
        yZsb.put(bE[NE[21]], AATb);
        "".length();
        return yZsb;
    }

    private static void AL() {
        bE = new String[NE[22]];
        RadioComponent.bE[RadioComponent.NE[0]] = RadioComponent.Gk("phN9qbL18lcgEf1ZIm5Nfg==", "ifsor");
        RadioComponent.bE[RadioComponent.NE[1]] = RadioComponent.hk("VlH5gdqu0S4=", "BHFDu");
        RadioComponent.bE[RadioComponent.NE[2]] = RadioComponent.Jk("", "LqsZk");
        RadioComponent.bE[RadioComponent.NE[3]] = RadioComponent.hk("rUjdWIQKtmA=", "uVmSi");
        RadioComponent.bE[RadioComponent.NE[4]] = RadioComponent.Gk("aJUtiaSkZKL7JTxBrV+Y6Q==", "PoKnx");
        RadioComponent.bE[RadioComponent.NE[5]] = RadioComponent.Jk("EDoxFDAYNiY=", "tSBuR");
        RadioComponent.bE[RadioComponent.NE[6]] = RadioComponent.hk("FWvmFv3f4jyE+xv9pfLFSQ==", "oBxdP");
        RadioComponent.bE[RadioComponent.NE[7]] = RadioComponent.Gk("TiQKCYFt96/zm924Ys9nwQ==", "vxnSp");
        RadioComponent.bE[RadioComponent.NE[8]] = RadioComponent.Jk("NgAcBzQ=", "DupbG");
        RadioComponent.bE[RadioComponent.NE[9]] = RadioComponent.Gk("QzmzKY4WHjqHNIe9xwbikA==", "TdLtb");
        RadioComponent.bE[RadioComponent.NE[10]] = RadioComponent.Gk("IjMfnjj9TXg=", "AvDFn");
        RadioComponent.bE[RadioComponent.NE[11]] = RadioComponent.Gk("7LrULa7JufKBgzmL3U4yBQ==", "QOyzv");
        RadioComponent.bE[RadioComponent.NE[12]] = RadioComponent.hk("wTDSdKIOVKo=", "iVLgD");
        RadioComponent.bE[RadioComponent.NE[13]] = RadioComponent.Jk("BS0MAAI=", "iLnen");
        RadioComponent.bE[RadioComponent.NE[14]] = RadioComponent.hk("5czlcmUr8578BSNdVsuKLQ==", "RAqlL");
        RadioComponent.bE[RadioComponent.NE[15]] = RadioComponent.Gk("YgsnqIbZM6k=", "dXGyX");
        RadioComponent.bE[RadioComponent.NE[16]] = RadioComponent.hk("fHNhzDAsef27klrntvlE6g==", "KUXHM");
        RadioComponent.bE[RadioComponent.NE[17]] = RadioComponent.hk("JUscUsiaBgA=", "qahqV");
        RadioComponent.bE[RadioComponent.NE[18]] = RadioComponent.hk("WVlvdcu7w4Y=", "URWYl");
        RadioComponent.bE[RadioComponent.NE[19]] = RadioComponent.Gk("5N/IF9AL3mw=", "xMlRq");
        RadioComponent.bE[RadioComponent.NE[20]] = RadioComponent.Gk("j9PMrUqzVNY=", "HjUHT");
        RadioComponent.bE[RadioComponent.NE[21]] = RadioComponent.Gk("Mh0npzki9Q4=", "NLytQ");
    }

    public RadioComponent(OnlineTableColumnEntity JATb) {
        RadioComponent kATb = null;
        kATb.column = JATb;
    }

    private static void GL() {
        NE = new int[23];
        RadioComponent.NE[0] = (7 ^ 0xA) & ~(0x37 ^ 0x3A);
        RadioComponent.NE[1] = " ".length();
        RadioComponent.NE[2] = "  ".length();
        RadioComponent.NE[3] = "   ".length();
        RadioComponent.NE[4] = 0x81 ^ 0xC4 ^ (7 ^ 0x46);
        RadioComponent.NE[5] = 0x26 ^ 0x23;
        RadioComponent.NE[6] = 0x57 ^ 0x51;
        RadioComponent.NE[7] = 123 + 61 - 20 + 2 ^ 28 + 80 - 31 + 84;
        RadioComponent.NE[8] = 0x37 ^ 0x2A ^ (0xF ^ 0x1A);
        RadioComponent.NE[9] = 22 + 13 - -61 + 62 ^ 26 + 33 - -31 + 61;
        RadioComponent.NE[10] = 0xA7 ^ 0xAD;
        RadioComponent.NE[11] = 73 + 83 - 54 + 43 ^ 143 + 55 - 152 + 108;
        RadioComponent.NE[12] = 0x1C ^ 3 ^ (0x13 ^ 0);
        RadioComponent.NE[13] = 0xB3 ^ 0xBE;
        RadioComponent.NE[14] = 88 + 108 - 83 + 22 ^ 114 + 107 - 132 + 48;
        RadioComponent.NE[15] = 0x92 ^ 0x9D;
        RadioComponent.NE[16] = 0xBE ^ 0xAE;
        RadioComponent.NE[17] = 0x3A ^ 0x66 ^ (0x73 ^ 0x3E);
        RadioComponent.NE[18] = 0x6F ^ 0x7D;
        RadioComponent.NE[19] = 0x5A ^ 0x49;
        RadioComponent.NE[20] = 0x13 ^ 7;
        RadioComponent.NE[21] = 3 ^ 0x16;
        RadioComponent.NE[22] = 0x53 ^ 0x54 ^ (0x75 ^ 0x64);
    }

    private static String hk(String XXsb, String wXsb) {
        try {
            SecretKeySpec Cysb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(wXsb.getBytes(StandardCharsets.UTF_8)), NE[8]), "DES");
            Cipher bysb = Cipher.getInstance("DES");
            bysb.init(NE[2], Cysb);
            return new String(bysb.doFinal(Base64.getDecoder().decode(XXsb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception Aysb) {
            Aysb.printStackTrace();
            return null;
        }
    }

    private static boolean kL(int n) {
        return n != 0;
    }

    static {
        RadioComponent.GL();
        RadioComponent.AL();
    }

    private static String Gk(String mZsb, String LZsb) {
        try {
            SecretKeySpec PZsb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(LZsb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher oZsb = Cipher.getInstance("Blowfish");
            oZsb.init(NE[2], PZsb);
            return new String(oZsb.doFinal(Base64.getDecoder().decode(mZsb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception NZsb) {
            NZsb.printStackTrace();
            return null;
        }
    }

    private static String Jk(String qysb, String uysb) {
        qysb = new String(Base64.getDecoder().decode(qysb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder Tysb = new StringBuilder();
        char[] sysb = uysb.toCharArray();
        int Rysb = NE[0];
        char[] Lysb = qysb.toCharArray();
        int kysb = Lysb.length;
        int Jysb = NE[0];
        while (RadioComponent.LL(Jysb, kysb)) {
            char wysb = Lysb[Jysb];
            Tysb.append((char)(wysb ^ sysb[Rysb % sysb.length]));
            "".length();
            ++Rysb;
            ++Jysb;
            "".length();
            if (-"   ".length() < 0) continue;
            return null;
        }
        return String.valueOf(Tysb);
    }

    private static boolean LL(int n, int n2) {
        return n < n2;
    }
}

