package seedu.address.model.person.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DeathsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Deaths(null));
    }

    @Test
    public void constructor_invalidDeaths_throwsIllegalArgumentException() {
        String invalidDeaths = "";
        assertThrows(IllegalArgumentException.class, () -> new Deaths(invalidDeaths));
    }

    @Test
    public void isValidDeaths() {
        // null deaths count
        assertThrows(NullPointerException.class, () -> Deaths.isValidDeaths(null));

        // invalid deaths count
        assertFalse(Deaths.isValidDeaths("")); // empty string
        assertFalse(Deaths.isValidDeaths(" ")); // spaces only
        assertFalse(Deaths.isValidDeaths("911a")); // contains letters
        assertFalse(Deaths.isValidDeaths("-1")); // negative number
        assertFalse(Deaths.isValidDeaths("124293842033123")); // long number exceeding Integer.MAX_VALUE

        // valid deaths count
        assertTrue(Deaths.isValidDeaths("911")); // normal number
        assertTrue(Deaths.isValidDeaths("0")); // zero
    }

    @Test
    public void equals() {
        Deaths deaths = new Deaths("10");

        // same values -> returns true
        assertTrue(deaths.equals(new Deaths("10")));

        // same object -> returns true
        assertTrue(deaths.equals(deaths));

        // null -> returns false
        assertFalse(deaths.equals(null));

        // different types -> returns false
        assertFalse(deaths.equals(5.0f));

        // different values -> returns false
        assertFalse(deaths.equals(new Deaths("20")));
    }

    @Test
    public void add() {
        Deaths deaths1 = new Deaths("5");
        Deaths deaths2 = new Deaths("10");
        assertEquals(new Deaths("15"), deaths1.add(deaths2));
    }

    @Test
    public void add_withOverflow_returnsMaxValue() {
        Deaths deaths1 = new Deaths(String.valueOf(Integer.MAX_VALUE - 1));
        Deaths deaths2 = new Deaths("5");
        assertEquals(new Deaths(String.valueOf(Integer.MAX_VALUE)), deaths1.add(deaths2));
    }

    @Test
    public void toStringMethod() {
        Deaths deaths = new Deaths("15");
        assertEquals("15", deaths.toString());
    }

    @Test
    public void hashCodeMethod() {
        Deaths deaths1 = new Deaths("10");
        Deaths deaths2 = new Deaths("10");
        assertEquals(deaths1.hashCode(), deaths2.hashCode());
    }
}
