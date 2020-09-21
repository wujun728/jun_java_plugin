package com.chentongwei.task;

import com.chentongwei.dao.doutu.IPictureDAO;
import com.chentongwei.dao.doutu.IPictureTagDAO;
import com.chentongwei.entity.doutu.vo.PictureListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * 专门用来跑一些需要批量操作的小程序
 *
 * @author TongWei.Chen 2017-07-11 09:19:18
 */
@Component
@Configurable
@EnableScheduling
public class TestTask {

    private static final Logger LOG = LoggerFactory.getLogger(TestTask.class);

    @Autowired
    private IPictureDAO pictureDAO;
    @Autowired
    private IPictureTagDAO pictureTagDAO;

    int count = 0;

    /**
     * 新增的width和height字段，默认值都是0，需要批量去获取width和height并更新到db
     */
    //    @Scheduled(cron = "0 18 09 * * ?")
    public void updateWidthAndHeight() {
        List<PictureListVO> list = pictureDAO.list();
        InputStream is = null;
        String url = "http://orz8lqn0g.bkt.clouddn.com/";

        for(int i = 0; i < list.size(); i ++) {
            try {
                is = new URL(url + list.get(i).getUrl()).openStream();
                BufferedImage sourceImg = ImageIO.read(is);
                //update
                boolean flag = pictureDAO.update(sourceImg.getWidth(), sourceImg.getHeight(), list.get(i).getId());
                boolean flag2 = pictureTagDAO.update(sourceImg.getWidth(), sourceImg.getHeight(), list.get(i).getId());
                if(! flag || ! flag2) {
                    LOG.error("图片id为{}的报错啦。。。", list.get(i).getId());
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    continue ;
                }
            } catch (IOException e) {
                System.out.println("报错了。。。。" + e.getMessage());
                continue ;
            }
            if(i % 100 == 0) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            count ++;
        }
        System.out.println("成功了=" + count);
    }
}
