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

public class SliderComponent
extends AbstractComponent {
    private static /* synthetic */ String[] Cf;
    private static /* synthetic */ int[] kf;

    private static String AN(String PNRb, String oNRb) {
        try {
            SecretKeySpec sNRb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(oNRb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher RNRb = Cipher.getInstance("Blowfish");
            RNRb.init(kf[2], sNRb);
            return new String(RNRb.doFinal(Base64.getDecoder().decode(PNRb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception qNRb) {
            qNRb.printStackTrace();
            return null;
        }
    }

    private static String um(String ZoRb, String yoRb) {
        try {
            SecretKeySpec EPRb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(yoRb.getBytes(StandardCharsets.UTF_8)), kf[9]), "DES");
            Cipher dPRb = Cipher.getInstance("DES");
            dPRb.init(kf[2], EPRb);
            return new String(dPRb.doFinal(Base64.getDecoder().decode(ZoRb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception CPRb) {
            CPRb.printStackTrace();
            return null;
        }
    }

    static {
        SliderComponent.oN();
        SliderComponent.CN();
    }

    private static void oN() {
        kf = new int[16];
        SliderComponent.kf[0] = (29 + 109 - 60 + 97 ^ 11 + 43 - 2 + 124) & (5 ^ 0x71 ^ (0x59 ^ 0x32) ^ -" ".length());
        SliderComponent.kf[1] = " ".length();
        SliderComponent.kf[2] = "  ".length();
        SliderComponent.kf[3] = "   ".length();
        SliderComponent.kf[4] = 0xC8 ^ 0x94 ^ (0x4C ^ 0x14);
        SliderComponent.kf[5] = 0xFFFFB73B & 0x6FD4;
        SliderComponent.kf[6] = 4 ^ 0x74 ^ (0x5C ^ 0x29);
        SliderComponent.kf[7] = 110 + 5 - 3 + 15 ^ (0xBB ^ 0xC2);
        SliderComponent.kf[8] = 8 ^ 0xF;
        SliderComponent.kf[9] = 0x72 ^ 0x7A;
        SliderComponent.kf[10] = 0xEF ^ 0xB1 ^ (0x6B ^ 0x3C);
        SliderComponent.kf[11] = 111 + 43 - 88 + 108 ^ 45 + 35 - -14 + 70;
        SliderComponent.kf[12] = 0x72 ^ 0x79;
        SliderComponent.kf[13] = 0x1D ^ 0x7A ^ (0x34 ^ 0x5F);
        SliderComponent.kf[14] = 0x95 ^ 0xB8 ^ (0x3F ^ 0x1F);
        SliderComponent.kf[15] = 0xE7 ^ 0xC3 ^ (0x1E ^ 0x34);
    }

    @Override
    public Map<String, Object> getComponent() {
        SliderComponent PPRb = null;
        HashMap<String, Object> oPRb = new HashMap<String, Object>();
        oPRb.put(Cf[kf[0]], PPRb.column.getFormDefault());
        "".length();
        oPRb.put(Cf[kf[1]], Cf[kf[2]]);
        "".length();
        oPRb.put(Cf[kf[3]], kf[0]);
        "".length();
        oPRb.put(Cf[kf[4]], kf[5]);
        "".length();
        oPRb.put(Cf[kf[6]], kf[1]);
        "".length();
        oPRb.put(Cf[kf[7]], kf[0]);
        "".length();
        oPRb.put(Cf[kf[8]], kf[0]);
        "".length();
        oPRb.put(Cf[kf[9]], kf[0]);
        "".length();
        oPRb.put(Cf[kf[10]], PPRb.getRules());
        "".length();
        HashMap<String, Object> NPRb = new HashMap<String, Object>();
        NPRb.put(Cf[kf[11]], PPRb.column.getFormInput());
        "".length();
        NPRb.put(Cf[kf[12]], PPRb.column.getComments());
        "".length();
        NPRb.put(Cf[kf[13]], PPRb.column.getName());
        "".length();
        NPRb.put(Cf[kf[14]], oPRb);
        "".length();
        return NPRb;
    }

    public SliderComponent(OnlineTableColumnEntity TPRb) {
        SliderComponent wPRb = null;
        wPRb.column = TPRb;
    }

    private static String Tm(String foRb, String EoRb) {
        foRb = new String(Base64.getDecoder().decode(foRb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder JoRb = new StringBuilder();
        char[] hoRb = EoRb.toCharArray();
        int GoRb = kf[0];
        char[] AoRb = foRb.toCharArray();
        int ZNRb = AoRb.length;
        int yNRb = kf[0];
        while (SliderComponent.PN(yNRb, ZNRb)) {
            char moRb = AoRb[yNRb];
            JoRb.append((char)(moRb ^ hoRb[GoRb % hoRb.length]));
            "".length();
            ++GoRb;
            ++yNRb;
            "".length();
            if (" ".length() >= 0) continue;
            return null;
        }
        return String.valueOf(JoRb);
    }

    private static void CN() {
        Cf = new String[kf[15]];
        SliderComponent.Cf[SliderComponent.kf[0]] = SliderComponent.Tm("DhAyExwGAQITBR8Q", "juTri");
        SliderComponent.Cf[SliderComponent.kf[1]] = SliderComponent.Tm("FAwAMx0=", "cedGu");
        SliderComponent.Cf[SliderComponent.kf[2]] = SliderComponent.um("Gwxh3o6w6Z8=", "lNiIO");
        SliderComponent.Cf[SliderComponent.kf[3]] = SliderComponent.Tm("LBoD", "AsmNf");
        SliderComponent.Cf[SliderComponent.kf[4]] = SliderComponent.um("yW+lutSK3gY=", "yellh");
        SliderComponent.Cf[SliderComponent.kf[6]] = SliderComponent.Tm("JCwPNw==", "WXjGa");
        SliderComponent.Cf[SliderComponent.kf[7]] = SliderComponent.um("hZq+D/3tV0WVWcHKeWRS9w==", "BuUet");
        SliderComponent.Cf[SliderComponent.kf[8]] = SliderComponent.um("jGbmgSbRe/YX5iWnt26j4Q==", "hfpyg");
        SliderComponent.Cf[SliderComponent.kf[9]] = SliderComponent.um("1OMesZbfE4N9GnG7L/gP4A==", "jWUsE");
        SliderComponent.Cf[SliderComponent.kf[10]] = SliderComponent.Tm("OwUICD0=", "IpdmN");
        SliderComponent.Cf[SliderComponent.kf[11]] = SliderComponent.AN("MtxmgpVv4W4=", "raezm");
        SliderComponent.Cf[SliderComponent.kf[12]] = SliderComponent.Tm("CCQAIgo=", "dEbGf");
        SliderComponent.Cf[SliderComponent.kf[13]] = SliderComponent.AN("xqlthn9iqyc=", "FkmeU");
        SliderComponent.Cf[SliderComponent.kf[14]] = SliderComponent.Tm("FxMQDiAWEA==", "xcdgO");
    }

    private static boolean PN(int n, int n2) {
        return n < n2;
    }
}

