package edu.school21.logic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class Utils {
    public static String getClassName(Class currClass) {
        return getValue(currClass.toString());
    }

    public static Class getClass(String packageName, String className) throws ClassNotFoundException {
        return Class.forName(packageName + '.' + className);
    }

    public static String getFieldName(Field field) {
        return getFieldType(field) + " " + field.getName();
    }

    public static String getFieldType(Field field) {
        return getValue(field.getType().getName());
    }

    public static Field getField(String fieldName, Class currClass) throws NoSuchFieldException {
        return currClass.getDeclaredField(fieldName);
    }

    public static String getMethodName(Method method) {
        return getValue(method.getReturnType().getName()) + " " + getValue(method.toString());
    }

    public static Method getMethod(String signature, List<Method> allMethods) throws NullPointerException {

        Method method = allMethods.stream()
                .filter(m -> signature.equalsIgnoreCase(getValue(m.toString())))
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Method not found"));
        return method;
    }

    private static String getValue(String str) {
        String[] splitStr = str.split("\\.");
        return splitStr[splitStr.length - 1];
    }
}
