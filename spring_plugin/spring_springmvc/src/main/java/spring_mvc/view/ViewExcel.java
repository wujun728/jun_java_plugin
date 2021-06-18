package spring_mvc.view;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import spring_mvc.model.user;

public class ViewExcel extends AbstractXlsView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sheet=wb.createSheet("list");
		sheet.setDefaultColumnWidth((short)12);
		HSSFRow hssfRow=sheet.createRow(0);
		hssfRow.createCell(0).setCellValue("编---号");
		hssfRow.createCell(1).setCellValue("姓---名");
		hssfRow.createCell(2).setCellValue("账户余额");
		hssfRow.createCell(3).setCellValue("生---日");
		HSSFCellStyle dateStyle = wb.createCellStyle();
		/*设置excel对日期格式的读取*/
		/*
		 * 	所有日期格式都可以通过getDataFormat()值来判断
			yyyy-MM-dd-----	14
			yyyy年m月d日---	31
			yyyy年m月-------	57
			m月d日  ----------	58
			HH:mm-----------20
			h时mm分  -------	32
		 * */
		dateStyle.setDataFormat((short)31);
		List<user> users=(List<user>)model.get("users");
		for(int i=0;i<users.size();i++){
			hssfRow=sheet.createRow(i+1);
			user user=users.get(i);
			hssfRow.createCell(0).setCellValue(user.id);
			hssfRow.createCell(1).setCellValue(user.name);
			hssfRow.createCell(2).setCellValue(user.getMoney());
			HSSFCell hc=hssfRow.createCell(3);
			hc.setCellStyle(dateStyle);
			hc.setCellValue(user.getBirthday());
		}
		String filename=new String(model.get("fileName").toString().getBytes("utf-8"),"iso-8859-1");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="+filename);
		OutputStream outputStream=response.getOutputStream();
		wb.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}
}
