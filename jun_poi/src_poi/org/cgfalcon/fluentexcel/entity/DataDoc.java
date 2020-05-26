package org.cgfalcon.fluentexcel.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: falcon.chu
 * Date: 13-5-31
 * Time: 下午3:38
 */

/**
 * 对应模板中的 节点 DOC
 */
public class DataDoc {

    private String docName = "Unknow";
    private String docType = "xlsx";
    private List<DataSheet> sheets;

    /**
     * default path of generated excel
     */
    private String docDir = "/tmp";

    public String getDocDir() {
        return docDir;
    }

    public void setDocDir(String docDir) {
        this.docDir = docDir;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public List<DataSheet> getSheets() {
        return sheets;
    }

    public void setSheets(List<DataSheet> sheets) {
        this.sheets = sheets;
    }

    public void addSheet(DataSheet sheet) {
        if (sheets == null) {
            sheets = new ArrayList<DataSheet>();
        }
        sheets.add(sheet);
    }
}
