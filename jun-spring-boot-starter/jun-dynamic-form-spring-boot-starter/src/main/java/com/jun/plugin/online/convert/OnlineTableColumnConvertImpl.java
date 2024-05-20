/*
 * Decompiled with CFR 0.152.
 */
package com.jun.plugin.online.convert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jun.plugin.online.entity.OnlineTableColumnEntity;
import com.jun.plugin.online.vo.OnlineTableColumnVO;

public class OnlineTableColumnConvertImpl
implements OnlineTableColumnConvert {
    public OnlineTableColumnConvertImpl() {
        OnlineTableColumnConvertImpl RJub;
    }

    @Override
    public List<OnlineTableColumnEntity> convertList2(List<OnlineTableColumnVO> Afub) {
        if (OnlineTableColumnConvertImpl.VJ(Afub)) {
            return null;
        }
        ArrayList<OnlineTableColumnEntity> Cfub = new ArrayList<OnlineTableColumnEntity>(Afub.size());
        Iterator<OnlineTableColumnVO> yEub = Afub.iterator();
        while (OnlineTableColumnConvertImpl.XJ(yEub.hasNext() ? 1 : 0)) {
            OnlineTableColumnConvertImpl Efub = this;
            OnlineTableColumnVO ffub = yEub.next();
            Cfub.add(Efub.convert(ffub));
            "".length();
            "".length();
            if (-" ".length() != "   ".length()) continue;
            return null;
        }
        return Cfub;
    }

    @Override
    public List<OnlineTableColumnVO> convertList(List<OnlineTableColumnEntity> dGub) {
        if (OnlineTableColumnConvertImpl.VJ(dGub)) {
            return null;
        }
        ArrayList<OnlineTableColumnVO> fGub = new ArrayList<OnlineTableColumnVO>(dGub.size());
        Iterator<OnlineTableColumnEntity> bGub = dGub.iterator();
        while (OnlineTableColumnConvertImpl.XJ(bGub.hasNext() ? 1 : 0)) {
            OnlineTableColumnConvertImpl EGub = this;
            OnlineTableColumnEntity JGub = bGub.next();
            fGub.add(EGub.convert(JGub));
            "".length();
            "".length();
            if (" ".length() != 0) continue;
            return null;
        }
        return fGub;
    }

    private static boolean XJ(int n) {
        return n != 0;
    }

    @Override
    public OnlineTableColumnEntity convert(OnlineTableColumnVO fJub) {
        if (OnlineTableColumnConvertImpl.VJ(fJub)) {
            return null;
        }
        OnlineTableColumnEntity dJub = new OnlineTableColumnEntity();
        dJub.setId(fJub.getId());
        dJub.setName(fJub.getName());
        dJub.setComments(fJub.getComments());
        dJub.setLength(fJub.getLength());
        dJub.setPointLength(fJub.getPointLength());
        dJub.setDefaultValue(fJub.getDefaultValue());
        dJub.setColumnType(fJub.getColumnType());
        dJub.setColumnPk(fJub.isColumnPk());
        dJub.setColumnNull(fJub.isColumnNull());
        dJub.setFormItem(fJub.isFormItem());
        dJub.setFormRequired(fJub.isFormRequired());
        dJub.setFormInput(fJub.getFormInput());
        dJub.setFormDefault(fJub.getFormDefault());
        dJub.setFormDict(fJub.getFormDict());
        dJub.setGridItem(fJub.isGridItem());
        dJub.setGridSort(fJub.isGridSort());
        dJub.setQueryItem(fJub.isQueryItem());
        dJub.setQueryType(fJub.getQueryType());
        dJub.setQueryInput(fJub.getQueryInput());
        dJub.setSort(fJub.getSort());
        return dJub;
    }

    @Override
    public OnlineTableColumnVO convert(OnlineTableColumnEntity shub) {
        if (OnlineTableColumnConvertImpl.VJ(shub)) {
            return null;
        }
        OnlineTableColumnVO Rhub = new OnlineTableColumnVO();
        Rhub.setId(shub.getId());
        Rhub.setName(shub.getName());
        Rhub.setComments(shub.getComments());
        Rhub.setLength(shub.getLength());
        Rhub.setPointLength(shub.getPointLength());
        Rhub.setDefaultValue(shub.getDefaultValue());
        Rhub.setColumnType(shub.getColumnType());
        Rhub.setColumnPk(shub.isColumnPk());
        Rhub.setColumnNull(shub.isColumnNull());
        Rhub.setFormItem(shub.isFormItem());
        Rhub.setFormRequired(shub.isFormRequired());
        Rhub.setFormInput(shub.getFormInput());
        Rhub.setFormDefault(shub.getFormDefault());
        Rhub.setFormDict(shub.getFormDict());
        Rhub.setGridItem(shub.isGridItem());
        Rhub.setGridSort(shub.isGridSort());
        Rhub.setQueryItem(shub.isQueryItem());
        Rhub.setQueryType(shub.getQueryType());
        Rhub.setQueryInput(shub.getQueryInput());
        Rhub.setSort(shub.getSort());
        return Rhub;
    }

    private static boolean VJ(Object object) {
        return object == null;
    }
}

