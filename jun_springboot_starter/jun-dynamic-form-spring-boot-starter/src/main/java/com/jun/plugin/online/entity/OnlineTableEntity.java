/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.baomidou.mybatisplus.annotation.FieldFill
 *  com.baomidou.mybatisplus.annotation.IdType
 *  com.baomidou.mybatisplus.annotation.TableField
 *  com.baomidou.mybatisplus.annotation.TableId
 *  com.baomidou.mybatisplus.annotation.TableLogic
 *  com.baomidou.mybatisplus.annotation.TableName
 *  com.baomidou.mybatisplus.annotation.Version
 */
package com.jun.plugin.online.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value="online_table")
public class OnlineTableEntity {
    @TableId(type=IdType.ASSIGN_UUID)
    private /* synthetic */ String id;
    @TableField(fill=FieldFill.INSERT)
    private /* synthetic */ Date createTime;
    private /* synthetic */ String comments;
    private /* synthetic */ String name;
    private /* synthetic */ Integer tree;
    @TableField(fill=FieldFill.INSERT)
    private /* synthetic */ Long creator;
    @TableLogic
    @TableField(fill=FieldFill.INSERT)
    private /* synthetic */ Integer deleted;
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private /* synthetic */ Long updater;
    @Version
    @TableField(fill=FieldFill.INSERT)
    private /* synthetic */ Integer version;
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private /* synthetic */ Date updateTime;
    private /* synthetic */ String treeLabel;
    private static /* synthetic */ int[] Ld;
    private /* synthetic */ Integer formLayout;
    private /* synthetic */ Integer tableType;
    private /* synthetic */ String treePid;

//    public Date getUpdateTime() {
//        OnlineTableEntity CmVb;
//        return CmVb.updateTime;
//    }
//
//    public String getName() {
//        OnlineTableEntity hNVb;
//        return hNVb.name;
//    }
//
//    public void setCreator(Long kJVb) {
//        JJVb.creator = kJVb;
//    }
//
//    public void setId(String qLVb) {
//        RLVb.id = qLVb;
//    }
//
//    public Long getUpdater() {
//        OnlineTableEntity fmVb;
//        return fmVb.updater;
//    }
//
//    public void setName(String GLVb) {
//        hLVb.name = GLVb;
//    }
//
//    public void setDeleted(Integer ZGVb) {
//        yGVb.deleted = ZGVb;
//    }
//
//    public void setCreateTime(Date CJVb) {
//        bJVb.createTime = CJVb;
//    }
//
//    public Date getCreateTime() {
//        OnlineTableEntity hmVb;
//        return hmVb.createTime;
//    }
//
//    public Long getCreator() {
//        OnlineTableEntity mmVb;
//        return mmVb.creator;
//    }
//
//    private static boolean sh(int n) {
//        return n == 0;
//    }
//
//    public void setFormLayout(Integer skVb) {
//        VkVb.formLayout = skVb;
//    }
//
//    public String getTreePid() {
//        OnlineTableEntity umVb;
//        return umVb.treePid;
//    }
//
//    private static boolean uh(Object object) {
//        return object != null;
//    }
//
//    public void setComments(String bLVb) {
//        ALVb.comments = bLVb;
//    }
//
//    public Integer getFormLayout() {
//        OnlineTableEntity bNVb;
//        return bNVb.formLayout;
//    }
//
//    public void setVersion(Integer EhVb) {
//        hhVb.version = EhVb;
//    }
//
//    private static void Ph() {
//        Ld = new int[4];
//        OnlineTableEntity.Ld[0] = " ".length();
//        OnlineTableEntity.Ld[1] = (0xE1 ^ 0xAE ^ (0x14 ^ 0x7D)) & (134 + 27 - 102 + 97 ^ 84 + 146 - 133 + 89 ^ -" ".length());
//        OnlineTableEntity.Ld[2] = 0x1D ^ 0x4A ^ (0x46 ^ 0x2A);
//        OnlineTableEntity.Ld[3] = 0x75 ^ 0x31 ^ (9 ^ 0x66);
//    }
//
//    public boolean equals(Object ofVb) {
//        block113: {
//            block114: {
//                Date kEVb;
//                Date LEVb;
//                block112: {
//                    OnlineTableEntity NfVb;
//                    OnlineTableEntity JEVb;
//                    block110: {
//                        block111: {
//                            Date mEVb;
//                            Date NEVb;
//                            block109: {
//                                block107: {
//                                    block108: {
//                                        String oEVb;
//                                        String PEVb;
//                                        block106: {
//                                            block104: {
//                                                block105: {
//                                                    String qEVb;
//                                                    String REVb;
//                                                    block103: {
//                                                        block101: {
//                                                            block102: {
//                                                                String sEVb;
//                                                                String TEVb;
//                                                                block100: {
//                                                                    block98: {
//                                                                        block99: {
//                                                                            String uEVb;
//                                                                            String VEVb;
//                                                                            block97: {
//                                                                                block95: {
//                                                                                    block96: {
//                                                                                        String wEVb;
//                                                                                        String XEVb;
//                                                                                        block94: {
//                                                                                            block92: {
//                                                                                                block93: {
//                                                                                                    Integer yEVb;
//                                                                                                    Integer ZEVb;
//                                                                                                    block91: {
//                                                                                                        block89: {
//                                                                                                            block90: {
//                                                                                                                Integer AfVb;
//                                                                                                                Integer bfVb;
//                                                                                                                block88: {
//                                                                                                                    block86: {
//                                                                                                                        block87: {
//                                                                                                                            Long CfVb;
//                                                                                                                            Long dfVb;
//                                                                                                                            block85: {
//                                                                                                                                block83: {
//                                                                                                                                    block84: {
//                                                                                                                                        Long EfVb;
//                                                                                                                                        Long ffVb;
//                                                                                                                                        block82: {
//                                                                                                                                            block80: {
//                                                                                                                                                block81: {
//                                                                                                                                                    Integer GfVb;
//                                                                                                                                                    Integer hfVb;
//                                                                                                                                                    block79: {
//                                                                                                                                                        block77: {
//                                                                                                                                                            block78: {
//                                                                                                                                                                Integer JfVb;
//                                                                                                                                                                Integer kfVb;
//                                                                                                                                                                block76: {
//                                                                                                                                                                    block74: {
//                                                                                                                                                                        block75: {
//                                                                                                                                                                            Integer LfVb;
//                                                                                                                                                                            Integer mfVb;
//                                                                                                                                                                            block73: {
//                                                                                                                                                                                if (OnlineTableEntity.Rh(ofVb, JEVb)) {
//                                                                                                                                                                                    return Ld[0];
//                                                                                                                                                                                }
//                                                                                                                                                                                if (OnlineTableEntity.sh(ofVb instanceof OnlineTableEntity)) {
//                                                                                                                                                                                    return Ld[1];
//                                                                                                                                                                                }
//                                                                                                                                                                                NfVb = (OnlineTableEntity)ofVb;
//                                                                                                                                                                                if (OnlineTableEntity.sh(NfVb.canEqual(JEVb) ? 1 : 0)) {
//                                                                                                                                                                                    return Ld[1];
//                                                                                                                                                                                }
//                                                                                                                                                                                mfVb = JEVb.getFormLayout();
//                                                                                                                                                                                LfVb = NfVb.getFormLayout();
//                                                                                                                                                                                if (!OnlineTableEntity.Th(mfVb)) break block73;
//                                                                                                                                                                                if (!OnlineTableEntity.uh(LfVb)) break block74;
//                                                                                                                                                                                "".length();
//                                                                                                                                                                                if (-" ".length() >= (0x8E ^ 0x8A)) {
//                                                                                                                                                                                    return ((1 ^ 4) & ~(0xD ^ 8)) != 0;
//                                                                                                                                                                                }
//                                                                                                                                                                                break block75;
//                                                                                                                                                                            }
//                                                                                                                                                                            if (!OnlineTableEntity.sh(((Object)mfVb).equals(LfVb) ? 1 : 0)) break block74;
//                                                                                                                                                                        }
//                                                                                                                                                                        return Ld[1];
//                                                                                                                                                                    }
//                                                                                                                                                                    kfVb = JEVb.getTree();
//                                                                                                                                                                    JfVb = NfVb.getTree();
//                                                                                                                                                                    if (!OnlineTableEntity.Th(kfVb)) break block76;
//                                                                                                                                                                    if (!OnlineTableEntity.uh(JfVb)) break block77;
//                                                                                                                                                                    "".length();
//                                                                                                                                                                    if (-"  ".length() >= 0) {
//                                                                                                                                                                        return ((0xD2 ^ 0x8A) & ~(0x50 ^ 8)) != 0;
//                                                                                                                                                                    }
//                                                                                                                                                                    break block78;
//                                                                                                                                                                }
//                                                                                                                                                                if (!OnlineTableEntity.sh(((Object)kfVb).equals(JfVb) ? 1 : 0)) break block77;
//                                                                                                                                                            }
//                                                                                                                                                            return Ld[1];
//                                                                                                                                                        }
//                                                                                                                                                        hfVb = JEVb.getTableType();
//                                                                                                                                                        GfVb = NfVb.getTableType();
//                                                                                                                                                        if (!OnlineTableEntity.Th(hfVb)) break block79;
//                                                                                                                                                        if (!OnlineTableEntity.uh(GfVb)) break block80;
//                                                                                                                                                        "".length();
//                                                                                                                                                        if (null != null) {
//                                                                                                                                                            return ((0x39 ^ 0x36) & ~(0x58 ^ 0x57)) != 0;
//                                                                                                                                                        }
//                                                                                                                                                        break block81;
//                                                                                                                                                    }
//                                                                                                                                                    if (!OnlineTableEntity.sh(((Object)hfVb).equals(GfVb) ? 1 : 0)) break block80;
//                                                                                                                                                }
//                                                                                                                                                return Ld[1];
//                                                                                                                                            }
//                                                                                                                                            ffVb = JEVb.getCreator();
//                                                                                                                                            EfVb = NfVb.getCreator();
//                                                                                                                                            if (!OnlineTableEntity.Th(ffVb)) break block82;
//                                                                                                                                            if (!OnlineTableEntity.uh(EfVb)) break block83;
//                                                                                                                                            "".length();
//                                                                                                                                            if ("   ".length() < " ".length()) {
//                                                                                                                                                return ((190 + 8 - 100 + 125 ^ 191 + 182 - 297 + 120) & (128 + 31 - 124 + 103 ^ 137 + 116 - 200 + 92 ^ -" ".length())) != 0;
//                                                                                                                                            }
//                                                                                                                                            break block84;
//                                                                                                                                        }
//                                                                                                                                        if (!OnlineTableEntity.sh(((Object)ffVb).equals(EfVb) ? 1 : 0)) break block83;
//                                                                                                                                    }
//                                                                                                                                    return Ld[1];
//                                                                                                                                }
//                                                                                                                                dfVb = JEVb.getUpdater();
//                                                                                                                                CfVb = NfVb.getUpdater();
//                                                                                                                                if (!OnlineTableEntity.Th(dfVb)) break block85;
//                                                                                                                                if (!OnlineTableEntity.uh(CfVb)) break block86;
//                                                                                                                                "".length();
//                                                                                                                                if (null != null) {
//                                                                                                                                    return ((0x18 ^ 0x4C) & ~(0x2C ^ 0x78)) != 0;
//                                                                                                                                }
//                                                                                                                                break block87;
//                                                                                                                            }
//                                                                                                                            if (!OnlineTableEntity.sh(((Object)dfVb).equals(CfVb) ? 1 : 0)) break block86;
//                                                                                                                        }
//                                                                                                                        return Ld[1];
//                                                                                                                    }
//                                                                                                                    bfVb = JEVb.getVersion();
//                                                                                                                    AfVb = NfVb.getVersion();
//                                                                                                                    if (!OnlineTableEntity.Th(bfVb)) break block88;
//                                                                                                                    if (!OnlineTableEntity.uh(AfVb)) break block89;
//                                                                                                                    "".length();
//                                                                                                                    if (" ".length() > "   ".length()) {
//                                                                                                                        return ((0xA1 ^ 0x90) & ~(0x98 ^ 0xA9)) != 0;
//                                                                                                                    }
//                                                                                                                    break block90;
//                                                                                                                }
//                                                                                                                if (!OnlineTableEntity.sh(((Object)bfVb).equals(AfVb) ? 1 : 0)) break block89;
//                                                                                                            }
//                                                                                                            return Ld[1];
//                                                                                                        }
//                                                                                                        ZEVb = JEVb.getDeleted();
//                                                                                                        yEVb = NfVb.getDeleted();
//                                                                                                        if (!OnlineTableEntity.Th(ZEVb)) break block91;
//                                                                                                        if (!OnlineTableEntity.uh(yEVb)) break block92;
//                                                                                                        "".length();
//                                                                                                        if (" ".length() <= ((0xC ^ 0x59 ^ "   ".length()) & (75 + 47 - 52 + 185 ^ 75 + 22 - 6 + 78 ^ -" ".length()))) {
//                                                                                                            return ((169 + 7 - 144 + 198 ^ 112 + 123 - 159 + 103) & (208 + 204 - 317 + 158 ^ 137 + 147 - 136 + 20 ^ -" ".length())) != 0;
//                                                                                                        }
//                                                                                                        break block93;
//                                                                                                    }
//                                                                                                    if (!OnlineTableEntity.sh(((Object)ZEVb).equals(yEVb) ? 1 : 0)) break block92;
//                                                                                                }
//                                                                                                return Ld[1];
//                                                                                            }
//                                                                                            XEVb = JEVb.getId();
//                                                                                            wEVb = NfVb.getId();
//                                                                                            if (!OnlineTableEntity.Th(XEVb)) break block94;
//                                                                                            if (!OnlineTableEntity.uh(wEVb)) break block95;
//                                                                                            "".length();
//                                                                                            if (" ".length() < ("   ".length() & ~"   ".length())) {
//                                                                                                return ((0x70 ^ 0x39) & ~(0x51 ^ 0x18)) != 0;
//                                                                                            }
//                                                                                            break block96;
//                                                                                        }
//                                                                                        if (!OnlineTableEntity.sh(XEVb.equals(wEVb) ? 1 : 0)) break block95;
//                                                                                    }
//                                                                                    return Ld[1];
//                                                                                }
//                                                                                VEVb = JEVb.getName();
//                                                                                uEVb = NfVb.getName();
//                                                                                if (!OnlineTableEntity.Th(VEVb)) break block97;
//                                                                                if (!OnlineTableEntity.uh(uEVb)) break block98;
//                                                                                "".length();
//                                                                                if (null != null) {
//                                                                                    return ((0xCD ^ 0x95 ^ (0x1E ^ 3)) & (184 + 158 - 333 + 233 ^ 100 + 12 - 83 + 154 ^ -" ".length()) & ((0x44 ^ 0x3D ^ (0x51 ^ 0x2C)) & (0xB6 ^ 0xBC ^ (0xAA ^ 0xA4) ^ -" ".length()) ^ -" ".length())) != 0;
//                                                                                }
//                                                                                break block99;
//                                                                            }
//                                                                            if (!OnlineTableEntity.sh(VEVb.equals(uEVb) ? 1 : 0)) break block98;
//                                                                        }
//                                                                        return Ld[1];
//                                                                    }
//                                                                    TEVb = JEVb.getComments();
//                                                                    sEVb = NfVb.getComments();
//                                                                    if (!OnlineTableEntity.Th(TEVb)) break block100;
//                                                                    if (!OnlineTableEntity.uh(sEVb)) break block101;
//                                                                    "".length();
//                                                                    if ((1 + 36 - -76 + 22 ^ 91 + 94 - 61 + 7) <= ("  ".length() & ("  ".length() ^ -" ".length()))) {
//                                                                        return ((0x81 ^ 0xA5 ^ (0xE0 ^ 0x92)) & (0xA5 ^ 0x80 ^ (0xC8 ^ 0xBB) ^ -" ".length())) != 0;
//                                                                    }
//                                                                    break block102;
//                                                                }
//                                                                if (!OnlineTableEntity.sh(TEVb.equals(sEVb) ? 1 : 0)) break block101;
//                                                            }
//                                                            return Ld[1];
//                                                        }
//                                                        REVb = JEVb.getTreePid();
//                                                        qEVb = NfVb.getTreePid();
//                                                        if (!OnlineTableEntity.Th(REVb)) break block103;
//                                                        if (!OnlineTableEntity.uh(qEVb)) break block104;
//                                                        "".length();
//                                                        if ("   ".length() == 0) {
//                                                            return ((164 + 94 - 105 + 34 ^ 52 + 135 - 184 + 155) & (87 + 51 - 10 + 4 ^ 131 + 54 - 31 + 7 ^ -" ".length())) != 0;
//                                                        }
//                                                        break block105;
//                                                    }
//                                                    if (!OnlineTableEntity.sh(REVb.equals(qEVb) ? 1 : 0)) break block104;
//                                                }
//                                                return Ld[1];
//                                            }
//                                            PEVb = JEVb.getTreeLabel();
//                                            oEVb = NfVb.getTreeLabel();
//                                            if (!OnlineTableEntity.Th(PEVb)) break block106;
//                                            if (!OnlineTableEntity.uh(oEVb)) break block107;
//                                            "".length();
//                                            if ((0x87 ^ 0x83) == 0) {
//                                                return ((0x4C ^ 0x7B) & ~(0xAC ^ 0x9B)) != 0;
//                                            }
//                                            break block108;
//                                        }
//                                        if (!OnlineTableEntity.sh(PEVb.equals(oEVb) ? 1 : 0)) break block107;
//                                    }
//                                    return Ld[1];
//                                }
//                                NEVb = JEVb.getCreateTime();
//                                mEVb = NfVb.getCreateTime();
//                                if (!OnlineTableEntity.Th(NEVb)) break block109;
//                                if (!OnlineTableEntity.uh(mEVb)) break block110;
//                                "".length();
//                                if (((0xE1 ^ 0xC2 ^ (0x35 ^ 0x1D)) & (0x43 ^ 0x13 ^ (0 ^ 0x5B) ^ -" ".length())) >= (9 + 25 - -29 + 88 ^ 55 + 76 - 21 + 37)) {
//                                    return ((153 + 184 - 157 + 69 ^ 24 + 142 - 165 + 189) & (1 + 124 - 30 + 123 ^ 96 + 21 - 80 + 120 ^ -" ".length())) != 0;
//                                }
//                                break block111;
//                            }
//                            if (!OnlineTableEntity.sh(((Object)NEVb).equals(mEVb) ? 1 : 0)) break block110;
//                        }
//                        return Ld[1];
//                    }
//                    LEVb = JEVb.getUpdateTime();
//                    kEVb = NfVb.getUpdateTime();
//                    if (!OnlineTableEntity.Th(LEVb)) break block112;
//                    if (!OnlineTableEntity.uh(kEVb)) break block113;
//                    "".length();
//                    if (((60 + 64 - 69 + 101 ^ 123 + 120 - 206 + 91) & (0x81 ^ 0xBF ^ (0x27 ^ 5) ^ -" ".length())) != 0) {
//                        return ((57 + 140 - -42 + 13 ^ 138 + 146 - 154 + 45) & (17 + 88 - 92 + 137 ^ 101 + 162 - 232 + 166 ^ -" ".length())) != 0;
//                    }
//                    break block114;
//                }
//                if (!OnlineTableEntity.sh(((Object)LEVb).equals(kEVb) ? 1 : 0)) break block113;
//            }
//            return Ld[1];
//        }
//        return Ld[0];
//    }
//
//    public String toString() {
//        OnlineTableEntity uAVb;
//        return "OnlineTableEntity(id=" + uAVb.getId() + ", name=" + uAVb.getName() + ", comments=" + uAVb.getComments() + ", formLayout=" + uAVb.getFormLayout() + ", tree=" + uAVb.getTree() + ", treePid=" + uAVb.getTreePid() + ", treeLabel=" + uAVb.getTreeLabel() + ", tableType=" + uAVb.getTableType() + ", creator=" + uAVb.getCreator() + ", createTime=" + uAVb.getCreateTime() + ", updater=" + uAVb.getUpdater() + ", updateTime=" + uAVb.getUpdateTime() + ", version=" + uAVb.getVersion() + ", deleted=" + uAVb.getDeleted() + ")";
//    }
//
//    public Integer getDeleted() {
//        OnlineTableEntity wLVb;
//        return wLVb.deleted;
//    }
//
//    public String getComments() {
//        OnlineTableEntity ENVb;
//        return ENVb.comments;
//    }
//
//    public void setTreeLabel(String wJVb) {
//        ZJVb.treeLabel = wJVb;
//    }
//
//    public OnlineTableEntity() {
//        OnlineTableEntity oNVb;
//    }
//
//    public void setTableType(Integer PJVb) {
//        sJVb.tableType = PJVb;
//    }
//
//    static {
//        OnlineTableEntity.Ph();
//    }
//
//    protected boolean canEqual(Object yCVb) {
//        return yCVb instanceof OnlineTableEntity;
//    }
//
//    private static boolean Rh(Object object, Object object2) {
//        return object == object2;
//    }
//
//    public Integer getVersion() {
//        OnlineTableEntity yLVb;
//        return yLVb.version;
//    }
//
//    public void setTree(Integer LkVb) {
//        mkVb.tree = LkVb;
//    }
//
//    public String getId() {
//        OnlineTableEntity LNVb;
//        return LNVb.id;
//    }
//
//    private static boolean Th(Object object) {
//        return object == null;
//    }
//
//    public void setTreePid(String dkVb) {
//        EkVb.treePid = dkVb;
//    }
//
//    public Integer getTableType() {
//        OnlineTableEntity PmVb;
//        return PmVb.tableType;
//    }
//
//    public int hashCode() {
//        int n;
//        int n2;
//        int n3;
//        int n4;
//        int n5;
//        int n6;
//        int n7;
//        int n8;
//        int n9;
//        int n10;
//        int n11;
//        int n12;
//        int n13;
//        int n14;
//        OnlineTableEntity obVb;
//        int ECVb = Ld[2];
//        int dCVb = Ld[0];
//        Integer CCVb = obVb.getFormLayout();
//        int n15 = dCVb * Ld[2];
//        if (OnlineTableEntity.Th(CCVb)) {
//            n14 = Ld[3];
//            "".length();
//            if (null != null) {
//                return (0x88 ^ 0x98 ^ (0x74 ^ 0x5A)) & (84 + 101 - 80 + 31 ^ 123 + 162 - 128 + 25 ^ -" ".length());
//            }
//        } else {
//            n14 = ((Object)CCVb).hashCode();
//        }
//        dCVb = n15 + n14;
//        Integer bCVb = obVb.getTree();
//        int n16 = dCVb * Ld[2];
//        if (OnlineTableEntity.Th(bCVb)) {
//            n13 = Ld[3];
//            "".length();
//            if (((0x56 ^ 0x1E) & ~(0x10 ^ 0x58)) > (0x54 ^ 0x50)) {
//                return (0x43 ^ 0x76) & ~(0x80 ^ 0xB5);
//            }
//        } else {
//            n13 = ((Object)bCVb).hashCode();
//        }
//        dCVb = n16 + n13;
//        Integer ACVb = obVb.getTableType();
//        int n17 = dCVb * Ld[2];
//        if (OnlineTableEntity.Th(ACVb)) {
//            n12 = Ld[3];
//            "".length();
//            if ("   ".length() != "   ".length()) {
//                return "  ".length() & ("  ".length() ^ -" ".length());
//            }
//        } else {
//            n12 = ((Object)ACVb).hashCode();
//        }
//        dCVb = n17 + n12;
//        Long ZbVb = obVb.getCreator();
//        int n18 = dCVb * Ld[2];
//        if (OnlineTableEntity.Th(ZbVb)) {
//            n11 = Ld[3];
//            "".length();
//            if (null != null) {
//                return (0x68 ^ 0xA) & ~(0xEE ^ 0x8C);
//            }
//        } else {
//            n11 = ((Object)ZbVb).hashCode();
//        }
//        dCVb = n18 + n11;
//        Long ybVb = obVb.getUpdater();
//        int n19 = dCVb * Ld[2];
//        if (OnlineTableEntity.Th(ybVb)) {
//            n10 = Ld[3];
//            "".length();
//            if ("  ".length() == 0) {
//                return (13 + 81 - 43 + 120 ^ 6 + 94 - -26 + 40) & (0 + 17 - -71 + 53 ^ 104 + 30 - 10 + 4 ^ -" ".length());
//            }
//        } else {
//            n10 = ((Object)ybVb).hashCode();
//        }
//        dCVb = n19 + n10;
//        Integer XbVb = obVb.getVersion();
//        int n20 = dCVb * Ld[2];
//        if (OnlineTableEntity.Th(XbVb)) {
//            n9 = Ld[3];
//            "".length();
//            if (((0x17 ^ 0x50) & ~(0x36 ^ 0x71)) >= (0x36 ^ 0x32)) {
//                return (0x68 ^ 0x40) & ~(0x84 ^ 0xAC);
//            }
//        } else {
//            n9 = ((Object)XbVb).hashCode();
//        }
//        dCVb = n20 + n9;
//        Integer wbVb = obVb.getDeleted();
//        int n21 = dCVb * Ld[2];
//        if (OnlineTableEntity.Th(wbVb)) {
//            n8 = Ld[3];
//            "".length();
//            if (-" ".length() > 0) {
//                return (0x4E ^ 0x57 ^ (0xC5 ^ 0xC3)) & (91 + 12 - -52 + 12 ^ 132 + 11 - 136 + 177 ^ -" ".length());
//            }
//        } else {
//            n8 = ((Object)wbVb).hashCode();
//        }
//        dCVb = n21 + n8;
//        String VbVb = obVb.getId();
//        int n22 = dCVb * Ld[2];
//        if (OnlineTableEntity.Th(VbVb)) {
//            n7 = Ld[3];
//            "".length();
//            if ((0x15 ^ 0x11) < -" ".length()) {
//                return (0xF1 ^ 0xB8) & ~(0xEC ^ 0xA5);
//            }
//        } else {
//            n7 = VbVb.hashCode();
//        }
//        dCVb = n22 + n7;
//        String ubVb = obVb.getName();
//        int n23 = dCVb * Ld[2];
//        if (OnlineTableEntity.Th(ubVb)) {
//            n6 = Ld[3];
//            "".length();
//            if ((0x3C ^ 0x38) <= 0) {
//                return (0xF1 ^ 0xB3) & ~(0x14 ^ 0x56);
//            }
//        } else {
//            n6 = ubVb.hashCode();
//        }
//        dCVb = n23 + n6;
//        String TbVb = obVb.getComments();
//        int n24 = dCVb * Ld[2];
//        if (OnlineTableEntity.Th(TbVb)) {
//            n5 = Ld[3];
//            "".length();
//            if (((0x10 ^ 0x3D) & ~(0x85 ^ 0xA8)) >= (0x59 ^ 0x5D)) {
//                return (0x18 ^ 3) & ~(0x37 ^ 0x2C);
//            }
//        } else {
//            n5 = TbVb.hashCode();
//        }
//        dCVb = n24 + n5;
//        String sbVb = obVb.getTreePid();
//        int n25 = dCVb * Ld[2];
//        if (OnlineTableEntity.Th(sbVb)) {
//            n4 = Ld[3];
//            "".length();
//            if (" ".length() == 0) {
//                return ("   ".length() ^ (0xE8 ^ 0xA5)) & (35 + 176 - 53 + 52 ^ 67 + 7 - 2 + 84 ^ -" ".length());
//            }
//        } else {
//            n4 = sbVb.hashCode();
//        }
//        dCVb = n25 + n4;
//        String RbVb = obVb.getTreeLabel();
//        int n26 = dCVb * Ld[2];
//        if (OnlineTableEntity.Th(RbVb)) {
//            n3 = Ld[3];
//            "".length();
//            if (((0x82 ^ 0xB1) & ~(0x41 ^ 0x72)) < ((0x5C ^ 0x6B) & ~(9 ^ 0x3E))) {
//                return (0x12 ^ 0x39) & ~(0x13 ^ 0x38);
//            }
//        } else {
//            n3 = RbVb.hashCode();
//        }
//        dCVb = n26 + n3;
//        Date qbVb = obVb.getCreateTime();
//        int n27 = dCVb * Ld[2];
//        if (OnlineTableEntity.Th(qbVb)) {
//            n2 = Ld[3];
//            "".length();
//            if (" ".length() < ((0xA2 ^ 0x8E) & ~(0x4B ^ 0x67))) {
//                return (0x14 ^ 0x38) & ~(0x8C ^ 0xA0);
//            }
//        } else {
//            n2 = ((Object)qbVb).hashCode();
//        }
//        dCVb = n27 + n2;
//        Date PbVb = obVb.getUpdateTime();
//        int n28 = dCVb * Ld[2];
//        if (OnlineTableEntity.Th(PbVb)) {
//            n = Ld[3];
//            "".length();
//            if (((0x96 ^ 0xC0 ^ (0x79 ^ 0x27)) & (0xCC ^ 0x92 ^ (0x7F ^ 0x29) ^ -" ".length())) > 0) {
//                return (0xEB ^ 0x91 ^ (0x23 ^ 0x13)) & (0x32 ^ 0x4C ^ (0x44 ^ 0x70) ^ -" ".length());
//            }
//        } else {
//            n = ((Object)PbVb).hashCode();
//        }
//        dCVb = n28 + n;
//        return dCVb;
//    }
//
//    public String getTreeLabel() {
//        OnlineTableEntity smVb;
//        return smVb.treeLabel;
//    }
//
//    public void setUpdater(Long VhVb) {
//        whVb.updater = VhVb;
//    }
//
//    public Integer getTree() {
//        OnlineTableEntity ymVb;
//        return ymVb.tree;
//    }
//
//    public void setUpdateTime(Date ohVb) {
//        PhVb.updateTime = ohVb;
//    }
}

