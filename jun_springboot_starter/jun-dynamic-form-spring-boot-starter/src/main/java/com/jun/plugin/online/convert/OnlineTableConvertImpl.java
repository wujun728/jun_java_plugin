/*
 * Decompiled with CFR 0.152.
 */
package com.jun.plugin.online.convert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jun.plugin.online.entity.OnlineTableEntity;
import com.jun.plugin.online.vo.OnlineTableVO;

public class OnlineTableConvertImpl
implements OnlineTableConvert {
    @Override
    public List<OnlineTableVO> convertList(List<OnlineTableEntity> Cfsb) {
        if (OnlineTableConvertImpl.wm(Cfsb)) {
            return null;
        }
        ArrayList<OnlineTableVO> bfsb = new ArrayList<OnlineTableVO>(Cfsb.size());
        Iterator<OnlineTableEntity> XEsb = Cfsb.iterator();
        while (OnlineTableConvertImpl.Xm(XEsb.hasNext() ? 1 : 0)) {
            OnlineTableConvertImpl Afsb = this;
            OnlineTableEntity Efsb = XEsb.next();
            bfsb.add(Afsb.convert(Efsb));
            "".length();
            "".length();
            if ((0x10 ^ 0x3E ^ (0x19 ^ 0x32)) != 0) continue;
            return null;
        }
        return bfsb;
    }

    @Override
    public OnlineTableVO convert(OnlineTableEntity Nfsb) {
        if (OnlineTableConvertImpl.wm(Nfsb)) {
            return null;
        }
        OnlineTableVO ofsb = new OnlineTableVO();
        ofsb.setId(Nfsb.getId());
        ofsb.setName(Nfsb.getName());
        ofsb.setComments(Nfsb.getComments());
        ofsb.setFormLayout(Nfsb.getFormLayout());
        ofsb.setTree(Nfsb.getTree());
        ofsb.setTreePid(Nfsb.getTreePid());
        ofsb.setTreeLabel(Nfsb.getTreeLabel());
        ofsb.setTableType(Nfsb.getTableType());
        ofsb.setVersion(Nfsb.getVersion());
        ofsb.setCreateTime(Nfsb.getCreateTime());
        return ofsb;
    }

    public OnlineTableConvertImpl() {
        OnlineTableConvertImpl dGsb;
    }

    private static boolean wm(Object object) {
        return object == null;
    }

    @Override
    public OnlineTableEntity convert(OnlineTableVO Vfsb) {
        if (OnlineTableConvertImpl.wm(Vfsb)) {
            return null;
        }
        OnlineTableEntity wfsb = new OnlineTableEntity();
        wfsb.setId(Vfsb.getId());
        wfsb.setName(Vfsb.getName());
        wfsb.setComments(Vfsb.getComments());
        wfsb.setFormLayout(Vfsb.getFormLayout());
        wfsb.setTree(Vfsb.getTree());
        wfsb.setTreePid(Vfsb.getTreePid());
        wfsb.setTreeLabel(Vfsb.getTreeLabel());
        wfsb.setTableType(Vfsb.getTableType());
        wfsb.setCreateTime(Vfsb.getCreateTime());
        wfsb.setVersion(Vfsb.getVersion());
        return wfsb;
    }

    private static boolean Xm(int n) {
        return n != 0;
    }
}

