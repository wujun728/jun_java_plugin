/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.StrUtil
 */
package com.jun.plugin.online.ddl;

import cn.hutool.core.util.StrUtil;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.jun.plugin.online.enums.FieldTypeEnum;
import com.jun.plugin.online.vo.OnlineTableColumnVO;
import com.jun.plugin.online.vo.OnlineTableVO;

public class MySQLTable
extends AbstractTable {
    private static /* synthetic */ String[] oE;
    private static /* synthetic */ int[] bf;

    @Override
    public String getDropColumnSQL(String GCsb, String hCsb) {
        Object[] objectArray = new Object[bf[2]];
        objectArray[MySQLTable.bf[0]] = GCsb;
        objectArray[MySQLTable.bf[1]] = hCsb;
        return String.format(oE[bf[13]], objectArray);
    }

    @Override
    public List<String> getUpdateColumnSQL(String obsb, OnlineTableColumnVO Nbsb, OnlineTableColumnVO Rbsb) {
        ArrayList<String> stringArrayList = new ArrayList<String>();
        if (!MySQLTable.Zm(StrUtil.equals((CharSequence)Nbsb.getName(), (CharSequence)Rbsb.getName()) ? 1 : 0) || !MySQLTable.Zm(StrUtil.equals((CharSequence)Nbsb.getColumnType(), (CharSequence)Rbsb.getColumnType()) ? 1 : 0) || !MySQLTable.Zm(StrUtil.equals((CharSequence)Nbsb.getComments(), (CharSequence)Rbsb.getComments()) ? 1 : 0) || MySQLTable.bN(StrUtil.equals((CharSequence)Nbsb.getDefaultValue(), (CharSequence)Rbsb.getDefaultValue()) ? 1 : 0)) {
            MySQLTable mySQLTable = null;
            Object[] objectArray = new Object[bf[5]];
            objectArray[MySQLTable.bf[0]] = obsb;
            objectArray[MySQLTable.bf[1]] = Rbsb.getName();
            objectArray[MySQLTable.bf[2]] = Nbsb.getName();
            objectArray[MySQLTable.bf[3]] = mySQLTable.getColumnType(Nbsb);
            objectArray[MySQLTable.bf[4]] = Nbsb.getComments();
            String Vbsb = String.format(oE[bf[14]], objectArray);
            stringArrayList.add(Vbsb);
        }
        return stringArrayList;
    }

    @Override
    public List<String> getInsertColumnSQL(String bdsb, OnlineTableColumnVO Adsb) {
        MySQLTable Cdsb = null;
        String string;
        if (MySQLTable.Zm(Adsb.isColumnNull() ? 1 : 0)) {
            string = oE[bf[8]];
            "".length();
            if (-"  ".length() > 0) {
                return null;
            }
        } else {
            string = oE[bf[9]];
        }
        String ZCsb = string;
        String yCsb = oE[bf[10]];
        if (MySQLTable.Zm(StrUtil.isNotBlank((CharSequence)Adsb.getDefaultValue()) ? 1 : 0)) {
            Object[] objectArray = new Object[bf[1]];
            objectArray[MySQLTable.bf[0]] = Adsb.getDefaultValue();
            yCsb = String.format(oE[bf[11]], objectArray);
        }
        ArrayList<String> XCsb = new ArrayList<String>();
        Object[] objectArray = new Object[bf[6]];
        objectArray[MySQLTable.bf[0]] = bdsb;
        objectArray[MySQLTable.bf[1]] = Adsb.getName();
        objectArray[MySQLTable.bf[2]] = Cdsb.getColumnType(Adsb);
        objectArray[MySQLTable.bf[3]] = ZCsb;
        objectArray[MySQLTable.bf[4]] = yCsb;
        objectArray[MySQLTable.bf[5]] = Adsb.getComments();
        String wCsb = String.format(oE[bf[12]], objectArray);
        XCsb.add(wCsb);
        return XCsb;
    }

    private static boolean Zm(int n) {
        return n != 0;
    }

    @Override
    public String getTableSQL(OnlineTableVO onlineTableVO) {
        MySQLTable mySQLTable;
        StringBuilder builder = new StringBuilder();
        onlineTableVO.getColumnList().forEach(onlineTableColumnVO -> {
            String RAsb = null;
            String string;
            if (MySQLTable.Zm(onlineTableColumnVO.isColumnNull() ? 1 : 0)) {
                string = oE[bf[24]];
                "".length();
                if ((0x6D ^ 0x69) < (0x7C ^ 0x78)) {
                    return;
                }
            } else {
                string = oE[bf[25]];
            }
            String TAsb = string;
            String sAsb = oE[bf[26]];
            if (MySQLTable.Zm(StrUtil.isNotBlank((CharSequence)onlineTableColumnVO.getDefaultValue()) ? 1 : 0)) {
                Object[] objectArray = new Object[bf[1]];
                objectArray[MySQLTable.bf[0]] = onlineTableColumnVO.getDefaultValue();
                sAsb = String.format(oE[bf[27]], objectArray);
            }
            if (MySQLTable.Zm(onlineTableColumnVO.getName().equalsIgnoreCase(oE[bf[28]]) ? 1 : 0)) {
                Object[] objectArray = new Object[bf[1]];
                objectArray[MySQLTable.bf[0]] = onlineTableColumnVO.getComments();
                String XAsb = String.format(oE[bf[29]], objectArray);
                if (null != null) {
                    return;
                }
            } else {
                MySQLTable qAsb = null;
                Object[] objectArray = new Object[bf[5]];
                objectArray[MySQLTable.bf[0]] = onlineTableColumnVO.getName();
                objectArray[MySQLTable.bf[1]] = qAsb.getColumnType((OnlineTableColumnVO)onlineTableColumnVO);
                objectArray[MySQLTable.bf[2]] = TAsb;
                objectArray[MySQLTable.bf[3]] = sAsb;
                objectArray[MySQLTable.bf[4]] = onlineTableColumnVO.getComments();
                RAsb = String.format(oE[bf[30]], objectArray);
            }
            builder.append(RAsb);
        });
        builder.append(oE[bf[0]]);
        StringBuilder fEsb = new StringBuilder();
        Object[] objectArray = new Object[bf[1]];
        objectArray[MySQLTable.bf[0]] = onlineTableVO.getName();
        fEsb.append(String.format(oE[bf[1]], objectArray));
        fEsb.append(oE[bf[2]]);
        fEsb.append((CharSequence)builder);
        fEsb.append(oE[bf[3]]);
        fEsb.append(oE[bf[4]]);
        Object[] objectArray2 = new Object[bf[1]];
        objectArray2[MySQLTable.bf[0]] = onlineTableVO.getComments();
        fEsb.append(String.format(oE[bf[5]], objectArray2));
        return String.valueOf(fEsb);
    }

    private static void ym() {
        bf = new int[32];
        MySQLTable.bf[0] = (0x3F ^ 0x25 ^ (0xB6 ^ 0xB1)) & (0x49 ^ 0x1F ^ (0xC2 ^ 0x89) ^ -" ".length());
        MySQLTable.bf[1] = " ".length();
        MySQLTable.bf[2] = "  ".length();
        MySQLTable.bf[3] = "   ".length();
        MySQLTable.bf[4] = 116 + 57 - 24 + 29 ^ 98 + 140 - 229 + 173;
        MySQLTable.bf[5] = 0x85 ^ 0x95 ^ (0x39 ^ 0x2C);
        MySQLTable.bf[6] = 0x33 ^ 0x35;
        MySQLTable.bf[7] = 16 + 93 - -18 + 18 ^ 129 + 18 - 43 + 46;
        MySQLTable.bf[8] = 0xD ^ 5;
        MySQLTable.bf[9] = 37 + 19 - -24 + 89 ^ 74 + 100 - 21 + 7;
        MySQLTable.bf[10] = 0x7C ^ 0 ^ (0xC9 ^ 0xBF);
        MySQLTable.bf[11] = 13 + 24 - -132 + 10 ^ 108 + 115 - 205 + 166;
        MySQLTable.bf[12] = 4 ^ 0x22 ^ (0x73 ^ 0x59);
        MySQLTable.bf[13] = 0x45 ^ 0x48;
        MySQLTable.bf[14] = 151 + 29 - 83 + 75 ^ 70 + 158 - 191 + 125;
        MySQLTable.bf[15] = 86 + 67 - 95 + 123 ^ 78 + 64 - -27 + 17;
        MySQLTable.bf[16] = 0xD2 ^ 0xC2;
        MySQLTable.bf[17] = 0x2C ^ 0x5B ^ (0x4E ^ 0x28);
        MySQLTable.bf[18] = 0xBC ^ 0xAE;
        MySQLTable.bf[19] = 0x7D ^ 0x35 ^ (0xC4 ^ 0x9F);
        MySQLTable.bf[20] = 0x55 ^ 0x41;
        MySQLTable.bf[21] = 0x64 ^ 0x71;
        MySQLTable.bf[22] = 0x80 ^ 0xAA ^ (0xA4 ^ 0x98);
        MySQLTable.bf[23] = 0x55 ^ 0x13 ^ (0xDD ^ 0x8C);
        MySQLTable.bf[24] = 24 + 73 - -19 + 15 ^ 37 + 12 - -103 + 3;
        MySQLTable.bf[25] = 62 + 32 - -36 + 3 ^ 137 + 105 - 196 + 110;
        MySQLTable.bf[26] = 0x7A ^ 0x60;
        MySQLTable.bf[27] = 0x3C ^ 0x78 ^ (0x76 ^ 0x29);
        MySQLTable.bf[28] = 0xEC ^ 0x89 ^ (0x7E ^ 7);
        MySQLTable.bf[29] = 71 + 22 - 57 + 133 ^ 145 + 144 - 251 + 142;
        MySQLTable.bf[30] = 1 ^ 0x1F;
        MySQLTable.bf[31] = 31 + 125 - 112 + 137 ^ 141 + 22 - 107 + 114;
    }

    private static String fL(String qwRb, String NwRb) {
        try {
            SecretKeySpec TwRb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(NwRb.getBytes(StandardCharsets.UTF_8)), bf[8]), "DES");
            Cipher swRb = Cipher.getInstance("DES");
            swRb.init(bf[2], TwRb);
            return new String(swRb.doFinal(Base64.getDecoder().decode(qwRb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception RwRb) {
            RwRb.printStackTrace();
            return null;
        }
    }

    private static String JL(String yyRb, String XyRb) {
        yyRb = new String(Base64.getDecoder().decode(yyRb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder wyRb = new StringBuilder();
        char[] VyRb = XyRb.toCharArray();
        int uyRb = bf[0];
        char[] oyRb = yyRb.toCharArray();
        int NyRb = oyRb.length;
        int myRb = bf[0];
        while (MySQLTable.dN(myRb, NyRb)) {
            char ZyRb = oyRb[myRb];
            wyRb.append((char)(ZyRb ^ VyRb[uyRb % VyRb.length]));
            "".length();
            ++uyRb;
            ++myRb;
            "".length();
            if ((0x16 ^ 0x12) > -" ".length()) continue;
            return null;
        }
        return String.valueOf(wyRb);
    }

    private static boolean bN(int n) {
        return n == 0;
    }

    private String getColumnType(OnlineTableColumnVO fbsb) {
        if (MySQLTable.Zm(FieldTypeEnum.String.toString().equalsIgnoreCase(fbsb.getColumnType()) ? 1 : 0)) {
            Object[] objectArray = new Object[bf[1]];
            objectArray[MySQLTable.bf[0]] = fbsb.getLength();
            return String.format(oE[bf[15]], objectArray);
        }
        if (MySQLTable.Zm(FieldTypeEnum.Long.toString().equalsIgnoreCase(fbsb.getColumnType()) ? 1 : 0)) {
            return oE[bf[16]];
        }
        if (MySQLTable.Zm(FieldTypeEnum.Integer.toString().equalsIgnoreCase(fbsb.getColumnType()) ? 1 : 0)) {
            return oE[bf[17]];
        }
        if (MySQLTable.Zm(FieldTypeEnum.Double.toString().equalsIgnoreCase(fbsb.getColumnType()) ? 1 : 0)) {
            Object[] objectArray = new Object[bf[2]];
            objectArray[MySQLTable.bf[0]] = fbsb.getLength();
            objectArray[MySQLTable.bf[1]] = fbsb.getPointLength();
            return String.format(oE[bf[18]], objectArray);
        }
        if (MySQLTable.Zm(FieldTypeEnum.Date.toString().equalsIgnoreCase(fbsb.getColumnType()) ? 1 : 0)) {
            return oE[bf[19]];
        }
        if (MySQLTable.Zm(FieldTypeEnum.DateTime.toString().equalsIgnoreCase(fbsb.getColumnType()) ? 1 : 0)) {
            return oE[bf[20]];
        }
        if (MySQLTable.Zm(FieldTypeEnum.BigDecimal.toString().equalsIgnoreCase(fbsb.getColumnType()) ? 1 : 0)) {
            Object[] objectArray = new Object[bf[2]];
            objectArray[MySQLTable.bf[0]] = fbsb.getLength();
            objectArray[MySQLTable.bf[1]] = fbsb.getPointLength();
            return String.format(oE[bf[21]], objectArray);
        }
        if (MySQLTable.Zm(FieldTypeEnum.Bit.toString().equalsIgnoreCase(fbsb.getColumnType()) ? 1 : 0)) {
            return oE[bf[22]];
        }
        if (MySQLTable.Zm(FieldTypeEnum.Text.toString().equalsIgnoreCase(fbsb.getColumnType()) ? 1 : 0)) {
            return oE[bf[23]];
        }
        return null;
    }

    @Override
    public String getRenameTableSQL(String odsb) {
        Object[] objectArray = new Object[bf[3]];
        objectArray[MySQLTable.bf[0]] = odsb;
        objectArray[MySQLTable.bf[1]] = odsb;
        objectArray[MySQLTable.bf[2]] = System.currentTimeMillis();
        return String.format(oE[bf[7]], objectArray);
    }

    public MySQLTable() {
        MySQLTable qEsb;
    }

    @Override
    public String getUpdateTableSQL(String Vdsb, String sdsb) {
        Object[] objectArray = new Object[bf[2]];
        objectArray[MySQLTable.bf[0]] = Vdsb;
        objectArray[MySQLTable.bf[1]] = sdsb;
        return String.format(oE[bf[6]], objectArray);
    }

    private static boolean dN(int n, int n2) {
        return n < n2;
    }

    private static void Pm() {
        oE = new String[bf[31]];
        MySQLTable.oE[MySQLTable.bf[0]] = MySQLTable.fL("41pctXntfKxfvTkv1sDGOzEi+6iuF5kt", "gGpaW");
        MySQLTable.oE[MySQLTable.bf[1]] = MySQLTable.JL("GwYpIhsdVDgiDRQRbGYcWA==", "xtLCo");
        MySQLTable.oE[MySQLTable.bf[2]] = MySQLTable.fL("UakrVpcij0o=", "rzafb");
        MySQLTable.oE[MySQLTable.bf[3]] = MySQLTable.fL("LjOnLwsc5Yk=", "CdHXj");
        MySQLTable.oE[MySQLTable.bf[4]] = MySQLTable.fL("6xwfZZY4eo8X4ZkPJHgNRvzVhYAr3sYp6C8H58aOXY8foZQAwhVASqqFcFI6a3tT", "tvvRx");
        MySQLTable.oE[MySQLTable.bf[5]] = MySQLTable.mL("fZi7M1K/9vF1yU2cRDoqFQ==", "IgDaf");
        MySQLTable.oE[MySQLTable.bf[6]] = MySQLTable.mL("GsjqNXxAih0vOAv79ItzcmNxgy8fJDiAF+/afvY9hjo=", "hNZta");
        MySQLTable.oE[MySQLTable.bf[7]] = MySQLTable.mL("lTrpaI1PH3MX/EFKzx96eOTzW1wHKQ9al3kcKHrfE+E=", "oUxYy");
        MySQLTable.oE[MySQLTable.bf[8]] = MySQLTable.mL("nBHKMTJg4mA=", "vyTFb");
        MySQLTable.oE[MySQLTable.bf[9]] = MySQLTable.mL("Kkzgrmss4vgLQuE025nsxg==", "fdmJe");
        MySQLTable.oE[MySQLTable.bf[10]] = MySQLTable.fL("gTns+7O9LJ4=", "wZsdE");
        MySQLTable.oE[MySQLTable.bf[11]] = MySQLTable.fL("9cJMwhWLhhZhqathVTkxGQ==", "WBYqf");
        MySQLTable.oE[MySQLTable.bf[12]] = MySQLTable.fL("qR7xVxpeSLc9+wOifWaJXgMxLHd8mhufGxjuivfwUYfAXM3ZiuwYlG58pZo2NNx8", "dRwMT");
        MySQLTable.oE[MySQLTable.bf[13]] = MySQLTable.mL("aMCZ7MQLFk6CRfpP6ZWeC/NVWWfA3l8j", "UXZoA");
        MySQLTable.oE[MySQLTable.bf[14]] = MySQLTable.fL("7ybmBDJsAfp+lFPVPAR9IgGDv6QWEEwqPpjQdBCkJlGUN/pT9ipKcVz82C1vgwh7", "JsRvh");
        MySQLTable.oE[MySQLTable.bf[15]] = MySQLTable.fL("8CDD+q1kd+z8gBFlszxSBQ==", "NIRkr");
        MySQLTable.oE[MySQLTable.bf[16]] = MySQLTable.fL("ihQ1+DOEu1o=", "ziFpy");
        MySQLTable.oE[MySQLTable.bf[17]] = MySQLTable.mL("P18AsNA+rcc=", "mNqDo");
        MySQLTable.oE[MySQLTable.bf[18]] = MySQLTable.fL("sfixquF23B5w5BOXsogvUw==", "ydcKF");
        MySQLTable.oE[MySQLTable.bf[19]] = MySQLTable.JL("KxkMNQ==", "OxxPq");
        MySQLTable.oE[MySQLTable.bf[20]] = MySQLTable.mL("2s0Gem/658BFuvs6iBgv4w==", "opdOs");
        MySQLTable.oE[MySQLTable.bf[21]] = MySQLTable.JL("CDA3JwUNOXxrDEB1cSpB", "lUTNh");
        MySQLTable.oE[MySQLTable.bf[22]] = MySQLTable.JL("JTA2IBA/LQ==", "QYXYy");
        MySQLTable.oE[MySQLTable.bf[23]] = MySQLTable.fL("vSDT/s+g4PI=", "hNklx");
        MySQLTable.oE[MySQLTable.bf[24]] = MySQLTable.fL("UNN2KlFt4ww=", "sxdwJ");
        MySQLTable.oE[MySQLTable.bf[25]] = MySQLTable.JL("DAofQgkXCQdC", "bekbg");
        MySQLTable.oE[MySQLTable.bf[26]] = MySQLTable.JL("", "Imkmp");
        MySQLTable.oE[MySQLTable.bf[27]] = MySQLTable.mL("ry17aJ03oeAksdpQhGO5NA==", "DoxFk");
        MySQLTable.oE[MySQLTable.bf[28]] = MySQLTable.JL("CwU=", "bafDI");
        MySQLTable.oE[MySQLTable.bf[29]] = MySQLTable.mL("8bMno2472mn0JGoqL/A3pJ9R+8AUscDAQ1xUH5eoAic/5Y+eCjfRF2zsJ7rvPHUtXZ2PeyqzGWY=", "ZoJyV");
        MySQLTable.oE[MySQLTable.bf[30]] = MySQLTable.mL("d/EXlZ/3EB1s1Y5arMmkjR08OYPZSi/IosNpJtmKpdc=", "rCPyw");
    }

    private static String mL(String AyRb, String ZXRb) {
        try {
            SecretKeySpec dyRb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(ZXRb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher CyRb = Cipher.getInstance("Blowfish");
            CyRb.init(bf[2], dyRb);
            return new String(CyRb.doFinal(Base64.getDecoder().decode(AyRb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception byRb) {
            byRb.printStackTrace();
            return null;
        }
    }

    static {
        MySQLTable.ym();
        MySQLTable.Pm();
    }
}

