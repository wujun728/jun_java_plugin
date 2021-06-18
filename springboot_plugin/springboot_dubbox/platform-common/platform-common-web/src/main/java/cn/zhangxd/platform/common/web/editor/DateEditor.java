package cn.zhangxd.platform.common.web.editor;

import cn.zhangxd.platform.common.utils.DateHelper;

import java.beans.PropertyEditorSupport;

/**
 * @author Wujun
 */
public class DateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        setValue(DateHelper.parseDate(text));
    }

}
