package com.bzu.csh;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts2.ServletActionContext;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.opensymphony.xwork2.Action;

public class downloadAction implements Action {

	private String downType;

	public String getDownType() {
		return downType;
	}

	public void setDownType(String downType) {
		this.downType = downType;
	}

	public String execute() {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		//HttpServletResponse response = ServletActionContext.getResponse();
		//此处模拟数据库读出的数据。在真正的项目中。我们可以通过在session中保存的前端数据集合替换这里
		List<Person> list = new ArrayList<Person>();
		for (int i = 1; i < 6; i++) {
			Person person = new Person();
			person.setId(String.valueOf(i));
			person.setName(String.valueOf((char) (i + 64)));
			person.setAge(i + 20);
			person.setSex("man");
			list.add(person);
		}
		OutputStream os = null;
		String fname = "personlist";
		if ("PDF".equals(downType)) {
			try {
			//	response.reset();
			//	os = response.getOutputStream();
				FileOutputStream out = new FileOutputStream("d://a.pdf");
				Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			//	response.setContentType("application/pdf");
			//	response.setHeader("Content-disposition",
			//			"attachment;filename=" + fname + ".pdf");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				PdfWriter.getInstance(document, out);
				document.open();
				int cols = list.size();
				// 创建PDF表格
				PdfPTable table = new PdfPTable(4);
				// 设置pdf表格的宽度
				table.setTotalWidth(500);
				// 设置是否要固定其宽度
				table.setLockedWidth(true);
				// 表头字体
				Font thfont = new Font();
				// 设置表头字体的大小
				thfont.setSize(7);
				// 设置表头字体的样式
				thfont.setStyle(Font.BOLD);
				Font tdfont = new Font();
				tdfont.setSize(7);
				tdfont.setStyle(Font.NORMAL);
				// 设置水平对齐方式
				table.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				// 设置table的header
				table.addCell(new Paragraph("id", thfont));
				table.addCell(new Paragraph("name", thfont));
				table.addCell(new Paragraph("sex", thfont));
				table.addCell(new Paragraph("age", thfont));
				// 循环设置table的每一行
				for (int i = 0; i < list.size(); i++) {
					Person p = (Person) list.get(i);
					table.addCell(new Paragraph(p.getId(), tdfont));
					table.addCell(new Paragraph(p.getName(), tdfont));
					table.addCell(new Paragraph(p.getSex(), tdfont));
					table.addCell(new Paragraph(String.valueOf(p.getAge()),
							tdfont));
				}
				document.add(table);
				document.close();
			//	baos.writeTo(response.getOutputStream());
				baos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("CSV".equals(downType)) {
		//	response.reset();
			// 生成csv文件
			//response.setHeader("Content-disposition", "attachment;filename="
			//		+ fname + ".csv");
			//response.setContentType("text/csv");
			//response.setCharacterEncoding("UTF-8");
			FileOutputStream out ;
			String sep = ",";
			try {
				out = new FileOutputStream(new File("d://a.csv"));
				//out = response.getOutputStream();
				out.write("id".getBytes());
				out.write(sep.getBytes());
				out.write("name".getBytes());
				out.write(sep.getBytes());
				out.write("sex".getBytes());
				out.write(sep.getBytes());
				out.write("age".getBytes());
				out.write(sep.getBytes());
				out.write(System.getProperty("line.separator").getBytes());
				for (int i = 0; i < list.size(); i++) {
					Person p = (Person) list.get(i);
					out.write(p.getId().getBytes());
					out.write((sep + "/t").getBytes());
					out.write(p.getName().getBytes());
					out.write((sep + "/t").getBytes());
					out.write(p.getSex().getBytes());
					out.write((sep + "/t").getBytes());
					out.write(String.valueOf(p.getAge()).getBytes());
					out.write((sep + "/t").getBytes());
					out.write(sep.getBytes());
					out.write(System.getProperty("line.separator").getBytes());
				}
				out.flush();
				//out.cloute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (downType.equals("Excel")) {
			//response.reset();
			// 生成xls文件
			//response.setContentType("application/vnd.ms-excel");
			//response.setHeader("Content-disposition", "attachment;filename="
			//		+ fname + ".xls");
			try {
				//os = response.getOutputStream();
				Label l = null;
				WritableWorkbook wbook = Workbook.createWorkbook(new File(
						"d://a.xls"));
				// 写sheet名称
				WritableSheet sheet = wbook.createSheet("my excel file", 0);
				jxl.write.WritableFont wfc4 = new jxl.write.WritableFont(
						WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
						jxl.format.UnderlineStyle.NO_UNDERLINE,
						jxl.format.Colour.BLACK);
				jxl.write.WritableCellFormat wcfFC4 = new jxl.write.WritableCellFormat(
						wfc4);
				wcfFC4.setBackground(jxl.format.Colour.LIGHT_GREEN);
				int col = 0;
				sheet.setColumnView(col, 12);
				l = new Label(col, 0, "id", wcfFC4);
				sheet.addCell(l);
				col++;
				sheet.setColumnView(col, 12);
				l = new Label(col, 0, "name", wcfFC4);
				sheet.addCell(l);
				col++;
				sheet.setColumnView(col, 12);
				l = new Label(col, 0, "sex", wcfFC4);
				sheet.addCell(l);
				col++;
				sheet.setColumnView(col, 12);
				l = new Label(col, 0, "age", wcfFC4);
				sheet.addCell(l);

				// 设置字体样式
				jxl.write.WritableFont wfc5 = new jxl.write.WritableFont(
						WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
						jxl.format.UnderlineStyle.NO_UNDERLINE,
						jxl.format.Colour.BLACK);
				jxl.write.WritableCellFormat wcfFC5 = new jxl.write.WritableCellFormat(
						wfc5);
				for (int i = 0; i < list.size(); i++) {
					Person p = (Person) list.get(i);
					int j = 0;
					l = new Label(j, i + 1, p.getId(), wcfFC5);
					sheet.addCell(l);
					j++;
					l = new Label(j, i + 1, p.getName(), wcfFC5);
					sheet.addCell(l);
					j++;
					l = new Label(j, i + 1, p.getSex(), wcfFC5);
					sheet.addCell(l);
					j++;
					l = new Label(j, i + 1, String.valueOf(p.getAge()), wcfFC5);
					sheet.addCell(l);
					j++;
				}
				// 写入流中
				wbook.write();
				wbook.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
}