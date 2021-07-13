package com.jun.plugin.poi.test.excel.reader.sax;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.jun.plugin.poi.test.excel.exception.BingSaxReadStopException;
import com.jun.plugin.poi.test.excel.reader.ExcelReadListener;
import com.jun.plugin.poi.test.excel.reader.ReadHandler;
import com.jun.plugin.poi.test.excel.reader.sax.ExcelXSSFSheetXMLHandler.BingSheetContentsHandler;
import com.jun.plugin.poi.test.excel.vo.CellKV;
import com.jun.plugin.poi.test.excel.vo.ListRow;

/**
 * @author Wujun
 * 
 * @date 2016-2-2 Description:
 *       读取07excel的sax方法，解析器默认使用org.apache.xerces.parsers.SAXParser
 *       。可以痛痛set方法手动设置
 */
public class DefaultXSSFSaxHandler implements ReadHandler {
	private OPCPackage pkg;
	private XMLReader parser;
	private ExcelReadListener excelReader;
	private boolean ignoreNumFormat = false;
	private DefaultSheetContentsHandler handler ;
	public DefaultXSSFSaxHandler(String path, ExcelReadListener excelReader)
			throws InvalidFormatException, IOException {
		this(path, excelReader, false);
	}

	public DefaultXSSFSaxHandler(File file, ExcelReadListener excelReader)
			throws InvalidFormatException, IOException {
		this(file, excelReader, false);
	}

	public DefaultXSSFSaxHandler(InputStream in, ExcelReadListener excelReader)
			throws InvalidFormatException, IOException {
		this(in, excelReader, false);
	}

	public DefaultXSSFSaxHandler(String path, ExcelReadListener excelReader,
			boolean ignoreNumFormat) throws InvalidFormatException, IOException {
		
		this(new File(path),excelReader,ignoreNumFormat);
		
	}

	public DefaultXSSFSaxHandler(File file, ExcelReadListener excelReader,
			boolean ignoreNumFormat) throws InvalidFormatException {
		
		
		try {
			this.pkg = OPCPackage.open(file, PackageAccess.READ);
			this.excelReader = excelReader;
			this.ignoreNumFormat = ignoreNumFormat;
			this.handler = new DefaultSheetContentsHandler(
					excelReader);
		} catch (IllegalArgumentException  e) {
			//异常是open方法抛出的。确保io关闭
			pkg.revert();
			throw e;
		}
	}

	public DefaultXSSFSaxHandler(InputStream in, ExcelReadListener excelReader,
			boolean ignoreNumFormat) throws InvalidFormatException, IOException {
		// 不应该调用？异常没有处理
		this(OPCPackage.open(in),excelReader,ignoreNumFormat);
	}
	public DefaultXSSFSaxHandler(OPCPackage pkg, ExcelReadListener excelReader,
			boolean ignoreNumFormat) throws InvalidFormatException {
		
		this.pkg = pkg;
		this.excelReader = excelReader;
		this.ignoreNumFormat = ignoreNumFormat;
		this.handler = new DefaultSheetContentsHandler(
				excelReader);
	}

	@Override
	public void readSheets(int maxReadLine) throws IOException, OpenXML4JException, SAXException {
		setMaxReturnLine(maxReadLine);
		readSheets();
	}
	
	@Override
	public void readSheets() throws IOException, OpenXML4JException, SAXException {
		if (pkg == null) {
			throw new NullPointerException("OPCPackage 对象为空");
		}
		XSSFReader xssfReader;
		XSSFReader.SheetIterator sheets;
		ExcelReadOnlySharedStringsTable strings;
		try {
			xssfReader = new XSSFReader(pkg);
			sheets = (XSSFReader.SheetIterator) xssfReader
					.getSheetsData();
			strings = new ExcelReadOnlySharedStringsTable(
					pkg);
		} catch (IllegalArgumentException e1) {
			pkg.revert();
			throw e1;
		}
		int sheetIndex = 0;
		
		ExcelXSSFSheetXMLHandler sheetXMLHandler = new ExcelXSSFSheetXMLHandler(
				xssfReader.getStylesTable(), strings, handler, false);
		// 是不按照格式化输出字符
		sheetXMLHandler.ignoreNumFormat(ignoreNumFormat);
		getParser().setContentHandler(sheetXMLHandler);
		while (sheets.hasNext()) {
			try (InputStream sheet = sheets.next()) {
				String name = sheets.getSheetName();
				excelReader.startSheet(sheetIndex, name);
				InputSource sheetSource = new InputSource(sheet);
			
				try {
					getParser().parse(sheetSource);
				} catch (SAXException e) {
					if (e instanceof BingSaxReadStopException) {
						
					} else {
						throw e;
					}
				}
				excelReader.endSheet(sheetIndex, name);
				sheetIndex++;
			}
		}
		//Close the package WITHOUT saving its content. Reinitialize this package and cancel all changes done to it.
		pkg.revert();
		excelReader.endWorkBook();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bing.excel.reader.SaxHandler#process(int)
	 */
	@Override
	public void readSheet(int index,int maxReadLine) throws IOException, OpenXML4JException,
			SAXException {
		setMaxReturnLine(maxReadLine);
		readSheet(new int[]{index});
	}
	@Override
	public void readSheet(int index) throws IOException, OpenXML4JException,
	SAXException {
		readSheet(new int[]{index});
	}

	@Override
	public void readSheet(int[] indexs,int maxReadLine) throws IOException, OpenXML4JException,
	SAXException {
		setMaxReturnLine(maxReadLine);
		readSheet(indexs);
	}
	@Override
	public void readSheet(int[] indexs) throws IOException, OpenXML4JException,
			SAXException {
		if (pkg == null) {
			throw new NullPointerException("OPCPackage is null");
		}
		//ImmutableCollection<Integer> sheetSelect=
		ImmutableSet.Builder<Integer> build =ImmutableSet.builder();
		for (int i : indexs) {
			if(i<0){
				throw new IllegalArgumentException("index of sheet is a number greater than 0");
			}else{
				build.add(i);
			}
		}
		ImmutableSet<Integer> setSheets = build.build();
 		XSSFReader xssfReader = new XSSFReader(pkg);
		XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator) xssfReader
				.getSheetsData();
		ExcelReadOnlySharedStringsTable strings = new ExcelReadOnlySharedStringsTable(
				pkg);
		int sheetIndex = 0;
		
		ExcelXSSFSheetXMLHandler sheetXMLHandler = new ExcelXSSFSheetXMLHandler(
				xssfReader.getStylesTable(), strings, handler, false);
		// 是不按照格式化输出字符
		sheetXMLHandler.ignoreNumFormat(ignoreNumFormat);
		getParser().setContentHandler(sheetXMLHandler);
		while (sheets.hasNext()) {
			try (InputStream sheet = sheets.next()) {
				String name = sheets.getSheetName();
				if (!setSheets.contains(sheetIndex)) {
					sheetIndex++;
					continue;
				}
				
				
				excelReader.startSheet(sheetIndex, name);
				InputSource sheetSource = new InputSource(sheet);
				
				try {
					getParser().parse(sheetSource);
				} catch (SAXException e) {
					if (e instanceof BingSaxReadStopException) {
						
					} else {
						throw e;
					}
				}
				excelReader.endSheet(sheetIndex, name);
				sheetIndex++;
			}
		}
		excelReader.endWorkBook();
		pkg.revert();
	}

	@Override
	public void readSheet(String indexName, int maxReadLine)
			throws IOException, OpenXML4JException, SAXException {
		setMaxReturnLine(maxReadLine);
		readSheet(indexName);
	}

	@Override
	public void readSheet(String sheetName) throws IOException, SAXException,
			OpenXML4JException {
		if (pkg == null) {
			throw new NullPointerException("OPCPackage 对象为空");
		}
		XSSFReader xssfReader = new XSSFReader(pkg);
		XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator) xssfReader
				.getSheetsData();
		ExcelReadOnlySharedStringsTable strings = new ExcelReadOnlySharedStringsTable(
				pkg);
		int sheetIndex = 0;
		
		ExcelXSSFSheetXMLHandler sheetXMLHandler = new ExcelXSSFSheetXMLHandler(
				xssfReader.getStylesTable(), strings, handler, false);
		// 是不按照格式化输出字符
		sheetXMLHandler.ignoreNumFormat(ignoreNumFormat);
		getParser().setContentHandler(sheetXMLHandler);
		while (sheets.hasNext()) {
			try (InputStream sheet = sheets.next()) {
				String name = sheets.getSheetName();
				
				if (!name.equals(sheetName)) {
					sheetIndex++;
					continue;
				}
				excelReader.startSheet(sheetIndex, name);
				InputSource sheetSource = new InputSource(sheet);
				try {
					getParser().parse(sheetSource);
				} catch (SAXException e) {
					if (e instanceof BingSaxReadStopException) {
					} else {
						throw e;
					}
				}
				excelReader.endSheet(sheetIndex, name);
				sheetIndex++;
				break;
			}
		}
		excelReader.endWorkBook();
		this.pkg.revert();
	}

	public XMLReader getParser() throws SAXException {
		if (parser == null) {
			parser = XMLReaderFactory
					.createXMLReader("org.apache.xerces.parsers.SAXParser");
		}
		return parser;
	}

	public void setParser(XMLReader parser) {
		this.parser = parser;
	}

	protected void setMaxReturnLine(int num) {
		this.handler.setMaxReadLine(num);
	}

	private static class DefaultSheetContentsHandler implements
			BingSheetContentsHandler {
		private ListRow rowList;
		private ExcelReadListener excelReader;
		private int maxReadLine = Integer.MAX_VALUE;

		public DefaultSheetContentsHandler(ExcelReadListener excelReader) {
			this.excelReader = excelReader;
		}

		/**
		 * Converts an Excel column name like "C" to a zero-based index.
		 * 
		 * @param name
		 * @return Index corresponding to the specified name
		 */
		private int nameToColumn(String name) {
			int firstDigit = -1;
			char[] array = name.toCharArray();
			for (int c = 0; c < array.length; ++c) {
				if (Character.isDigit(name.charAt(c))) {
					firstDigit = c;
					break;
				}
			}

			int column = -1;
			for (int i = 0; i < firstDigit; ++i) {
				int c = array[i];
				column = (column + 1) * 26 + c - 'A';
			}
			return column;
		}

		@Override
		public void startRow(int rowNum) throws BingSaxReadStopException {
			if (rowNum > maxReadLine) {
				throw new BingSaxReadStopException("stop mark");
			}
			rowList = new ListRow();
		}

		@Override
		public void endRow(int rowNum) {

			excelReader.optRow(rowNum, rowList);
		}

		@Override
		public void cell(int rowNum, String cellReference,
				String formattedValue, XSSFComment comment)
				 {
			
			if (!Strings.isNullOrEmpty(formattedValue)) {
				int column = nameToColumn(cellReference);
				rowList.add(new CellKV<String>(column, formattedValue));
			}
			
		}

		@Override
		public void headerFooter(String text, boolean isHeader, String tagName) {
		}

		public void setMaxReadLine(int maxReadLine) {
			if (maxReadLine > 0){
				this.maxReadLine = maxReadLine-1;
			}
		}

	}

	
}
