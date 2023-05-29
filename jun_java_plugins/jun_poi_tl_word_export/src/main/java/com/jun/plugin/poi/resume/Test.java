package com.jun.plugin.poi.resume;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.ListRenderPolicy;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
//        InputStream inputStream = Thread.currentThread().getContextClassLoader()
//                .getResourceAsStream("template/test.docx");
        ListRenderPolicy policy = new ListRenderPolicy();
        Configure config = Configure.newBuilder()
                .bind("pictures", policy).build();
        List<WordVO> detailList = new ArrayList<>();
        WordVO one = new WordVO();
        WordVO two = new WordVO();
        one.setProblem("问题测试1");
        two.setProblem("问题测试2");
        one.setReason("原因测试1");
        two.setReason("原因测试2");
        List<PictureRenderData> pList1 = new ArrayList<>();
        List<PictureRenderData> pList2 = new ArrayList<>();
        pList1.add(new PictureRenderData(100, 120, "D:\\Documents\\Desktop\\dynamic-resume\\start\\img\\1.png"));
        pList1.add(new PictureRenderData(100, 120, "D:\\Documents\\Desktop\\dynamic-resume\\start\\img\\2.png"));
        pList2.add(new PictureRenderData(100, 120, "D:\\Documents\\Desktop\\dynamic-resume\\start\\img\\3.png"));
        //one.setPicture(pList1);
        two.setPicture(pList2);
        //detailList.add(one);
        detailList.add(two);
        List<Object> list = new ArrayList<>();
        detailList.forEach(wordVO -> {
            list.add(new TextRenderData(wordVO.getProblem()));
            list.add(new TextRenderData(wordVO.getReason()));
            if (CollectionUtils.isNotEmpty(wordVO.getPicture())) {
                list.addAll(wordVO.getPicture());
            }
        });
        map.put("pictures", list);
        
        
//        XWPFTemplate template = XWPFTemplate.compile(inputStream, config).render(map);
        XWPFTemplate template = XWPFTemplate.compile("D:\\Documents\\Desktop\\dynamic-resume\\start\\test-template.docx", config).render(map);
        template.writeToFile("D:\\Documents\\Desktop\\dynamic-resume\\start\\测试416-222222.docx");
    }


}
