package com.jun.excel;
//package com.utils;
//
//import java.io.OutputStream;
//import java.lang.reflect.Field;
//import java.util.List;
//
//import javax.servlet.http.HttpServletResponse;
//
//import jxl.SheetSettings;
//import jxl.Workbook;
//import jxl.format.Alignment;
//import jxl.format.Border;
//import jxl.format.BorderLineStyle;
//import jxl.format.VerticalAlignment;
//import jxl.write.Label;
//import jxl.write.WritableCellFormat;
//import jxl.write.WritableFont;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;
//import org.apache.struts2.ServletActionContext;
//
//public class ExportExcel {
//
//	public final static String exportExcel(String fileName, String[] Title,
//			List<Object> listContent) {
//		String result = "系统提示：Excel文件导出成功！";
//
//		try {
//
//			HttpServletResponse response = ServletActionContext.getResponse();
//			OutputStream os = response.getOutputStream();// 取得输出流
//			response.reset();
//			response.setHeader("Content-disposition", "attachment; filename="
//					+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));
//			response.setContentType("application/msexcel");// 定义输出类型
//
//			WritableWorkbook workbook = Workbook.createWorkbook(os);
//
//			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
//
//			SheetSettings sheetset = sheet.getSettings();
//
//			sheetset.setProtected(false);
//
//			/** ************设置单元格字体************** */
//			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
//			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
//					WritableFont.BOLD);
//
//			/** ************以下设置三种单元格样式，灵活备用************ */
//			// 用于标题居中
//			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
//			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
//			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
//			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
//			wcf_center.setWrap(false); // 文字是否换行
//
//			// 用于正文居左
//			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
//			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
//			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
//			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
//			wcf_left.setWrap(false); // 文字是否换行
//
//			/** ***************以下是EXCEL开头大标题，暂时省略********************* */
//			// sheet.mergeCells(0, 0, colWidth, 0);
//			// sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
//
//			// 输出第一行标题
//			for (int i = 0; i < Title.length; i++) {
//				sheet.addCell(new Label(i, 0, Title[i], wcf_center));
//			}
//			/** ***************以下是EXCEL正文数据********************* */
//			Field[] fields = null;
//			int i = 1;
//			for (Object obj : listContent) {
//				fields = obj.getClass().getDeclaredFields();
//				int j = 0;
//				for (Field v : fields) {
//					v.setAccessible(true);
//					Object va = v.get(obj);
//					if (va == null) {
//						va = "";
//					}
//					sheet.addCell(new Label(j, i, va.toString(), wcf_left));
//					j++;
//				}
//				i++;
//			}
//			workbook.write();
//			workbook.close();
//		} catch (Exception e) {
//			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
//			System.out.println(result);
//			e.printStackTrace();
//		}
//		return result;
//	}
//}