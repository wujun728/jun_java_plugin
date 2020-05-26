package com.jun.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.google.gson.Gson;
import com.jun.utils.utils.ExcelUtils;
import com.jun.utils.utils.FileUtils;

public final class ExcelToJson {

    private HashMap<String, Object> jsonMap;

    public void execute(String _excelFilePath, String _outputJsonPath1) {

        // ====================
        // ==读取Excel
        // ====================
        HSSFWorkbook wb = null;
        POIFSFileSystem fs;
        try {
            fs = new POIFSFileSystem(new FileInputStream(_excelFilePath));
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        jsonMap = new HashMap();
        int sheetNum = wb.getNumberOfSheets();
        for (int i = 0; i < sheetNum; i++) {
            HSSFSheet sheet = wb.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            if (sheetName.startsWith("#")) {
                System.out.println("IGNORE SHEET :" + sheetName);
            } else if (sheetName.startsWith("$")) {
                readSheetAsKeyValue(sheet);
            } else {
                readSheetAsNormal(sheet);
            }
        }
        // ==============
        // ==生成JSON
        // ==============
        Gson gson = new Gson();
        String json = gson.toJson(jsonMap);
        FileUtils.writeStringToFile(json, _outputJsonPath1);
        System.out.println("Done, Save file to : " + _outputJsonPath1 + "\n");
        System.out.println("Enjoy.!\n");
    }

    private void readSheetAsNormal(HSSFSheet _sheet) {
        String sheetName = _sheet.getSheetName();
        ArrayList sheetArray = new ArrayList();

        int currentRowNum = 1;

        // ==============
        // ==读取整个表格
        // ==============
        while (true) {
            if (_sheet.getRow(currentRowNum) != null) {
                int currentCellNum = 0;
                HashMap rowMap = new HashMap();
                sheetArray.add(rowMap);

                // ================
                // ==读取一行信息
                // ================
                while (true) {
                    HSSFCell keyCell = _sheet.getRow(0).getCell(currentCellNum);
                    if (keyCell != null) {
                        if (keyCell.getStringCellValue().startsWith("#")) {
                            // 设置IGNORE忽略
                            currentCellNum++;
                            continue;
                        } else {
                            HSSFCell valueCell = _sheet.getRow(currentRowNum).getCell(currentCellNum);
                            ExcelUtils.setHashMapValue(keyCell, valueCell, rowMap);
                            currentCellNum++;
                        }
                    } else {
                        break;
                    }
                }
                // ======================

            } else {
                break;
            }
            currentRowNum++;
        }
        // ======================

        if (!sheetArray.isEmpty()) {
            jsonMap.put(sheetName, sheetArray);
        }
    }

    /**
     * 如果Sheet签以 " $ " 符号开头,则认为该Sheet标签下的值都为Key-Value格式 注意!! 1·Key值不能重复
     * 2·Value值仅能为一个
     */
    private void readSheetAsKeyValue(HSSFSheet _sheet) {
        String sheetName = _sheet.getSheetName().substring(1);
        HashMap<String, String> sheetMap = new HashMap<String, String>();

        int currentRowNum = 1;
        while (true) {
            if (_sheet.getRow(currentRowNum) != null) {
                // 注意!! key值不能重复
                HSSFCell keyCell = _sheet.getRow(currentRowNum).getCell(0);
                HSSFCell valueCell = _sheet.getRow(currentRowNum).getCell(1);
                ExcelUtils.setHashMapValue(keyCell, valueCell, sheetMap);
                // sheetMap.put(keyCell.getStringCellValue(),
                // valueCell.getCellFormula());
                currentRowNum++;
            } else {
                break;
            }
        }
        if (!sheetMap.isEmpty()) {
            jsonMap.put(sheetName, sheetMap);
        }
    }
}
