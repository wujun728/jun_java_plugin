/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.baomidou.mybatisplus.core.conditions.Wrapper
 *  com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
 *  net.maku.framework.mybatis.service.impl.BaseServiceImpl
 *  org.springframework.stereotype.Service
 */
package com.jun.plugin.online.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.jun.plugin.online.common.impl.BaseServiceImpl;
import com.jun.plugin.online.convert.OnlineTableColumnConvert;
import com.jun.plugin.online.dao.OnlineTableColumnDao;
import com.jun.plugin.online.entity.OnlineTableColumnEntity;
import com.jun.plugin.online.service.OnlineTableColumnService;
import com.jun.plugin.online.vo.OnlineTableColumnVO;
import org.springframework.stereotype.Service;

@Service
public class OnlineTableColumnServiceImpl
extends BaseServiceImpl<OnlineTableColumnDao, OnlineTableColumnEntity>
implements OnlineTableColumnService {
    private static /* synthetic */ String[] mE;
    private static /* synthetic */ int[] PE;

    @Override
    public void saveBatch(String Aosb, List<OnlineTableColumnVO> uNsb) {
        OnlineTableColumnServiceImpl bosb;
        List<OnlineTableColumnEntity> yNsb = OnlineTableColumnConvert.INSTANCE.convertList2(uNsb);
        int XNsb = PE[0];
        Iterator<OnlineTableColumnEntity> RNsb = yNsb.iterator();
        while (OnlineTableColumnServiceImpl.hm(RNsb.hasNext() ? 1 : 0)) {
            OnlineTableColumnEntity Cosb = RNsb.next();
            Cosb.setSort(XNsb++);
            Cosb.setTableId(Aosb);
            "".length();
            if (null == null) continue;
            return;
        }
        super.saveBatch(yNsb);
        "".length();
    }

    public OnlineTableColumnServiceImpl() {
        OnlineTableColumnServiceImpl GNsb;
    }

    private static void fm() {
        PE = new int[10];
        OnlineTableColumnServiceImpl.PE[0] = " ".length() & ~" ".length();
        OnlineTableColumnServiceImpl.PE[1] = -" ".length();
        OnlineTableColumnServiceImpl.PE[2] = 0x52 ^ 0x4E ^ (0x65 ^ 0x7C);
        OnlineTableColumnServiceImpl.PE[3] = " ".length();
        OnlineTableColumnServiceImpl.PE[4] = "  ".length();
        OnlineTableColumnServiceImpl.PE[5] = "   ".length();
        OnlineTableColumnServiceImpl.PE[6] = 0xFD ^ 0x8C ^ (0x7F ^ 0xA);
        OnlineTableColumnServiceImpl.PE[7] = 0x5E ^ 0x58;
        OnlineTableColumnServiceImpl.PE[8] = 114 + 125 - 154 + 49 ^ 46 + 41 - 17 + 59;
        OnlineTableColumnServiceImpl.PE[9] = 0xB8 ^ 0xB0;
    }

    @Override
    public void delete(String LNsb) {
        OnlineTableColumnServiceImpl mNsb = this;
        LambdaQueryWrapper<OnlineTableColumnEntity> wrapper =  new LambdaQueryWrapper();
        wrapper.eq(OnlineTableColumnEntity::getTableId,LNsb);
        mNsb.remove(wrapper);
    }

    private static boolean hm(int n) {
        return n != 0;
    }

    @Override
    public List<OnlineTableColumnVO> getByTableId(String oosb) {
        OnlineTableColumnServiceImpl sosb = this;
        List<OnlineTableColumnEntity> qosb = ((OnlineTableColumnDao)sosb.baseMapper).getByTableId(oosb);
        return OnlineTableColumnConvert.INSTANCE.convertList(qosb);
    }

    private static boolean Lm(int n, int n2) {
        return n < n2;
    }

    private static String bL(String mLsb, String LLsb) {
        mLsb = new String(Base64.getDecoder().decode(mLsb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder kLsb = new StringBuilder();
        char[] JLsb = LLsb.toCharArray();
        int hLsb = PE[0];
        char[] bLsb = mLsb.toCharArray();
        int ALsb = bLsb.length;
        int Zksb = PE[0];
        while (OnlineTableColumnServiceImpl.Lm(Zksb, ALsb)) {
            char NLsb = bLsb[Zksb];
            kLsb.append((char)(NLsb ^ JLsb[hLsb % JLsb.length]));
            "".length();
            ++hLsb;
            ++Zksb;
            "".length();
            if (-"  ".length() <= 0) continue;
            return null;
        }
        return String.valueOf(kLsb);
    }

    private static String dL(String Amsb, String ZLsb) {
        try {
            SecretKeySpec fmsb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(ZLsb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher Emsb = Cipher.getInstance("Blowfish");
            Emsb.init(PE[4], fmsb);
            return new String(Emsb.doFinal(Base64.getDecoder().decode(Amsb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception dmsb) {
            dmsb.printStackTrace();
            return null;
        }
    }

    private static boolean Jm(int n, int n2) {
        return n == n2;
    }

    private static String CL(String omsb, String Pmsb) {
        try {
            SecretKeySpec Tmsb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(Pmsb.getBytes(StandardCharsets.UTF_8)), PE[9]), "DES");
            Cipher smsb = Cipher.getInstance("DES");
            smsb.init(PE[4], Tmsb);
            return new String(smsb.doFinal(Base64.getDecoder().decode(omsb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception Rmsb) {
            Rmsb.printStackTrace();
            return null;
        }
    }

    static {
        OnlineTableColumnServiceImpl.fm();
        OnlineTableColumnServiceImpl.EL();
    }

    private static void EL() {
        mE = new String[PE[8]];
        OnlineTableColumnServiceImpl.mE[OnlineTableColumnServiceImpl.PE[0]] = OnlineTableColumnServiceImpl.bL("Cyk9By4OICwaKw==", "lLISO");
        OnlineTableColumnServiceImpl.mE[OnlineTableColumnServiceImpl.PE[3]] = OnlineTableColumnServiceImpl.CL("aelaJbGXE/eyvCY4Kg5uLojLKwcr9Ef49x1rm7wMHZyklnGD6lNvot0XfWv6UEkZXu23RGpbCLA=", "FlvJn");
        OnlineTableColumnServiceImpl.mE[OnlineTableColumnServiceImpl.PE[4]] = OnlineTableColumnServiceImpl.dL("rPZMuSVI01I=", "Vdnov");
        OnlineTableColumnServiceImpl.mE[OnlineTableColumnServiceImpl.PE[5]] = OnlineTableColumnServiceImpl.bL("aiYTEAQjRRUQHCVFNhMYJwkNSlsOABgHE20GGB8VbSUbGxchHkI=", "Bjyqr");
        OnlineTableColumnServiceImpl.mE[OnlineTableColumnServiceImpl.PE[6]] = OnlineTableColumnServiceImpl.dL("iINicOaVXROnLBCTiRgqoNGUanBx4AQ1R/iWHl38lBU4zgAFuxSDczTLV2uIWCxf", "CrdSZ");
        OnlineTableColumnServiceImpl.mE[OnlineTableColumnServiceImpl.PE[2]] = OnlineTableColumnServiceImpl.CL("v7/PNtXidp01COHlCGLuUZDkfGTF8+0h", "zemYH");
        OnlineTableColumnServiceImpl.mE[OnlineTableColumnServiceImpl.PE[7]] = OnlineTableColumnServiceImpl.bL("DwMaFC0vCUwZICsPCBRhIggfEDMvDAAcOycZBRov", "FmluA");
    }
}

