package database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

public class ObjectMapper<T> {

    private Class klass;
    private Map<String, Field> fields = new HashMap<>();

    public ObjectMapper(Class klass) {
        this.klass = klass;

        List<Field> fieldList = Arrays.asList(klass.getDeclaredFields());
        for (Field field : fieldList) {
            Column col = field.getAnnotation(Column.class);
            if (col != null) {
                field.setAccessible(true);
                fields.put(col.value().isEmpty() ? field.getName() : col.value(), field);
            }
        }
    }

    public T map(Map<String, Object> row) {
        try {
            T dto = (T) klass.getConstructor().newInstance();
            if(row.isEmpty()) {
                return null;
            }
            for (Map.Entry<String, Object> entity : row.entrySet()) {
                if (entity.getValue() == null) {
                    continue;
                }
                String column = entity.getKey();
                Field field = fields.get(column);
                if (field != null) {
                    Object val = entity.getValue();
                    field.set(dto, convertInstanceOfObject(val));
                }
            }
            return dto;
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<T> map(List<Map<String, Object>> rows) {
        List<T> list = new LinkedList<>();

        for (Map<String, Object> row : rows) {
            list.add(map(row));
        }

        return list;
    }

    public T mapOne(ResultSet rs) {
        return this.map(this.resultSetToHashMap(rs));
    }

    public List<T> map(ResultSet rs) {
        return this.map(this.resultSetToArrayList(rs));
    }

    public HashMap<String, Object> resultSetToHashMap(ResultSet rs){
        HashMap<String, Object> row = new HashMap<>();
        try {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            if (rs.next()) {
                row = new HashMap(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
            }
        } catch (Exception ex) { ex.printStackTrace(); }

        return row;
    }

    public List resultSetToArrayList(ResultSet rs){
        ArrayList list = new ArrayList(50);
        try {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            while (rs.next()) {
                HashMap row = new HashMap(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(row);
            }
        } catch (Exception ex) { ex.printStackTrace(); }

        return list;
    }

    private T convertInstanceOfObject(Object o) {
        try {
            return (T) o;
        } catch (ClassCastException e) {
            return null;
        }
    }
}
