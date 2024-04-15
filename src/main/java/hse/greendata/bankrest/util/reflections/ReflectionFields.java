package hse.greendata.bankrest.util.reflections;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionFields {
    private ReflectionFields() {
    }

    public static List<String> getFieldNames(Class<?> clazz) {
        List<String> fieldNames = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

    public static boolean hasField(Class<?> clazz, String fieldName) {
        List<String> fieldNames = getFieldNames(clazz);
        return fieldNames.contains(fieldName);
    }
}
