package dao.util;

import dao.annotations.DBColumn;
import dao.annotations.Ignored;
import dao.annotations.Table;

import java.lang.reflect.Field;
import java.util.HashMap;

public class InfoCollector {

    public static String getTableName(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);

        if (table == null) {
            String className = clazz.getSimpleName().toLowerCase();
            return (!className.endsWith("s")) ? className+"s" : className;
        }

        return table.tableName();
    }

    public static HashMap<String, String> getColumns (Class<?> clazz) {
        HashMap<String, String> columns = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field: fields) {
            if (isIgnored(field)) continue;
            DBColumn column = field.getAnnotation(DBColumn.class);

            if (column == null) {
                columns.put(field.getName(), field.getName());
            }
            else {
                columns.put(field.getName(), column.columnName());
            }
        }

        return columns;
    }

    private static boolean isIgnored (Field field) {
        return field.getAnnotation(Ignored.class) != null;
    }
}
