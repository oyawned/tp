package seedu.address.model.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EntityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Entity(null));
    }

    @Test
    public void getName() {
        Entity entity = new Entity("validentity");
        assertTrue(entity.getName().equals("validentity"));
    }

    @Test
    public void equals() {
        Entity entity = new Entity("validentity");

        // same values -> returns true
        assertTrue(entity.equals(new Entity("validentity")));

        // same object -> returns true
        assertTrue(entity.equals(entity));

        // null -> returns false
        assertFalse(entity.equals(null));

        // different types -> returns false
        assertFalse(entity.equals(5.0f));

        // different values -> returns false
        assertFalse(entity.equals(new Entity("Other Valid Entity")));
    }
}
