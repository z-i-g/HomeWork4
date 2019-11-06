package reflection;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Clean {

    public void cleanUp(Object object, Set<String> fieldToCleanup, Set<String> fieldToOutput) throws NoSuchFieldException, IllegalAccessException {

        Class testClass = object.getClass();

        if(object instanceof java.util.Map) {
            System.out.println(true);
        } else {
            Field[] fields = testClass.getFields();

            for(Field field: fields) {
                for (String s : fieldToCleanup) {
                    if(field.getName() == s) {
                        switch (field.getType().toString()) {
                            case "int": field.setInt(object, 0);
                                break;
                            case "boolean": field.setBoolean(object, false);
                                break;
                            case "char": field.setChar(object, Character.MIN_VALUE);
                                break;
                            case "byte": field.setByte(object, (byte) 0);
                                break;
                            case "float": field.setFloat(object, Float.NaN);
                                break;
                            case "double": field.setDouble(object, Double.NaN);
                                break;
                            case "long": field.setLong(object, 0L);
                                break;
                            case "short": field.setShort(object, (short) 0);
                            default: field.set(object, null);
                                break;
                        }
                    }
                }
                for (String s : fieldToOutput) {
                    if(field.getName() == s) {
                        String value = field.get(object).toString();
                        System.out.println("field - " + field.getName() + " " + value);
                        }
                    }
                }
            }
        }

    public void objectCleanUpOutput() {

    }

    public void mapImplCleanUpOutput() {

    }

    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {

        Clean clean = new Clean();
        TestCar testCar = new TestCar("Lada", "2109", "sedan", "gasoline", 4, 86, (byte) 5);
        HashMap hashMap = new HashMap();


        Set<String> fieldToCleanUp = new HashSet<String>();
        fieldToCleanUp.add("name");
        fieldToCleanUp.add("door");
        fieldToCleanUp.add("power");

        Set<String> fieldToOutput = new HashSet<String>();
        fieldToOutput.add("model");
        fieldToOutput.add("kuzov");
        fieldToOutput.add("fuel");
        fieldToOutput.add("numberOfSpeed");

        clean.cleanUp(hashMap, fieldToCleanUp, fieldToOutput);

        clean.cleanUp(testCar, fieldToCleanUp, fieldToOutput);

    }

}
