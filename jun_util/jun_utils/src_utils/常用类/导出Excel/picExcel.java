	public ActionForward picExcel(ActionMapping mapping,ActionForm form,
		HttpServletRequest request,HttpServletResponse response)throws Exception {
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		ServletContext context = this.getServlet().getServletContext();
		try {
			String path = context.getRealPath("/reports/hab/apsfa/PicStat.xls");//导出的Excel文件在服务器上的输出路径
			java.io.File reportFile1 =
				new File(context.getRealPath("/reports/hab/apsfa/statPic.xls"));//此Excel文件是服务器上的，一个包含一张统计图片的Excel模板
			java.io.FileInputStream fos = new FileInputStream(reportFile1);//文件输入流
			java.io.FileOutputStream os = new FileOutputStream(path);//文件输出流

			String barName =
				FileHelper.getUploadDir() + "/" + "collentResult2.jpg";//要导出到Excel文件中的图片路径

			org.apache.poi.poifs.filesystem.POIFSFileSystem fs = new POIFSFileSystem(fos);
			org.apache.poi.hssf.usermodel.HSSFWorkbook wb = new HSSFWorkbook(fs);
			org.apache.poi.hssf.usermodel.HSSFSheet sheet = wb.getSheetAt(0);//Excel文件中的第一个工作表
			org.apache.poi.hssf.usermodel.HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			org.apache.poi.hssf.usermodel.HSSFClientAnchor anchor =new HSSFClientAnchor(
					0,0,350,100,(short) 0,1,(short) 10,10);

			HkSfaStatBC bc = new HkSfaStatBCImpl();//后台实现类 --结果统计
			HkSfaActivityBC abc = new HkSfaActivityBCImpl();//后台实现类 --审计活动
			String activitypk = request.getParameter("activitypk");//活动PK
			List childGroups = abc.getChildgroup(activitypk);//得到此活动的所有小组

			double[][] data = bc.problemCollect(activitypk);//得到统计结果，一个二维数组

			org.apache.poi.hssf.usermodel.HSSFRow row = sheet.createRow(11);//在第一个工作表中创建一行（第12行）
			for (int k = 0; k < childGroups.size(); k++) {
				ApSfaGroupactivity activityGroup =(ApSfaGroupactivity) childGroups.get(k);//ApSfaGroupactivity为活动小组的Pojo
				org.apache.poi.hssf.usermodel.HSSFCell cell = row.createCell((short) (k + 1));//在row中创建单元格
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);//设置编码
				cell.setCellValue(activityGroup.getName());//给单元格设置值
			}

			/* 同前一个循环，给Excel添加数据 */
			String[] strs ={ "不适用", "符合", "整改关闭", "不符合", "未检查", "检查数量", "已选择总数" };
			for (int i = 12; i < strs.length + 12; i++) {
				org.apache.poi.hssf.usermodel.HSSFRow row1 = sheet.createRow(i);
				for (int j = 0; j < data[0].length + 1; j++) {
					org.apache.poi.hssf.usermodel.HSSFCell cell = row1.createCell((short) j);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					if (j == 0) {
						cell.setCellValue(strs[i - 12]);
					} else {
						int a = (int) data[i - 12][j - 1];
						cell.setCellValue(a);
					}
				}
			}

			java.io.File jpgfile = new File(barName);//barName 为统计图片在服务器上的路径
			org.apache.commons.io.output.ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();//字节输出流，用来写二进制文件
			java.awt.image.BufferedImage bufferImg = ImageIO.read(jpgfile);
			javax.imageio.ImageIO.write(bufferImg, "jpg", byteArrayOut);

			patriarch.createPicture(anchor,wb.addPicture(
					byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));//将统计图片添加到Excel文件中
			wb.write(os);
			os.close();
			net.sf.excelutils.ExcelUtils.addValue("actionServlet", this);
			String config = null;

			config = "/reports/hab/apsfa/PicStat.xls";
			response.setHeader("Content-Disposition","attachment; filename=\"" + "PicStat.xls\"");
			net.sf.excelutils.ExcelUtils.export(getServlet().getServletContext(),config,response.getOutputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}