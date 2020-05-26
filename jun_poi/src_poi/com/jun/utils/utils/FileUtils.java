package com.jun.utils.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileUtils {
	public static void writeStringToFile(String _str, String _filePath) {
		try {
			File f = new File(_filePath);
			if (f.exists())
			{
				f.delete();
			}
			f.createNewFile();
			OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(f, true), "UTF-8");
			ow.write(_str);
			ow.close();
			// BufferedWriter output = new BufferedWriter(new FileWriter(f));
			// output.write(_str);
			// output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
