package com.jun.plugin.poi.resume;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class ExperiencePolicy implements RenderPolicy {
    @Override
    public void render(ElementTemplate elementTemplate, Object data, XWPFTemplate xwpfTemplate) {
        //当前位置
        XWPFRun run = ((RunTemplate) elementTemplate).getRun();
        String[] array=((String) data).split("#");
        for(int i=0;i<array.length;i++){
            String msg=array[i];
            run.setText(msg, i);
            if(i<=array.length-1){
                run.addBreak();

            }
        }
    }
}
