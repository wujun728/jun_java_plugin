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

public class SelectComponent
extends AbstractComponent {
    private static /* synthetic */ String[] LE;
    private static /* synthetic */ int[] qE;

    public SelectComponent(OnlineTableColumnEntity GXsb) {
        SelectComponent hXsb = null;
        hXsb.column = GXsb;
    }

    @Override
    public Map<String, Object> getComponent() {
        SelectComponent Xwsb = null;
        HashMap<String, Object> AXsb = new HashMap<String, Object>();
        AXsb.put(LE[qE[0]], Xwsb.column.getFormDefault());
        "".length();
        AXsb.put(LE[qE[1]], LE[qE[2]]);
        "".length();
        AXsb.put(LE[qE[3]], qE[0]);
        "".length();
        AXsb.put(LE[qE[4]], null);
        "".length();
        AXsb.put(LE[qE[5]], qE[0]);
        "".length();
        AXsb.put(LE[qE[6]], qE[0]);
        "".length();
        AXsb.put(LE[qE[7]], qE[0]);
        "".length();
        AXsb.put(LE[qE[8]], qE[0]);
        "".length();
        AXsb.put(LE[qE[9]], qE[0]);
        "".length();
        AXsb.put(LE[qE[10]], Xwsb.column.getFormDict());
        "".length();
        AXsb.put(LE[qE[11]], Xwsb.getRules());
        "".length();
        if (SelectComponent.oL(StrUtil.isBlank((CharSequence)Xwsb.column.getFormDict()) ? 1 : 0)) {
            AXsb.put(LE[qE[12]], LE[qE[13]]);
            "".length();
            "".length();
            if (-(0xB0 ^ 0xAF ^ (0x8B ^ 0x90)) >= 0) {
                return null;
            }
        } else {
            AXsb.put(LE[qE[14]], LE[qE[15]]);
            "".length();
        }
        HashMap<String, String> Zwsb = new HashMap<String, String>();
        Zwsb.put(LE[qE[16]], LE[qE[17]]);
        "".length();
        Zwsb.put(LE[qE[18]], LE[qE[19]]);
        "".length();
        AXsb.put(LE[qE[20]], Zwsb);
        "".length();
        HashMap<String, Object> ywsb = new HashMap<String, Object>();
        ywsb.put(LE[qE[21]], Xwsb.column.getFormInput());
        "".length();
        ywsb.put(LE[qE[22]], Xwsb.column.getComments());
        "".length();
        ywsb.put(LE[qE[23]], Xwsb.column.getName());
        "".length();
        ywsb.put(LE[qE[24]], AXsb);
        "".length();
        return ywsb;
    }

    private static boolean PL(int n, int n2) {
        return n < n2;
    }

    private static String yk(String dwsb, String hwsb) {
        dwsb = new String(Base64.getDecoder().decode(dwsb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder Gwsb = new StringBuilder();
        char[] fwsb = hwsb.toCharArray();
        int Ewsb = qE[0];
        char[] yVsb = dwsb.toCharArray();
        int XVsb = yVsb.length;
        int wVsb = qE[0];
        while (SelectComponent.PL(wVsb, XVsb)) {
            char kwsb = yVsb[wVsb];
            Gwsb.append((char)(kwsb ^ fwsb[Ewsb % fwsb.length]));
            "".length();
            ++Ewsb;
            ++wVsb;
            "".length();
            if ("   ".length() >= 0) continue;
            return null;
        }
        return String.valueOf(Gwsb);
    }

    private static void hL() {
        LE = new String[qE[25]];
        SelectComponent.LE[SelectComponent.qE[0]] = SelectComponent.Xk("iYUpOMB2xZu6GvYfURuv8w==", "kmBOK");
        SelectComponent.LE[SelectComponent.qE[1]] = SelectComponent.Xk("k0sO6MdB634=", "JGySI");
        SelectComponent.LE[SelectComponent.qE[2]] = SelectComponent.Xk("/hGJdd5r+jk=", "uTlnG");
        SelectComponent.LE[SelectComponent.qE[3]] = SelectComponent.yk("KgUeJAE3HBc=", "GprPh");
        SelectComponent.LE[SelectComponent.qE[4]] = SelectComponent.Zk("7FXsWB42G3pSEim7Yw8Muw==", "KSLex");
        SelectComponent.LE[SelectComponent.qE[5]] = SelectComponent.yk("NTMENDsROwI0", "YRfQW");
        SelectComponent.LE[SelectComponent.qE[6]] = SelectComponent.yk("PB4VOBYoFhsgFg==", "ZwyLs");
        SelectComponent.LE[SelectComponent.qE[7]] = SelectComponent.Xk("G+dQn05Bj/HfOnOe+6QeVA==", "SfDMz");
        SelectComponent.LE[SelectComponent.qE[8]] = SelectComponent.Zk("lAlbqXFOz0KpWHxCf3ghrw==", "cotKN");
        SelectComponent.LE[SelectComponent.qE[9]] = SelectComponent.Zk("GM5y+MzsQWDFaImVRw/twQ==", "AxURZ");
        SelectComponent.LE[SelectComponent.qE[10]] = SelectComponent.Xk("7qlv82FpZzwd3Vr0VACwYg==", "OXbda");
        SelectComponent.LE[SelectComponent.qE[11]] = SelectComponent.Xk("qRNnMjxccMw=", "IJMxU");
        SelectComponent.LE[SelectComponent.qE[12]] = SelectComponent.Xk("yofSL9fDyyJz2aXSdNJARQ==", "vcQzO");
        SelectComponent.LE[SelectComponent.qE[13]] = SelectComponent.yk("NwMmATo2", "XsRhU");
        SelectComponent.LE[SelectComponent.qE[14]] = SelectComponent.yk("LRsGCDUsGCYYKic=", "BkraZ");
        SelectComponent.LE[SelectComponent.qE[15]] = SelectComponent.Zk("SPR6bTchxmA=", "orbkZ");
        SelectComponent.LE[SelectComponent.qE[16]] = SelectComponent.yk("OjsnDCE=", "VZEiM");
        SelectComponent.LE[SelectComponent.qE[17]] = SelectComponent.yk("BhkCOA4DEgQg", "bpaLB");
        SelectComponent.LE[SelectComponent.qE[18]] = SelectComponent.yk("Bg49GTw=", "poQlY");
        SelectComponent.LE[SelectComponent.qE[19]] = SelectComponent.yk("LCItBRApJzsU", "HKNqF");
        SelectComponent.LE[SelectComponent.qE[20]] = SelectComponent.Zk("KtVYcVQFn9I=", "ndVXI");
        SelectComponent.LE[SelectComponent.qE[21]] = SelectComponent.Xk("FKiaz5JEoCs=", "WlJiS");
        SelectComponent.LE[SelectComponent.qE[22]] = SelectComponent.Zk("51wTjOE735M=", "dKiGA");
        SelectComponent.LE[SelectComponent.qE[23]] = SelectComponent.Zk("ICD+BRdc+aM=", "bWOQD");
        SelectComponent.LE[SelectComponent.qE[24]] = SelectComponent.yk("LRUSKCEsFg==", "BefAN");
    }

    private static String Zk(String Xusb, String wusb) {
        try {
            SecretKeySpec CVsb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(wusb.getBytes(StandardCharsets.UTF_8)), qE[8]), "DES");
            Cipher bVsb = Cipher.getInstance("DES");
            bVsb.init(qE[2], CVsb);
            return new String(bVsb.doFinal(Base64.getDecoder().decode(Xusb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception AVsb) {
            AVsb.printStackTrace();
            return null;
        }
    }

    static {
        SelectComponent.NL();
        SelectComponent.hL();
    }

    private static String Xk(String NVsb, String kVsb) {
        try {
            SecretKeySpec qVsb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(kVsb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher PVsb = Cipher.getInstance("Blowfish");
            PVsb.init(qE[2], qVsb);
            return new String(PVsb.doFinal(Base64.getDecoder().decode(NVsb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception oVsb) {
            oVsb.printStackTrace();
            return null;
        }
    }

    private static void NL() {
        qE = new int[26];
        SelectComponent.qE[0] = (0x71 ^ 0x74) & ~(0x27 ^ 0x22);
        SelectComponent.qE[1] = " ".length();
        SelectComponent.qE[2] = "  ".length();
        SelectComponent.qE[3] = "   ".length();
        SelectComponent.qE[4] = 0x16 ^ 0xB ^ (0x72 ^ 0x6B);
        SelectComponent.qE[5] = 40 + 143 - 86 + 65 ^ 96 + 40 - 129 + 160;
        SelectComponent.qE[6] = 0xC ^ 8 ^ "  ".length();
        SelectComponent.qE[7] = 0xA6 ^ 0xA1;
        SelectComponent.qE[8] = 0xC ^ 4;
        SelectComponent.qE[9] = 0x9E ^ 0xAE ^ (0x12 ^ 0x2B);
        SelectComponent.qE[10] = 14 + 169 - 182 + 184 ^ 130 + 107 - 106 + 48;
        SelectComponent.qE[11] = 0x58 ^ 0x53;
        SelectComponent.qE[12] = 21 + 61 - 4 + 81 ^ 145 + 64 - 198 + 136;
        SelectComponent.qE[13] = 0x59 ^ 0x54;
        SelectComponent.qE[14] = 127 + 64 - 189 + 159 ^ 57 + 144 - 142 + 116;
        SelectComponent.qE[15] = 92 + 90 - 103 + 49 ^ 100 + 19 - 80 + 104;
        SelectComponent.qE[16] = 0x8A ^ 0x83 ^ (0xAC ^ 0xB5);
        SelectComponent.qE[17] = 0x24 ^ 0x35;
        SelectComponent.qE[18] = 0xD2 ^ 0xC0;
        SelectComponent.qE[19] = 65 + 61 - 20 + 28 ^ 106 + 102 - 92 + 33;
        SelectComponent.qE[20] = 0xB0 ^ 0xA4;
        SelectComponent.qE[21] = 0xA1 ^ 0xB4;
        SelectComponent.qE[22] = 0x6F ^ 0x79;
        SelectComponent.qE[23] = 42 + 99 - 134 + 125 ^ 31 + 98 - 79 + 97;
        SelectComponent.qE[24] = 0x1C ^ 8 ^ (0x12 ^ 0x1E);
        SelectComponent.qE[25] = 75 + 89 - 67 + 57 ^ 10 + 115 - 5 + 11;
    }

    private static boolean oL(int n) {
        return n != 0;
    }
}

