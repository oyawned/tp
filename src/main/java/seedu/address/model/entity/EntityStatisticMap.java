  package seedu.address.model.entity;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.statistics.Statistics;

/**
 * Manages a mapping of Entity objects to their Statistics.
 * Encapsulates Map<Entity, Statistics> operations.
 */
public class EntityStatisticMap {

    private final Map<Entity, Statistics> entityStats;

    /**
     * Constructs an empty EntityStatisticMap.
     */
    public EntityStatisticMap() {
        this.entityStats = new HashMap<>();
    }

    /**
     * Constructs an EntityStatisticMap with the given map.
     * @param entityStats Map of Entity to Statistics to store
     */
    public EntityStatisticMap(Map<Entity, Statistics> entityStats) {
        requireNonNull(entityStats);
        this.entityStats = new HashMap<>(entityStats);
    }

    /**
     * Returns the statistics for a given entity.
     * @param entity The entity to get statistics for
     * @return Statistics for the entity, or null if not found
     */
    public Statistics getStatistics(Entity entity) {
        return entityStats.get(entity);
    }

    /**
     * Returns the sum total of statistics for all entities.
     * @return
     */
    public Statistics getOverallStatistics() {
        return entityStats.values().stream().reduce((x, y) -> x.add(y)).orElse(Statistics.createDefault());
    }

    /**
     * Adds or updates statistics for a given entity.
     * @param entity The entity to add/update
     * @param statistics The statistics to associate with the entity
     */
    public void putStatistics(Entity entity, Statistics statistics) {
        requireNonNull(entity);
        requireNonNull(statistics);
        entityStats.put(entity, statistics);
    }

    /**
     * Checks if the map contains statistics for a given entity.
     * @param entity The entity to check
     * @return true if the entity exists in the map, false otherwise
     */
    public boolean containsKey(Entity entity) {
        return entityStats.containsKey(entity);
    }

    /**
     * Returns a collection of all statistics values.
     * @return Collection of all Statistics objects
     */
    public Collection<Statistics> values() {
        return entityStats.values();
    }

    /**
     * Returns the internal map for operations that need direct access.
     * @return The internal Map<Entity, Statistics>
     */
    public Map<Entity, Statistics> getMap() {
        return entityStats;
    }

    /**
     * Returns an unmodifiable view of the internal map.
     * @return Unmodifiable Map<Entity, Statistics>
     */
    public Map<Entity, Statistics> getUnmodifiableMap() {
        return Collections.unmodifiableMap(entityStats);
    }

    /**
     * Puts all entries from another map into this map.
     * @param other The other EntityStatisticMap to copy from
     */
    public void putAll(EntityStatisticMap other) {
        requireNonNull(other);
        entityStats.putAll(other.entityStats);
    }

    /**
     * Puts all entries from a Map into this map.
     * @param other The map to copy from
     */
    public void putAll(Map<Entity, Statistics> other) {
        requireNonNull(other);
        entityStats.putAll(other);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EntityStatisticMap)) {
            return false;
        }

        EntityStatisticMap otherEntityStatisticMap = (EntityStatisticMap) other;
        return entityStats.equals(otherEntityStatisticMap.entityStats);
    }

    @Override
    public int hashCode() {
        return entityStats.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("entityStats", entityStats)
                .toString();
    }

    public static class Builder {

        private EntityStatisticMap map;

        public Builder() {
            map = new EntityStatisticMap();
        }

        public Builder withEntity(Entity entity, Statistics stat) {
            map.putStatistics(entity, stat);
            return this;
        }

        public EntityStatisticMap build() {
            return map;
        }
    }
}
