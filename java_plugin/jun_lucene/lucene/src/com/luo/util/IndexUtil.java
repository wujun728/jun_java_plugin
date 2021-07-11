package com.luo.util;

import java.io.File;
import java.io.IOException;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import com.luo.pojo.Message;
import com.luo.vo.IndexField;

public class IndexUtil {

	public static IndexField message2IndexField(Message message) {
		IndexField indexField=new IndexField();
		indexField.setId(String.valueOf(message.getId()));
		indexField.setTitle(message.getTitle());
		indexField.setContent(message.getContent());
		indexField.setAddTime(message.getAddTime());
		try {
			File f=new File(message.getAttachUrl());
			if(f.exists()){
				indexField.setSummary(new Tika().parseToString(f));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		}
		return indexField;
	}

}
