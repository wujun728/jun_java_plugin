package com.bing.excel.core.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import com.bing.excel.core.BingExcel;
import com.bing.excel.core.ReaderCondition;
import com.bing.excel.core.handler.ConverterHandler;
import com.bing.excel.core.handler.LocalConverterHandler;
import com.bing.excel.core.reflect.TypeAdapterConverter;
import com.bing.excel.exception.IllegalEntityException;
import com.bing.excel.mapper.AnnotationMapper;
import com.bing.excel.mapper.ExcelConverterMapperHandler;
import com.bing.excel.reader.AbstractExcelReadListener;
import com.bing.excel.reader.ExcelReaderFactory;
import com.bing.excel.reader.ReadHandler;
import com.bing.excel.vo.CellKV;
import com.bing.excel.vo.ListLine;
import com.bing.excel.vo.ListRow;
import com.bing.excel.writer.ExcelWriterFactory;
import com.bing.excel.writer.WriteHandler;
import com.google.common.collect.Lists;

/**
 * 创建时间：2015-12-8上午11:56:30 项目名称：excel
 * 
 * @author shizhongtao
 * @version 1.0
 * @since JDK 1.7 文件名称：BingExcelImpl.java 类说明：
 */
public class BingExcelImpl implements BingExcel {

	/**
	 * model entity Converter,the relationship is sheet-to-entity
	 */
	private final Map<Class<?>, TypeAdapterConverter<?>> typeTokenCache = new ConcurrentHashMap<Class<?>, TypeAdapterConverter<?>>();
	/**
	 * globe filed converter
	 */
	private final ConverterHandler localConverterHandler;
	private final Set<Class<?>> targetTypes = Collections
			.synchronizedSet(new HashSet<Class<?>>());
	private ExcelConverterMapperHandler ormMapper = new AnnotationMapper();
	private List<SheetVo> resultList;

	public BingExcelImpl(ConverterHandler localConverterHandler) {
		this.localConverterHandler = localConverterHandler;
	}

	public BingExcelImpl() {
		this.localConverterHandler = new LocalConverterHandler();
	}

	@Override
	public <T> SheetVo<T> readFile(File file, Class<T> clazz, int startRowNum)
			throws Exception {
		return readFile(file, new ReaderCondition<T>(0, startRowNum, clazz));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> SheetVo<T> readFile(File file, ReaderCondition<T> condition)
			throws Exception {

		ReaderCondition[] arr = new ReaderCondition[] { condition };
		List<SheetVo> list = readFileToList(file, arr);

		return list.size() == 0 ? null : list.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bing.excel.core.ExcelBing#readSheetsToList(java.io.File,
	 * com.bing.excel.core.ReaderCondition[])
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<SheetVo> readFileToList(File file, ReaderCondition[] conditions)
			throws Exception {
		resultList = null;
		BingExcelReaderListener listner = new BingExcelReaderListener(
				conditions);
		ReadHandler handler = ExcelReaderFactory.create(file, listner, true);
		int[] indexArr = new int[conditions.length];
		int minNum = -1;
		for (int i = 0; i < conditions.length; i++) {
			int sheetNum = conditions[i].getSheetIndex();
			indexArr[i] = sheetNum;
			if (minNum == -1) {
				minNum = conditions[i].getEndRow();
			} else if (minNum > conditions[i].getEndRow()) {
				minNum = conditions[i].getEndRow();
			}
		}
		handler.readSheet(indexArr, minNum);
		return this.resultList;
	}

	@Override
	public <T> SheetVo<T> readStream(InputStream stream, Class<T> clazz,
			int startRowNum) throws IOException, SQLException,
			OpenXML4JException, SAXException {
		return readStream(stream, new ReaderCondition<T>(0, startRowNum, clazz));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> SheetVo<T> readStream(InputStream stream,
			ReaderCondition<T> condition) throws IOException, SQLException,
			OpenXML4JException, SAXException {
		ReaderCondition[] arr = new ReaderCondition[] { condition };
		List<SheetVo> list = readStreamToList(stream, arr);
		return list.size() == 0 ? null : list.get(0);
	}

	@Override
	public List<SheetVo> readStreamToList(InputStream stream,
			ReaderCondition[] conditions) throws IOException, SQLException,
			OpenXML4JException, SAXException {
		resultList = null;
		BingExcelReaderListener listner = new BingExcelReaderListener(
				conditions);
		ReadHandler handler = ExcelReaderFactory.create(stream, listner, true);
		int[] indexArr = new int[conditions.length];
		int minNum = 0;
		for (int i = 0; i < conditions.length; i++) {
			int sheetNum = conditions[i].getSheetIndex();
			indexArr[i] = sheetNum;
			if (minNum > conditions[i].getEndRow()) {
				minNum = conditions[i].getEndRow();
			}
		}
		handler.readSheet(indexArr, minNum);
		return this.resultList;
	}

	@Override
	public void writeExcel(File file, Iterable... iterables)
			throws FileNotFoundException {
		WriteHandler handler = ExcelWriterFactory.createXSSF(file);
		writeToExcel(handler, iterables);

	}

	@Override
	public void writeOldExcel(File file, Iterable... iterables)
			throws FileNotFoundException {
		WriteHandler handler = ExcelWriterFactory.createHSSF(file);
		writeToExcel(handler, iterables);
	}

	@Override
	public void writeExcel(String path, Iterable... iterables) {
		WriteHandler handler=ExcelWriterFactory.createXSSF(path);
		writeToExcel(handler, iterables);
	}

	@Override
	public void writeExcel(OutputStream stream, Iterable... iterables)  {
		WriteHandler handler=ExcelWriterFactory.createXSSF(stream);
		writeToExcel(handler, iterables);
	}

	@Override
	public void writeOldExcel(String path, Iterable... iterables) {
		WriteHandler handler = ExcelWriterFactory.createHSSF(path);
		writeToExcel(handler, iterables);

	}

	@Override
	public void writeOldExcel(OutputStream stream, Iterable... iterables) {
		WriteHandler handler = ExcelWriterFactory.createHSSF(stream);
		writeToExcel(handler, iterables);
	}

	private void writeToExcel(WriteHandler handler,Iterable... iterables) {
		for (Iterable list : iterables) {
			boolean isAdd=false;
			TypeAdapterConverter<?> typeAdapter=null;
			for (Object object : list) {
				if (!isAdd) {
					if (object != null) {
						isAdd = true;
						Class clazz=object.getClass();
						ormMapper.processAnnotations(clazz);
						registeAdapter(clazz);
						//create sheet
						handler.createSheet(ormMapper.getModelName(clazz));
						 typeAdapter = typeTokenCache
								.get(clazz);
						List<CellKV<String>> header = typeAdapter.getHeader( ormMapper);
						handler.writeHeader(header);
						ListLine listLine = typeAdapter.marshal(object, ormMapper);
						handler.writeLine(listLine);
					}
					
				}else{
					ListLine listLine = typeAdapter.marshal(object, ormMapper);
					handler.writeLine(listLine);
				}
			}
		}
		handler.flush();
		
	}

	private void registeAdapter(Class type) {

		synchronized (type) {
			if (targetTypes.contains(type)) {
				return;
			}
			try {
				// 转换的类型不可能对应的是基本类型
				if (type.isPrimitive()) {
					return;
				}
				// 目前先不考虑model的接口继承问题 TODO
				if (type.isInterface()
						|| (type.getModifiers() & Modifier.ABSTRACT) > 0) {
					return;
				}
				final Field[] fields = type.getDeclaredFields();
				List<Field> tempConverterFields = new ArrayList<>();
				for (int i = 0; i < fields.length; i++) {
					final Field field = fields[i];

					if (field.isEnumConstant()
							|| (field.getModifiers() & (Modifier.STATIC | Modifier.TRANSIENT)) > 0) {
						continue;
					}
					// 应该不会出现
					if (field.isSynthetic()) {
						continue;
					}
					field.setAccessible(true);
					tempConverterFields.add(field);

				}
				Constructor<?> constructor;
				try {
					constructor = type.getDeclaredConstructor();
				} catch (NoSuchMethodException | SecurityException e) {
					throw new IllegalEntityException(type,
							"Gets the default constructor failed,the Objet must contains a  [no-args&public constructor] ",e);
				}
				TypeAdapterConverter typeAdapterConverter = getTypeAdapterConverter(
						constructor, tempConverterFields);
				typeTokenCache.put(type, typeAdapterConverter);

			} finally {
				targetTypes.add(type);
			}

		}

	}

	private TypeAdapterConverter getTypeAdapterConverter(
			Constructor<?> constructor, List<Field> tempConverterFields) {

		TypeAdapterConverter adConverter = new TypeAdapterConverter<>(
				constructor, tempConverterFields, localConverterHandler);
		return adConverter;
	}

	/**
	 * reade Class
	 * 
	 * @author shizhongtao
	 * 
	 * @date 2016-4-12 Description:
	 */
	private class BingExcelReaderListener extends AbstractExcelReadListener {

		private final ReaderCondition[] conditions;
		private Class tagertClazz = null;
		private int startRow = 0;// start to read from first lines;
		private List<SheetVo> list;
		private SheetVo currentSheetVo;

		public BingExcelReaderListener(ReaderCondition[] conditions) {
			super();
			this.conditions = conditions;
			Class[] arr = new Class[conditions.length];
			for (int i = 0; i < conditions.length; i++) {
				arr[i] = conditions[i].getTargetClazz();
			}
			ormMapper.processAnnotations(arr);
		}

		@Override
		public void optRow(int curRow, ListRow rowList) {
			if (curRow < startRow) {
				return;
			}
			if (tagertClazz != null) {
				TypeAdapterConverter<?> typeAdapter = typeTokenCache
						.get(tagertClazz);
				if (typeAdapter == null) {
					if (targetTypes.contains(tagertClazz)) {
						throw new IllegalEntityException(tagertClazz, "类型定义错误");
					} else {
						throw new NullPointerException("没有对应的适配器，无法转换");
					}
				} else {
					Object object = typeAdapter.unmarshal(rowList, ormMapper);
					currentSheetVo.addObject(object);
				}

			}
		}

		@Override
		public void startSheet(int sheetIndex, String name) {

			tagertClazz = null;
			startRow = 0;
			for (int i = 0; i < conditions.length; i++) {
				if (conditions[i].getSheetIndex() == sheetIndex) {
					tagertClazz = conditions[i].getTargetClazz();
					registeAdapter(tagertClazz);
					startRow = conditions[i].getStartRow();
					currentSheetVo = new SheetVo<>(sheetIndex, name);
					break;
				}
			}
		}

		@Override
		public void endSheet(int sheetIndex, String name) {
			if (currentSheetVo != null) {
				if (list == null) {
					list = Lists.newArrayList();
				}
				list.add(currentSheetVo);
				currentSheetVo = null;
			}
		}

		@Override
		public void endWorkBook() {
			if (list == null) {
				resultList = Collections.EMPTY_LIST;
			} else {
				resultList = this.list;
			}
		}

	}

	public static class SheetVo<E> {
		private int sheetIndex;
		private String sheetName;
		private List<E> list = new ArrayList<>();

		public SheetVo(int sheetIndex, String sheetName) {
			super();
			this.sheetIndex = sheetIndex;
			this.sheetName = sheetName;
		}

		public int getSheetIndex() {
			return sheetIndex;
		}

		public String getSheetName() {
			return sheetName;
		}

		public List<E> getObjectList() {
			return list;
		}

		void addObject(E obj) {
			this.list.add(obj);
		}

	}

}
