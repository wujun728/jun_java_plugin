/*
 * Decompiled with CFR 0.152.
 */
package com.jun.plugin.online.enums;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public final class FieldTypeEnum  {
    public static final /* synthetic */ /* enum */ FieldTypeEnum String;
    public static final /* synthetic */ /* enum */ FieldTypeEnum Long;
    public static final /* synthetic */ /* enum */ FieldTypeEnum Integer;
    public static final /* synthetic */ /* enum */ FieldTypeEnum Double;
    public static final /* synthetic */ /* enum */ FieldTypeEnum Date;
    public static final /* synthetic */ /* enum */ FieldTypeEnum DateTime;
    public static final /* synthetic */ /* enum */ FieldTypeEnum BigDecimal;
    public static final /* synthetic */ /* enum */ FieldTypeEnum Bit;
    public static final /* synthetic */ /* enum */ FieldTypeEnum Text;
    private static final /* synthetic */ FieldTypeEnum[] $VALUES;
    private static /* synthetic */ String[] ZE;
    private static /* synthetic */ int[] ff;

    public static FieldTypeEnum[] values() {
        return (FieldTypeEnum[])$VALUES.clone();
    }

//    public static FieldTypeEnum valueOf(String hAsb) {
//        return Enum.valueOf(FieldTypeEnum.class, hAsb);
//    }

    private FieldTypeEnum() {
        FieldTypeEnum AAsb;
    }

    private static /* synthetic */ FieldTypeEnum[] $values() {
        FieldTypeEnum[] fieldTypeEnumArray = new FieldTypeEnum[ff[0]];
        fieldTypeEnumArray[FieldTypeEnum.ff[1]] = String;
        fieldTypeEnumArray[FieldTypeEnum.ff[2]] = Long;
        fieldTypeEnumArray[FieldTypeEnum.ff[3]] = Integer;
        fieldTypeEnumArray[FieldTypeEnum.ff[4]] = Double;
        fieldTypeEnumArray[FieldTypeEnum.ff[5]] = Date;
        fieldTypeEnumArray[FieldTypeEnum.ff[6]] = DateTime;
        fieldTypeEnumArray[FieldTypeEnum.ff[7]] = BigDecimal;
        fieldTypeEnumArray[FieldTypeEnum.ff[8]] = Bit;
        fieldTypeEnumArray[FieldTypeEnum.ff[9]] = Text;
        return fieldTypeEnumArray;
    }

    static {
        FieldTypeEnum.EN();
        FieldTypeEnum.Vm();
        String = new FieldTypeEnum();
        Long = new FieldTypeEnum();
        Integer = new FieldTypeEnum();
        Double = new FieldTypeEnum();
        Date = new FieldTypeEnum();
        DateTime = new FieldTypeEnum();
        BigDecimal = new FieldTypeEnum();
        Bit = new FieldTypeEnum();
        Text = new FieldTypeEnum();
        $VALUES = FieldTypeEnum.$values();
    }

    private static void Vm() {
        ZE = new String[ff[0]];
        FieldTypeEnum.ZE[FieldTypeEnum.ff[1]] = FieldTypeEnum.qm("jd9xcHnfZvQ=", "gVijd");
        FieldTypeEnum.ZE[FieldTypeEnum.ff[2]] = FieldTypeEnum.Rm("bnjaRgmDq3Y=", "kkGXo");
        FieldTypeEnum.ZE[FieldTypeEnum.ff[3]] = FieldTypeEnum.sm("IwQmAwwPGA==", "jjRfk");
        FieldTypeEnum.ZE[FieldTypeEnum.ff[4]] = FieldTypeEnum.qm("A4sTRhD2ces=", "fMCQX");
        FieldTypeEnum.ZE[FieldTypeEnum.ff[5]] = FieldTypeEnum.Rm("NRnKnBj7gAY=", "MOhmJ");
        FieldTypeEnum.ZE[FieldTypeEnum.ff[6]] = FieldTypeEnum.Rm("OhUe7ikdS5e6Js3S0Ve5LA==", "tYRSR");
        FieldTypeEnum.ZE[FieldTypeEnum.ff[7]] = FieldTypeEnum.Rm("TEeZb2eWSkczK9/ASOdK5g==", "DfhMA");
        FieldTypeEnum.ZE[FieldTypeEnum.ff[8]] = FieldTypeEnum.qm("zthuMDDm5L8=", "wNZqM");
        FieldTypeEnum.ZE[FieldTypeEnum.ff[9]] = FieldTypeEnum.qm("63OlVnwDp04=", "CjAiM");
    }

    private static String qm(String LZRb, String kZRb) {
        try {
            SecretKeySpec qZRb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(kZRb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher oZRb = Cipher.getInstance("Blowfish");
            oZRb.init(ff[3], qZRb);
            return new String(oZRb.doFinal(Base64.getDecoder().decode(LZRb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception NZRb) {
            NZRb.printStackTrace();
            return null;
        }
    }

    private static String sm(String mXRb, String LXRb) {
        mXRb = new String(Base64.getDecoder().decode(mXRb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder kXRb = new StringBuilder();
        char[] JXRb = LXRb.toCharArray();
        int hXRb = ff[1];
        char[] AXRb = mXRb.toCharArray();
        int ZwRb = AXRb.length;
        int ywRb = ff[1];
        while (FieldTypeEnum.fN(ywRb, ZwRb)) {
            char NXRb = AXRb[ywRb];
            kXRb.append((char)(NXRb ^ JXRb[hXRb % JXRb.length]));
            "".length();
            ++hXRb;
            ++ywRb;
            "".length();
            if ("  ".length() != 0) continue;
            return null;
        }
        return java.lang.String.valueOf(kXRb);
    }

    private static String Rm(String CwRb, String bwRb) {
        try {
            SecretKeySpec fwRb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(bwRb.getBytes(StandardCharsets.UTF_8)), ff[9]), "DES");
            Cipher EwRb = Cipher.getInstance("DES");
            EwRb.init(ff[3], fwRb);
            return new String(EwRb.doFinal(Base64.getDecoder().decode(CwRb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception dwRb) {
            dwRb.printStackTrace();
            return null;
        }
    }

    private static void EN() {
        ff = new int[10];
        FieldTypeEnum.ff[0] = 0xC7 ^ 0x80 ^ (0x18 ^ 0x56);
        FieldTypeEnum.ff[1] = (138 + 100 - 158 + 95 ^ 90 + 26 - 45 + 70) & (161 + 144 - 156 + 16 ^ 97 + 17 - 71 + 92 ^ -" ".length());
        FieldTypeEnum.ff[2] = " ".length();
        FieldTypeEnum.ff[3] = "  ".length();
        FieldTypeEnum.ff[4] = "   ".length();
        FieldTypeEnum.ff[5] = 0xE ^ 0xA;
        FieldTypeEnum.ff[6] = 0xB5 ^ 0x92 ^ (0x1A ^ 0x38);
        FieldTypeEnum.ff[7] = 11 + 146 - 71 + 95 ^ 61 + 175 - 109 + 52;
        FieldTypeEnum.ff[8] = 0xF ^ 0x68 ^ (0xE1 ^ 0x81);
        FieldTypeEnum.ff[9] = 0x66 ^ 0x6E;
    }

    private static boolean fN(int n, int n2) {
        return n < n2;
    }
}

