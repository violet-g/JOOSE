package averagedepth2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AverageDepth2Test {
    InputStream stdin;
    PrintStream stdout;

    @Before
    public void setup() {
        stdin = System.in;
        stdout = System.out;
    }

    @After
    public void teardown() {
        System.setIn(stdin);
        System.setOut(stdout);
    }

    private String runTest(int numValues, double... values) {
        String testInput = numValues + " ";
        for (double value : values) {
            testInput += value + System.lineSeparator();
        }
        testInput = testInput.trim();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setIn(new ByteArrayInputStream(testInput.getBytes()));
        System.setOut(ps);
        AverageDepth2.main(null);
        System.out.flush();
        return baos.toString();
    }

    private void checkStdOut(String testOutput, double max, double min, double avg) {
        boolean hasMax = false, hasMin = false, hasAvg = false;
        for (String line : testOutput.toLowerCase().split(System.lineSeparator())) {
            line = line.trim();
            if (line.contains("max")) {
                Assert.assertTrue("Maximum value wrong", line.endsWith(String.valueOf(max)));
                hasMax = true;
            } else if (line.contains("min")) {
                Assert.assertTrue("Minimum value wrong", line.endsWith(String.valueOf(min)));
                hasMin = true;
            } else if (line.contains("average")) {
                Assert.assertTrue("Average value wrong", line.endsWith(String.valueOf(avg)));
                hasAvg = true;
            }
        }
        Assert.assertTrue("Maximum value missing", hasMax);
        Assert.assertTrue("Minimum value missing", hasMin);
        Assert.assertTrue("Average value missing", hasAvg);
    }

    @Test
    public void testExample() throws Exception {
        String testOut = runTest(5, 10.5, 17.3, -50, 50, 103.5, 800, 80);
        checkStdOut(testOut, 103.5, 10.5, 52.260000000000005);
    }

}

