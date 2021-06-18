package com.github.sd4324530.fastexcel;

import com.github.sd4324530.fastexcel.entity.MyTest;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author peiyu
 */
public class ExcelTest {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelTest.class);

    @Test
    public void test() {
        try {
            createExcel();
        } catch (IOException e) {
            LOG.error("异常", e);
        } catch (InvalidFormatException e) {
            LOG.error("异常", e);
        }
    }

    private void createExcel() throws IOException, InvalidFormatException {
        FastExcel fastExcel = new FastExcel("E:/data.xlsx");
        fastExcel.setSheetName("活动信息数据");
        List<MyTest> tests = fastExcel.parse(MyTest.class);
        if (null != tests && !tests.isEmpty()) {
            for (MyTest myTest : tests) {
                LOG.debug("记录:{}", myTest.toString());
            }

            FastExcel create = new FastExcel("E:/data2.xlsx");
            create.setSheetName("活动信息数据");
            boolean result = create.createExcel(tests);
            LOG.debug("结果:{}", result);
            create.close();
        } else {
            LOG.debug("没有结果");
        }
        fastExcel.close();
    }
}
