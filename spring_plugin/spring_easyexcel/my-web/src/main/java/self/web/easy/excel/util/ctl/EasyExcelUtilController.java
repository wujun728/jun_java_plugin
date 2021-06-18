package self.web.easy.excel.util.ctl;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import self.web.BaseController;
import self.web.easy.excel.util.ctl.model.ExportTestModel;
import self.web.easy.excel.util.ctl.model.ExportTestModelSheet2;
import xiong.exception.ExcelException;
import xiong.utils.ExcelUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Log4j
@Controller
@RequestMapping("easyExcelUtil")
public class EasyExcelUtilController extends BaseController {

    @GetMapping("getExportData")
    @ResponseBody
    public Object getExportData(HttpServletResponse response) {
        List<ExportTestModel> list = new ArrayList<>();
        for (int i = 0; i <= 1000; i++) {
            list.add(new ExportTestModel(i));
        }
        try {
            ExcelUtil.writeExcel(response, list, "导出测试", null, ExcelTypeEnum.XLSX);
        } catch (ExcelException e) {
            log.info(e);
        }
        return success("导出成功");
    }

    @GetMapping("getExportDataMultiSheet")
    @ResponseBody
    public Object getExportDataMultiSheet(HttpServletResponse response) {
        List<ExportTestModel> list = new ArrayList<>();
        for (int i = 0; i <= 1000; i++) {
            list.add(new ExportTestModel(i));
        }
        List<ExportTestModelSheet2> list2 = new ArrayList<>();
        for (int i = 100; i >= 0; i--) {
            list2.add(new ExportTestModelSheet2(i));
        }
        try {
            ExcelUtil.writeExcel(response, "导出测试", ExcelTypeEnum.XLSX, list, list2);
        } catch (ExcelException e) {
            log.info(e);
        }
        return success("导出成功");
    }

    @PostMapping("importExcel")
    @ResponseBody
    public Object importExcel(MultipartHttpServletRequest request) {
        Iterator<String> itr = request.getFileNames();
        String uploadedFile = itr.next();
        List<MultipartFile> files = request.getFiles(uploadedFile);
        if (CollectionUtils.isEmpty(files)) {
            return fail("请选择文件！");
        }
        try {
            List<ExportTestModel> list = ExcelUtil.readFirstSheetExcel(files.get(0), ExportTestModel.class);
            return success(JSON.toJSONString(list, SerializerFeature.PrettyFormat));
        } catch (ExcelException e) {
            log.info(e);
            return fail("" + e.getMessage());
        }
    }

    @PostMapping("importExcelMultiSheet")
    @ResponseBody
    public Object importExcelMultiSheet(MultipartHttpServletRequest request) {
        Iterator<String> itr = request.getFileNames();
        String uploadedFile = itr.next();
        List<MultipartFile> files = request.getFiles(uploadedFile);
        if (CollectionUtils.isEmpty(files)) {
            return fail("请选择文件！");
        }
        try {
            Map<String, Object> map = new HashMap<>();
            List[] result = ExcelUtil.readExcel(files.get(0), ExportTestModel.class, ExportTestModelSheet2.class);
            map.put("1", result[0]);
            map.put("2", result[1]);
            return success(JSON.toJSONString(map, SerializerFeature.PrettyFormat));
        } catch (ExcelException e) {
            log.info(e);
            return fail("" + e.getMessage());
        }
    }
}
