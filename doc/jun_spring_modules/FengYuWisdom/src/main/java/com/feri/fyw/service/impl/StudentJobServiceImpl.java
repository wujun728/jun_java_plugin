package com.feri.fyw.service.impl;

import com.alibaba.excel.EasyExcel;
import com.feri.fyw.dao.StageExamDao;
import com.feri.fyw.dao.StudentJobDao;
import com.feri.fyw.dto.StudentJobDto;
import com.feri.fyw.entity.StageExam;
import com.feri.fyw.entity.StudentJob;
import com.feri.fyw.listener.StageExamReadListener;
import com.feri.fyw.listener.StudentJobReadListener;
import com.feri.fyw.service.intf.StageExamService;
import com.feri.fyw.service.intf.StudentJobService;
import com.feri.fyw.util.RUtil;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-18 10:05
 */
@Service
public class StudentJobServiceImpl implements StudentJobService {
    @Autowired
    private StudentJobDao dao;
    @Override
    public R save(StudentJob job) {
        if(dao.insert(job)>0){
            return RUtil.ok();
        }else {
            return RUtil.fail();
        }
    }

    @Override
    public R saveBatch(MultipartFile file) {
        if(file.isEmpty()){
            try {
                List<StudentJob> list= EasyExcel.read(file.getInputStream(),StudentJob.class,new StudentJobReadListener()).doReadAllSync();
                if(list!=null &&list.size()>0){
                    if(dao.insertBatch(list)>0){
                        //
                        return RUtil.ok();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return RUtil.fail();
    }

    @Override
    public PageBean queryAll(int page, int limit) {
        PageHelper.startPage(page, limit);
        PageInfo<StudentJob> pageInfo=new PageInfo<>(dao.selectAll());
        return new PageBean(0,"",pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public void createTop(HttpServletRequest request, OutputStream os) {
        //榜单生成
        //1.获取数据
        List<StudentJobDto> list=dao.selectAllTop();
        //2.对数据进行脱敏 &图片
        //生成图片  awt  画板
        try {
            //650 1155
            //1.加载背景图片
            BufferedImage bgImg= ImageIO.read(new File(request.getServletContext().getRealPath("/tem/offer.jpg")));
            //2.获取画板
            Graphics2D graphics2D=bgImg.createGraphics();
            //3.设置样式
            graphics2D.setColor(Color.RED);
            graphics2D.setFont(new Font("楷体",Font.PLAIN,50));
            //4.写出内容
            graphics2D.drawString("千锋郑州Java2013就业榜",52,340);
            graphics2D.setColor(Color.blue);
            graphics2D.setFont(new Font("楷体",Font.PLAIN,40));
            graphics2D.drawString("姓名",52,400);
            graphics2D.drawString("薪水",200,400);
            graphics2D.drawString("公司",320,400);
            graphics2D.drawString("城市",480,400);
            graphics2D.setColor(Color.BLACK);
            graphics2D.setFont(new Font("楷体",Font.PLAIN,30));
            System.err.println(list.size());
            for(int i=1;i<=list.size();i++){
                //数据脱敏
                StudentJobDto dto=list.get(i-1);
                graphics2D.drawString(rename(dto.getSname()),52,400+50*i);
                graphics2D.drawString(dto.getSalary()+"",170,400+50*i);
                graphics2D.drawString(renameCom(dto.getCompany()),260,400+50*i);
                graphics2D.drawString(dto.getCity(),500,400+50*i);
            }
            // graphics2D.drawImage();
            //5.释放画板
            graphics2D.dispose();
            //6.生成新的图片
            ImageIO.write(bgImg,"jpg",os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //姓名脱敏  保留第一个
    private String rename(String name){
        return name.substring(0,1)+"同学";
    }
    //公司脱敏 保留开头2个字母 追加结尾公司
    private String renameCom(String company){
        return company.substring(0,2)+"******公司";
    }


}
