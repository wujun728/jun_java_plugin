package spring_mvc.utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;

/**
 * 导出Excel
 * @author Wujun
 *
 */
public class ExportExcel<T> {
	   public void exportExcel(List<T> list ,HttpServletRequest request, HttpServletResponse response)
	    {
	        List<T> contactsList=list;

	        HttpSession session = request.getSession();
	        session.setAttribute("state", null);
	        // 生成提示信息，
	        response.setContentType("application/vnd.ms-excel");
	        String filename=(String) request.getAttribute("filename");
	        if(filename==null||filename.equals("")){
	        	filename=list.get(0).getClass().getName();
	        }
	        String codedFileName = null;
	        OutputStream fOut = null;
	        try
	        {
	            // 进行转码，使其支持中文文件名
	            codedFileName = new String(filename.getBytes("utf-8"), "iso-8859-1");
	            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
	            // response.addHeader("Content-Disposition", "attachment;   filename=" + codedFileName + ".xls");
	            // 产生工作簿对象
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            //产生工作表对象
	            HSSFSheet sheet = workbook.createSheet();
	            int rowIndex = 1,cellIndex = 0;
	            Field[] field = contactsList.get(0).getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组
	            HSSFRow headerRow = sheet.createRow(0);//创建一行
	            while (cellIndex<field.length){
	                HSSFCell cell = headerRow.createCell(cellIndex);//创建一列
	                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	                cell.setCellValue(field[cellIndex].getName());
	                cellIndex++;
	            }

	            for(int i=0;i<contactsList.size();i++){
	                cellIndex=0;
	                HSSFRow currentRow = sheet.createRow(rowIndex);//创建一行
	                while (cellIndex<field.length){
	                    HSSFCell cell = currentRow.createCell(cellIndex);//创建一列
	                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	                    Object value=InvokeUtility.getFieldValueByName(field[cellIndex].getName(),contactsList.get(i));//通过反射获取属性的value
	                    String returnValue;
	                    if(value!=null){
	                        if(value.getClass()== Date.class){
	                            DateFormat to_type   = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                            returnValue=to_type.format(value);
	                        }
	                        else{
	                            returnValue=String.valueOf(value);
	                        }
	                    }else{
	                        returnValue="";
	                    }
	                    cell.setCellValue(returnValue);
	                    cellIndex++;
	                }
	                rowIndex++;
	            }
	            fOut = response.getOutputStream();
	            workbook.write(fOut);
	        }
	        catch (UnsupportedEncodingException e1)
	        {}
	        catch (Exception e)
	        {}
	        finally
	        {
	            try
	            {
	                fOut.flush();
	                fOut.close();
	            }
	            catch (IOException e)
	            {}
	            session.setAttribute("state", "open");
	        }
	        System.out.println("文件生成...");
	    }
}
