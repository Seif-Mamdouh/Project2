import org.junit.Test;
import static org.junit.Assert.*;

public class DateTest {
    @Test
    public void isValid() {

        //Test valid dates
        assertTrue(new Date(9, 12, 1900).isValid());
        assertTrue(new Date(1, 1, 2022).isValid());
        assertTrue(new Date(29, 2, 2024).isValid());

        // Test invalid dates
        assertFalse(new Date(0, 1, 2022).isValid());  // Invalid day
        assertFalse(new Date(1, 0, 2022).isValid());  // Invalid month
        assertFalse(new Date(1, 1, 1899).isValid());  // Year before the minimum (1900)
        assertFalse(new Date(1, 13, 2022).isValid());  // Invalid month

    }
}