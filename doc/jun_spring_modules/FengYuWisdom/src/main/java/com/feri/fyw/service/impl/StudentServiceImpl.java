package com.feri.fyw.service.impl;

import com.alibaba.excel.EasyExcel;
import com.feri.fyw.dao.StudentDao;
import com.feri.fyw.dto.GradePersonDto;
import com.feri.fyw.dto.SexTjDto;
import com.feri.fyw.dto.StudentSexDto;
import com.feri.fyw.entity.Admin;
import com.feri.fyw.entity.Student;
import com.feri.fyw.listener.StudentReadListener;
import com.feri.fyw.listener.StudentReadListener2;
import com.feri.fyw.service.intf.StudentService;
import com.feri.fyw.util.RUtil;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 09:52
 */
@Service //IOC
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao dao;

    @Override
    public R save(Student student) {
        if(dao.insert(student)>0){
            return RUtil.ok();
        }else {
            return RUtil.fail();
        }
    }

    @Override
    public PageBean queryPage() {
        List<Student> list=dao.selectAll();
        return new PageBean(0,"",list.size(),list);
    }

    @Override
    public R saveBatch(MultipartFile file) {
        //1.校验
        if(!file.isEmpty()){
            //2.获取文件内容
            try {
                InputStream is=file.getInputStream();
                //3.实例化自定义监听器
                StudentReadListener listener=new StudentReadListener();
                //4.解析Excel
                List<Student>list=EasyExcel.read(is,Student.class,listener).doReadAllSync();
                //5.获取解析的结果,执行批处理
                if(dao.insertBatch(list)>0){
                    return RUtil.ok(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return RUtil.fail();
    }

    @Override
    public R saveBatchV2(MultipartFile file) {
        //1.校验
        if(!file.isEmpty()){
            //2.获取文件内容
            try {
                InputStream is=file.getInputStream();
                //3.实例化自定义监听器
                StudentReadListener2 listener=new StudentReadListener2(dao);
                //4.解析Excel
                EasyExcel.read(is,Student.class,listener).doReadAll();
                return RUtil.ok();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return RUtil.fail();
    }

    @Override
    public R queryTjSex() {
        List<StudentSexDto> list=dao.selectTjSex();
        if(list!=null &&list.size()>0){
            //处理数据 满足页面的要求
            //2个数组
            List<String> sexs=new ArrayList<>();
            List<Map<String,Object>> sexvals=new ArrayList<>();
            for(StudentSexDto s:list){
                String sex=parseSex(s.getSex());
                sexs.add(sex);
                HashMap<String,Object> vals=new HashMap<>();
                vals.put("name",sex);
                vals.put("value",s.getCt());
                sexvals.add(vals);
            }
            SexTjDto dto=new SexTjDto();
            dto.setSexs(sexs);
            dto.setSexvals(sexvals);
            return RUtil.ok(dto);
        }
        return RUtil.fail();
    }

    @Override
    public R queryTjPersons() {
        List<GradePersonDto> list=dao.selectTjPersons();
        if(list!=null &&list.size()>0){
            Map<String,Object> map=new HashMap<>();
            List<String> grades=new ArrayList<>();
            List<Long> persons=new ArrayList<>();
            for(GradePersonDto g:list){
                if(g.getName()==null){
                    continue;
                }
                grades.add(g.getName());
                persons.add(g.getCt());
            }
            map.put("grades",grades);
            map.put("persons",persons);
            return RUtil.ok(map);
        }
        return RUtil.fail();
    }

    private String parseSex(int s){
        String sex="未知";
        switch (s){
            case 1:sex="男";break;
            case 2:sex="女";break;
            case 3:sex="第三性别";break;
        }
        return sex;
    }
}
