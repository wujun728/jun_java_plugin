package com.fit.imgzip.controller;

import com.fit.imgzip.util.ImageUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * 图片上传处理类
 * @author zlzhaoe
 */
@Controller
public class ImgZipController {
	@ApiOperation(value = "图片上传",notes = "多项目图片上传工具类",httpMethod = "POST")
	@RequestMapping(value="/img_upload_compress")
	@ResponseBody
	public Map<String, Object> img_upload_compress(
			@ApiParam(name = "file",value="文件（图片）",required = true) @RequestParam(value = "file") MultipartFile file,
			@ApiParam(name = "path",value="项目名称")@RequestParam(value = "path",required = false)String path,
			@ApiParam(name = "scale",value="图片压缩比例:0.0~1.0")@RequestParam(value = "scale",required = false)Double scale) throws IOException {
		Map<String, Object> result = new HashMap<>();
		String resultImagePath = ImageUtil.imgUploadCompress(file, path,scale);
		if(resultImagePath.equals("0")){
			result.put("code", "0");
			result.put("msg", "上传失败,后端异常！");
		}else if(resultImagePath.equals("-1")){
			result.put("code", "-1");
			result.put("msg", "非法图片格式！");
		}else{
			result.put("code", "1");
			result.put("msg", resultImagePath);
		}
		return result;
	}

}
