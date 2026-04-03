package seedu.address.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * Manages a list of Entity objects.
 * Entities are loaded from a JSON file and are not modifiable by users.
 * However, the list of reference entities can be reloaded (future feature)
 */
public class EntityReference {

    private static List<Entity> loadedEntities;
    private final List<Entity> entities;

    /**
     * Constructs an EntityReference with the given list of entities.
     * @param entities List of Entity objects to store
     */
    public EntityReference(List<Entity> entities) {
        EntityReference.loadedEntities = new ArrayList<>(entities);
        this.entities = new ArrayList<>(entities);
    }

    /**
     * Returns an unmodifiable list of all entities.
     */
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    /**
     * Returns the currently set reference entity list.
     */
    public List<Entity> getLoadedEntities() {
        return loadedEntities;
    }

    /**
     * Reloads this objects entities into the static reference.
     * @return
     */

    public void reload() {
        EntityReference.loadedEntities = new ArrayList<>(this.entities);
    }

    /**
     * Finds an entity by name. Uses the loaded entity list.
     * @param name The name of the entity to find
     * @return Optional containing the entity if found, empty otherwise
     */
    public static Optional<Entity> findByName(String name) {
        return loadedEntities.stream()
                .filter(entity -> entity.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Checks if an entity with the given name exists. Uses the loaded entity list.
     * @param name The name to check
     * @return true if an entity with the name exists, false otherwise
     */
    public static boolean hasEntity(String name) {
        return findByName(name).isPresent();
    }

    @Override
    public int hashCode() {
        return entities.hashCode();
    }

    @Override
    public String toString() {
        return entities.toString();
    }
}