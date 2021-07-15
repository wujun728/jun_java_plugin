package com.jun.plugin.utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <title>ʹ��XML�ļ���ȡ�����л��Ķ������</title> <description>�ṩ����Ͷ�ȡ�ķ���</description>
 * 
 * @author ��� <copyright>�廪��ѧ���̿����о�Ժ@2005</copyright>
 * @version 1.0 2005-8-5 16:44:49
 */
public class ObjectToXMLUtil {
	/**
	 * ��java�Ŀ����л��Ķ���(ʵ��Serializable�ӿ�)���л����浽XML�ļ�����,�����һ�α����������л��������ü��Ͻ��з�װ ����ʱ���������ڵĶ���ԭ����XML�ļ�����
	 * 
	 * @param obj
	 *            Ҫ���л��Ŀ����л��Ķ���
	 * @param fileName
	 *            ����ȫ�ı���·�����ļ���
	 * @throws FileNotFoundException
	 *             ָ��λ�õ��ļ�������
	 * @throws IOException
	 *             ���ʱ�����쳣
	 * @throws Exception
	 *             ��������ʱ�쳣
	 */
	public static void objectXmlEncoder(Object obj, String fileName) throws FileNotFoundException, IOException, Exception {
		// ��������ļ�
		File fo = new File(fileName);
		// �ļ�������,�ʹ������ļ�
		if (!fo.exists()) {
			// �ȴ����ļ���Ŀ¼
			String path = fileName.substring(0, fileName.lastIndexOf('.'));
			File pFile = new File(path);
			pFile.mkdirs();
		}
		// �����ļ������
		FileOutputStream fos = new FileOutputStream(fo);
		// ����XML�ļ����������ʵ��
		XMLEncoder encoder = new XMLEncoder(fos);
		// �������л������XML�ļ�
		encoder.writeObject(obj);
		encoder.flush();
		// �ر����л�����
		encoder.close();
		// �ر������
		fos.close();
	}

	/**
	 * ��ȡ��objSourceָ����XML�ļ��е����л�����Ķ���,���صĽ�����List��װ
	 * 
	 * @param objSource
	 *            ��ȫ���ļ�·�����ļ�ȫ��
	 * @return ��XML�ļ����汣��Ķ��󹹳ɵ�List�б�(������һ�����߶�������л�����Ķ���)
	 * @throws FileNotFoundException
	 *             ָ���Ķ����ȡ��Դ������
	 * @throws IOException
	 *             ��ȡ�������
	 * @throws Exception
	 *             ��������ʱ�쳣����
	 */
	public static List objectXmlDecoder(String objSource) throws FileNotFoundException, IOException, Exception {
		List objList = new ArrayList();
		File fin = new File(objSource);
		FileInputStream fis = new FileInputStream(fin);
		XMLDecoder decoder = new XMLDecoder(fis);
		Object obj = null;
		try {
			while ((obj = decoder.readObject()) != null) {
				objList.add(obj);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		fis.close();
		decoder.close();
		return objList;
	}
}