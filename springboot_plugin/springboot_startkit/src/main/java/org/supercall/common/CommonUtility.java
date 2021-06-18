package org.supercall.common;

import org.springframework.context.ApplicationContext;
import org.springframework.util.ExceptionTypeFilter;
import org.springframework.util.StringUtils;
import org.supercall.domainModel.FilterLists;
import org.supercall.domainModel.TableHeader;
import org.supercall.domainModel.framework.SimpleGridConfigModel;
import org.supercall.models.SysConfig;
import org.supercall.mybatis.Pagination;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by kira on 16/7/26.
 */
public class CommonUtility {
    public static ApplicationContext applicationContext;

    public static Pagination getPagination(int offset, int limit) {
        return new Pagination(offset, limit);
    }

    public static HashMap<String, Object> getParameterMap(
            HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return (HashMap<String, Object>) returnMap;
    }


    public static HashMap<String, Object> buildSimpleList(IListPlugin iListPlugin,
                                                          HttpServletRequest request,
                                                          SimpleGridConfigModel simpleGridConfigModel) {
        try {
            String offset = request.getParameter("offset");
            String limit = request.getParameter("limit");
            int off = StringUtils.isEmpty(offset) ? 0 : Integer.parseInt(offset);
            int lim = StringUtils.isEmpty(limit) ? 10 : Integer.parseInt(limit);
            Pagination pagination = CommonUtility.getPagination(off, lim);
            HashMap<String, Object> map = CommonUtility.getParameterMap(request);
            List<LinkedHashMap<String, Object>> result = iListPlugin.all(pagination, map);

            HashMap<String, Object> tableEntity = new HashMap<>();
            tableEntity.put("tableContent", result);

            int total = 0;
            if (pagination.getTotal() <= lim) {
                total = 1;
            }
            if (pagination.getTotal() > lim) {
                int num = pagination.getTotal() / lim;
                if (num == 1) {
                    total = 2;
                } else {
                    total = pagination.getTotal() / Integer.valueOf(limit);
                }
            }
            tableEntity.put("total", total);
            tableEntity.put("limit", lim);
            tableEntity.put("offset", off);
            tableEntity.put("currentpage", (off / lim) + 1);
            List<FilterLists> filterLists = new ArrayList<>();

            simpleGridConfigModel.getTableFilterConfigs().forEach(e -> {
                Object datasources = null;
                if (e.getColumnType().equals("selector")) {
                    try {
                        Class classType = Class.forName(e.getDataSourcePluginName());
                        Object invoker = classType.getConstructor(new Class[]{}).newInstance(new Object[]{});
                        Method method = classType.getMethod(e.getDataSourcePlginFunction());
                        datasources = method.invoke(invoker);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                FilterLists filterEntity = new FilterLists(e.getDisplayName(), e.getColumnName(), e.getColumnType(), "", datasources);

                if (map.get(filterEntity.getKey()) != null) {
                    filterEntity.setValue((String) map.get(filterEntity.getKey()));
                }
                filterLists.add(filterEntity);

            });
            tableEntity.put("filterLists", filterLists);


            List<TableHeader> tableHeaders = new ArrayList<>();
            simpleGridConfigModel.getGridColumnConfigs().forEach(e->{
                tableHeaders.add(new TableHeader(e.getColunmName(), "", ""));
                tableEntity.put("tableHeader", tableHeaders);
            });
            return tableEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
