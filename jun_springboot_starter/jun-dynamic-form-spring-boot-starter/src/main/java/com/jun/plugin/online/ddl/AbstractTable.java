package com.jun.plugin.online.ddl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import com.jun.plugin.online.vo.OnlineTableColumnVO;
import com.jun.plugin.online.vo.OnlineTableVO;

public abstract class AbstractTable {
    private static /* synthetic */ int[] TC;
    private static /* synthetic */ String[] oC;

    private static String ZE(String sfXb, String TfXb) {
        try {
            SecretKeySpec XfXb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(TfXb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher wfXb = Cipher.getInstance("Blowfish");
            wfXb.init(TC[2], XfXb);
            return new String(wfXb.doFinal(Base64.getDecoder().decode(sfXb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception VfXb) {
            VfXb.printStackTrace();
            return null;
        }
    }

    public String getDropTableSQL(String yGXb) {
        Object[] objectArray = new Object[TC[1]];
        objectArray[AbstractTable.TC[0]] = yGXb;
        return String.format(oC[TC[0]], objectArray);
    }

    public abstract List<String> getInsertColumnSQL(String var1, OnlineTableColumnVO var2);

    public abstract List<String> getUpdateColumnSQL(String var1, OnlineTableColumnVO var2, OnlineTableColumnVO var3);

    public abstract String getRenameTableSQL(String var1);

    private static void Vf() {
        TC = new int[3];
        AbstractTable.TC[0] = "   ".length() & ~"   ".length();
        AbstractTable.TC[1] = " ".length();
        AbstractTable.TC[2] = "  ".length();
    }

    public abstract String getUpdateTableSQL(String var1, String var2);

    static {
        AbstractTable.Vf();
        AbstractTable.hf();
    }

    public abstract String getTableSQL(OnlineTableVO var1);

    public abstract String getDropColumnSQL(String var1, String var2);

    private static void hf() {
        oC = new String[TC[1]];
        AbstractTable.oC[AbstractTable.TC[0]] = AbstractTable.ZE("i/JDPuJ6Ri46WgII5mHUYrXUjDf9hCai", "UAblt");
    }

    public AbstractTable() {
        AbstractTable JhXb;
    }
}

