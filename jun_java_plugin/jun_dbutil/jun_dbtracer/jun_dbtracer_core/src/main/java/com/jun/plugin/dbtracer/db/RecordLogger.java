package com.jun.plugin.dbtracer.db;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.dbtracer.Bootstrap;
import com.jun.plugin.dbtracer.UpdateHistory;
import com.jun.plugin.dbtracer.xml.TableConfiguration;
import com.jun.plugin.dbtracer.xml.TableField;

public class RecordLogger {

    private static Logger logger = LoggerFactory.getLogger(RecordLogger.class);

    public static void log(UpdateHistory history) {
        try {
            if (history == null) {
                return;
            }
            // TODO

            String tblName = history.getTable();

            Map<String, TableConfiguration> config = Bootstrap.getTableConfiguration();
            TableConfiguration tblConfig = config.get(tblName);
            Map<String, TableField> filedName2Config = tblConfig.getFiledName2Config();

            Map<String, Object> originalCol2Val = history.getOriginalCol2Val();
            Map<String, String> newCol2Val = history.getNewCol2Val();
            Set<Entry<String, Object>> originalEntrySet = originalCol2Val.entrySet();
            StringBuffer sb = new StringBuffer();
            int idx = 0;
            int columeSize = originalEntrySet.size();
            for (Entry<String, Object> entry : originalEntrySet) {
                String nameEn = entry.getKey();
                TableField field = filedName2Config.get(nameEn);
                String nameCn = field.getNameCn();
                sb.append(nameCn).append(":");

                Object originalVal = entry.getValue();
                sb.append("{").append(getDescriptionByValue(field, originalVal.toString())).append("}--->{");
                String newVal = newCol2Val.get(nameEn);
                sb.append(getDescriptionByValue(field, newVal));
                sb.append("}");
                if (idx < columeSize - 1) {
                    sb.append(",");
                }
            }

            System.out.println("Log change history:" + sb + ",where=" + history.getWhere());
            System.out.println("Log change history vo:" + history);

            // TODO write to DB
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private static String getDescriptionByValue(TableField field, String value) {
        String description = field.getValue2Description().get(value);
        if (description != null) {
            return description;
        }

        return value;
    }

}
