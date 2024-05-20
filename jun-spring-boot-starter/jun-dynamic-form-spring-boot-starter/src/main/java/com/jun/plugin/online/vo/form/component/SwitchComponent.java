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

public class SwitchComponent
extends AbstractComponent {
    private static /* synthetic */ String[] qd;
    private static /* synthetic */ int[] sd;

    private static void JJ() {
        qd = new String[sd[12]];
        SwitchComponent.qd[SwitchComponent.sd[0]] = SwitchComponent.fJ("xsQOis4MjyzUuVeC0+lu9A==", "tNtla");
        SwitchComponent.qd[SwitchComponent.sd[1]] = SwitchComponent.GJ("9uJHhCrho7DOX98UyOMp8g==", "CzJnW");
        SwitchComponent.qd[SwitchComponent.sd[2]] = SwitchComponent.hJ("", "SPnWn");
        SwitchComponent.qd[SwitchComponent.sd[3]] = SwitchComponent.GJ("a7I6x5nejB+pkbVkY5DOWw==", "dNJcq");
        SwitchComponent.qd[SwitchComponent.sd[4]] = SwitchComponent.hJ("", "FbViN");
        SwitchComponent.qd[SwitchComponent.sd[5]] = SwitchComponent.hJ("Ly0GKioLJQAq", "CLdOF");
        SwitchComponent.qd[SwitchComponent.sd[6]] = SwitchComponent.fJ("Z3axMabQYbr8KYj/zSLyhw==", "qfTmv");
        SwitchComponent.qd[SwitchComponent.sd[7]] = SwitchComponent.hJ("OBALCSU=", "JeglV");
        SwitchComponent.qd[SwitchComponent.sd[8]] = SwitchComponent.GJ("sdsHAuvuJfg=", "WymCs");
        SwitchComponent.qd[SwitchComponent.sd[9]] = SwitchComponent.GJ("XmfEI6km7qI=", "IeHCg");
        SwitchComponent.qd[SwitchComponent.sd[10]] = SwitchComponent.hJ("DBcPIA==", "bvbEA");
        SwitchComponent.qd[SwitchComponent.sd[11]] = SwitchComponent.hJ("OikzIRc7Kg==", "UYGHx");
    }

    public SwitchComponent(OnlineTableColumnEntity Gmub) {
        SwitchComponent kmub = null;
        kmub.column = Gmub;
    }

    private static boolean sJ(int n, int n2) {
        return n < n2;
    }

    @Override
    public Map<String, Object> getComponent() {
        SwitchComponent Cmub = null;
        HashMap<String, Object> bmub = new HashMap<String, Object>();
        bmub.put(qd[sd[0]], Cmub.column.getFormDefault());
        "".length();
        bmub.put(qd[sd[1]], qd[sd[2]]);
        "".length();
        bmub.put(qd[sd[3]], qd[sd[4]]);
        "".length();
        bmub.put(qd[sd[5]], sd[0]);
        "".length();
        bmub.put(qd[sd[6]], sd[0]);
        "".length();
        bmub.put(qd[sd[7]], Cmub.getRules());
        "".length();
        HashMap<String, Object> Amub = new HashMap<String, Object>();
        Amub.put(qd[sd[8]], Cmub.column.getFormInput());
        "".length();
        Amub.put(qd[sd[9]], Cmub.column.getComments());
        "".length();
        Amub.put(qd[sd[10]], Cmub.column.getName());
        "".length();
        Amub.put(qd[sd[11]], bmub);
        "".length();
        return Amub;
    }

    private static void qJ() {
        sd = new int[13];
        SwitchComponent.sd[0] = (116 + 82 - 165 + 120 ^ 36 + 14 - -108 + 9) & (57 + 58 - 92 + 134 ^ 51 + 42 - 55 + 125 ^ -" ".length());
        SwitchComponent.sd[1] = " ".length();
        SwitchComponent.sd[2] = "  ".length();
        SwitchComponent.sd[3] = "   ".length();
        SwitchComponent.sd[4] = 0x9B ^ 0x9F;
        SwitchComponent.sd[5] = 0xD3 ^ 0x94 ^ (3 ^ 0x41);
        SwitchComponent.sd[6] = 0x30 ^ 0x3E ^ (0x55 ^ 0x5D);
        SwitchComponent.sd[7] = 0x42 ^ 0x45;
        SwitchComponent.sd[8] = 47 + 154 - 99 + 55 ^ 73 + 148 - 127 + 55;
        SwitchComponent.sd[9] = 0x26 ^ 0x2F;
        SwitchComponent.sd[10] = 78 + 61 - 84 + 127 ^ 111 + 122 - 213 + 168;
        SwitchComponent.sd[11] = 25 + 124 - 31 + 43 ^ 63 + 45 - 14 + 76;
        SwitchComponent.sd[12] = 0xC4 ^ 0x88 ^ (0x3E ^ 0x7E);
    }

    static {
        SwitchComponent.qJ();
        SwitchComponent.JJ();
    }

    private static String GJ(String bLub, String ALub) {
        try {
            SecretKeySpec ELub = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(ALub.getBytes(StandardCharsets.UTF_8)), sd[8]), "DES");
            Cipher dLub = Cipher.getInstance("DES");
            dLub.init(sd[2], ELub);
            return new String(dLub.doFinal(Base64.getDecoder().decode(bLub.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception CLub) {
            CLub.printStackTrace();
            return null;
        }
    }

    private static String hJ(String Lkub, String kkub) {
        Lkub = new String(Base64.getDecoder().decode(Lkub.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder Jkub = new StringBuilder();
        char[] hkub = kkub.toCharArray();
        int Gkub = sd[0];
        char[] Akub = Lkub.toCharArray();
        int ZJub = Akub.length;
        int yJub = sd[0];
        while (SwitchComponent.sJ(yJub, ZJub)) {
            char mkub = Akub[yJub];
            Jkub.append((char)(mkub ^ hkub[Gkub % hkub.length]));
            "".length();
            ++Gkub;
            ++yJub;
            "".length();
            if ((100 + 191 - 208 + 110 ^ 175 + 87 - 231 + 165) > 0) continue;
            return null;
        }
        return String.valueOf(Jkub);
    }

    private static String fJ(String NLub, String oLub) {
        try {
            SecretKeySpec sLub = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(oLub.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher RLub = Cipher.getInstance("Blowfish");
            RLub.init(sd[2], sLub);
            return new String(RLub.doFinal(Base64.getDecoder().decode(NLub.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception qLub) {
            qLub.printStackTrace();
            return null;
        }
    }
}

