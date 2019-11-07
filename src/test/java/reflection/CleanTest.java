package reflection;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.jupiter.api.Assertions.*;

class CleanTest {

    TestCar testCar = new TestCar("Chevrolet", "Aveo LT", "Sedan", "gasoline", 4, 116, (byte) 5);
    HashMap testHashMap = new HashMap();
    HashSet setValues = new HashSet();
    Clean testClean = new Clean();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Test
    void cleanUpObject() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Set<String> fieldToCleanUp = new HashSet<String>();
        fieldToCleanUp.add("name");
        fieldToCleanUp.add("door");
        fieldToCleanUp.add("power");

        Set<String> fieldToOutput = new HashSet<String>();
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
        testHashMap.put(1, 2);
        testHashMap.put("Hello", "World");
        testHashMap.put(3, "hi");
        testHashMap.put("ToDo", "Mir");

        assertEquals(testHashMap.keySet().size(), 4);

        testClean.cleanUp(testHashMap, null, null);

        assertEquals(testHashMap.keySet().size(), 0);
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
        testClean.cleanUp(testHashMap, null, null);

        assertEquals(outputString, outContent.toString());
    }

    @Test
    public void systemOutObject() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        Set<String> fieldToCleanUp = new HashSet<String>();
        fieldToCleanUp.add("name");
        fieldToCleanUp.add("door");
        fieldToCleanUp.add("power");

        Set<String> fieldToOutput = new HashSet<String>();
        fieldToOutput.add("model");
        fieldToOutput.add("kuzov");
        fieldToOutput.add("fuel");
        fieldToOutput.add("numberOfSpeed");

        testClean.cleanUp(testCar, fieldToCleanUp, fieldToOutput);

        String outputString = fieldToOutput.toString();

        assertEquals(outputString, outContent.toString());
    }
}