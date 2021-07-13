package com.jun.plugin.poi.test.excel.reader.sax;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * @author Wujun
 *
 * @date 2016-1-26
 * Description:  解决读取mac上xlsx结尾的excel文件读取中文问题
 */
public class ExcelReadOnlySharedStringsTable extends ReadOnlySharedStringsTable {

	public ExcelReadOnlySharedStringsTable(OPCPackage pkg) throws IOException,
			SAXException {
		super(pkg);

	}

	public ExcelReadOnlySharedStringsTable(PackagePart part,
			PackageRelationship rel_ignored) throws IOException, SAXException {
		super(part, rel_ignored);

	}

	/**
	 * An integer representing the total count of strings in the workbook. This
	 * count does not include any numbers, it counts only the total of text
	 * strings in the workbook.
	 */
	private int count;

	/**
	 * An integer representing the total count of unique strings in the Shared
	 * String Table. A string is unique even if it is a copy of another string,
	 * but has different formatting applied at the character level.
	 */
	private int uniqueCount;

	/**
	 * The shared strings table.
	 */
	private List<String> strings;

	/**
	 * Read this shared strings table from an XML file.
	 * 
	 * @param is
	 *            The input stream containing the XML document.
	 * @throws IOException
	 *             if an error occurs while reading.
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public void readFrom(InputStream is) throws IOException, SAXException {
		if (is.available() > 0) {
			InputSource sheetSource = new InputSource(is);
			try {
				XMLReader sheetParser = SAXHelper.newXMLReader();
				sheetParser.setContentHandler(this);
				sheetParser.parse(sheetSource);
			} catch (ParserConfigurationException e) {
				throw new RuntimeException("SAX parser appears to be broken - "
						+ e.getMessage());
			}
		}
	}

	/**
	 * Return an integer representing the total count of strings in the
	 * workbook. This count does not include any numbers, it counts only the
	 * total of text strings in the workbook.
	 * 
	 * @return the total count of strings in the workbook
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * Returns an integer representing the total count of unique strings in the
	 * Shared String Table. A string is unique even if it is a copy of another
	 * string, but has different formatting applied at the character level.
	 * 
	 * @return the total count of unique strings in the workbook
	 */
	public int getUniqueCount() {
		return this.uniqueCount;
	}

	/**
	 * Return the string at a given index. Formatting is ignored.
	 * 
	 * @param idx
	 *            index of item to return.
	 * @return the item at the specified position in this Shared String table.
	 */
	public String getEntryAt(int idx) {
		return strings.get(idx);
	}

	public List<String> getItems() {
		return strings;
	}

	// // ContentHandler methods ////

	private StringBuffer characters;
	private boolean rPhIsOpen = false;
	private boolean tIsOpen;

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		if ("sst".equals(name)) {
			String count = attributes.getValue("count");
			if (count != null)
				this.count = Integer.parseInt(count);
			String uniqueCount = attributes.getValue("uniqueCount");
			if (uniqueCount != null)
				this.uniqueCount = Integer.parseInt(uniqueCount);

			this.strings = new ArrayList<String>(this.uniqueCount);

			characters = new StringBuffer();
		} else if ("si".equals(name)) {
			characters.setLength(0);
		} else if ("t".equals(name)) {
			tIsOpen = true;
		} else if ("rPh".equals(name)) {
			rPhIsOpen = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if ("si".equals(name)) {
			strings.add(characters.toString());
		} else if ("t".equals(name)) {
			tIsOpen = false;
		} else if ("rPh".equals(name)) {
			rPhIsOpen = false;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (tIsOpen) {
			if (!rPhIsOpen) {
				characters.append(ch, start, length);
			}
		}
	}

}
