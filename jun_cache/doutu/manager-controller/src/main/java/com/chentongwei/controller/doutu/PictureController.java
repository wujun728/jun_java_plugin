package com.chentongwei.controller.doutu;

import com.alibaba.fastjson.JSONObject;
import com.chentongwei.common.constant.ResponseEnum;
import com.chentongwei.common.constant.StatusEnum;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Page;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.exception.BussinessException;
import com.chentongwei.common.handler.CommonExceptionHandler;
import com.chentongwei.entity.common.vo.UploadImgVO;
import com.chentongwei.entity.common.vo.UserVO;
import com.chentongwei.entity.doutu.io.PictureListAdminIO;
import com.chentongwei.entity.doutu.io.PictureSaveIO;
import com.chentongwei.entity.doutu.vo.PictureListAdminVO;
import com.chentongwei.service.doutu.IPictureService;
import com.chentongwei.util.QETag;
import com.chentongwei.util.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.Image;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 图片接口
 *
 * @author TongWei.Chen 2017-06-13 13:24:03
 */
@RestController
@RequestMapping("/doutu/picture")
public class PictureController {

    @Autowired
    private IPictureService pictureService;
    @Autowired
    private QiniuUtil qiniuUtil;

    /**
     * 图片根据分类查询列表
     *
     * @param pictureListAdminIO：搜索条件
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(PictureListAdminIO pictureListAdminIO) {
        List<PictureListAdminVO> pictureList = pictureService.listPictureAdmin(pictureListAdminIO);
        return ResultCreator.getSuccess(pictureList);
    }

    /**
     * 保存图片信息
     *
     * @param pictureSaveIO：图片IO
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(HttpServletRequest request, @RequestBody @Valid PictureSaveIO pictureSaveIO) {
        //用户上传，并非爬虫
        pictureSaveIO.setStatus(StatusEnum.PICTURE_ORIGIN_USER.getCode());
        HttpSession session = request.getSession();
        UserVO user = (UserVO)session.getAttribute("user");
        if(null != user) {
            pictureSaveIO.setCreatorId(user.getId());
        } else {
            //默认爬虫爬来的
            pictureSaveIO.setCreatorId(-1L);
        }
        boolean flag = pictureService.save(pictureSaveIO);
        return ResultCreator.getSuccess(flag);
    }

    /**
     * 作废
     *
     * @param id:图片id
     * @param status:作废状态。0：已作废 1：正常
     * @return
     */
    @RequestMapping(value = "/obsolete/{id}", method = RequestMethod.POST)
    public Result obsolete(@PathVariable Long id) {
        return ResultCreator.getSuccess(pictureService.obsolete(id));
    }

    /**
     * 图片上传
     * 1.上传到七牛云
     * 2.保存到db
     * 3.保存到redis
     *
     * @return
     */
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public Result uploadImg(@RequestParam(value = "file", required = true) MultipartFile file) {
        try {
            UploadImgVO result = executeUpload(file);
            return ResultCreator.getSuccess(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行上传
     */
    private UploadImgVO executeUpload(MultipartFile file) throws IOException {
        if(! file.getContentType().startsWith("image")) {
            throw new BussinessException(ResponseEnum.FILE_FORMAT_ERROR);
        }
        Image image = ImageIO.read(file.getInputStream());
        if(image == null) {
            throw new BussinessException(ResponseEnum.FILE_FORMAT_ERROR);
        }
        String orginName = file.getOriginalFilename();
        int index = orginName.lastIndexOf(".");
        String suffix = orginName.substring(index);

        String hex = QETag.data(file.getBytes());
        //检查文件是否已经存在
        int count = pictureService.existsHash(hex);
        CommonExceptionHandler.existsCheck(count, ResponseEnum.EXISTS_FILE);
        //上传到七牛
        JSONObject jsonObject = qiniuUtil.upload(file.getBytes(), suffix);

        String hash = jsonObject.getString("hash");
        String url = jsonObject.getString("key");

        UploadImgVO uploadImgVO = new UploadImgVO();
        uploadImgVO.setHash(hash);
        uploadImgVO.setUrl(url);
        uploadImgVO.setSuffix(suffix);
        uploadImgVO.setBytes(file.getBytes());

        return uploadImgVO;
    }
}
