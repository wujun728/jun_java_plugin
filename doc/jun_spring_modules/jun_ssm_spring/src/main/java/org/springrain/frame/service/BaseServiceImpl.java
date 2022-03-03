package org.springrain.frame.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springrain.frame.common.BaseLogger;
import org.springrain.frame.dao.IBaseJdbcDao;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.ClassUtils;
import org.springrain.frame.util.DateUtils;
import org.springrain.frame.util.EntityInfo;
import org.springrain.frame.util.ExcelUtils;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.OpenOfficeKit;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.SecUtils;
import org.springrain.frame.util.SpringUtils;

import freemarker.template.Template;
import jxl.Cell;

/**
 * 基础的Service父类,所有的Service都必须继承此类,每个数据库都需要一个实现.</br>
 * 例如
 * demo数据的实现类是org.springrain.springrain.service.BasedemoServiceImpl,demo2数据的实现类是org.springrain.demo2.service.Basedemo2ServiceImpl</br>
 * 
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version 2013-03-19 11:08:15
 * @see org.springrain.frame.service.BaseServiceImpl
 */
public abstract class BaseServiceImpl extends BaseLogger implements IBaseService {

	@Resource
	public SpringUtils springUtils;
	// @Resource
	public FreeMarkerConfigurer freeMarkerConfigurer = null;

	@Resource
	private CacheManager cacheManager;

	public abstract IBaseJdbcDao getBaseDao();

	@Override
    public SpringUtils getSpringUtils() {
		return springUtils;
	}

	@Override
	public Object getBean(String beanName) throws Exception {
		if (beanName == null) {
            return null;
        }
		return getSpringUtils().getBean(beanName);
	}

	@Override
	public Object getBean(Class clazz) throws Exception {
		return getSpringUtils().getBeanByType(clazz);
	}

	@Override
	public Cache getCache(String cacheName) throws Exception {
		if (StringUtils.isBlank(cacheName)) {
			return null;
		}
		return cacheManager.getCache(cacheName);
	}

	@Override
	public <T> T getByCache(String cacheName, String key, Class<T> clazz) throws Exception {
		return getCache(cacheName).get(key, clazz);
	}

	@Override
	public void putByCache(String cacheName, String key, Object value) throws Exception {
		getCache(cacheName).put(key, value);
	}

	@Override
	public void cleanCache(String cacheName) throws Exception {
		getCache(cacheName).clear();
	}

	@Override
	public void evictByKey(String cacheName, String key) throws Exception {
		getCache(cacheName).evict(key);
	}

	@Override
	public <T> T getByCache(String cacheName, String key, Class<T> clazz, Page page) throws Exception {

		if (page == null || page.getPageIndex() == null || StringUtils.isBlank(key)) {
			return null;
		}

		String listKey = key + "_" + page.toString();
		T t = getByCache(cacheName, listKey, clazz);

		if (t == null) {
			return t;
		}

		String pageKey = key + GlobalStatic.pageCacheExtKey;
		Integer totalCount = getByCache(cacheName, pageKey, Integer.class);
		if (totalCount != null) {
			page.setTotalCount(totalCount);
		}
		return t;
	}

	@Override
	public void putByCache(String cacheName, String key, Object value, Page page) throws Exception {
		if (page == null || page.getPageIndex() == null || StringUtils.isBlank(key)) {
			return;
		}
		String listKey = key + "_" + page.toString();
		putByCache(cacheName, listKey, value);

		if (page != null) {
			putByCache(cacheName, key + GlobalStatic.pageCacheExtKey, page.getTotalCount());
		}
	}

	@Override
	public void evictByKey(String cacheName, String key, Page page) throws Exception {
		evictByKey(cacheName, key);
		evictByKey(cacheName, key + GlobalStatic.pageCacheExtKey);
	}

	@Override
	public <T> List<T> queryForList(Finder finder, Class<T> clazz) throws Exception {
		return getBaseDao().queryForList(finder, clazz);
	}

	@Override
	public List<Map<String, Object>> queryForList(Finder finder) throws Exception {
		return getBaseDao().queryForList(finder);
	}

	@Override
	public Map<String, Object> queryObjectByFunction(Finder finder) throws Exception {
		return getBaseDao().queryObjectByFunction(finder);
	}

	@Override
	public <T> T findById(Object id, Class<T> clazz, String tableExt) throws Exception {
		return getBaseDao().findByID(id, clazz, tableExt);
	}

	@Override
	public Map<String, Object> queryObjectByProc(Finder finder) throws Exception {
		return getBaseDao().queryObjectByProc(finder);
	}

	@Override
	public List<Map<String, Object>> queryForListByProc(Finder finder) throws Exception {
		return getBaseDao().queryForListByProc(finder);
	}

	@Override
	public List<Map<String, Object>> queryForListByFunction(Finder finder) throws Exception {
		return getBaseDao().queryForListByFunction(finder);
	}

	@Override
	public <T> T queryForObject(Finder finder, Class<T> clazz) throws Exception {

		return getBaseDao().queryForObject(finder, clazz);
	}

	@Override
	public Map<String, Object> queryForObject(Finder finder) throws Exception {
		return getBaseDao().queryForObject(finder);
	}

	@Override
	public Integer update(Finder finder) throws Exception {

		return getBaseDao().update(finder);
	}

	@Override
	public void deleteById(Object id, Class clazz) throws Exception {
		getBaseDao().deleteById(id, clazz);
	}

	@Override
	public void deleteByIds(List ids, Class clazz) throws Exception {
		getBaseDao().deleteByIds(ids, clazz);
	}

	@Override
	public void deleteByEntity(IBaseEntity entity) throws Exception {
		EntityInfo entityInfoByEntity = ClassUtils.getEntityInfoByEntity(entity);
		String tableName = entityInfoByEntity.getTableName();
		String tableExt = entityInfoByEntity.getTableSuffix();
		if (StringUtils.isNotBlank(tableExt)) {
			tableName = tableName + tableExt;
		}

		Finder finder = new Finder("DELETE FROM ");
		finder.append(tableName).append(" WHERE 1=1 ");
		getFinderWhereByQueryBean(finder, entity);
		getBaseDao().update(finder);
	}

	@Override
	public <T> List<T> queryForList(Finder finder, Class<T> clazz, Page page) throws Exception {
		return getBaseDao().queryForList(finder, clazz, page);
	}

	@Override
	public List<Map<String, Object>> queryForList(Finder finder, Page page) throws Exception {
		return getBaseDao().queryForList(finder, page);
	}

	@Override
	public <T> T queryForObject(T entity) throws Exception {
		return getBaseDao().queryForObject(entity);

	}

	@Override
	public <T> List<T> queryForListByEntity(T entity, Page page) throws Exception {
		return getBaseDao().queryForListByEntity(entity, page);

	}

	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz, Object queryBean)
			throws Exception {
		return getBaseDao().findListDataByFinder(finder, page, clazz, queryBean);
	}

	@Override
	public <T> File findDataExportExcel(Finder finder, String ftlurl, Page page, Class<T> clazz, Object queryBean)
			throws Exception {
		Map map = new HashMap();
		return findDataExportExcel(finder, ftlurl, page, clazz, queryBean, map);
	}

	@Override
	public <T> File findDataExportExcel(Finder finder, String ftlurl, Page page, Class<T> clazz, Object queryBean,
			Map map) throws Exception {
		
		return findDataExportFile(finder, ftlurl, page, clazz, queryBean, map,GlobalStatic.excelext);
	}
	
	public <T> File findDataExportFile(Finder finder, String ftlurl, Page page, Class<T> clazz, Object queryBean,
			Map map,String fileType) throws Exception{


		if (freeMarkerConfigurer == null) {
			freeMarkerConfigurer = (FreeMarkerConfigurer) SpringUtils.getBean("freeMarkerConfigurer");
		}

		if (freeMarkerConfigurer == null) {
			return null;
		}

		// Map map = new HashMap();
		ReturnDatas returnDatas = new ReturnDatas();
		map.put(GlobalStatic.exportexcel, true);// 设置导出excel变量
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(ftlurl + GlobalStatic.suffix);
		page.setPageSize(GlobalStatic.excelPageSize);
		page.setPageIndex(1);
		List datas = findListDataByFinder(finder, page, clazz, queryBean);
		returnDatas.setData(datas);
		returnDatas.setPage(page);
		returnDatas.setQueryBean(queryBean);
		returnDatas.setMessage("export");
		map.put(GlobalStatic.returnDatas, returnDatas);
		String fileName = UUID.randomUUID().toString();
		String tempFFilepath = GlobalStatic.tempRootpath + "/" + fileName + "/freemarker.html";
		String tempExcelpath = GlobalStatic.tempRootpath + "/" + fileName + "/" + fileName + fileType;
		File tempfdir = new File(GlobalStatic.tempRootpath + "/" + fileName);
		if (tempfdir.exists() == false) {
			tempfdir.mkdirs();
		}

		File ffile = new File(tempFFilepath);

		File excelFile = new File(tempExcelpath);
		boolean first = true;
		if(ftlurl.contains("Xml")) {
            first = false;
        }
		boolean end = false;
		int pageCount = page.getPageCount();
		if (pageCount < 2) {
			pageCount = 1;
			end = true;
		}
		createExceFile(template, ffile, excelFile, first, end, map);
		first = false;
		for (int i = 2; i <= pageCount; i++) {
			if (i == pageCount) {
				end = true;
			}
			page.setPageIndex(i);
			datas = findListDataByFinder(finder, page, clazz, queryBean);
			returnDatas.setData(datas);
			map.put(GlobalStatic.returnDatas, returnDatas);
			createExceFile(template, ffile, excelFile, first, end, map);
		}
		if (ffile.exists()) {
			ffile.delete();
		}

		if (excelFile.exists()) {
			excelFile.setReadOnly();
		}
		// excel转化
		try {
			File excelnew = new File(
					GlobalStatic.tempRootpath + "/" + fileName + "/" + SecUtils.getUUID() + GlobalStatic.excelext);
			OpenOfficeKit.cvtXls(excelFile, excelnew);
			return excelnew;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return excelFile;
	
	}

	/**
	 * 创建一个 excel 文件
	 * 
	 * @param template
	 * @param ffile
	 * @param excelFile
	 * @param first
	 * @param end
	 * @param map
	 * @return
	 * @throws Exception
	 */

	private File createExceFile(Template template, File ffile, File excelFile, boolean first, boolean end, Map map)
			throws Exception {
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ffile), "UTF-8"));
			template.process(map, out);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("生成freemarker页面错误");
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}

		FileInputStream in = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			in = new FileInputStream(ffile);
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			fos = new FileOutputStream(excelFile, true);
			osw = new OutputStreamWriter(fos, "UTF-8");
			bw = new BufferedWriter(osw);
			if (first) {// 如果是第一次,输出编码格式,防止 office 乱码
				bw.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
			}
			String line = null;

			boolean iswrite = false;
			while ((line = br.readLine()) != null) {
				if (StringUtils.isBlank(line)) {
                    continue;
                }

				line = line.trim();
				if (line.startsWith("<!--first_") && first == false) {
					iswrite = false;
					continue;
				}
				if (line.startsWith("<!--last_") && end == false) {
					iswrite = false;
					continue;
				}

				if ("<!--first_start_export-->".equals(line)) {
					iswrite = first;
					continue;

				} else if ("<!--last_start_export-->".equals(line)) {
					iswrite = end;
					continue;

				} else if ("<!--first_start_no_export-->".equals(line)) {
					iswrite = false;
					continue;

				} else if ("<!--first_end_no_export-->".equals(line)) {
					iswrite = true;
					continue;

				} else if ("<!--start_no_export-->".equals(line)) {
					iswrite = false;
					continue;
				} else if ("<!--start_export-->".equals(line)) {
					iswrite = true;
					continue;
				} else if ("<!--last_end_export-->".equals(line)) {
					iswrite = false;
					continue;
				} else if (line.startsWith("<!--end_")) {// 不包含需要输出的内容
					if ("<!--end_no_export-->".equals(line)) {// 特殊标签,不输出内容
						iswrite = true;
					} else {
						iswrite = false;
					}
					continue;
				}

				if (iswrite) {// 如果是写入标签
					bw.write(line);
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("追加xlsx内容错误");
		} finally {
			if (bw != null) {
                bw.close();
            }
			if (osw != null) {
                osw.close();
            }
			if (fos != null) {
                fos.close();
            }
			if (br != null) {
                br.close();
            }
			if (in != null) {
                in.close();
            }
		}

		return excelFile;
	}

	@Override
	public Object save(Object entity) throws Exception {
		return getBaseDao().save(entity);
	}

	@Override
	public List<Integer> save(List list) throws Exception {
		return getBaseDao().save(list);
	}

	@Override
	public Integer update(IBaseEntity entity) throws Exception {
		return getBaseDao().update(entity);
	}

	@Override
	public Integer update(IBaseEntity entity, boolean onlyupdatenotnull) throws Exception {
		return getBaseDao().update(entity, onlyupdatenotnull);
	}

	@Override
	public List<Integer> update(List list) throws Exception {
		return getBaseDao().update(list);
	}

	@Override
	public List<Integer> update(List list, boolean onlyupdatenotnull) throws Exception {
		return getBaseDao().update(list, onlyupdatenotnull);
	}

	@Override
	public <T> T findById(Object id, Class<T> clazz) throws Exception {
		return getBaseDao().findByID(id, clazz);
	}

	@Override
	public Object saveorupdate(Object entity) throws Exception {
		return getBaseDao().saveorupdate(entity);
	}

	@Override
    public <T> T queryForObjectByProc(Finder finder, Class<T> clazz) throws Exception {
		return getBaseDao().queryForObjectByProc(finder, clazz);
	}

	@Override
    public <T> List<T> queryForListByProc(Finder finder, Class<T> clazz) throws Exception {
		return getBaseDao().queryForListByProc(finder, clazz);
	}

	@Override
    public <T> T queryForObjectByByFunction(Finder finder, Class<T> clazz) throws Exception {
		return getBaseDao().queryForObjectByByFunction(finder, clazz);
	}

	@Override
    public <T> List<T> queryForListByFunction(Finder finder, Class<T> clazz) throws Exception {
		return getBaseDao().queryForListByFunction(finder, clazz);
	}

	@Override
	public Finder getFinderWhereByQueryBean(Finder finder, Object o) throws Exception {
		return getBaseDao().getFinderWhereByQueryBean(finder, o);
	}

	@Override
	public Finder getFinderOrderBy(Finder finder, Page page) throws Exception {
		return getBaseDao().getFinderOrderBy(finder, page);
	}

	@Override
	public <T> String saveImportExcelFile(File excelFile, Class<T> clazz, String siteId, String businessId,
			boolean istest) throws Exception {
		StringBuilder message = new StringBuilder();
		List<Cell[]> excel = ExcelUtils.getExcle(excelFile);
		if (CollectionUtils.isEmpty(excel)) {
			return "Excel文件没有sheet!";
		}
		Map<Integer, String> map = new HashMap<Integer, String>();
		Cell[] title = excel.get(0);
		title = excel.get(0);
		if (title == null || title.length < 1) {
			return "表头没有数据!";
		}
		List<String> listTitle = new ArrayList<>();
		// 封装字段
		for (int i = 0; i < title.length; i++) {
			map.put(i, title[i].getContents());
			listTitle.add(title[i].getContents());
		}

		for (int j = 1; j < excel.size(); j++) {
			Cell[] cells = excel.get(j);
			T r = clazz.newInstance();
			for (int m = 0; m < cells.length; m++) {
				Object o = r;
				Cell cell = cells[m];
				String name = map.get(m);
				try {
					if (name.contains(".")) {
						String[] strs = name.split("\\.");
						// 获取最后的属性名称
						name = strs[strs.length - 1];
						// 循环获取实体对象
						for (int i = 0; i < strs.length - 1; i++) {
							o = ClassUtils.getPropertieValue(strs[i], o);
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					String s = "第" + (m + 1) + "列列名有错误," + name + " 类型错误!";
					if (istest) {
						message.append(s).append("</br>");
					} else {
						throw new Exception(s);
					}
				}
				String value = cell.getContents().trim();
				Class className = ClassUtils.getReturnType(name, o.getClass());

				if (className == String.class) {
					try {
						ClassUtils.setPropertieValue(name, o, value);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						String s = "第" + (j + 1) + "行,第" + (m + 1) + "列:" + name + " 类型错误!";
						if (istest) {
							message.append(s).append("</br>");
						} else {
							throw new Exception(s);
						}
					}
				} else if (className == Date.class) {
					try {
						value = value.replace("/", "-");
						Date d = DateUtils.convertString2Date(value);

						ClassUtils.setPropertieValue(name, o, d);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						String s = "第" + (j + 1) + "行,第" + (m + 1) + "列:" + name + " 类型错误!";
						if (istest) {
							message.append(s).append("</br>");
						} else {
							throw new Exception(s);
						}
					}

				} else if (className == Double.class) {
					try {
						Double db = null;
						if (StringUtils.isNotBlank(value)) {
							db = Double.valueOf(value);
						}
						ClassUtils.setPropertieValue(name, o, db);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						String s = "第" + (j + 1) + "行,第" + (m + 1) + "列:" + name + " 类型错误!";
						if (istest) {
							message.append(s).append("</br>");
						} else {
							throw new Exception(s);
						}
					}
				} else if (className == Float.class) {
					try {
						Float f = null;
						if (StringUtils.isNotBlank(value)) {
							f = Float.valueOf(value);
						}
						ClassUtils.setPropertieValue(name, o, f);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						String s = "第" + (j + 1) + "行,第" + (m + 1) + "列:" + name + " 类型错误!";
						if (istest) {
							message.append(s).append("</br>");
						} else {
							throw new Exception(s);
						}
					}

				} else if (className == Integer.class) {
					try {
						Integer _i = null;

						if (StringUtils.isNotBlank(value)) {
							_i = Integer.valueOf(value);
						}

						ClassUtils.setPropertieValue(name, o, _i);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						String s = "第" + (j + 1) + "行,第" + (m + 1) + "列:" + name + " 类型错误!";
						if (istest) {
							message.append(s).append("</br>");
						} else {
							throw new Exception(s);
						}
					}
				}

				else if (className == BigDecimal.class) {
					try {
						BigDecimal bd = null;
						if (StringUtils.isNotBlank(value)) {
							bd = new BigDecimal(value);
						}
						ClassUtils.setPropertieValue(name, o, bd);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						String s = "第" + (j + 1) + "行,第" + (m + 1) + "列:" + name + " 类型错误!";
						if (istest) {
							message.append(s).append("</br>");
						} else {
							throw new Exception(s);
						}
					}
				}

			}
			try {
				// 插入siteId、businessId
				if (StringUtils.isNotBlank(siteId)) {
					Field field = r.getClass().getDeclaredField("siteId");
					field.setAccessible(true);
					field.set(r, siteId);
					if (StringUtils.isNotBlank(businessId)) {
						field = r.getClass().getDeclaredField("businessId");
						field.setAccessible(true);
						field.set(r, businessId);
					}
				}

				String s = saveFromExcel(r, (j + 1), istest, listTitle);
				if (istest && StringUtils.isNotBlank(s)) {
					message.append(s).append("</br>");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new Exception("第" + (j + 1) + "行,保存失败");
			}
		}
		if (excelFile.exists() && (istest == false)) {
			excelFile.delete();
		}
		if (StringUtils.isBlank(message.toString())) {
			return null;
		}
		if (excelFile.exists() && istest && StringUtils.isNotBlank(message.toString())) {
			excelFile.delete();
		}

		return message.toString();

	}

	@Override
	public <T> String saveImportExcelFile(File excelFile, Class<T> clazz, String siteId, String businessId)
			throws Exception {
		String message = saveImportExcelFile(excelFile, clazz, siteId, businessId, true);
		if (StringUtils.isNotBlank(message)) {
			return message;
		}
		return saveImportExcelFile(excelFile, clazz, siteId, businessId, false);

	}

	@Override
	public String saveFromExcel(Object entity, int index, boolean istest, List<String> listTitle) throws Exception {
		if (istest) {
			return null;
		}
		return save(entity).toString();
	}

	@Override
	public Object executeCallBack(CallableStatementCreator callableStatementCreator, List<SqlParameter> parameter)
			throws Exception {
		return getBaseDao().executeCallBack(callableStatementCreator, parameter);
	}

}
