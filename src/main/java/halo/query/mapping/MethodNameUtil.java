package halo.query.mapping;

import java.lang.reflect.Field;

public class MethodNameUtil {

    /**
     * 生成setter方法名称，按照头字母大写规则进行生成，如果第二个字母为大写，那么首字母还是小写
     *
     * @param fieldName 字段名
     * @return 获得setter方法名
     */
    public static String createSetMethodString(String fieldName) {
        return createSetterOrGetterMethodString("set", fieldName);
    }

    /**
     * 生成getter方法名称，按照头字母大写规则进行生成，如果第二个字母为大写，那么首字母还是小写
     *
     * @param idField id字段名
     * @return 获得getter方法名
     */
    public static String createGetMethodString(Field idField) {
        if (idField.getType().equals(boolean.class)) {
            return createSetterOrGetterMethodString("is", idField.getName());
        }
        return createSetterOrGetterMethodString("get", idField.getName());
    }

    /**
     * 生成setter/getter方法名称，按照头字母大写规则进行生成，如果第二个字母为大写，那么首字母还是小写
     *
     * @param prefix    前缀
     * @param fieldName 字段名
     * @return 获得setter getter方法名称
     */
    public static String createSetterOrGetterMethodString(String prefix,
                                                          String fieldName) {
        String first = fieldName.substring(0, 1);
        String second = fieldName.substring(1, 2);
        if (second.equals(second.toUpperCase())) {
            try {
                // 如果第二位为数字，第一位还是需要大写
                Integer.parseInt(second);
                return prefix + first.toUpperCase() + fieldName.substring(1);
            } catch (NumberFormatException e) {
                return prefix + fieldName;
            }
        }
        return prefix + first.toUpperCase() + fieldName.substring(1);
    }
}
