package PrimeFactors;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AppTest extends TestCase {


    //Testy do primefactors
        private List<Integer> list(int... factors){
            List<Integer> listOfFactors = new ArrayList<>();

            for(int i : factors){
                listOfFactors.add(i);
            }
            return listOfFactors;
        }

        @Test
        public void testPrimeFactors() {
            //test(list(), 1);


            assertEquals(list(), App.primeFactors(1));
            assertEquals(list(2), App.primeFactors(2));
        }

        @Test
        public void testTwo() {
            assertEquals(list(2), App.primeFactors(2));
        }

        @Test
        public void testThree() {
            assertEquals(list(3), App.primeFactors(3));
        }

        @Test
        public void testFour() {
            assertEquals(list(2,2), App.primeFactors(4));
        }

        @Test
        public void testSeventyTwo() {
            assertEquals(list(2,2,2,3,3), App.primeFactors(72));
        }

    @Test
    public void testOneHundredTwentyEight() {
        assertEquals(list(2,2,2,2,2,2,2), App.primeFactors(128));
    }

}
