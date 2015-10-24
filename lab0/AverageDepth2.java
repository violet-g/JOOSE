package averagedepth2;

import java.util.Scanner;

public class AverageDepth2 {

    public static void main(String[] args) {
        
    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.print("Type number of measurements: ");
    	int numMeasurements = scanner.nextInt();
    	
    	double maxDepth = 0;
    	double minDepth = 750;
    	double averageDepth = 0;
    	double sum = 0;
    	
    	for ( int i = 1; i <= numMeasurements; i++) {
    		System.out.print("Type measurement " + i + ": ");
            double nextMeasurement = scanner.nextDouble();
            if (nextMeasurement < 0 || nextMeasurement > 750) {
            	System.out.println("Measurement out of range!");
            	i--;
            	continue;
            }
            if (nextMeasurement > maxDepth) {
            	maxDepth = nextMeasurement;
            }
            if (nextMeasurement < minDepth) {
            	minDepth = nextMeasurement;
            }
            sum += nextMeasurement;
    	}
    	
    	averageDepth = sum/numMeasurements;
        
        scanner.close();
        
        System.out.println();
        System.out.println("Maximum depth = " + maxDepth);
        System.out.println("Minimum depth = " + minDepth);
        System.out.println("Average depth = " + averageDepth);
         	
    }

}
