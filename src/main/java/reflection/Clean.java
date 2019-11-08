package reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Класс для тестирования возможностей reflection
 *
 * @version 1.0
 * @autor Айрат Загидуллин
 */
public class Clean {

    /**
     * Изменение полей класса если Object, изменение ключей/значений если implements Map
     *
     * @param object         - исследуемый объект
     * @param fieldToCleanup - имена полей класса для установки дефолтных значений
     * @param fieldToOutput  - имена полей класса для вывода в консоль
     */
    public void cleanUp(Object object, Set<String> fieldToCleanup, Set<String> fieldToOutput) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Class testClass = object.getClass();

        if (object instanceof java.util.Map) {
            cleanUpMapIml(testClass, object, fieldToCleanup, fieldToOutput);
        } else {
            Field[] fields = testClass.getDeclaredFields();
            if (fields.length == 0) {
                throw new IllegalArgumentException("В коллекции нет данных!");
            } else {
                for (Field field : fields) {
                    for (String s : fieldToCleanup) {
                        if (field.getName().equals(s)) {
                            field.setAccessible(true);
                            switch (field.getType().toString()) {
                                case "int":
                                    field.setInt(object, 0);
                                    break;
                                case "boolean":
                                    field.setBoolean(object, false);
                                    break;
                                case "char":
                                    field.setChar(object, Character.MIN_VALUE);
                                    break;
                                case "byte":
                                    field.setByte(object, (byte) 0);
                                    break;
                                case "float":
                                    field.setFloat(object, Float.NaN);
                                    break;
                                case "double":
                                    field.setDouble(object, Double.NaN);
                                    break;
                                case "long":
                                    field.setLong(object, 0L);
                                    break;
                                case "short":
                                    field.setShort(object, (short) 0);
                                    break;
                                default:
                                    field.set(object, null);
                                    break;
                            }
                        }
                    }
                    for (String s : fieldToOutput) {
                        if (field.getName() == s) {
                            field.setAccessible(true);
                            String value = field.get(object).toString();
//                        System.out.println("field - " + field.getName() + " " + value);
                        }
                    }
                }
                System.out.print(fieldToOutput);
            }
        }
    }

    private void cleanUpMapIml(Class mapClass, Object objectMap, Set<String> fieldToCleanupMap, Set<String> fieldToOutputMap) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        Method methodSize = mapClass.getMethod("size", null);
        Object objSize = methodSize.invoke(objectMap, null);

        if ((objSize.toString()).equals("0")) {
            throw new IllegalArgumentException("В коллекции нет данных!");
        } else {
            Method methodValues = mapClass.getMethod("values", null);
            Collection collValues = (Collection) methodValues.invoke(objectMap, null);

            for (Iterator iter = collValues.iterator(); iter.hasNext(); ) {
                String valueOutput = iter.next().toString();
                fieldToOutputMap.add(valueOutput);
            }
            System.out.print(fieldToOutputMap);

            Object[] valueObject = fieldToCleanupMap.toArray();
            Method methodRemove = mapClass.getMethod("remove", java.lang.Object.class);

            for (int i = 0; i < valueObject.length; i++) {
                methodRemove.invoke(objectMap, valueObject[i]);
            }
        }
    }

    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

    }


}
