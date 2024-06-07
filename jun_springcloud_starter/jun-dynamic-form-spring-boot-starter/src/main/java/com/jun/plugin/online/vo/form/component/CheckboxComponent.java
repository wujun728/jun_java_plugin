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

public class CheckboxComponent
extends AbstractComponent {
    private static /* synthetic */ int[] Tf;
    private static /* synthetic */ String[] Pf;

    private static void Go() {
        Pf = new String[Tf[20]];
        CheckboxComponent.Pf[CheckboxComponent.Tf[0]] = CheckboxComponent.wN("Bg0LAwQOHDsDHRcN", "bhmbq");
        CheckboxComponent.Pf[CheckboxComponent.Tf[1]] = CheckboxComponent.XN("8v9efIVn3v8=", "Hybmq");
        CheckboxComponent.Pf[CheckboxComponent.Tf[2]] = CheckboxComponent.ZN("vvNdcSYtReGnEI3f8lPS2A==", "DRHgG");
        CheckboxComponent.Pf[CheckboxComponent.Tf[3]] = CheckboxComponent.ZN("CKOM+MqP30Pp4n3xWLNhdA==", "olvcd");
        CheckboxComponent.Pf[CheckboxComponent.Tf[4]] = CheckboxComponent.wN("NA4mIiQ1HwIlLQ==", "FkWWA");
        CheckboxComponent.Pf[CheckboxComponent.Tf[5]] = CheckboxComponent.ZN("snJ59SQlxNe8iIUe+b2qeQ==", "XPNzK");
        CheckboxComponent.Pf[CheckboxComponent.Tf[6]] = CheckboxComponent.ZN("E8s9ouIkmX0=", "LFidv");
        CheckboxComponent.Pf[CheckboxComponent.Tf[7]] = CheckboxComponent.XN("RXem9ccbPOlYONn5fZz8WQ==", "spXNP");
        CheckboxComponent.Pf[CheckboxComponent.Tf[8]] = CheckboxComponent.wN("JjE5Bycn", "IAMnH");
        CheckboxComponent.Pf[CheckboxComponent.Tf[9]] = CheckboxComponent.wN("DgQSOx0PBzIrAgQ=", "atfRr");
        CheckboxComponent.Pf[CheckboxComponent.Tf[10]] = CheckboxComponent.XN("V4cAYNbNC98=", "uZQUm");
        CheckboxComponent.Pf[CheckboxComponent.Tf[11]] = CheckboxComponent.XN("4pd//hqIZqA=", "kIboB");
        CheckboxComponent.Pf[CheckboxComponent.Tf[12]] = CheckboxComponent.XN("EwgTGBXh/sovx2ZrfARkGA==", "zLYmS");
        CheckboxComponent.Pf[CheckboxComponent.Tf[13]] = CheckboxComponent.wN("IgQHMDw=", "TekEY");
        CheckboxComponent.Pf[CheckboxComponent.Tf[14]] = CheckboxComponent.XN("u8LpjlO1gU2zGNEiVmyX9Q==", "Zjlym");
        CheckboxComponent.Pf[CheckboxComponent.Tf[15]] = CheckboxComponent.XN("ViEb11fmm2I=", "rfaon");
        CheckboxComponent.Pf[CheckboxComponent.Tf[16]] = CheckboxComponent.wN("IA0RLw==", "TtaJv");
        CheckboxComponent.Pf[CheckboxComponent.Tf[17]] = CheckboxComponent.ZN("fypkxqKrDjA=", "WGOUL");
        CheckboxComponent.Pf[CheckboxComponent.Tf[18]] = CheckboxComponent.wN("OhchEg==", "TvLwe");
        CheckboxComponent.Pf[CheckboxComponent.Tf[19]] = CheckboxComponent.wN("HyYjJSMeJQ==", "pVWLL");
    }

    private static String ZN(String qERb, String RERb) {
        try {
            SecretKeySpec VERb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(RERb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher uERb = Cipher.getInstance("Blowfish");
            uERb.init(Tf[2], VERb);
            return new String(uERb.doFinal(Base64.getDecoder().decode(qERb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception TERb) {
            TERb.printStackTrace();
            return null;
        }
    }

    private static void oo() {
        Tf = new int[21];
        CheckboxComponent.Tf[0] = (64 + 178 - 168 + 139 ^ 83 + 122 - 145 + 73) & (0x31 ^ 0x42 ^ (0x4E ^ 0x6D) ^ -" ".length());
        CheckboxComponent.Tf[1] = " ".length();
        CheckboxComponent.Tf[2] = "  ".length();
        CheckboxComponent.Tf[3] = "   ".length();
        CheckboxComponent.Tf[4] = 93 + 78 - 68 + 43 ^ 47 + 58 - 74 + 119;
        CheckboxComponent.Tf[5] = 0xB2 ^ 0xBB ^ (0x3E ^ 0x32);
        CheckboxComponent.Tf[6] = 0x5B ^ 0x6D ^ (0x73 ^ 0x43);
        CheckboxComponent.Tf[7] = 102 + 45 - 52 + 41 ^ 106 + 82 - 160 + 115;
        CheckboxComponent.Tf[8] = 0x18 ^ 0x10;
        CheckboxComponent.Tf[9] = 56 + 10 - -64 + 24 ^ 96 + 5 - 38 + 84;
        CheckboxComponent.Tf[10] = 0xCA ^ 0xB0 ^ (0x2E ^ 0x5E);
        CheckboxComponent.Tf[11] = 0x17 ^ 0x1C;
        CheckboxComponent.Tf[12] = 0x66 ^ 0x22 ^ (0x7B ^ 0x33);
        CheckboxComponent.Tf[13] = 0x55 ^ 0x58;
        CheckboxComponent.Tf[14] = 40 + 86 - 91 + 114 ^ 39 + 87 - 41 + 70;
        CheckboxComponent.Tf[15] = 146 + 158 - 267 + 126 ^ 117 + 67 - 17 + 5;
        CheckboxComponent.Tf[16] = 0x91 ^ 0x81;
        CheckboxComponent.Tf[17] = 0x24 ^ 0x4C ^ (0x72 ^ 0xB);
        CheckboxComponent.Tf[18] = 0x6B ^ 0x79;
        CheckboxComponent.Tf[19] = 0xB9 ^ 0x98 ^ (0x23 ^ 0x11);
        CheckboxComponent.Tf[20] = 0x97 ^ 0x83;
    }

    public CheckboxComponent(OnlineTableColumnEntity AGRb) {
        CheckboxComponent bGRb = null;
        bGRb.column = AGRb;
    }

    private static boolean Po(int n) {
        return n != 0;
    }

    private static boolean yo(int n, int n2) {
        return n < n2;
    }

    @Override
    public Map<String, Object> getComponent() {
        CheckboxComponent VfRb = null;
        HashMap<String, Object> ufRb = new HashMap<String, Object>();
        ufRb.put(Pf[Tf[0]], VfRb.column.getFormDefault());
        "".length();
        ufRb.put(Pf[Tf[1]], Tf[0]);
        "".length();
        ufRb.put(Pf[Tf[2]], Tf[0]);
        "".length();
        ufRb.put(Pf[Tf[3]], Tf[0]);
        "".length();
        ufRb.put(Pf[Tf[4]], Tf[0]);
        "".length();
        ufRb.put(Pf[Tf[5]], VfRb.column.getFormDict());
        "".length();
        ufRb.put(Pf[Tf[6]], VfRb.getRules());
        "".length();
        if (CheckboxComponent.Po(StrUtil.isBlank((CharSequence)VfRb.column.getFormDict()) ? 1 : 0)) {
            ufRb.put(Pf[Tf[7]], Pf[Tf[8]]);
            "".length();
            "".length();
            if (null != null) {
                return null;
            }
        } else {
            ufRb.put(Pf[Tf[9]], Pf[Tf[10]]);
            "".length();
        }
        HashMap<String, String> TfRb = new HashMap<String, String>();
        TfRb.put(Pf[Tf[11]], Pf[Tf[12]]);
        "".length();
        TfRb.put(Pf[Tf[13]], Pf[Tf[14]]);
        "".length();
        ufRb.put(Pf[Tf[15]], TfRb);
        "".length();
        HashMap<String, Object> sfRb = new HashMap<String, Object>();
        sfRb.put(Pf[Tf[16]], VfRb.column.getFormInput());
        "".length();
        sfRb.put(Pf[Tf[17]], VfRb.column.getComments());
        "".length();
        sfRb.put(Pf[Tf[18]], VfRb.column.getName());
        "".length();
        sfRb.put(Pf[Tf[19]], ufRb);
        "".length();
        return sfRb;
    }

    static {
        CheckboxComponent.oo();
        CheckboxComponent.Go();
    }

    private static String XN(String ffRb, String CfRb) {
        try {
            SecretKeySpec JfRb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(CfRb.getBytes(StandardCharsets.UTF_8)), Tf[8]), "DES");
            Cipher hfRb = Cipher.getInstance("DES");
            hfRb.init(Tf[2], JfRb);
            return new String(hfRb.doFinal(Base64.getDecoder().decode(ffRb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception GfRb) {
            GfRb.printStackTrace();
            return null;
        }
    }

    private static String wN(String wdRb, String VdRb) {
        wdRb = new String(Base64.getDecoder().decode(wdRb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder ZdRb = new StringBuilder();
        char[] ydRb = VdRb.toCharArray();
        int XdRb = Tf[0];
        char[] RdRb = wdRb.toCharArray();
        int qdRb = RdRb.length;
        int PdRb = Tf[0];
        while (CheckboxComponent.yo(PdRb, qdRb)) {
            char CERb = RdRb[PdRb];
            ZdRb.append((char)(CERb ^ ydRb[XdRb % ydRb.length]));
            "".length();
            ++XdRb;
            ++PdRb;
            "".length();
            if (((24 + 38 - -120 + 37 ^ 16 + 54 - -64 + 6) & (210 + 141 - 145 + 17 ^ 6 + 61 - -18 + 51 ^ -" ".length())) == 0) continue;
            return null;
        }
        return String.valueOf(ZdRb);
    }
}

