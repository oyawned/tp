package seedu.address.model.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

public class EntityPathPairTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EntityPathPair(null, null));
        
        assertThrows(NullPointerException.class, () -> new EntityPathPair(null, Path.of("/")));
        
        assertThrows(NullPointerException.class, () -> new EntityPathPair(new Entity("validentity"), null));
    }

    @Test
    public void getEntity() {
        EntityPathPair entity = new EntityPathPair(new Entity("validentity"), Path.of("/"));
        assertTrue(entity.getEntity().equals(new Entity("validentity")));
    }

    @Test
    public void getPath() {
        EntityPathPair entity = new EntityPathPair(new Entity("validentity"), Path.of("/"));
        assertTrue(entity.getPath().equals(Path.of("/")));
    }

    @Test
    public void equals() {
        EntityPathPair entity = new EntityPathPair(new Entity("validentity"), Path.of("/"));

        // same values -> returns true
        assertTrue(entity.equals(new EntityPathPair(new Entity("validentity"), Path.of("/"))));

        // same object -> returns true
        assertTrue(entity.equals(entity));

        // null -> returns false
        assertFalse(entity.equals(null));

        // different types -> returns false
        assertFalse(entity.equals(5.0f));

        // different values -> returns false
        assertFalse(entity.equals(new EntityPathPair(new Entity("validentity"), Path.of("/abc/"))));
        assertFalse(entity.equals(new EntityPathPair(new Entity("validentity1"), Path.of("/"))));
    }
}
