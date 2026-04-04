package seedu.address.model.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTITY_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTITY_NAME_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTITY_REFERENCE;

import org.junit.jupiter.api.Test;

public class EntityReferenceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Entity(null));
    }

    @Test
    public void reloadTest() {
        EntityReference emptyEntityReference = new EntityReference(new ArrayList<>());
        emptyEntityReference.reload();
        assertTrue(emptyEntityReference.getEntities().equals(EntityReference.getLoadedEntities()));
        assertFalse(VALID_ENTITY_REFERENCE.equals(EntityReference.getLoadedEntities()));
        VALID_ENTITY_REFERENCE.reload();
        assertFalse(emptyEntityReference.getEntities().equals(EntityReference.getLoadedEntities()));
        assertTrue(VALID_ENTITY_REFERENCE.getEntities().equals(EntityReference.getLoadedEntities()));
    }

    @Test
    public void entityRetrievalTest() {
        VALID_ENTITY_REFERENCE.reload();
        assertTrue(EntityReference.hasEntity(VALID_ENTITY_NAME_1));
        assertFalse(EntityReference.hasEntity("INVALID_ENTITY"));
    
        assertTrue(EntityReference.findByName(VALID_ENTITY_NAME_1).get().equals(VALID_ENTITY_1));
        assertTrue(EntityReference.findByName("INVALID_ENTITY").isEmpty());

        EntityReference emptyEntityReference = new EntityReference(new ArrayList<>());
        emptyEntityReference.reload();
        assertFalse(EntityReference.hasEntity(VALID_ENTITY_NAME_1));
        assertFalse(EntityReference.hasEntity("INVALID_ENTITY"));
    
        assertTrue(EntityReference.findByName(VALID_ENTITY_NAME_1).isEmpty());
        assertTrue(EntityReference.findByName("INVALID_ENTITY").isEmpty());
    }
}
