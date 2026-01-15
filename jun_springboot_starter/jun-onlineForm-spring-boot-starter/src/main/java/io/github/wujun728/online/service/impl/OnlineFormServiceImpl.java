package io.github.wujun728.online.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.wujun728.online.common.PageResult;
import io.github.wujun728.online.dao.OnlineFormDao;
import io.github.wujun728.online.dao.OnlineTableColumnDao;
import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import io.github.wujun728.online.entity.OnlineTableEntity;
import io.github.wujun728.online.query.OnlineFormQuery;
import io.github.wujun728.online.service.OnlineFormService;
import io.github.wujun728.online.service.OnlineTableService;
import io.github.wujun728.online.vo.form.OnlineFormVO;
import io.github.wujun728.online.vo.form.WidgetFormConfigVO;
import io.github.wujun728.online.vo.form.WidgetFormVO;
import io.github.wujun728.online.vo.form.component.ComponentContext;
import io.github.wujun728.online.vo.query.QueryContext;
import net.maku.framework.common.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * 在线表单服务实现
 */
@Service
public class OnlineFormServiceImpl implements OnlineFormService {

    private final OnlineFormDao onlineFormDao;
    private final OnlineTableColumnDao onlineTableColumnDao;
    private final OnlineTableService onlineTableService;

    public OnlineFormServiceImpl(OnlineTableService onlineTableService, OnlineTableColumnDao onlineTableColumnDao, OnlineFormDao onlineFormDao) {
        this.onlineTableService = onlineTableService;
        this.onlineTableColumnDao = onlineTableColumnDao;
        this.onlineFormDao = onlineFormDao;
    }

    @Override
    public Map<String, Object> get(String tableId, Long id) {
        OnlineTableEntity tableEntity = getTableById(tableId);
        return onlineFormDao.getById(tableEntity.getName(), id);
    }

    @Override
    public void save(String tableId, Map<String, String> params) {
        OnlineTableEntity tableEntity = getTableById(tableId);
        Long userId = 1L; // SecurityUser.getUserId();
        Map<String, Object> saveParams = new HashMap<>();
        List<OnlineTableColumnEntity> columnList = onlineTableColumnDao.getByTableId(tableId);

        for (OnlineTableColumnEntity column : columnList) {
            // 处理特殊字段
            if ("id".equalsIgnoreCase(column.getName())) {
                continue;
            }
            if ("creator".equalsIgnoreCase(column.getName())) {
                saveParams.put("creator", userId);
                continue;
            }
            if ("create_time".equalsIgnoreCase(column.getName())) {
                saveParams.put("create_time", new Date());
                continue;
            }
            if ("updater".equalsIgnoreCase(column.getName())) {
                saveParams.put("updater", userId);
                continue;
            }
            if ("update_time".equalsIgnoreCase(column.getName())) {
                saveParams.put("update_time", new Date());
                continue;
            }

            // 处理普通字段
            String value = params.getOrDefault(column.getName(), null);
            if (StrUtil.isNotBlank(value)) {
                saveParams.put(column.getName(), value);
            }
        }

        onlineFormDao.save(tableEntity.getName(), saveParams);
    }

    @Override
    public OnlineFormVO getJSON(String tableId) {
        OnlineTableEntity tableEntity = getTableById(tableId);
        List<OnlineTableColumnEntity> columnList = onlineTableColumnDao.getByTableId(tableId);

        OnlineFormVO formVO = new OnlineFormVO();
        formVO.setQuery(getQueryJSON(columnList));
        formVO.setTable(getTableJSON(columnList));
        formVO.setForm(getFormJSON(tableEntity, columnList));

        return formVO;
    }

    @Override
    public PageResult<Map<String, Object>> page(String tableId, OnlineFormQuery query) {
        OnlineTableEntity tableEntity = getTableById(tableId);
        Map<String, Object> queryParams = getQueryParams(tableId, query.getParams());

        Page<?> page = new Page<>(query.getPage(), query.getLimit());
        List<Map<String, Object>> list = onlineFormDao.getList(page, tableEntity.getName(), queryParams);

        return new PageResult<>(list, page.getTotal());
    }

    @Override
    public void update(String tableId, Map<String, String> params) {
        OnlineTableEntity tableEntity = getTableById(tableId);
        Map<String, Object> updateParams = new HashMap<>();
        List<OnlineTableColumnEntity> columnList = onlineTableColumnDao.getByTableId(tableId);

        for (OnlineTableColumnEntity column : columnList) {
            // 处理特殊字段
            if ("id".equalsIgnoreCase(column.getName())) {
                continue;
            }
            if ("updater".equalsIgnoreCase(column.getName())) {
                updateParams.put("updater", 1L); // SecurityUser.getUserId()
                continue;
            }
            if ("update_time".equalsIgnoreCase(column.getName())) {
                updateParams.put("update_time", new Date());
                continue;
            }

            // 处理表单项字段
            if (column.isFormItem()) {
                String value = params.getOrDefault(column.getName(), null);
                updateParams.put(column.getName(), value);
            }
        }

        Long id = Long.parseLong(params.get("id"));
        onlineFormDao.update(tableEntity.getName(), id, updateParams);
    }

    @Override
    public void delete(String tableId, List<Long> ids) {
        OnlineTableEntity tableEntity = getTableById(tableId);
        onlineFormDao.delete(tableEntity.getName(), ids);
    }

    /**
     * 获取表信息
     */
    private OnlineTableEntity getTableById(String tableId) {
        OnlineTableEntity tableEntity = onlineTableService.getById((Serializable) tableId);
        if (tableEntity == null) {
            throw new ServerException("表单不存在");
        }
        return tableEntity;
    }

    /**
     * 构建查询参数
     */
    private Map<String, Object> getQueryParams(String tableId, Map<String, Object> params) {
        List<OnlineTableColumnEntity> columnList = onlineTableColumnDao.getByTableId(tableId);
        Map<String, Object> queryParams = new HashMap<>();

        for (OnlineTableColumnEntity column : columnList) {
            if (!column.isQueryItem()) {
                continue;
            }

            // 处理日期范围查询
            if ("dateRange".equals(column.getQueryInput()) || "datetimeRange".equals(column.getQueryInput())) {
                Object value = params.getOrDefault(column.getName(), null);
                if (value instanceof List) {
                    List<?> dateRange = (List<?>) value;
                    if (dateRange.size() >= 2) {
                        queryParams.put(column.getName() + " >= ", dateRange.get(0));
                        queryParams.put(column.getName() + " <= ", dateRange.get(1));
                    }
                }
            }
            // 处理普通查询
            else {
                String value = (String) params.getOrDefault(column.getName(), null);
                if (StrUtil.isNotBlank(value)) {
                    if ("like".equalsIgnoreCase(column.getQueryType())) {
                        queryParams.put(column.getName() + " like ", "%" + value + "%");
                    } else {
                        queryParams.put(column.getName() + " = ", value);
                    }
                }
            }
        }

        return queryParams;
    }

    /**
     * 获取表单配置JSON
     */
    private WidgetFormVO getFormJSON(OnlineTableEntity tableEntity, List<OnlineTableColumnEntity> columnList) {
        WidgetFormVO formVO = new WidgetFormVO();
        WidgetFormConfigVO configVO = new WidgetFormConfigVO();

        // 设置表单配置
        configVO.setLabelWidth(100);
        configVO.setSize("small");
        configVO.setLabelPosition(null);
        configVO.setStyle(null);
        formVO.setConfig(configVO);

        // 根据布局类型生成表单组件
        int formLayout = tableEntity.getFormLayout();
        List<Map<String, Object>> componentList = new ArrayList<>();

        // 单列布局
        if (formLayout == 1) {
            for (OnlineTableColumnEntity column : columnList) {
                if (column.isFormItem()) {
                    ComponentContext componentContext = new ComponentContext(column);
                    componentList.add(componentContext.getComponent());
                }
            }
        }
        // 多列布局
        else {
            // 布局容器配置
            Map<String, Object> layoutContainer = new HashMap<>();
            layoutContainer.put("type", "grid");
            layoutContainer.put("direction", "horizontal");
            layoutContainer.put("justify", "start");

            // 布局容器样式
            Map<String, Object> layoutStyle = new HashMap<>();
            layoutStyle.put("background", "transparent");
            layoutStyle.put("padding", "0px");
            layoutStyle.put("margin", "0px");
            layoutContainer.put("style", layoutStyle);

            // 创建列容器
            List<List<Map<String, Object>>> columns = new ArrayList<>();
            for (int i = 0; i < formLayout; i++) {
                columns.add(new ArrayList<>());
            }

            // 分配组件到列
            int columnIndex = 0;
            for (OnlineTableColumnEntity column : columnList) {
                if (column.isFormItem()) {
                    int currentColumn = columnIndex % formLayout;
                    ComponentContext componentContext = new ComponentContext(column);
                    columns.get(currentColumn).add(componentContext.getComponent());
                    columnIndex++;
                }
            }

            // 构建列配置
            List<Map<String, Object>> columnConfigs = new ArrayList<>();
            for (int i = 0; i < formLayout; i++) {
                Map<String, Object> columnConfig = new HashMap<>();
                columnConfig.put("span", 24 / formLayout);
                columnConfig.put("list", columns.get(i));
                columnConfigs.add(columnConfig);
            }

            layoutContainer.put("column", columnConfigs);
            componentList.add(layoutContainer);
        }

        formVO.setList(componentList);
        return formVO;
    }

    /**
     * 获取查询配置JSON
     */
    private WidgetFormVO getQueryJSON(List<OnlineTableColumnEntity> columnList) {
        WidgetFormVO formVO = new WidgetFormVO();
        WidgetFormConfigVO configVO = new WidgetFormConfigVO();

        // 设置查询表单配置
        configVO.setLabelWidth(null);
        configVO.setSize("small");
        configVO.setLabelPosition(null);
        configVO.setStyle(null);
        formVO.setConfig(configVO);

        // 生成查询组件
        List<Map<String, Object>> queryComponents = new ArrayList<>();
        for (OnlineTableColumnEntity column : columnList) {
            if (column.isQueryItem()) {
                QueryContext queryContext = new QueryContext(column);
                queryComponents.add(queryContext.getQuery());
            }
        }

        formVO.setList(queryComponents);
        return formVO;
    }

    /**
     * 获取表格配置JSON
     */
    private List<Map<String, Object>> getTableJSON(List<OnlineTableColumnEntity> columnList) {
        List<Map<String, Object>> tableColumns = new ArrayList<>();

        for (OnlineTableColumnEntity column : columnList) {
            if (column.isGridItem()) {
                Map<String, Object> tableColumn = new HashMap<>();
                tableColumn.put("prop", column.getName());
                tableColumn.put("label", column.getComments());
                tableColumn.put("sortable", column.isGridSort());

                // 如果有字典配置，添加字典
                if (StrUtil.isNotBlank(column.getFormDict())) {
                    tableColumn.put("dictCode", column.getFormDict());
                }

                tableColumns.add(tableColumn);
            }
        }

        return tableColumns;
    }
}

