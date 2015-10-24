package averagedepth;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AverageDepthTest {
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

    private String runTest(int val1, int val2, int val3) {
        String testInput = val1 + " " + val2 + " " + val3;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setIn(new ByteArrayInputStream(testInput.getBytes()));
        System.setOut(ps);
        AverageDepth.main(null);
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
        String testOutput = runTest(25, 15, 10);
        checkStdOut(testOutput, 25, 10, 16);
    }

    @Test
    public void testAllSame() throws Exception {
        String testOutput = runTest(10, 10, 10);
        checkStdOut(testOutput, 10, 10, 10);
    }
}
