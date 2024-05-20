/*
 * Decompiled with CFR 0.152.
 */
package com.jun.plugin.online.vo.form.component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.jun.plugin.online.entity.OnlineTableColumnEntity;

public abstract class AbstractComponent {
    private static /* synthetic */ int[] dd;
    private static /* synthetic */ String[] bd;
    public /* synthetic */ OnlineTableColumnEntity column;

    private static boolean yG(int n, int n2) {
        return n < n2;
    }

    static {
        AbstractComponent.wG();
        AbstractComponent.uG();
    }

    private static boolean XG(int n) {
        return n != 0;
    }

    private static String sG(String Gywb, String fywb) {
        Gywb = new String(Base64.getDecoder().decode(Gywb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder kywb = new StringBuilder();
        char[] Jywb = fywb.toCharArray();
        int hywb = dd[0];
        char[] bywb = Gywb.toCharArray();
        int Aywb = bywb.length;
        int ZXwb = dd[0];
        while (AbstractComponent.yG(ZXwb, Aywb)) {
            char Nywb = bywb[ZXwb];
            kywb.append((char)(Nywb ^ Jywb[hywb % Jywb.length]));
            "".length();
            ++hywb;
            ++ZXwb;
            "".length();
            if ("  ".length() > ((0x96 ^ 0x88 ^ (0x5E ^ 0x63)) & (0xAC ^ 0xB7 ^ (0xA ^ 0x32) ^ -" ".length()))) continue;
            return null;
        }
        return String.valueOf(kywb);
    }

    private static void uG() {
        bd = new String[dd[3]];
        AbstractComponent.bd[AbstractComponent.dd[0]] = AbstractComponent.sG("JzEfExAnMQo=", "UTnfy");
        AbstractComponent.bd[AbstractComponent.dd[1]] = AbstractComponent.sG("PiIwKQI0Ig==", "SGCZc");
        AbstractComponent.bd[AbstractComponent.dd[2]] = AbstractComponent.TG("YM+f/RGSQk1BZP6KGqyJbLwliZGoe2oI", "xiAmb");
    }

    private static String TG(String AZwb, String bZwb) {
        try {
            SecretKeySpec fZwb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(bZwb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher EZwb = Cipher.getInstance("Blowfish");
            EZwb.init(dd[2], fZwb);
            return new String(EZwb.doFinal(Base64.getDecoder().decode(AZwb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception dZwb) {
            dZwb.printStackTrace();
            return null;
        }
    }

    private static void wG() {
        dd = new int[4];
        AbstractComponent.dd[0] = (0x77 ^ 0xE ^ (0x7F ^ 0x50)) & (0xAA ^ 0x85 ^ (0xE0 ^ 0x99) ^ -" ".length());
        AbstractComponent.dd[1] = " ".length();
        AbstractComponent.dd[2] = "  ".length();
        AbstractComponent.dd[3] = "   ".length();
    }

    protected List<Map<String, Object>> getRules() {
        AbstractComponent PZwb = null;
        ArrayList<Map<String, Object>> oZwb = new ArrayList<Map<String, Object>>();
        if (AbstractComponent.XG(PZwb.column.isFormRequired() ? 1 : 0)) {
            HashMap<String, Object> qZwb = new HashMap<String, Object>();
            qZwb.put(bd[dd[0]], dd[1]);
            "".length();
            qZwb.put(bd[dd[1]], bd[dd[2]]);
            "".length();
            oZwb.add(qZwb);
            "".length();
        }
        return oZwb;
    }

    public AbstractComponent() {
        AbstractComponent uZwb;
    }

    public abstract Map<String, Object> getComponent();
}

