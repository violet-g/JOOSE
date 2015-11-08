import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import workers.HourlyEmployee;
import workers.PermanentEmployee;
import workers.SalariedEmployee;
import workers.TemporaryEmployee;
import workers.Waged;

/**
 * Java lab 6
 * Main driver class
 * This class sets up some instances of 
 * Employees for a nameless university,
 * then works out the amount required to
 * pay them all for this month.
 * @author mefoster (based on code from jsinger)
 */

public class UniversityPayroll {
    
    public static void main(String [] args) {
        Waged [] professors = {
            new SalariedEmployee("Muscatelli",
            					 "Anton",
                                 LocalDate.of(2009, 10, 1),
                                 250000), /* https://www.whatdotheyknow.com/request/24757/response/66126/attach/2/RESPONSE%20F0081590.pdf */
            new SalariedEmployee("Dumbledore",
            					 "Albus",
                                 LocalDate.of(1881, 1, 1),
                                 50000) /* conversion from galleons, sickles and knuts */
        };

        HourlyEmployee [] otherStaff = {
            new PermanentEmployee("Builder",
                                  "Bob",
                                  LocalDate.of(2013, 1, 1),
                                  7.50,
                                  100),
            new PermanentEmployee("Builder",
                                  "Wendy",
                                  LocalDate.of(2013, 1, 1),
                                  7.50,
                                  120),
            new TemporaryEmployee("Bloggs",
                                  "Freda",
                                  LocalDate.of(2010, 10, 10),
                                  7.20, /* "National living wage" -- https://www.gov.uk/national-minimum-wage-rates */
                                  100),
        };

        for (HourlyEmployee h : otherStaff) {
            h.setHoursThisMonth(120);
        }
        
        List<Waged> allWorkers = new ArrayList<Waged>();

        allWorkers.addAll(Arrays.asList(professors));
        allWorkers.addAll(Arrays.asList(otherStaff));
        
        // OPTIONAL: see if you can modify this code to use the Java Streams API
        double amountToPay = 0;
        for (Waged w : allWorkers) {
            double pay = w.calculateMonthlyWage();
            System.out.printf("%s pay: %.2f\n", w.toString(), pay);
            amountToPay += pay;
        }

        System.out.println("----------");
        System.out.printf("total amount to pay: %.2f\n", amountToPay);
        
    }
}

