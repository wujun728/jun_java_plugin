package com.jun.plugin.poi.resume;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;

public class Main {
    static String  basePath="D:\\Documents\\Desktop\\dynamic-resume\\start\\";
    public static void main(String[] args) throws Exception {
    	test1();

    }


	private static void test1() throws IOException, FileNotFoundException {
		Map<String, Object> resume = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        
//        ListRenderPolicy policy = new ListRenderPolicy();
//        Configure config = Configure.newBuilder()
//                .bind("pictures", policy).build();
//        List<WordVO> detailList = new ArrayList<>();
//        WordVO two = new WordVO();
//        two.setProblem("问题测试2");
//        two.setReason("原因测试2");
//        List<PictureRenderData> pList2 = new ArrayList<>();
//        pList2.add(new PictureRenderData(100, 120, "D:\\Documents\\Desktop\\dynamic-resume\\start\\img\\3.png"));
//        two.setPicture(pList2);
//        detailList.add(two);
//        List<Object> list = new ArrayList<>();
//        detailList.forEach(wordVO -> {
//            list.add(new TextRenderData(wordVO.getProblem()));
//            list.add(new TextRenderData(wordVO.getReason()));
//            if (CollectionUtils.isNotEmpty(wordVO.getPicture())) {
//                list.addAll(wordVO.getPicture());
//            }
//        });
        data.put("pictures", new PictureRenderData(100, 120, "D:\\Documents\\Desktop\\dynamic-resume\\start\\img\\3.png"));
        
        data.put("name", "Sayi");
        data.put("itcode","lxra");
        data.put("staffName","李小冉");
        data.put("age",23);
        data.put("gender","女");
        data.put("workplace","北京");
        data.put("highestEducation","本科");
        data.put("beginWordTime","20211009");
        data.put("enterCompanyTime","20211009");
        data.put("judicialEntity","北京神州信息");
        data.put("flPlace","北京");
        data.put("department","政府SBU");
        data.put("majorName","计算机科学与应用");
        data.put("photo","xxx.png");
        
        data.put("post","工程师");
        data.put("experience","1111111111111111");
        List<Map<String,Object>> list11=new ArrayList<>();
        list11.add(data);
        resume.put("resume",list11);
        XWPFTemplate template = XWPFTemplate.compile(basePath+"简历模板2.docx").render(
                resume);
        template.writeAndClose(new FileOutputStream(basePath+"简历模板output41611.docx"));
	}


    private static void test() throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Sayi");
        data.put("itcode","lxra");
        data.put("staffName","李小冉");
        data.put("age",23);
        data.put("gender","女");
        data.put("workplace","北京");
        data.put("highestEducation","本科");
        data.put("beginWordTime","20211009");
        data.put("enterCompanyTime","20211009");
        data.put("judicialEntity","北京神州信息");
        data.put("flPlace","北京");
        data.put("department","政府SBU");
        data.put("majorName","计算机科学与应用");
        data.put("photo","xxx.png");
        List<Map<String,Object>> announce=new ArrayList<>();
        Map<String,Object> map1=new HashMap<>();
        map1.put("name","张三");
        map1.put("age",23);
        Map<String,Object> map2=new HashMap<>();
        map2.put("name","李四");
        map2.put("age",13);
        announce.add(map1);
        announce.add(map2);
        List<String> list=new ArrayList<>();
        list.add("hhh");
        list.add("bbb");
        data.put("announce", list);

        JSONObject json=new JSONObject();
        json.put("announce",new String[]{"zhagnsan","list"});
        System.out.println(json.toJSONString());
        XWPFTemplate template = XWPFTemplate.compile(basePath+"简历模板.docx").render(
                data);
        template.writeAndClose(new FileOutputStream(basePath+"output1111-12.docx"));
    }
}
