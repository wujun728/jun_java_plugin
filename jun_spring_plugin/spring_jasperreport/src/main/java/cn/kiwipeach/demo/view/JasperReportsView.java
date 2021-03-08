package cn.kiwipeach.demo.view;

import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Properties;

public class JasperReportsView extends JasperReportsMultiFormatView {
	public static final String REPORT_DATA_KEY = "jrMainDataSource";
	public static final String ATTACHEMT_FILE_NAME_KEY = "attachmentFileName";
	public static final String IS_DOWNLOAD_KEY = "isDownload";

	@Override
	protected void renderReport(JasperPrint populatedReport, Map<String, Object> model, HttpServletResponse response) throws Exception {
		//显示格式
		String format = String.valueOf(model.get(DEFAULT_FORMAT_KEY));
		String attachmentFileName = String.valueOf(model.get(ATTACHEMT_FILE_NAME_KEY));
		if (format == null) {
			throw new IllegalArgumentException("model中未找到指定的输出格式(format:pdf)");
		}
		//是否下载，只针对pdf格式
		boolean isDownload = Boolean.valueOf(String.valueOf(model.get(IS_DOWNLOAD_KEY)));
		Properties contentDispositionMappings = getContentDispositionMappings();
		if (isDownload) {
			//报表下载名字
			if (attachmentFileName == null) {
				throw new IllegalArgumentException("model中未指定输出文件名(attachmentFileName)");
			}
			contentDispositionMappings.put(format, "attachment; filename=" + ChineseStringToAscii(attachmentFileName.toString()) + "." + format);
		}else{
			String header = contentDispositionMappings.getProperty(format);
			if (header != null) {
				contentDispositionMappings.remove(format);
			}
		}
		super.renderReport(populatedReport, model, response);
	}
	
	@Override
	protected JasperPrint fillReport(Map<String, Object> model)
			throws Exception {
		Object reportDataKey = model.get(REPORT_DATA_KEY);
		if(reportDataKey != null){
			setReportDataKey(REPORT_DATA_KEY);
		}
		return super.fillReport(model);
	}
	
	public String ChineseStringToAscii(String paramString) {
		try {
			byte[] arrayOfByte = paramString.getBytes("GBK");
			char[] arrayOfChar = new char[arrayOfByte.length];
			for (int i = 0; i < arrayOfByte.length; i++) {
				arrayOfChar[i] = ((char) (arrayOfByte[i] & 0xFF));
			}
			return new String(arrayOfChar);
		} catch (Exception localException) {
			localException.getLocalizedMessage();
		}
		return paramString;
	}

}
