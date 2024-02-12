package edu.school21.logic;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Logic {
    private static final String PACKAGE_NAME = "edu.school21.models";
    private static List<Class> allClasses;
    private static Class currClass;
    private static Object object;
    private static List<Field> allFields;
    private static List<Method> allMethods;
    private static Method currMethod;
    private static Field currField;
    private static Scanner in = new Scanner(System.in);

    public static void printAllClasses() throws RuntimeException, IOException {
        allClasses = getAllClasses();
        System.out.println("Classes:");
        allClasses.forEach(c -> System.out.println("\t- " + Utils.getClassName(c)));
    }

    private static List<Class> getAllClasses() throws RuntimeException, IOException {
        String pathToPackage =
                System.getProperty("user.dir") + "/src/main/java/" + PACKAGE_NAME.replace('.', '/') + "/";
        List<Class> classes = new LinkedList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(pathToPackage))) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .map(p -> p.toString().replace(pathToPackage, "")
                            .replace(".java", ""))
                    .forEach(className -> {
                        try {
                            classes.add(Utils.getClass(PACKAGE_NAME, className));
                        } catch (
                                ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        return classes;
    }

    public static void scanClass() throws ClassNotFoundException {
        System.out.println("Enter class name:");
        String answerStr = in.nextLine().trim();
        currClass = Utils.getClass(PACKAGE_NAME, answerStr);
    }

    public static void printFieldsAndMethods() {
        System.out.println("fields:");
        allFields = Arrays.asList(currClass.getDeclaredFields());
        allFields.forEach(f -> System.out.println("\t\t" + Utils.getFieldName(f)));
        System.out.println("methods:");
        allMethods = Arrays.asList(currClass.getDeclaredMethods());
        allMethods.forEach(m -> System.out.println("\t\t" + Utils.getMethodName(m)));
    }

    public static void createObj() throws Exception {
        Constructor constructor =
                Arrays.stream(currClass.getConstructors())
                        .filter(c -> (c.getParameterCount() > 0))
                        .findFirst()
                        .orElseThrow(() -> new NullPointerException("Unable to create object"));

        System.out.println("Let’s create an object.");
        object = constructor.newInstance(scanFieldParameters());
        System.out.println("Object created: " + object.toString());
    }

    private static Object[] scanFieldParameters() throws NumberFormatException,
            RuntimeException {
        ArrayList<Object> parameters = new ArrayList<>();
        for (Field field : allFields) {
            System.out.println(field.getName() + ":");
            String answerStr = in.nextLine().trim();
            parameters.add(parseValue(answerStr, Utils.getFieldType(field)));
        }
        return parameters.toArray();
    }


    private static Object parseValue(String valueStr,
                                     String type) throws RuntimeException {
        switch (type) {
            case "String":
                return valueStr;
            case "int":
            case "Integer":
                return Integer.parseInt(valueStr);
            case "boolean":
            case "Boolean":
                return Boolean.parseBoolean(valueStr);
            case "double":
            case "Double":
                return Double.parseDouble(valueStr);
            case "long":
            case "Long":
                return Long.parseLong(valueStr);
            default:
                throw new RuntimeException("Unknown parameter type");
        }
    }

    public static void changeField() throws Exception {
        if (allFields.isEmpty())
            throw new NullPointerException("Unable to change fields");
        System.out.println("Enter name of the field for changing:");
        String answerStr = in.nextLine();
        Field field = Utils.getField(answerStr, currClass);
        String type = Utils.getFieldType(field);
        System.out.println("Enter " + type +
                " value:");
        answerStr = in.nextLine();
        field.setAccessible(true);
        field.set(object, parseValue(answerStr,
                type));
        System.out.println("Object updated: " + object);
    }

    public static void runMethod() throws Exception {
        if (allFields.isEmpty())
            throw new NullPointerException("Unable to change fields");
        System.out.println("Enter name of the method for call:");
        String answerStr = in.nextLine();
        currMethod = Utils.getMethod(answerStr, allMethods);
        if (!currMethod.isAccessible())
            currMethod.setAccessible(true);
        Object returnObg = currMethod.invoke(object, scanMethodParameters());
        if (!currMethod.getReturnType().getName().equals("void")) {
            System.out.println("Method returned:");
            System.out.println(returnObg);
        }
    }

    private static Object[] scanMethodParameters() throws NumberFormatException,
            RuntimeException {
        ArrayList<Object> parameters = new ArrayList<>();
        String type;
        String answerStr;
        for (Class param : currMethod.getParameterTypes()) {
            type = Utils.getClassName(param);
            System.out.println("Enter " + type +
                    " value:");
            answerStr = in.nextLine();
            parameters.add(parseValue(answerStr, type));
        }
        return parameters.toArray();
    }

    public static void closeScanner() {
        in.close();
    }
}
