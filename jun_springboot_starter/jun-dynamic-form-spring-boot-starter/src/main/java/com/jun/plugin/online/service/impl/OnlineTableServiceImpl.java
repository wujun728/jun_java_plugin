/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.ObjUtil
 *  cn.hutool.core.util.StrUtil
 *  com.baomidou.mybatisplus.core.conditions.Wrapper
 *  com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
 *  com.baomidou.mybatisplus.core.metadata.IPage
 *  com.baomidou.mybatisplus.core.toolkit.Wrappers
 *  net.maku.framework.common.exception.ServerException
 *  net.maku.framework.common.utils.PageResult
 *  net.maku.framework.mybatis.service.impl.BaseServiceImpl
 *  org.springframework.stereotype.Service
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jun.plugin.online.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.rmi.ServerException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.jun.plugin.online.common.PageResult;
import com.jun.plugin.online.common.impl.BaseServiceImpl;
import com.jun.plugin.online.convert.OnlineTableConvert;
import com.jun.plugin.online.dao.OnlineTableDao;
import com.jun.plugin.online.ddl.MySQLTable;
import com.jun.plugin.online.entity.OnlineTableEntity;
import com.jun.plugin.online.query.OnlineTableQuery;
import com.jun.plugin.online.service.OnlineTableColumnService;
import com.jun.plugin.online.service.OnlineTableService;
import com.jun.plugin.online.vo.OnlineTableColumnVO;
import com.jun.plugin.online.vo.OnlineTableVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Service
public class OnlineTableServiceImpl
extends BaseServiceImpl<OnlineTableDao, OnlineTableEntity>
implements OnlineTableService {
    private static /* synthetic */ int[] Vf;
    private static /* synthetic */ String[] qf;
    private   /* synthetic */ OnlineTableColumnService onlineTableColumnService;

    private static boolean dP(int n) {
        return n == 0;
    }

    public OnlineTableServiceImpl(OnlineTableColumnService ukqb) {
        OnlineTableServiceImpl Tkqb = this;
        Tkqb.onlineTableColumnService = ukqb;
    }

    private static String Eo(String wEqb, String VEqb) {
        try {
            SecretKeySpec bfqb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(VEqb.getBytes(StandardCharsets.UTF_8)), Vf[9]), "DES");
            Cipher Afqb = Cipher.getInstance("DES");
            Afqb.init(Vf[3], bfqb);
            return new String(Afqb.doFinal(Base64.getDecoder().decode(wEqb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception ZEqb) {
            ZEqb.printStackTrace();
            return null;
        }
    }

    @Override
    public OnlineTableVO get(String tableId) {
        OnlineTableServiceImpl onlineTableService = this;
        OnlineTableEntity onlineTableEntity = onlineTableService.baseMapper.selectById(tableId);
        OnlineTableVO onlineTableVO = OnlineTableConvert.INSTANCE.convert(onlineTableEntity);
        onlineTableVO.setColumnList(onlineTableService.onlineTableColumnService.getByTableId(tableId));
        return onlineTableVO;
    }

    private static boolean EP(int n) {
        return n != 0;
    }

//    private static void Zo() {
//        Vf = new int[27];
//        OnlineTableServiceImpl.Vf[0] = (68 + 137 - 78 + 17 ^ 68 + 112 - 58 + 33) & (0x6A ^ 0x50 ^ (0xF ^ 0x3E) ^ -" ".length());
//        OnlineTableServiceImpl.Vf[1] = " ".length();
//        OnlineTableServiceImpl.Vf[2] = -" ".length();
//        OnlineTableServiceImpl.Vf[3] = "  ".length();
//        OnlineTableServiceImpl.Vf[4] = "   ".length();
//        OnlineTableServiceImpl.Vf[5] = 0x23 ^ 0x6B ^ (0x70 ^ 0x3D);
//        OnlineTableServiceImpl.Vf[6] = 0x26 ^ 0x22;
//        OnlineTableServiceImpl.Vf[7] = 33 + 41 - 23 + 77 ^ 40 + 53 - -27 + 14;
//        OnlineTableServiceImpl.Vf[8] = 0xA5 ^ 0xA2;
//        OnlineTableServiceImpl.Vf[9] = 146 + 64 - 101 + 78 ^ 28 + 8 - -51 + 92;
//        OnlineTableServiceImpl.Vf[10] = 0x7F ^ 0x20 ^ (0x19 ^ 0x4F);
//        OnlineTableServiceImpl.Vf[11] = 66 + 40 - 6 + 90 ^ 14 + 45 - -54 + 67;
//        OnlineTableServiceImpl.Vf[12] = 0x24 ^ 0x41 ^ (0x34 ^ 0x5A);
//        OnlineTableServiceImpl.Vf[13] = 0xB5 ^ 0x8E ^ (0x6A ^ 0x5D);
//        OnlineTableServiceImpl.Vf[14] = 0xD3 ^ 0x9F ^ (0x83 ^ 0xC2);
//        OnlineTableServiceImpl.Vf[15] = 0x2B ^ 0x25;
//        OnlineTableServiceImpl.Vf[16] = 0xA6 ^ 0xA9;
//        OnlineTableServiceImpl.Vf[17] = 139 + 158 - 256 + 131 ^ 149 + 18 - 7 + 28;
//        OnlineTableServiceImpl.Vf[18] = 0x31 ^ 0x20;
//        OnlineTableServiceImpl.Vf[19] = 0xA1 ^ 0xB3;
//        OnlineTableServiceImpl.Vf[20] = 0x2C ^ 0x3F;
//        OnlineTableServiceImpl.Vf[21] = 0x74 ^ 0x60;
//        OnlineTableServiceImpl.Vf[22] = 0x92 ^ 0x87;
//        OnlineTableServiceImpl.Vf[23] = 0xDF ^ 0xC7 ^ (0x60 ^ 0x6E);
//        OnlineTableServiceImpl.Vf[24] = "   ".length() & ~"   ".length() ^ (0x42 ^ 0x55);
//        OnlineTableServiceImpl.Vf[25] = 22 + 109 - -18 + 4 ^ 45 + 100 - 116 + 100;
//        OnlineTableServiceImpl.Vf[26] = 0x73 ^ 0x6A;
//    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void delete(List<String> fLqb) {
        OnlineTableServiceImpl CLqb = this;
        ArrayList ELqb = new ArrayList();
        fLqb.forEach(NJqb -> {
            OnlineTableServiceImpl PJqb = this;
            OnlineTableEntity qJqb = (OnlineTableEntity)((OnlineTableDao)PJqb.baseMapper).selectById((Serializable)((Object)NJqb));
            ELqb.add(qJqb.getName());
            "".length();
        });
        CLqb.removeByIds(fLqb);
        "".length();
        MySQLTable dLqb = new MySQLTable();
        ELqb.forEach(dkqb -> {
            OnlineTableServiceImpl fkqb = this;
            String Ckqb = dLqb.getRenameTableSQL((String)dkqb);
            ((OnlineTableDao)fkqb.baseMapper).exeSQL(Ckqb);
        });
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void update(OnlineTableVO tableVO) {
        OnlineTableServiceImpl onlineTableService = this;
        List<OnlineTableColumnVO> newColumnList = tableVO.getColumnList();
        List<OnlineTableColumnVO> oldColumnList = onlineTableService.onlineTableColumnService.getByTableId(tableVO.getId());
        MySQLTable mySQLTable = new MySQLTable();
        oldColumnList.forEach(oldColumn -> {
            OnlineTableServiceImpl uGqb = this;
            Optional<OnlineTableColumnVO> PGqb = newColumnList.stream().filter(columnVO -> ObjUtil.equals((Object)columnVO.getId(), (Object)oldColumn.getId())).findFirst();
            if (OnlineTableServiceImpl.EP(PGqb.get()==null ? 1 : 0)) {
                String dropSQL = mySQLTable.getDropColumnSQL(tableVO.getName(), oldColumn.getName());
                ((OnlineTableDao)uGqb.baseMapper).exeSQL(dropSQL);
                return;
            }
            List<String> updateColumnSQL = mySQLTable.getUpdateColumnSQL(tableVO.getName(), PGqb.get(), (OnlineTableColumnVO)oldColumn);
            updateColumnSQL.forEach(uSql -> {
                OnlineTableServiceImpl dGqb = this;
                ((OnlineTableDao)dGqb.baseMapper).exeSQL((String)uSql);
            });
        });
        newColumnList.forEach(newColunn -> {
            Optional<OnlineTableColumnVO> Xhqb = oldColumnList.stream().filter(dhqb -> ObjUtil.equals((Object)dhqb.getId(), (Object)newColunn.getId())).findFirst();
            if (OnlineTableServiceImpl.EP(Xhqb.get()==null ? 1 : 0)) {
                OnlineTableServiceImpl whqb;
                List<String> dJqb = mySQLTable.getInsertColumnSQL(tableVO.getName(), (OnlineTableColumnVO)newColunn);
                dJqb.forEach(mhqb -> {
                    OnlineTableServiceImpl Nhqb = this;
                    ((OnlineTableDao)Nhqb.baseMapper).exeSQL((String)mhqb);
                });
            }
        });
        OnlineTableEntity onlineTableEntity = (OnlineTableEntity)((OnlineTableDao)onlineTableService.baseMapper).selectById((Serializable)((Object)tableVO.getId()));
        if (OnlineTableServiceImpl.dP(StrUtil.equals((CharSequence)tableVO.getComments(), (CharSequence)onlineTableEntity.getComments()) ? 1 : 0)) {
            String updateTableSQL = mySQLTable.getUpdateTableSQL(onlineTableEntity.getName(), tableVO.getComments());
            ((OnlineTableDao)onlineTableService.baseMapper).exeSQL(updateTableSQL);
        }
        OnlineTableEntity onlineTableEntity1 = OnlineTableConvert.INSTANCE.convert(tableVO);
        onlineTableService.updateById(onlineTableEntity1);
        onlineTableService.onlineTableColumnService.delete(onlineTableEntity1.getId());
        onlineTableService.onlineTableColumnService.saveBatch(onlineTableEntity1.getId(), tableVO.getColumnList());
    }

    private static String Ao(String mfqb, String Jfqb) {
        try {
            SecretKeySpec Pfqb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(Jfqb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher ofqb = Cipher.getInstance("Blowfish");
            ofqb.init(Vf[3], Pfqb);
            return new String(ofqb.doFinal(Base64.getDecoder().decode(mfqb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception Nfqb) {
            Nfqb.printStackTrace();
            return null;
        }
    }

    private static boolean CP(int n) {
        return n > 0;
    }

//    private static String do(String CEqb, String GEqb) {
//        CEqb = new String(Base64.getDecoder().decode(CEqb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
//        StringBuilder fEqb = new StringBuilder();
//        char[] EEqb = GEqb.toCharArray();
//        int dEqb = Vf[0];
//        char[] Xdqb = CEqb.toCharArray();
//        int wdqb = Xdqb.length;
//        int Vdqb = Vf[0];
//        while (OnlineTableServiceImpl.GP(Vdqb, wdqb)) {
//            char JEqb = Xdqb[Vdqb];
//            fEqb.append((char)(JEqb ^ EEqb[dEqb % EEqb.length]));
//            "".length();
//            ++dEqb;
//            ++Vdqb;
//            "".length();
//            if ("  ".length() <= "  ".length()) continue;
//            return null;
//        }
//        return String.valueOf(fEqb);
//    }

    private static int bP(long l, long l2) {
        return l == l2 ? 0 : (l < l2 ? -1 : 1);
    }

    private static boolean GP(int n, int n2) {
        return n < n2;
    }

//    static {
////        OnlineTableServiceImpl.Zo();
////        OnlineTableServiceImpl.qo();
//    }

    private LambdaQueryWrapper<OnlineTableEntity> getWrapper(OnlineTableQuery TNqb) {
        LambdaQueryWrapper<OnlineTableEntity> uNqb = Wrappers.lambdaQuery();
        uNqb.like(StrUtil.isNotBlank((CharSequence)TNqb.getName()), OnlineTableEntity::getName, (Object)TNqb.getName());
        uNqb.like(StrUtil.isNotBlank((CharSequence)TNqb.getComments()), OnlineTableEntity::getComments, (Object)TNqb.getComments());
        uNqb.orderByDesc(OnlineTableEntity::getCreateTime);
        return uNqb;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(OnlineTableVO onlineTableVO) throws ServerException {
        OnlineTableServiceImpl onlineTableService = null;
        LambdaQueryWrapper<OnlineTableEntity> wrapper = new LambdaQueryWrapper();
        wrapper.eq(OnlineTableEntity::getName, (Object)onlineTableVO.getName());
        long count = ((OnlineTableDao)onlineTableService.baseMapper).selectCount(wrapper);
        if (OnlineTableServiceImpl.CP(OnlineTableServiceImpl.bP(count, 0L))) {
            Object[] objectArray = new Object[Vf[1]];
            objectArray[OnlineTableServiceImpl.Vf[0]] = onlineTableVO.getName();
            throw new ServerException(String.format(qf[Vf[0]], objectArray));
        }
        OnlineTableEntity onlineTableEntity = OnlineTableConvert.INSTANCE.convert(onlineTableVO);
        ((OnlineTableDao)onlineTableService.baseMapper).insert(onlineTableEntity);
        onlineTableService.onlineTableColumnService.saveBatch(onlineTableEntity.getId(), onlineTableVO.getColumnList());
        MySQLTable mySQLTable = new MySQLTable();
        String tableSQL = mySQLTable.getTableSQL(onlineTableVO);
        ((OnlineTableDao)onlineTableService.baseMapper).exeSQL(tableSQL);
    }

//    private static void qo() {
//        qf = new String[Vf[26]];
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[0]] = OnlineTableServiceImpl.Ao("Gan+1ia3S9IuzOJXo2bvXoL/PlGH4C7+P5mS5ag+m2ME19lYG0XwYqw73ecDEVGq", "ROdch");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[1]] = OnlineTableServiceImpl.do("Py4yJD49KjICGDEmIw==", "XKFgL");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[3]] = OnlineTableServiceImpl.do("CTMDODMDMw==", "nVwvR");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[4]] = OnlineTableServiceImpl.Eo("OlmpSrPS2q0HpVjlmBH92A==", "EsDHj");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[6]] = OnlineTableServiceImpl.Eo("ZZyt3aJze9xSsfG/aYQkMlFyL9T6ucsMYM21djpVOiGlqNp38tCUC2X2htWHLPzCjpixQYiRpnc=", "AtFxM");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[5]] = OnlineTableServiceImpl.Ao("fRF/N3nrGOQ=", "czbXG");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[7]] = OnlineTableServiceImpl.Ao("F2RUAbA6TthacwWTVoyuQVWavNEZ7NKzGGXSsM+1QNLKzDxAlO6elg==", "TVNye");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[8]] = OnlineTableServiceImpl.Ao("xO918YdE6Urky/2qYpHz5T35jwFp/ttPi457i8qRemnn8Vuxw05qXiE5PNg055qL", "YTnBm");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[9]] = OnlineTableServiceImpl.do("eWACACAnKGEfNTglYS4gJSx1", "QINjA");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[10]] = OnlineTableServiceImpl.do("LCwGZBguLAYiHiA2RCYDLSIfIgk/Lx44VSwsGS5VOywEJxEmN0Q4Dz8zBDkOYBAtPhQsNwIkFA==", "OCkKz");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[11]] = OnlineTableServiceImpl.Eo("8/P5WcpFP+I=", "OwrMM");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[12]] = OnlineTableServiceImpl.Ao("olG/eypmkS42mJrhw3SdCg9QH5gZof63vCfEd8/4XKLI4x/2+YlyqQ==", "ccQCi");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[13]] = OnlineTableServiceImpl.Ao("dXEkXptHfmIYOwRma9RY3iwF5otWP4+25eNorrg0Qtn2KCknt9Fxo2ROreXDzJ/D", "TrKvV");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[14]] = OnlineTableServiceImpl.Eo("L/tBwBCcBntcPXfE3GtE4i7MhIr2ere6", "wlolF");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[15]] = OnlineTableServiceImpl.do("Fx8nWDsVHycePRsFZRogFhE+HioEHD8EdhcfOBJ2AB8lGzIdBGUELAQAJQUtWyMMAjcXBCMYNw==", "tpJwY");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[16]] = OnlineTableServiceImpl.Eo("2ao7t58G7LM=", "kdGFQ");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[17]] = OnlineTableServiceImpl.Eo("gLXoSwwMCaiVGCIw1802/0dGQRwSRIjmNpgOluxWIUwa4xoNh9ovUg==", "DUPwS");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[18]] = OnlineTableServiceImpl.do("NB0VVxU7ExRXFzQUCBYddR0PDBEuAU43FjYRDx0sOxoNHT00DAgMAQ==", "Zxaxx");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[19]] = OnlineTableServiceImpl.Ao("FpXLRp9V3Iuc+rEnrkDJHBkHes8ZJL6b", "WYaIW");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[20]] = OnlineTableServiceImpl.do("LAknRSAuCScDJiATZQc7LQc+AzE/Cj8ZbSwJOA9tOwklBikmEmUZNz8WJRg2YDUMHywsEiMFLA==", "OfJjB");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[21]] = OnlineTableServiceImpl.Eo("65Iv5BUzslc=", "QIUTq");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[22]] = OnlineTableServiceImpl.Ao("Iwfjv/W2231cPCwcIYpsgfwgoBVS5NYxyvAqlmZTgkbSjZ2RI2Vn4Q==", "zLlZQ");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[23]] = OnlineTableServiceImpl.Ao("5hGe1uN6LZ6EEMW2D0ob2RznsZcE50ucOCe7yVo93O8LCbwBIMjGo77r1oizmrnm", "zWFOB");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[24]] = OnlineTableServiceImpl.Eo("14NRr4/JW7ld2AqcUP2ce01vTonM3zKT", "USFAd");
//        OnlineTableServiceImpl.qf[OnlineTableServiceImpl.Vf[25]] = OnlineTableServiceImpl.Eo("OO8epMM1V3uaZp1LpGM+0mQ9762N9RLLa1t3V/xWzJc=", "KmFfS");
//    }

    private static boolean fP(int n, int n2) {
        return n == n2;
    }

    @Override
    public PageResult<OnlineTableVO> page(OnlineTableQuery boqb) {
        OnlineTableServiceImpl Coqb = this;
        IPage doqb = ((OnlineTableDao)Coqb.baseMapper).selectPage(Coqb.getPage(boqb), (Wrapper)Coqb.getWrapper(boqb));
        return new PageResult(OnlineTableConvert.INSTANCE.convertList(doqb.getRecords()), doqb.getTotal());
    }
}

