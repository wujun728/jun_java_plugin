# czy-nexus-commons-utils
   是发布到 [search.maven](https://search.maven.org/)  、 [mvnrepository](https://mvnrepository.com/)公共仓库的管理库
        
   maven 使用：

        <dependency>        
            <groupId>com.github.andyczy</groupId>       
            <artifactId>java-excel-utils</artifactId>       
            <version>3.1</version>      
        </dependency> 
        
        
   [版本-2.0之前教程](https://github.com/andyczy/czy-nexus-commons-utils/blob/master/README.md)   
         
              
## 版本 3 说明
    1、导出函数式编程换成对象编程。             
    2、可保存到指定本地路径。        
    3、保存2.0版本之前的 exportForExcel(...) 函数[2.0之前版本]。
    4、新增 exportForExcelsOptimize()  函数[版本3系]。
        
    
      
### 版本 3 -- 导出配置 ExcelUtils.exportForExcelsOptimize()
        ExcelPojo excelPojo = new ExcelPojo();
        // 必填项--导出数据
        excelPojo.setDataLists(lists);  
        // 必填项--sheet名称
        excelPojo.setSheetName(sheetNameList);
        // 文件名称(可为空，默认是：sheet 第一个名称)
        excelPojo.setFileName(excelName);
        
        // 输出流：response 响应（输出流：必须选一、可两个都设置）
        excelPojo.setResponse(response);
        // 输出流：可直接输出本地路径（输出流：必须选一、可两个都设置）
        // excelPojo.setFilePath("F:\\test.xlsx"); 
 
        // 每个表格的大标题（可为空）
        excelPojo.setLabelName(labelName);
        // 自定义：固定表头（可为空）
        excelPojo.setPaneMap(setPaneMap);
        // 自定义：单元格合并（可为空）
        excelPojo.setRegionMap(regionMap);
        
        // 自定义：对每个单元格自定义列宽（可为空）
        excelPojo.setColumnMap(mapColumnWidth);
        // 自定义：某一行样式（可为空）
        excelPojo.setRowStyles(stylesRow);
        // 自定义：某一列样式（可为空）
        excelPojo.setColumnStyles(columnStyles);
        // 自定义：每一个单元格样式（可为空）
        excelPojo.setStyles(styles);
                
        // 自定义：对每个单元格自定义下拉列表（可为空）
        excelPojo.setDropDownMap(dropDownMap);
        // 自定义：忽略边框(可为空：默认是有边框)
        excelPojo.setNotBorderMap(notBorderMap);       
         
        // 执行导出
        ExcelUtils excelUtils = ExcelUtils.setExcelUtils(excelPojo);
        excelUtils.exportForExcelsOptimize();       
        
### 2.0之前版本 -- 导出配置 ExcelUtils.exportForExcel(...)
        * 可提供模板下载           
        * 自定义下拉列表：对每个单元格自定义下拉列表         
        * 自定义列宽：对每个单元格自定义列宽         
        * 自定义样式：对每个单元格自定义样式  
        * 自定义样式：单元格自定义某一列或者某一行样式            
        * 自定义单元格合并：对每个单元格合并 
        * 自定义：每个表格的大标题          
        * 自定义：对每个单元格固定表头    
        
        
### 导入配置 ExcelUtils.importForExcelData(...)
        * 获取多单元数据         
        * 自定义：多单元从第几行开始获取数据            
        * 自定义：多单元根据那些列为空来忽略行数据         

  
        
### 数据格式
   [javadoc 文档](https://oss.sonatype.org/service/local/repositories/releases/archive/com/github/andyczy/java-excel-utils/2.0.1/java-excel-utils-2.0.1-javadoc.jar/!/com/github/andyczy/java/excel/ExcelUtils.html)

   所有参数：
   
         * @param response
         * @param dataLists    导出的数据(不可为空：如果只有标题就导出模板)
         * @param sheetName    sheet名称（不可为空）
         * @param columnMap    自定义：对每个单元格自定义列宽（可为空）
         * @param dropDownMap  自定义：对每个单元格自定义下拉列表（可为空）
         * @param styles       自定义：每一个单元格样式（可为空）
         * @param rowStyles    自定义：某一行样式（可为空）
         * @param columnStyles 自定义：某一列样式（可为空）
         * @param regionMap    自定义：单元格合并（可为空）
         * @param paneMap      自定义：固定表头（可为空）
         * @param labelName    自定义：每个表格的大标题（可为空）
         * @param fileName     文件名称(可为空，默认是：sheet 第一个名称)
         * @param notBorderMap 自定义：忽略边框(可为空，默认是有边框)
             
   controller：伪代码
    
        @RequestMapping(value = "/exportBill")
        @ResponseBody
        public void exportBill(HttpServletResponse response){
            ExcelUtils.exportForExcel(response,dataLists,notBorderMap,regionMap,columnMap,styles,paneMap,fileName,
                                      sheetName,labelName,rowStyles,columnStyles,dropDownMap);
        }
    
   导出数据：参数 dataLists
   
        @Override
           public List<List<String[]>> exportBill(String deviceNo,String snExt,Integer parentInstId,String startDate, String endDate){
               List<List<String[]>> listArray = new ArrayList<>();
               List<String[]> stringList = new ArrayList<>();
               PageInfo<BillInfo> pagePageInfo = getBillPage(1,10000,null,snExt,deviceNo,parentInstId,startDate,endDate);
               String[] valueString = null;
               String[] headers = {"序号","标题一","标题二","标题三","标题四","标题五","标题六"};
               stringList.add(headers);
               for (int i = 0; i < pagePageInfo.getList().size(); i++) {
                   valueString = new String[]{(i + 1) + "", pagePageInfo.getList().get(i).getSnExt(),
                           getNeededDateStyle(pagePageInfo.getList().get(i).getPayTime(),"yyyy-MM-dd hh:mm:ss"),
                           pagePageInfo.getList().get(i).getInstName(),pagePageInfo.getList().get(i).getStatisticsPrice()+"",
                           pagePageInfo.getList().get(i).getDeviceNo(),
                           pagePageInfo.getList().get(i).getWarning()==1?"是":"否"};
                   stringList.add(valueString);
               }
               listArray.add(stringList);
               return listArray;
           }       
   
   自定义列宽：参数 columnMap
   
       参数说明：
       HashMap<Integer, HashMap<Integer, Integer>> mapColumnWidth = new HashMap<>();
       HashMap<Integer, Integer> mapColumn = new HashMap<>();
       //自定义列宽
       mapColumn.put(0, 3);  //第一列、宽度为3
       mapColumn.put(1, 20);
       mapColumn.put(2, 15);
       //第一个单元格列宽
       mapColumnWidth.put(1, mapColumn);
       
   自定义固定表头：参数 paneMap
   
       参数说明：
       HashMap setPaneMap = new HashMap();
       //第一个表格、第三行开始固定表头
       setPaneMap.put(1, 1); 
       
   
   自定义合并单元格：参数 regionMap
   
        参数说明：
        List<List<Integer[]>> setMergedRegion = new ArrayList<>();
        List<Integer[]> sheet1 = new ArrayList<>();                  //第一个表格设置。
        Integer[] sheetColumn1 = new Integer[]{0, 1, 0, 2}         //代表起始行号，终止行号， 起始列号，终止列号进行合并。（excel从零行开始数）
        setMergedRegion.add(sheet1);
       
   自定义下拉列表值：参数 dropDownMap
   
        参数说明：
        HashMap hashMap = new HashMap();
        List<String[]> sheet1 = new ArrayList<>();                  //第一个表格设置。
        String[] sheetColumn1 = new String[]{"1", "2", "4"};        //必须放第一：设置下拉列表的列（excel从零行开始数）
        String[] sex = {"男,女"};                                   //下拉的值放在 sheetColumn1 后面。
        sheet1.add(sheetColumn1);
        sheet1.add(sex);
        hashMap.put(1,sheet1);                                      //第一个表格的下拉列表值   
        
   自定义每个表格第几行或者是第几列的样式：参数 rowStyles / columnStyles
           
        参数说明：
        HashMap hashMap = new HashMap();
        List list = new ArrayList();
        list.add(new Boolean[]{true, false, false, false, true});                //1、样式（是否居中？，是否右对齐？，是否左对齐？， 是否加粗？，是否有边框？ ）
        list.add(new Integer[]{1, 3});                                           //2、第几行或者是第几列
        list.add(new Integer[]{10,14,null});                                     //3、颜色值（8是黑色、10红色等） 、颜色、字体、行高？（可不设置）
        hashMap.put(1,list);                                                     //第一表格
        
   自定义每一个单元格样式：参数 styles
        
       参数说明：
       HashMap cellStyles = new HashMap();
       List< List<Object[]>> list = new ArrayList<>();
       List<Object[]> objectsList = new ArrayList<>();
       List<Object[]> objectsListTwo = new ArrayList<>();
       objectsList.add(new Boolean[]{true, false, false, false, true});      //1、样式一（是否居中？，是否右对齐？，是否左对齐？， 是否加粗？，是否有边框？ ）
       objectsList.add(new Integer[]{10, 12});                               //1、颜色值 、字体大小、行高（必须放第二）
       objectsList.add(new Integer[]{5, 1});                                 //1、第五行第一列
       objectsList.add(new Integer[]{6, 1});                                 //1、第六行第一列
       
       objectsListTwo.add(new Boolean[]{false, false, false, true, true});   //2、样式二（必须放第一）
       objectsListTwo.add(new Integer[]{10, 12,null});                       //2、颜色值（8是黑色、10红色等） 、颜色、字体、行高？（可不设置）（必须放第二）
       objectsListTwo.add(new Integer[]{2, 1});                              //2、第二行第一列
       
       list.add(objectsList);
       list.add(objectsListTwo);
       cellStyles.put(1, list);                                              //第一个表格所有自定义单元格样式 
             
   
   自定义忽略边框：参数 notBorderMap
   
       HashMap notBorderMap = new HashMap();
       notBorderMap.put(1, new Integer[]{1, 5});   //忽略边框（1行、5行）、默认是数据是全部加边框
   
   导入配置：
        
       @param indexMap 多单元从第几行开始获取数据，默认从第二行开始获取（可为空，如 hashMapIndex.put(1,3); 第一个表格从第三行开始获取）
       @param continueRowMap 多单元根据那些列为空来忽略行数据（可为空，如 mapContinueRow.put(1,new Integer[]{1, 3}); 第一个表格从1、3列为空就忽略）
                    
### License
java-excel-utils is Open Source software released under the Apache 2.0 license.     