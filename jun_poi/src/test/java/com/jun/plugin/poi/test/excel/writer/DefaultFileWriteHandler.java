package com.jun.plugin.poi.test.excel.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.jun.plugin.poi.test.excel.vo.CellKV;
import com.jun.plugin.poi.test.excel.vo.ListLine;
import com.jun.plugin.poi.test.excel.writer.exception.ExcelOutException;

/**
 * 不是线程安全的
 * @author shizhongtao
 *
 */
public class DefaultFileWriteHandler extends AbstractWriteHandler {
	
	private transient OutputStream os;
	
	

	 DefaultFileWriteHandler(Workbook wb, File file)
			throws FileNotFoundException {
		super(wb,new FileOutputStream(file));
		this.os=super.os;
	}

	 DefaultFileWriteHandler(Workbook wb, String path) {
		super(wb, path);
		this.os=super.os;
	}

	
	

	@Override
	public void flush() {
		try {
			if (os != null) {
				super.flush();
				this.os.close();
			}
		} catch (IOException e) {
			throw new ExcelOutException("Happen exception when flush", e);
		}
	}
}
