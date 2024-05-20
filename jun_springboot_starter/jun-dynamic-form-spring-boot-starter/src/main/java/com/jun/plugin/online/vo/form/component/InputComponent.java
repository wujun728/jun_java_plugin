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

public class InputComponent
extends AbstractComponent {
    private static /* synthetic */ int[] Ed;
    private static /* synthetic */ String[] PC;

    private static String kf(String Cwwb, String wVwb) {
        Cwwb = new String(Base64.getDecoder().decode(Cwwb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder Awwb = new StringBuilder();
        char[] ZVwb = wVwb.toCharArray();
        int yVwb = Ed[0];
        char[] sVwb = Cwwb.toCharArray();
        int RVwb = sVwb.length;
        int qVwb = Ed[0];
        while (InputComponent.Ah(qVwb, RVwb)) {
            char dwwb = sVwb[qVwb];
            Awwb.append((char)(dwwb ^ ZVwb[yVwb % ZVwb.length]));
            "".length();
            ++yVwb;
            ++qVwb;
            "".length();
            if ((0xA9 ^ 0xA3 ^ (0xC8 ^ 0xC6)) != "   ".length()) continue;
            return null;
        }
        return String.valueOf(Awwb);
    }

    private static void ZG() {
        Ed = new int[17];
        InputComponent.Ed[0] = (4 ^ 0x21) & ~(0x7F ^ 0x5A);
        InputComponent.Ed[1] = " ".length();
        InputComponent.Ed[2] = "  ".length();
        InputComponent.Ed[3] = "   ".length();
        InputComponent.Ed[4] = 5 ^ 0x52 ^ (0x6D ^ 0x3E);
        InputComponent.Ed[5] = 0x85 ^ 0x80;
        InputComponent.Ed[6] = 165 + 1 - 132 + 153 ^ 105 + 143 - 192 + 133;
        InputComponent.Ed[7] = 0x35 ^ 0x32;
        InputComponent.Ed[8] = 121 + 122 - 210 + 115 ^ 11 + 154 - 74 + 65;
        InputComponent.Ed[9] = 0x96 ^ 0x9F;
        InputComponent.Ed[10] = 8 ^ 0x16 ^ (0xB0 ^ 0xA4);
        InputComponent.Ed[11] = 107 + 95 - 114 + 50 ^ 62 + 111 - 167 + 123;
        InputComponent.Ed[12] = 0xBF ^ 0xB3;
        InputComponent.Ed[13] = 0xBE ^ 0xB3;
        InputComponent.Ed[14] = 0xB4 ^ 0xBA;
        InputComponent.Ed[15] = 0xD ^ 2;
        InputComponent.Ed[16] = 0xD8 ^ 0xB2 ^ (0x75 ^ 0xF);
    }

    public InputComponent(OnlineTableColumnEntity NXwb) {
        InputComponent oXwb = null;
        oXwb.column = NXwb;
    }

    private static String bf(String Twwb, String swwb) {
        try {
            SecretKeySpec wwwb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(swwb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher Vwwb = Cipher.getInstance("Blowfish");
            Vwwb.init(Ed[2], wwwb);
            return new String(Vwwb.doFinal(Base64.getDecoder().decode(Twwb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception uwwb) {
            uwwb.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> getComponent() {
        InputComponent dXwb = null;
        HashMap<String, Object> fXwb = new HashMap<String, Object>();
        fXwb.put(PC[Ed[0]], dXwb.column.getFormDefault());
        "".length();
        fXwb.put(PC[Ed[1]], PC[Ed[2]]);
        "".length();
        fXwb.put(PC[Ed[3]], null);
        "".length();
        fXwb.put(PC[Ed[4]], Ed[0]);
        "".length();
        fXwb.put(PC[Ed[5]], Ed[0]);
        "".length();
        fXwb.put(PC[Ed[6]], Ed[0]);
        "".length();
        fXwb.put(PC[Ed[7]], Ed[0]);
        "".length();
        fXwb.put(PC[Ed[8]], Ed[0]);
        "".length();
        fXwb.put(PC[Ed[9]], Ed[0]);
        "".length();
        fXwb.put(PC[Ed[10]], dXwb.getRules());
        "".length();
        HashMap<String, Object> EXwb = new HashMap<String, Object>();
        EXwb.put(PC[Ed[11]], PC[Ed[12]]);
        "".length();
        EXwb.put(PC[Ed[13]], dXwb.column.getComments());
        "".length();
        EXwb.put(PC[Ed[14]], dXwb.column.getName());
        "".length();
        EXwb.put(PC[Ed[15]], fXwb);
        "".length();
        return EXwb;
    }

    private static void uf() {
        PC = new String[Ed[16]];
        InputComponent.PC[InputComponent.Ed[0]] = InputComponent.bf("t2/fIfTRJnbMNWSjcum+NQ==", "zrgjl");
        InputComponent.PC[InputComponent.Ed[1]] = InputComponent.Gf("dncIqYBsils=", "fRYzU");
        InputComponent.PC[InputComponent.Ed[2]] = InputComponent.bf("UOk04AzFzYU=", "NOXDT");
        InputComponent.PC[InputComponent.Ed[3]] = InputComponent.bf("W0xAqZ1TgxeZvFr0ZgIehw==", "kqZiU");
        InputComponent.PC[InputComponent.Ed[4]] = InputComponent.bf("ghPIuwRx8JswherbAofYBw==", "WkTLz");
        InputComponent.PC[InputComponent.Ed[5]] = InputComponent.bf("j2z66JEBzp1qi4HA/EDVeQ==", "XVqMH");
        InputComponent.PC[InputComponent.Ed[6]] = InputComponent.kf("JwQPMzg7DRc=", "UanWW");
        InputComponent.PC[InputComponent.Ed[7]] = InputComponent.kf("KDQpCRkqOiAN", "KXLhk");
        InputComponent.PC[InputComponent.Ed[8]] = InputComponent.Gf("yNb6TPUuxgSqWu0WmNo6ug==", "nULiP");
        InputComponent.PC[InputComponent.Ed[9]] = InputComponent.bf("DLqsP+LCjSFLl2VhnXC0pw==", "ePzno");
        InputComponent.PC[InputComponent.Ed[10]] = InputComponent.kf("NwIhHBg=", "EwMyk");
        InputComponent.PC[InputComponent.Ed[11]] = InputComponent.Gf("CBp1PK+VZUA=", "mySom");
        InputComponent.PC[InputComponent.Ed[12]] = InputComponent.kf("ESI4HgY=", "xLHkr");
        InputComponent.PC[InputComponent.Ed[13]] = InputComponent.kf("IAAvDS4=", "LaMhB");
        InputComponent.PC[InputComponent.Ed[14]] = InputComponent.bf("EM1nF5iCE8k=", "TiHuV");
        InputComponent.PC[InputComponent.Ed[15]] = InputComponent.Gf("yfLBNvtEa4E=", "VJMVL");
    }

    private static boolean Ah(int n, int n2) {
        return n < n2;
    }

    private static String Gf(String GVwb, String fVwb) {
        try {
            SecretKeySpec kVwb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(fVwb.getBytes(StandardCharsets.UTF_8)), Ed[8]), "DES");
            Cipher JVwb = Cipher.getInstance("DES");
            JVwb.init(Ed[2], kVwb);
            return new String(JVwb.doFinal(Base64.getDecoder().decode(GVwb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception hVwb) {
            hVwb.printStackTrace();
            return null;
        }
    }

    static {
        InputComponent.ZG();
        InputComponent.uf();
    }
}

