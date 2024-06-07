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

public class DateTimeComponent
extends AbstractComponent {
    private static /* synthetic */ String[] GE;
    private static /* synthetic */ int[] kE;

    private static String Tk(String TbTb, String NbTb) {
        TbTb = new String(Base64.getDecoder().decode(TbTb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder RbTb = new StringBuilder();
        char[] qbTb = NbTb.toCharArray();
        int PbTb = kE[0];
        char[] JbTb = TbTb.toCharArray();
        int hbTb = JbTb.length;
        int GbTb = kE[0];
        while (DateTimeComponent.wk(GbTb, hbTb)) {
            char ubTb = JbTb[GbTb];
            RbTb.append((char)(ubTb ^ qbTb[PbTb % qbTb.length]));
            "".length();
            ++PbTb;
            ++GbTb;
            "".length();
            if ("   ".length() == "   ".length()) continue;
            return null;
        }
        return String.valueOf(RbTb);
    }

    private static String Lk(String hCTb, String JCTb) {
        try {
            SecretKeySpec NCTb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(JCTb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher mCTb = Cipher.getInstance("Blowfish");
            mCTb.init(kE[2], NCTb);
            return new String(mCTb.doFinal(Base64.getDecoder().decode(hCTb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception LCTb) {
            LCTb.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> getComponent() {
        DateTimeComponent XCTb = null;
        HashMap<String, Object> wCTb = new HashMap<String, Object>();
        wCTb.put(GE[kE[0]], XCTb.column.getFormDefault());
        "".length();
        wCTb.put(GE[kE[1]], GE[kE[2]]);
        "".length();
        wCTb.put(GE[kE[3]], GE[kE[4]]);
        "".length();
        wCTb.put(GE[kE[5]], GE[kE[6]]);
        "".length();
        wCTb.put(GE[kE[7]], null);
        "".length();
        wCTb.put(GE[kE[8]], null);
        "".length();
        wCTb.put(GE[kE[9]], null);
        "".length();
        wCTb.put(GE[kE[10]], kE[0]);
        "".length();
        wCTb.put(GE[kE[11]], kE[0]);
        "".length();
        wCTb.put(GE[kE[12]], kE[0]);
        "".length();
        wCTb.put(GE[kE[13]], XCTb.getRules());
        "".length();
        HashMap<String, Object> VCTb = new HashMap<String, Object>();
        VCTb.put(GE[kE[14]], GE[kE[15]]);
        "".length();
        VCTb.put(GE[kE[16]], XCTb.column.getComments());
        "".length();
        VCTb.put(GE[kE[17]], XCTb.column.getName());
        "".length();
        VCTb.put(GE[kE[18]], wCTb);
        "".length();
        return VCTb;
    }

    private static boolean wk(int n, int n2) {
        return n < n2;
    }

    static {
        DateTimeComponent.Vk();
        DateTimeComponent.uk();
    }

    private static void Vk() {
        kE = new int[20];
        DateTimeComponent.kE[0] = (62 + 93 - 95 + 134 ^ 20 + 59 - 57 + 124) & (0xE5 ^ 0x86 ^ (0x32 ^ 1) ^ -" ".length());
        DateTimeComponent.kE[1] = " ".length();
        DateTimeComponent.kE[2] = "  ".length();
        DateTimeComponent.kE[3] = "   ".length();
        DateTimeComponent.kE[4] = 0x20 ^ 0x24;
        DateTimeComponent.kE[5] = 0xA7 ^ 0xA2;
        DateTimeComponent.kE[6] = 1 ^ 0x58 ^ (0x98 ^ 0xC7);
        DateTimeComponent.kE[7] = 0x3B ^ 0x3C;
        DateTimeComponent.kE[8] = 0xB5 ^ 0xBD;
        DateTimeComponent.kE[9] = 0x1E ^ 0x17;
        DateTimeComponent.kE[10] = 0x2E ^ 0x24;
        DateTimeComponent.kE[11] = 1 ^ 0x26 ^ (2 ^ 0x2E);
        DateTimeComponent.kE[12] = 0xBE ^ 0x96 ^ (4 ^ 0x20);
        DateTimeComponent.kE[13] = 145 + 59 - 109 + 74 ^ 112 + 42 - 130 + 140;
        DateTimeComponent.kE[14] = 35 + 33 - -18 + 97 ^ 17 + 180 - 112 + 100;
        DateTimeComponent.kE[15] = 0x77 ^ 0x78;
        DateTimeComponent.kE[16] = 0x47 ^ 0x4E ^ (0x1B ^ 2);
        DateTimeComponent.kE[17] = 0xEB ^ 0x81 ^ (0x63 ^ 0x18);
        DateTimeComponent.kE[18] = 0x8B ^ 0x99;
        DateTimeComponent.kE[19] = 0xD4 ^ 0xC7;
    }

    public DateTimeComponent(OnlineTableColumnEntity ddTb) {
        DateTimeComponent EdTb = null;
        EdTb.column = ddTb;
    }

    private static String Rk(String VATb, String wATb) {
        try {
            SecretKeySpec AbTb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(wATb.getBytes(StandardCharsets.UTF_8)), kE[8]), "DES");
            Cipher ZATb = Cipher.getInstance("DES");
            ZATb.init(kE[2], AbTb);
            return new String(ZATb.doFinal(Base64.getDecoder().decode(VATb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception yATb) {
            yATb.printStackTrace();
            return null;
        }
    }

    private static void uk() {
        GE = new String[kE[19]];
        DateTimeComponent.GE[DateTimeComponent.kE[0]] = DateTimeComponent.Lk("uT3Cpao33NyQtu58uAP9Uw==", "LIgOZ");
        DateTimeComponent.GE[DateTimeComponent.kE[1]] = DateTimeComponent.Rk("exXWT5E6fHw=", "ktWYr");
        DateTimeComponent.GE[DateTimeComponent.kE[2]] = DateTimeComponent.Lk("JdAMhEOa76g=", "epUND");
        DateTimeComponent.GE[DateTimeComponent.kE[3]] = DateTimeComponent.Lk("oekb6Kf8rcM=", "fgzYQ");
        DateTimeComponent.GE[DateTimeComponent.kE[4]] = DateTimeComponent.Rk("fORwVaV4G+J98IDPBT1ghw==", "ZNnnS");
        DateTimeComponent.GE[DateTimeComponent.kE[5]] = DateTimeComponent.Rk("h9vPse5n4sY=", "HNjGj");
        DateTimeComponent.GE[DateTimeComponent.kE[6]] = DateTimeComponent.Tk("HA8fN14IG2sqN2UeDlQeKGw1HQ==", "EVFns");
        DateTimeComponent.GE[DateTimeComponent.kE[7]] = DateTimeComponent.Lk("lBR93QjyUikWf9QQBqWEOQ==", "HzyEV");
        DateTimeComponent.GE[DateTimeComponent.kE[8]] = DateTimeComponent.Lk("WqiWRacmBHyMYylS3dusZfMDAVa4by9J", "LLUeU");
        DateTimeComponent.GE[DateTimeComponent.kE[9]] = DateTimeComponent.Lk("Z09ytS5iQ/andpLbFhyYzA==", "prWwa");
        DateTimeComponent.GE[DateTimeComponent.kE[10]] = DateTimeComponent.Lk("bOM7oV7h+ar81U9EOKaoOQ==", "nVxSm");
        DateTimeComponent.GE[DateTimeComponent.kE[11]] = DateTimeComponent.Rk("WOLce1Q/8KHcHLphmOAPfQ==", "zTDlx");
        DateTimeComponent.GE[DateTimeComponent.kE[12]] = DateTimeComponent.Tk("Ex4/LQMREDYp", "prZLq");
        DateTimeComponent.GE[DateTimeComponent.kE[13]] = DateTimeComponent.Lk("rwnCbjkHHaw=", "TpQML");
        DateTimeComponent.GE[DateTimeComponent.kE[14]] = DateTimeComponent.Tk("ODggJg==", "LAPCj");
        DateTimeComponent.GE[DateTimeComponent.kE[15]] = DateTimeComponent.Lk("195cmuRu9ic=", "sJAtZ");
        DateTimeComponent.GE[DateTimeComponent.kE[16]] = DateTimeComponent.Tk("KyUILQc=", "GDjHk");
        DateTimeComponent.GE[DateTimeComponent.kE[17]] = DateTimeComponent.Tk("ABcZIw==", "nvtFy");
        DateTimeComponent.GE[DateTimeComponent.kE[18]] = DateTimeComponent.Tk("JgIfGSknAQ==", "IrkpF");
    }
}

