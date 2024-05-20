/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.StrUtil
 *  com.baomidou.mybatisplus.core.metadata.IPage
 *  com.baomidou.mybatisplus.extension.plugins.pagination.Page
 *  net.maku.framework.common.exception.ServerException
 *  net.maku.framework.common.utils.PageResult
 *  net.maku.framework.security.user.SecurityUser
 *  org.springframework.stereotype.Service
 */
package com.jun.plugin.online.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.rmi.ServerException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.jun.plugin.online.common.PageResult;
import com.jun.plugin.online.dao.OnlineFormDao;
import com.jun.plugin.online.dao.OnlineTableColumnDao;
import com.jun.plugin.online.entity.OnlineTableColumnEntity;
import com.jun.plugin.online.entity.OnlineTableEntity;
import com.jun.plugin.online.query.OnlineFormQuery;
import com.jun.plugin.online.service.OnlineFormService;
import com.jun.plugin.online.service.OnlineTableService;
import com.jun.plugin.online.vo.form.OnlineFormVO;
import com.jun.plugin.online.vo.form.WidgetFormConfigVO;
import com.jun.plugin.online.vo.form.WidgetFormVO;
import com.jun.plugin.online.vo.form.component.ComponentContext;
import com.jun.plugin.online.vo.query.QueryContext;
import org.springframework.stereotype.Service;

@Service
public class OnlineFormServiceImpl
implements OnlineFormService {
    private static /* synthetic */ int[] uf;
    private   /* synthetic */ OnlineFormDao onlineFormDao;
    private static /* synthetic */ String[] XE;
    private   /* synthetic */ OnlineTableColumnDao onlineTableColumnDao;
    private   /* synthetic */ OnlineTableService onlineTableService;

    private static boolean Vo(int n) {
        return n != 0;
    }

    private Map<String, Object> getQueryParams(String ZXqb, Map<String, Object> yXqb) {
        OnlineFormServiceImpl fyqb = this;
        List<OnlineTableColumnEntity> Cyqb = fyqb.onlineTableColumnDao.getByTableId(ZXqb);
        HashMap<String, Object> byqb = new HashMap<String, Object>();
        Cyqb.forEach(osqb -> {
            if (OnlineFormServiceImpl.wo(osqb.isQueryItem() ? 1 : 0)) {
                return;
            }
            if (!OnlineFormServiceImpl.wo(osqb.getQueryInput().equals(XE[uf[25]]) ? 1 : 0) || OnlineFormServiceImpl.Vo(osqb.getQueryInput().equals(XE[uf[18]]) ? 1 : 0)) {
                Object Rsqb = yXqb.getOrDefault(osqb.getName(), null);
                if (true) {
                    ArrayList ssqb = (ArrayList)Rsqb;
                    byqb.put(osqb.getName() + " >= ", ssqb.get(uf[0]));
                    "".length();
                    byqb.put(osqb.getName() + " <= ", ssqb.get(uf[1]));
                    "".length();
                    "".length();
                    if ((0x20 ^ 0x78 ^ (0x9F ^ 0xC3)) <= ((0x21 ^ 6 ^ (0x60 ^ 0x74)) & (0x58 ^ 0x66 ^ (0x8A ^ 0x87) ^ -" ".length()))) {
                        return;
                    }
                } else if (OnlineFormServiceImpl.Xo(Rsqb)) {
                    byqb.put(osqb.getName() + " = ", Rsqb);
                    "".length();
                }
                return;
            }
            String Nsqb = (String) yXqb.getOrDefault(osqb.getName(), null);
            if (OnlineFormServiceImpl.Vo(StrUtil.isBlank((CharSequence)Nsqb) ? 1 : 0)) {
                return;
            }
            if (OnlineFormServiceImpl.Vo(osqb.getQueryType().equalsIgnoreCase(XE[uf[26]]) ? 1 : 0)) {
                byqb.put(osqb.getName() + " like ", "%" + Nsqb + "%");
                "".length();
                "".length();
                if ((0x1B ^ 0x1E) <= 0) {
                    return;
                }
            } else {
                byqb.put(osqb.getName() + " = ", Nsqb);
                "".length();
            }
        });
        return byqb;
    }

    @Override
    public Map<String, Object> get(String EbRb, Long ZARb) throws ServerException {
        OnlineFormServiceImpl fbRb = this;
        OnlineTableEntity CbRb = fbRb.getTableById(EbRb);
        return fbRb.onlineFormDao.getById(CbRb.getName(), ZARb);
    }

    @Override
    public void save(String fARb, Map<String, String> mARb) throws ServerException {
        OnlineFormServiceImpl oARb = this;
        OnlineTableEntity LARb = oARb.getTableById(fARb);
        Long kARb = 1L;//SecurityUser.getUserId();
        HashMap<String, Object> JARb = new HashMap<String, Object>();
        List<OnlineTableColumnEntity> hARb = oARb.onlineTableColumnDao.getByTableId(fARb);
        hARb.forEach(mRqb -> {
            if (OnlineFormServiceImpl.Vo(mRqb.getName().equalsIgnoreCase(XE[uf[32]]) ? 1 : 0)) {
                return;
            }
            if (OnlineFormServiceImpl.Vo(mRqb.getName().equalsIgnoreCase(XE[uf[33]]) ? 1 : 0)) {
                JARb.put(XE[uf[34]], kARb);
                "".length();
                return;
            }
            if (OnlineFormServiceImpl.Vo(mRqb.getName().equalsIgnoreCase(XE[uf[35]]) ? 1 : 0)) {
                JARb.put(XE[uf[36]], new Date());
                "".length();
                return;
            }
            if (OnlineFormServiceImpl.Vo(mRqb.getName().equalsIgnoreCase(XE[uf[37]]) ? 1 : 0)) {
                JARb.put(XE[uf[38]], kARb);
                "".length();
                return;
            }
            if (OnlineFormServiceImpl.Vo(mRqb.getName().equalsIgnoreCase(XE[uf[39]]) ? 1 : 0)) {
                JARb.put(XE[uf[40]], new Date());
                "".length();
                return;
            }
            String LRqb = mARb.getOrDefault(mRqb.getName(), null);
            if (OnlineFormServiceImpl.Vo(StrUtil.isBlank((CharSequence)LRqb) ? 1 : 0)) {
                return;
            }
            JARb.put(mRqb.getName(), LRqb);
            "".length();
        });
        oARb.onlineFormDao.save(LARb.getName(), JARb);
    }

    @Override
    public OnlineFormVO getJSON(String XCRb) throws ServerException {
        OnlineFormServiceImpl RCRb = this;
        OnlineTableEntity wCRb = RCRb.getTableById(XCRb);
        List<OnlineTableColumnEntity> VCRb = RCRb.onlineTableColumnDao.getByTableId(XCRb);
        OnlineFormVO uCRb = new OnlineFormVO();
        WidgetFormVO TCRb = RCRb.getQueryJSON(VCRb);
        WidgetFormVO sCRb = RCRb.getFormJSON(wCRb, VCRb);
        uCRb.setQuery(TCRb);
        uCRb.setTable(RCRb.getTableJSON(VCRb));
        uCRb.setForm(sCRb);
        return uCRb;
    }

    private static boolean To(int n, int n2) {
        return n == n2;
    }

    private static boolean wo(int n) {
        return n == 0;
    }

    private static boolean so(Object object) {
        return object == null;
    }

    @Override
    public PageResult<Map<String, Object>> page(String sbRb, OnlineFormQuery RbRb) throws ServerException {
        OnlineFormServiceImpl TbRb = this;
        OnlineTableEntity XbRb = TbRb.getTableById(sbRb);
        Map<String, Object> wbRb = TbRb.getQueryParams(sbRb, RbRb.getParams());
        Page VbRb = new Page((long)RbRb.getPage().intValue(), (long)RbRb.getLimit().intValue());
        List<Map<String, Object>> ubRb = TbRb.onlineFormDao.getList((IPage<?>)VbRb, XbRb.getName(), wbRb);
        return new PageResult(ubRb, VbRb.getTotal());
    }

    private static boolean Xo(Object object) {
        return object != null;
    }

    private OnlineTableEntity getTableById(String NXqb) throws ServerException {
        OnlineFormServiceImpl RXqb = this;
        OnlineTableEntity PXqb = (OnlineTableEntity)RXqb.onlineTableService.getById((Serializable)((Object)NXqb));
        if (OnlineFormServiceImpl.so(PXqb)) {
            throw new ServerException(XE[uf[1]]);
        }
        return PXqb;
    }

    private static boolean AP(int n, int n2) {
        return n < n2;
    }

    private static void No() {
        XE = new String[uf[41]];
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[0]] = OnlineFormServiceImpl.Em("J0Ay2n3Ng/4=", "hEPfk");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[1]] = OnlineFormServiceImpl.Em("fZVY6LyPDWnLTlyCG5VZvIkZzafpkgbw", "XLniK");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[2]] = OnlineFormServiceImpl.Jo("JwoRJhEvGw==", "CowGd");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[4]] = OnlineFormServiceImpl.ko("HTXRASeMNvM=", "eRCgz");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[5]] = OnlineFormServiceImpl.Jo("ETA2HjUE", "vEBjP");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[6]] = OnlineFormServiceImpl.ko("qaQSvtH+YOA=", "vKNhG");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[7]] = OnlineFormServiceImpl.Em("PxQpwCyojjI=", "cBsUt");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[8]] = OnlineFormServiceImpl.ko("wNmZ3uBKxSU=", "nPQjq");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[9]] = OnlineFormServiceImpl.Em("4qe//l9i1xI=", "nGlkF");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[10]] = OnlineFormServiceImpl.Em("ybO6f74mO9A=", "uXzYe");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[11]] = OnlineFormServiceImpl.Em("ZZPI/Iu7rSU=", "mRiOR");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[12]] = OnlineFormServiceImpl.ko("u9eKQxZQ+2Q=", "NUpcd");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[13]] = OnlineFormServiceImpl.Em("hAnXCtswrxI=", "QnIha");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[14]] = OnlineFormServiceImpl.Jo("IwYPNSQ=", "OgmPH");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[15]] = OnlineFormServiceImpl.Jo("5qGt5qG25bmI5bCo", "QsKhn");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[16]] = OnlineFormServiceImpl.ko("QjI+R1UDrAo=", "JsZfz");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[17]] = OnlineFormServiceImpl.Jo("JhgGIQ==", "UhgOQ");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[19]] = OnlineFormServiceImpl.ko("ND5Wg2LNXmE=", "Ddrrv");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[20]] = OnlineFormServiceImpl.ko("zyYgXuRh5FE=", "BQQPp");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[21]] = OnlineFormServiceImpl.Jo("HSQDDw==", "sEnjO");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[22]] = OnlineFormServiceImpl.Jo("OAAgKjk=", "TaBOU");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[23]] = OnlineFormServiceImpl.ko("3yLx28+OhfF80L85WJXogg==", "BskAn");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[24]] = OnlineFormServiceImpl.Jo("JhgGHw==", "Bqekt");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[25]] = OnlineFormServiceImpl.Em("cXbJdC6hYpo=", "clCnr");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[18]] = OnlineFormServiceImpl.ko("DdeNxnNQz3GUEerqLL9GUQ==", "fsbFe");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[26]] = OnlineFormServiceImpl.Em("lKqrwxw6CEo=", "gspLC");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[27]] = OnlineFormServiceImpl.ko("GRsHakeb+hw=", "SpDtl");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[28]] = OnlineFormServiceImpl.Em("VWlnUcmVW7I=", "QooYy");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[29]] = OnlineFormServiceImpl.Em("vzYlpUA7nC0=", "wfoYS");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[30]] = OnlineFormServiceImpl.ko("b/MNx2reLMDFs74YPgXXVw==", "RjQCg");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[31]] = OnlineFormServiceImpl.ko("pz18X8Z9saFRszug0qxKqA==", "QtjvH");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[32]] = OnlineFormServiceImpl.Em("VBJ3vbhzaoM=", "xbMZa");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[33]] = OnlineFormServiceImpl.Jo("DCESIwYAIQ==", "oSwBr");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[34]] = OnlineFormServiceImpl.ko("DUCO2RscUno=", "Xgnxt");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[35]] = OnlineFormServiceImpl.ko("yW/jqhl364uTeKIDoRuOIw==", "zbQHr");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[36]] = OnlineFormServiceImpl.Jo("FDshMxMSFjA7ChI=", "wIDRg");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[37]] = OnlineFormServiceImpl.ko("xtoANh+Btrw=", "vezPo");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[38]] = OnlineFormServiceImpl.Jo("PCQ8OSMsJg==", "ITXXW");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[39]] = OnlineFormServiceImpl.Em("9D2jmFu8nq0AJOULJ+7+Yw==", "EkfSX");
        OnlineFormServiceImpl.XE[OnlineFormServiceImpl.uf[40]] = OnlineFormServiceImpl.Em("dy33ftQy/Kj6uMumsAGVYg==", "Isemo");
    }

    @Override
    public void update(String hZqb, Map<String, String> GZqb) throws ServerException {
        OnlineFormServiceImpl JZqb = this;
        OnlineTableEntity NZqb = JZqb.getTableById(hZqb);
        HashMap<String, Object> mZqb = new HashMap<String, Object>();
        List<OnlineTableColumnEntity> LZqb = JZqb.onlineTableColumnDao.getByTableId(hZqb);
        LZqb.forEach(wRqb -> {
            if (OnlineFormServiceImpl.Vo(wRqb.getName().equalsIgnoreCase(XE[uf[27]]) ? 1 : 0)) {
                return;
            }
            if (OnlineFormServiceImpl.Vo(wRqb.getName().equalsIgnoreCase(XE[uf[28]]) ? 1 : 0)) {
                mZqb.put(XE[uf[29]], 1L/*SecurityUser.getUserId()*/);
                "".length();
                return;
            }
            if (OnlineFormServiceImpl.Vo(wRqb.getName().equalsIgnoreCase(XE[uf[30]]) ? 1 : 0)) {
                mZqb.put(XE[uf[31]], new Date());
                "".length();
                return;
            }
            if (OnlineFormServiceImpl.wo(wRqb.isFormItem() ? 1 : 0)) {
                return;
            }
            String ZRqb = GZqb.getOrDefault(wRqb.getName(), null);
            mZqb.put(wRqb.getName(), ZRqb);
            "".length();
        });
        Long kZqb = Long.parseLong(GZqb.get(XE[uf[0]]));
        JZqb.onlineFormDao.update(NZqb.getName(), kZqb, mZqb);
    }

    private static boolean uo(int n, int n2) {
        return n > n2;
    }

    private WidgetFormVO getFormJSON(OnlineTableEntity dVqb, List<OnlineTableColumnEntity> oVqb) {
        WidgetFormVO NVqb = new WidgetFormVO();
        WidgetFormConfigVO mVqb = new WidgetFormConfigVO();
        mVqb.setLabelWidth(uf[3]);
        mVqb.setSize(XE[uf[4]]);
        mVqb.setLabelPosition(null);
        mVqb.setStyle(null);
        NVqb.setConfig(mVqb);
        int LVqb = dVqb.getFormLayout();
        ArrayList<Map<String, Object>> kVqb = new ArrayList<Map<String, Object>>();
        if (OnlineFormServiceImpl.To(LVqb, uf[1])) {
            oVqb.forEach(uTqb -> {
                if (OnlineFormServiceImpl.Vo(uTqb.isFormItem() ? 1 : 0)) {
                    ComponentContext wTqb = new ComponentContext((OnlineTableColumnEntity)uTqb);
                    kVqb.add(wTqb.getComponent());
                    "".length();
                }
            });
            NVqb.setList(kVqb);
            return NVqb;
        }
        HashMap<String, Object> JVqb = new HashMap<String, Object>();
        JVqb.put(XE[uf[5]], uf[0]);
        "".length();
        JVqb.put(XE[uf[6]], XE[uf[7]]);
        "".length();
        JVqb.put(XE[uf[8]], XE[uf[9]]);
        "".length();
        HashMap<String, Object> hVqb = new HashMap<String, Object>();
        hVqb.put(XE[uf[10]], XE[uf[11]]);
        "".length();
        hVqb.put(XE[uf[12]], XE[uf[13]]);
        "".length();
        hVqb.put(XE[uf[14]], XE[uf[15]]);
        "".length();
        hVqb.put(XE[uf[16]], JVqb);
        "".length();
        int GVqb = uf[0];
        ArrayList fVqb = new ArrayList(LVqb);
        int wVqb22 = uf[0];
        while (OnlineFormServiceImpl.uo(LVqb, wVqb22)) {
            ArrayList XVqb = new ArrayList();
            fVqb.add(XVqb);
            "".length();
            ++wVqb22;
            "".length();
            if (null == null) continue;
            return null;
        }
        Iterator<OnlineTableColumnEntity> wVqb2211 = oVqb.iterator();
        while (OnlineFormServiceImpl.Vo(wVqb2211.hasNext() ? 1 : 0)) {
            OnlineTableColumnEntity TVqb = wVqb2211.next();
            if (OnlineFormServiceImpl.Vo(TVqb.isFormItem() ? 1 : 0)) {
                int VVqb = GVqb % LVqb;
                ComponentContext uVqb = new ComponentContext(TVqb);
                ((List)fVqb.get(VVqb)).add(uVqb.getComponent());
                "".length();
                ++GVqb;
            }
            "".length();
            if ("  ".length() >= " ".length()) continue;
            return null;
        }
        ArrayList EVqb = new ArrayList();
        int RVqb = uf[0];
        while (OnlineFormServiceImpl.uo(LVqb, RVqb)) {
            HashMap<String, Integer> sVqb = new HashMap<String, Integer>();
            sVqb.put(XE[uf[17]], uf[18] / LVqb);
            "".length();
            sVqb.put(XE[uf[19]], (Integer)fVqb.get(RVqb));
            "".length();
            EVqb.add(sVqb);
            "".length();
            ++RVqb;
            "".length();
            if (null == null) continue;
            return null;
        }
        hVqb.put(XE[uf[20]], EVqb);
        "".length();
        kVqb.add(hVqb);
        "".length();
        NVqb.setList(kVqb);
        return NVqb;
    }

    private static String Jo(String Gqqb, String fqqb) {
        Gqqb = new String(Base64.getDecoder().decode(Gqqb.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder Eqqb = new StringBuilder();
        char[] dqqb = fqqb.toCharArray();
        int Cqqb = uf[0];
        char[] wPqb = Gqqb.toCharArray();
        int VPqb = wPqb.length;
        int uPqb = uf[0];
        while (OnlineFormServiceImpl.AP(uPqb, VPqb)) {
            char hqqb = wPqb[uPqb];
            Eqqb.append((char)(hqqb ^ dqqb[Cqqb % dqqb.length]));
            "".length();
            ++Cqqb;
            ++uPqb;
            "".length();
            if ((0xA7 ^ 0xA3) == (0x6E ^ 0x6A)) continue;
            return null;
        }
        return String.valueOf(Eqqb);
    }

    @Override
    public void delete(String qyqb, List<Long> Tyqb) throws ServerException {
        OnlineFormServiceImpl Vyqb = this;
        OnlineTableEntity syqb = Vyqb.getTableById(qyqb);
        Vyqb.onlineFormDao.delete(syqb.getName(), Tyqb);
    }

    private WidgetFormVO getQueryJSON(List<OnlineTableColumnEntity> AXqb) {
        WidgetFormVO dXqb = new WidgetFormVO();
        WidgetFormConfigVO CXqb = new WidgetFormConfigVO();
        CXqb.setLabelWidth(null);
        CXqb.setSize(XE[uf[2]]);
        CXqb.setLabelPosition(null);
        CXqb.setStyle(null);
        dXqb.setConfig(CXqb);
        ArrayList<Map<String, Object>> bXqb = new ArrayList<Map<String, Object>>();
        AXqb.forEach(Zsqb -> {
            if (OnlineFormServiceImpl.Vo(Zsqb.isQueryItem() ? 1 : 0)) {
                QueryContext dTqb = new QueryContext((OnlineTableColumnEntity)Zsqb);
                bXqb.add(dTqb.getQuery());
                "".length();
            }
        });
        dXqb.setList(bXqb);
        return dXqb;
    }

    static {
        OnlineFormServiceImpl.Ro();
        OnlineFormServiceImpl.No();
    }

    private static String Em(String Xqqb, String uqqb) {
        try {
            SecretKeySpec ARqb = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(uqqb.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher Zqqb = Cipher.getInstance("Blowfish");
            Zqqb.init(uf[2], ARqb);
            return new String(Zqqb.doFinal(Base64.getDecoder().decode(Xqqb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception yqqb) {
            yqqb.printStackTrace();
            return null;
        }
    }

    public OnlineFormServiceImpl(OnlineTableService Guqb, OnlineTableColumnDao buqb, OnlineFormDao Auqb) {
        OnlineFormServiceImpl duqb = this;
        duqb.onlineTableService = Guqb;
        duqb.onlineTableColumnDao = buqb;
        duqb.onlineFormDao = Auqb;
    }

    private static void Ro() {
        uf = new int[42];
        OnlineFormServiceImpl.uf[0] = (0xAB ^ 0x97) & ~(0x6F ^ 0x53);
        OnlineFormServiceImpl.uf[1] = " ".length();
        OnlineFormServiceImpl.uf[2] = "  ".length();
        OnlineFormServiceImpl.uf[3] = 0x25 ^ 0x41;
        OnlineFormServiceImpl.uf[4] = "   ".length();
        OnlineFormServiceImpl.uf[5] = 0x1F ^ 0x1B;
        OnlineFormServiceImpl.uf[6] = 145 + 26 - 98 + 94 ^ 123 + 84 - 61 + 16;
        OnlineFormServiceImpl.uf[7] = 0xB3 ^ 0x9A ^ (0x59 ^ 0x76);
        OnlineFormServiceImpl.uf[8] = 0xA0 ^ 0xA7;
        OnlineFormServiceImpl.uf[9] = 0xC ^ 0x67 ^ (0xDD ^ 0xBE);
        OnlineFormServiceImpl.uf[10] = 46 + 70 - -8 + 41 ^ 99 + 137 - 193 + 129;
        OnlineFormServiceImpl.uf[11] = 0x47 ^ 0x4D;
        OnlineFormServiceImpl.uf[12] = 0x88 ^ 0x83;
        OnlineFormServiceImpl.uf[13] = 124 + 154 - 275 + 155 ^ 48 + 11 - -52 + 35;
        OnlineFormServiceImpl.uf[14] = 0x95 ^ 0x98;
        OnlineFormServiceImpl.uf[15] = 0x3B ^ 0x35;
        OnlineFormServiceImpl.uf[16] = 95 + 8 - 96 + 153 ^ 24 + 174 - 67 + 44;
        OnlineFormServiceImpl.uf[17] = 0x18 ^ 8;
        OnlineFormServiceImpl.uf[18] = 0x4C ^ 0x54;
        OnlineFormServiceImpl.uf[19] = 0xB5 ^ 0xA4;
        OnlineFormServiceImpl.uf[20] = 0x11 ^ 0x75 ^ (9 ^ 0x7F);
        OnlineFormServiceImpl.uf[21] = 0x70 ^ 0x63;
        OnlineFormServiceImpl.uf[22] = "   ".length() ^ (0xB4 ^ 0xA3);
        OnlineFormServiceImpl.uf[23] = 0xE2 ^ 0xC1 ^ (0x79 ^ 0x4F);
        OnlineFormServiceImpl.uf[24] = 0x34 ^ 0x22;
        OnlineFormServiceImpl.uf[25] = 0xD3 ^ 0xC4;
        OnlineFormServiceImpl.uf[26] = 30 + 106 - -21 + 1 ^ 131 + 63 - 87 + 28;
        OnlineFormServiceImpl.uf[27] = 43 + 129 - 53 + 104 ^ 180 + 83 - 148 + 82;
        OnlineFormServiceImpl.uf[28] = 0x51 ^ 0x4A;
        OnlineFormServiceImpl.uf[29] = 0x1E ^ 2;
        OnlineFormServiceImpl.uf[30] = 13 + 74 - 51 + 111 ^ 45 + 108 - 127 + 116;
        OnlineFormServiceImpl.uf[31] = 0x2F ^ 0x31;
        OnlineFormServiceImpl.uf[32] = 95 + 54 - 129 + 197 ^ 192 + 172 - 210 + 44;
        OnlineFormServiceImpl.uf[33] = 76 + 135 - 187 + 128 ^ 54 + 175 - 156 + 111;
        OnlineFormServiceImpl.uf[34] = 0x42 ^ 0x1B ^ (0x18 ^ 0x60);
        OnlineFormServiceImpl.uf[35] = 0x68 ^ 0x57 ^ (0x29 ^ 0x34);
        OnlineFormServiceImpl.uf[36] = 0xEC ^ 0x9B ^ (0x33 ^ 0x67);
        OnlineFormServiceImpl.uf[37] = 0x96 ^ 0xB2;
        OnlineFormServiceImpl.uf[38] = 0x53 ^ 0x5B ^ (0x6E ^ 0x43);
        OnlineFormServiceImpl.uf[39] = 0x61 ^ 0x47 ^ (0x69 ^ 0x2C) & ~(0xE9 ^ 0xAC);
        OnlineFormServiceImpl.uf[40] = 0x61 ^ 0x46;
        OnlineFormServiceImpl.uf[41] = 0x92 ^ 0xBA;
    }

    private static String ko(String LPqb, String kPqb) {
        try {
            SecretKeySpec oPqb = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(kPqb.getBytes(StandardCharsets.UTF_8)), uf[9]), "DES");
            Cipher NPqb = Cipher.getInstance("DES");
            NPqb.init(uf[2], oPqb);
            return new String(NPqb.doFinal(Base64.getDecoder().decode(LPqb.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception mPqb) {
            mPqb.printStackTrace();
            return null;
        }
    }

    private List<Map<String, Object>> getTableJSON(List<OnlineTableColumnEntity> swqb) {
        ArrayList<Map<String, Object>> Rwqb = new ArrayList<Map<String, Object>>();
        swqb.forEach(LTqb -> {
            if (OnlineFormServiceImpl.Vo(LTqb.isGridItem() ? 1 : 0)) {
                HashMap<String, Object> NTqb = new HashMap<String, Object>();
                NTqb.put(XE[uf[21]], LTqb.getName());
                "".length();
                NTqb.put(XE[uf[22]], LTqb.getComments());
                "".length();
                NTqb.put(XE[uf[23]], LTqb.isGridSort());
                "".length();
                if (OnlineFormServiceImpl.Vo(StrUtil.isNotBlank((CharSequence)LTqb.getFormDict()) ? 1 : 0)) {
                    NTqb.put(XE[uf[24]], LTqb.getFormDict());
                    "".length();
                }
                Rwqb.add(NTqb);
                "".length();
            }
        });
        return Rwqb;
    }
}

