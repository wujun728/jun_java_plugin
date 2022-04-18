package com.jun.plugin.poi.resume;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import org.apache.poi.xwpf.usermodel.BreakClear;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class BreakPagePolicy implements RenderPolicy {
    @Override
    public void render(ElementTemplate elementTemplate, Object breakPage, XWPFTemplate xwpfTemplate) {
        //当前位置
        XWPFRun run = ((RunTemplate) elementTemplate).getRun();
        run.setText(null,0);
        if((boolean)breakPage){
            run.addBreak(BreakType.PAGE);
        }

    }
}
