package averagedepth;

import java.util.Scanner;

public class AverageDepth {

	public static void main(String[] args) {
		
        Scanner entries = new Scanner(System.in);
        System.out.print("Type measurement 1: ");
        int measurement1 = entries.nextInt();
        
        System.out.print("Type measurement 2: ");
        int measurement2 = entries.nextInt();
        
        System.out.print("Type measurement 3: ");
        int measurement3 = entries.nextInt();
        
        entries.close();
        
        double maxDepth;
        if (measurement1 > measurement2) {
        	if (measurement1 > measurement3) {
        		maxDepth = measurement1;
        	} else {
        		maxDepth = measurement3;
        	}
        } else {
        	if (measurement2 > measurement3) {
        		maxDepth = measurement2;
        	} else {
        		maxDepth = measurement3;
        	}	
        }
        
        double minDepth;
        if (measurement1 < measurement2) {
        	if (measurement1 < measurement3) {
        		minDepth = measurement1;
        	} else {
        		minDepth = measurement3;
        	}
        } else {
        	if (measurement2 < measurement3) {
        		minDepth = measurement2;
        	} else {
        		minDepth = measurement3;
        	}
        }
        
        double averageDepth = (measurement1 + measurement2 + measurement3)/3;
        
        System.out.println();
        System.out.println("Maximum depth = " + maxDepth);
        System.out.println("Minimum depth = " + minDepth);
        System.out.println("Average depth = " + averageDepth);
        
	}
}
