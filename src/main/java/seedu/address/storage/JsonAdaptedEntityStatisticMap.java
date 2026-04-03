package seedu.address.storage;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.EntityStatisticMap;
import seedu.address.model.person.statistics.Statistics;

/**
 * Jackson-friendly version of {@link EntityStatisticMap}.
 */
class JsonAdaptedEntityStatisticMap {

    private final Map<String, EntityData> entityStatistics = new HashMap<>();

    /**
     * Helper class to store entity data (name and iconPath) along with statistics.
     */
    public static class EntityData {
        private final JsonAdaptedStatistics statistics;
        private final String iconPath;

        @JsonCreator
        public EntityData(@JsonProperty("statistics") JsonAdaptedStatistics statistics,
                        @JsonProperty("iconPath") String iconPath) {
            this.statistics = statistics;
            this.iconPath = iconPath;
        }

        public JsonAdaptedStatistics getStatistics() {
            return statistics;
        }

        public String getIconPath() {
            return iconPath;
        }
    }

    /**
     * Constructs a {@code JsonAdaptedEntityStatisticMap} with the given map.
     */
    @JsonCreator
    public JsonAdaptedEntityStatisticMap(
            @JsonProperty("entityStatistics") Map<String, EntityData> entityStatistics) {
        if (entityStatistics != null) {
            this.entityStatistics.putAll(entityStatistics);
        }
    }

    /**
     * Converts a given {@code EntityStatisticMap} into this class for Jackson use.
     */
    public JsonAdaptedEntityStatisticMap(EntityStatisticMap source) {
        Map<Entity, Statistics> map = source.getMap();
        for (Map.Entry<Entity, Statistics> entry : map.entrySet()) {
            EntityData entityData = new EntityData(
                new JsonAdaptedStatistics(entry.getValue()),
                entry.getKey().getIconPath()
            );
            entityStatistics.put(entry.getKey().getName(), entityData);
        }
    }

    /**
     * Converts this Jackson-friendly adapted entity statistics map into the model's
     * {@code EntityStatisticMap} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EntityStatisticMap toModelType() throws IllegalValueException {
        EntityStatisticMap entityStatisticMap = new EntityStatisticMap();
        for (Map.Entry<String, EntityData> entry : entityStatistics.entrySet()) {
            String name = entry.getKey();
            String iconPath = entry.getValue().getIconPath();
            // Use name as iconPath if iconPath is null or empty to match PersonBuilder behavior
            if (iconPath == null || iconPath.isEmpty()) {
                iconPath = name;
            }
            Entity entity = new Entity(name, iconPath);
            Statistics statistics = entry.getValue().getStatistics().toModelType();
            entityStatisticMap.addStatistics(entity, statistics);
        }
        return entityStatisticMap;
    }
}
