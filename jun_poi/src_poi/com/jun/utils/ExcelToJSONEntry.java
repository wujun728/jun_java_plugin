package com.jun.utils;

public final class ExcelToJSONEntry {

    public static void main(String[] args) {
        ExcelToJson excelJson = new ExcelToJson();
        System.out.println("");
        System.out.println("=======================");
        System.out.println("EXCEL 2 JSON");
        System.out.println("");
        System.out.println("Powered by  Eran");
        System.out.println("=======================");
        System.out.println("");
        if (args.length == 2) {
            excelJson.execute(args[0], args[1]);
        } else {
            System.out.println("HOW TO USE :");
            System.out.println("you should provide 2 arguments , first one specify the excel file path , the second one specify the json file you want to export");
            System.out.println("");
            System.out.println("like: ");
            System.out.println("java -jar excel2json.jar C:\\example.xls E:\\out\\example.json");
            System.out.println("");
        }
    }

}
