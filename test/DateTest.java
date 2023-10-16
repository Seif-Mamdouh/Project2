import org.junit.Test;
import static org.junit.Assert.*;

public class DateTest {
    @Test
    public void isValid() {
        //Test valid date
        assertTrue(new Date(9, 12, 1900).isValid());
    }

    @Test
    public void inValidDay(){
        assertFalse(new Date(0, 1, 2022).isValid());  // Invalid day
    }

    @Test
    public void invalidMonth(){
        assertFalse(new Date(1, 0, 2022).isValid());  // Invalid montn
    }

    @Test
    public void notMinYear(){
        assertFalse(new Date(1, 1, 1899).isValid());  // Year before the minimum (1900)
    }

    @Test
    public void inValidMonth(){
        assertFalse(new Date(1, 13, 2022).isValid());  // Invalid month
    }
}