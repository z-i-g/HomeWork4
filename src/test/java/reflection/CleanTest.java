package reflection;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class CleanTest {

    Set<String> fieldToOutput = new HashSet<String>();
    Set<String> fieldToCleanUp = new HashSet<String>();
    Clean testClean = new Clean();
    HashMap testHashMap = new HashMap();
    HashSet setValues = new HashSet();
    Set setClean = new HashSet();
    TestCar testCar = new TestCar("Chevrolet", "Aveo LT", "Sedan", "gasoline", 4, 116, (byte) 5);
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Test
    void cleanUpObject() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        fieldToCleanUp.add("name");
        fieldToCleanUp.add("door");
        fieldToCleanUp.add("power");

        fieldToOutput.add("model");
        fieldToOutput.add("kuzov");
        fieldToOutput.add("fuel");
        fieldToOutput.add("numberOfSpeed");

        testClean.cleanUp(testCar, fieldToCleanUp, fieldToOutput);

        assertEquals(testCar.getName(), null);
        assertEquals(testCar.getDoor(), 0);
        assertEquals(testCar.getPower(), 0);
    }

    @Test
    void cleanUpImlMap() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        setClean.add(3);

        Object[] object = testHashMap.values().toArray();
        for(int i = 0; i < object.length; i++) {
            setValues.add(object[i]);
            System.out.println(object[i].toString());
        }

        assertEquals(testHashMap.keySet().size(), 4);

        testClean.cleanUp(testHashMap, setClean, setValues);
        assertEquals(testHashMap.size(), 3);

        testClean.cleanUp(testHashMap, testHashMap.keySet(), setValues);
        assertEquals(testHashMap.size(), 0);

    }

    @Test
    public void systemOutHashIml() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        testHashMap.put(1, 2);
        testHashMap.put("Hello", "World");
        testHashMap.put(3, "hi");
        testHashMap.put("ToDo", "Mir");
        Collection coll = testHashMap.values();
        for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
            String valueOutput = iter.next().toString();
            setValues.add(valueOutput);
        }
        String outputString = setValues.toString();
        testClean.cleanUp(testHashMap, fieldToCleanUp, fieldToOutput);

        assertEquals(outputString, outContent.toString());
    }

    @Test
    public void systemOutObject() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        fieldToCleanUp.add("name");
        fieldToCleanUp.add("door");
        fieldToCleanUp.add("power");

        fieldToOutput.add("model");
        fieldToOutput.add("kuzov");
        fieldToOutput.add("fuel");
        fieldToOutput.add("numberOfSpeed");
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        testClean.cleanUp(testCar, fieldToCleanUp, fieldToOutput);

        String outputString = fieldToOutput.toString();

        assertEquals(outputString, outContent.toString());
    }


}