package poi;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public class ReadExcel {
	
	//判断指定的单元格是否是合并单元格  
	private static boolean isMergedRegion(Sheet sheet,int row ,int column) {  
		  int sheetMergeCount = sheet.getNumMergedRegions();  
		  for (int i = 0; i < sheetMergeCount; i++) {  
			CellRangeAddress range = sheet.getMergedRegion(i);  
			int firstColumn = range.getFirstColumn();  
			int lastColumn = range.getLastColumn();  
			int firstRow = range.getFirstRow();  
			int lastRow = range.getLastRow();  
			if(row >= firstRow && row <= lastRow){  
				if(column >= firstColumn && column <= lastColumn){  
					return true;  
				}  
			}  
		  }  
		  	return false;  
		}  
	// 获取合并单元格的值 
	public static CellRegion getMergedRegionValue(Sheet sheet ,int row , int column){   
		CellRegion r=new CellRegion();
	    int sheetMergeCount = sheet.getNumMergedRegions();    
	        
	    for(int i = 0 ; i < sheetMergeCount ; i++){    
	        CellRangeAddress ca = sheet.getMergedRegion(i);    
	        int firstColumn = ca.getFirstColumn();    
	        int lastColumn = ca.getLastColumn();    
	        int firstRow = ca.getFirstRow();    
	        int lastRow = ca.getLastRow();    
	            
	        if(row >= firstRow && row <= lastRow){    
	                
	            if(column >= firstColumn && column <= lastColumn){    
	                Row fRow = sheet.getRow(firstRow);    
	                Cell fCell = fRow.getCell(firstColumn); 
	                r.startrownum=firstRow;
	                r.endrownum=lastRow;
	                r.value=getCellValue(fCell);
	                return r;    
	            }    
	        }    
	    }    
	        
	    return null ;    
	}    
	
	
	
	/**   
	* 获取单元格的值   
	* @param cell   
	* @return   
	*/    
	public static String getCellValue(Cell cell){    
		
	    if(cell == null) 
	    	return "";    
	        
	    if(cell.getCellType() == Cell.CELL_TYPE_STRING){    
	            
	        return cell.getStringCellValue();    
	            
	    }else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){    
	            
	        return String.valueOf(cell.getBooleanCellValue());    
	            
	    }else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){    
	            
	        return String.valueOf(cell.getNumericCellValue()) ;    
	            
	    }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){    
	            
	        return String.valueOf(cell.getNumericCellValue());    
	            
	    }    
	    return "";    
	}    
	
	//求单元格或者合并单元格的高度
	public static int getheight(Sheet sheet,int rownum,int colnum){
		if(isMergedRegion(sheet, rownum, colnum)){
			CellRegion r=getMergedRegionValue(sheet, rownum, colnum);
			return r.endrownum-r.startrownum+1;
		}else
			return 1;
	}
	//获取合并或未合并的任意单元格
	public static String getvalue(Sheet sheet,int rownum,int colnum){
		if(isMergedRegion(sheet,rownum,colnum)){
			CellRegion c=getMergedRegionValue(sheet,rownum,colnum);
			return c.value;
		}else{
			Row row=sheet.getRow(rownum);
			Cell cell=row.getCell(colnum);
			return getCellValue(cell);
		}
	}
	
	public static void main(String[] args) {
		 HSSFWorkbook wb = null;
         POIFSFileSystem fs = null;
         try {
                 //设置要读取的文件路径
                 fs = new POIFSFileSystem(new FileInputStream("d:\\table.xls"));
                 //HSSFWorkbook相当于一个excel文件，HSSFWorkbook是解析excel2007之前的版本（xls）
                 //之后版本使用XSSFWorkbook（xlsx）
                 wb = new HSSFWorkbook(fs);
                 //获得sheet工作簿
                 HSSFSheet sheet = wb.getSheetAt(0);
                 //获得行
                 HSSFRow row = sheet.getRow(6);
                 CellRegion r=new CellRegion();
//                 System.out.println(sheet.getLastRowNum());
//                 r = getMergedRegionValue(sheet, 78,26);  
//                 if(r!=null)
            	 System.out.print(r.value+"  ");
                System.out.println(getvalue(sheet, 6,1));
         } catch (Exception e) {
                 e.printStackTrace();
         }
	}
}
