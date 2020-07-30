package com.gitee.docx2pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;

/**
 * 文档工具类
 * 
 * <pre>
 * <!-- docx转pdf -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-scratchpad</artifactId>
            <version>3.11</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>ooxml-schemas</artifactId>
            <version>1.1</version>
        </dependency>
        <dependency>
            <groupId>com.itextpdf</groupId>
            <artifactId>itextpdf</artifactId>
            <version>5.4.3</version>
        </dependency>
        
        <dependency>
            <groupId>fr.opensagres.xdocreport</groupId>
            <artifactId>org.apache.poi.xwpf.converter.pdf</artifactId>
            <version>1.0.6</version>
        </dependency>
        <dependency>
            <groupId>fr.opensagres.xdocreport</groupId>
            <artifactId>org.apache.poi.xwpf.converter.xhtml</artifactId>
            <version>1.0.6</version>
        </dependency>
        <dependency>
            <groupId>org.docx4j</groupId>
            <artifactId>docx4j-ImportXHTML</artifactId>
            <version>3.2.0</version>
        </dependency>
        <!-- docx转pdf end -->
 * </pre>
 */
public class DocUtil {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        String docx = "1.docx";
        String pdf = "1.pdf";
        
        // 直接转换
        InputStream docxStream = DocUtil.class.getClassLoader().getResourceAsStream(docx);
        byte[] pdfData = docxToPdf(docxStream);
        FileUtils.writeByteArrayToFile(new File(pdf), pdfData);
        
        // 替换内容后转换例子
        InputStream docxStream2 = DocUtil.class.getClassLoader().getResourceAsStream("2.docx");
        Map<String, String> data = new HashMap<String, String>();
        data.put("{title}", "标题内容");
        data.put("{username}", "张三");
        byte[] pdfData2 = bindDocxDataAndToPdf(docxStream2, data);
        FileUtils.writeByteArrayToFile(new File("data.pdf"), pdfData2);
        
        System.out.println("finished.");
    }

    /**
     * 替换docx文件内容,并转换成PDF
     * 
     * @param input
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] bindDocxDataAndToPdf(InputStream input, Map<String, String> data) throws Exception {
        byte[] replacedContent = replaceDocxContent(input, data);
        byte[] pdfData = docxToPdf(new ByteArrayInputStream(replacedContent));
        return pdfData;
    }

    /**
     * docx转成pdf
     * 
     * @param docxStream
     *            docx文件流
     * @return 返回pdf数据
     * @throws Exception
     */
    public static byte[] docxToPdf(InputStream docxStream) throws Exception {
        ByteArrayOutputStream targetStream = null;
        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(docxStream);

            PdfOptions options = PdfOptions.create();
            // 中文字体处理
            options.fontProvider(new IFontProvider() {

                @Override
                public Font getFont(String familyName, String encoding, float size, int style, java.awt.Color color) {
                    try {
                        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                        Font fontChinese = new Font(bfChinese, 12, style, color);
                        if (familyName != null)
                            fontChinese.setFamily(familyName);
                        return fontChinese;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            });

            targetStream = new ByteArrayOutputStream();
            PdfConverter.getInstance().convert(doc, targetStream, options);

            return targetStream.toByteArray();
        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            IOUtils.closeQuietly(targetStream);
        }
    }

    /**
     * docx转换成html内容
     * 
     * @param docxIn
     *            docx文件输入流
     * @return
     * @throws Exception
     */
    public static byte[] docxToHtml(InputStream docxIn) throws Exception {
        ByteArrayOutputStream out = null;
        try {
            XWPFDocument document = new XWPFDocument(docxIn);

            XHTMLOptions options = XHTMLOptions.create();

            out = new ByteArrayOutputStream();
            XHTMLConverter.getInstance().convert(document, out, options);

            return out.toByteArray();
        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 替换docx内容
     * 
     * @param in
     *            docx输入流
     * @param map
     *            替换键值对
     * @return 返回替换后的文件流
     * @throws Exception
     */
    public static byte[] replaceDocxContent(InputStream in, Map<String, String> map) throws Exception {
        // 读取word模板
        XWPFDocument hdt = null;
        ByteArrayOutputStream out = null;
        try {
            hdt = new XWPFDocument(in);
            // 替换段落内容
            List<XWPFParagraph> paragraphs = hdt.getParagraphs();
            replaceParagraphsContent(paragraphs, map);

            // 替换表格内容
            List<XWPFTable> tables = hdt.getTables();
            // 读取表格
            for (XWPFTable table : tables) {
                int rcount = table.getNumberOfRows();
                // 遍历表格中的行
                for (int i = 0; i < rcount; i++) {
                    XWPFTableRow row = table.getRow(i);
                    // 遍历行中的单元格
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        List<XWPFParagraph> cellParagraphs = cell.getParagraphs();
                        replaceParagraphsContent(cellParagraphs, map);
                    }
                }
            }

            out = new ByteArrayOutputStream();

            hdt.write(out);

            return out.toByteArray();
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        } finally {
            IOUtils.closeQuietly(out);
        }

    }

    private static void replaceParagraphsContent(List<XWPFParagraph> paragraphs, Map<String, String> map) {
        for (XWPFParagraph paragraph : paragraphs) {
            List<XWPFRun> runs = paragraph.getRuns();
            for (XWPFRun run : runs) {
                String text = run.getText(0);
                if (text != null) {
                    boolean isSetText = false;
                    for (Entry<String, String> entry : map.entrySet()) {
                        String key = entry.getKey();
                        if (text.indexOf(key) != -1) {// 在配置文件中有这个关键字对应的键
                            String value = entry.getValue();
                            if (value == null) {
                                throw new RuntimeException(key + "对应的值不能为null");
                            }
                            // 文本替换
                            text = text.replace(key, value);
                            isSetText = true;
                        }
                    }
                    if (isSetText) {
                        run.setText(text, 0);
                    }
                }
            }
        }
    }
}