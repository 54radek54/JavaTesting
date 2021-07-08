package itacalc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class AppTest
{

        @Test
        public void testEmptyString() {
            assertEquals(0, App.add(""));
        }

        @Test
        public void testOneNumber() {
            assertEquals(1, App.add("1"));
            assertEquals(5, App.add("5"));
        }

        @Test
        public void testTwoNumbers(){
            assertEquals(3, App.add("1,2"));
            assertEquals(8, App.add("3,5"));
        }

        @Test
        public void testThreeNumbers(){
            assertEquals(6, App.add("1,2,3"));
            assertEquals(7, App.add("2,2,3"));
        }

        @Test
        public void testNewLine(){
            assertEquals(6, App.add("1\n2,3"));
            assertEquals(7, App.add("2,2\n3"));
        }

        @Test
        public void testNegativeNumber(){
            Exception exception=assertThrows(NegException.class, ()-> App.add("-1,2"));
            assertEquals("Negatives not allowed", exception.getMessage());
            Exception exception1=assertThrows(NegException.class, ()-> App.add("-1,2,4,-5"));
            assertEquals("Negatives not allowed", exception1.getMessage());
        }

        @Test
        public void testOverThousand(){
            assertEquals(2, App.add("1000,2"));
            assertEquals(5, App.add("1000,3,2"));
        }

        @Test
        public void testOtherDelimiter(){
            assertEquals(3, App.add("//;\n1;2"));
            assertEquals(8, App.add("//;\n6;2"));
        }
    }

