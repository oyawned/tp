package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.EntityReference;
import seedu.address.model.match.PlayerInMatch;
import seedu.address.model.person.Name;
import seedu.address.model.person.statistics.Statistics;

/**
 * Jackson-friendly version of {@link PlayerInMatch}
 */
public class JsonAdaptedPlayerInMatch {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Player's %s field is missing!";

    private final String name;
    private final String entity;
    private final JsonAdaptedStatistics statistics;

    /**
     * Constructs a {@code JsonAdaptedPlayerInMatch} with the given player name, entity, and statistics.
     */
    @JsonCreator
    public JsonAdaptedPlayerInMatch(@JsonProperty("name") String name,
            @JsonProperty("entity") String entity,
            @JsonProperty("statistics") JsonAdaptedStatistics statistics) {
        this.name = name;
        this.entity = entity;
        this.statistics = statistics;
    }

    /**
     * Converts a given {@code PlayerInMatch} into this class for Jackson use.
     */
    public JsonAdaptedPlayerInMatch(PlayerInMatch source) {
        name = source.getName().fullName;
        entity = source.getEntity().getName();
        statistics = new JsonAdaptedStatistics(source.getStatistics());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code PlayerInMatch} object.
     */
    public PlayerInMatch toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (entity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Entity"));
        }
        final Entity modelEntity = EntityReference.findByName(entity)
                .orElseThrow(() -> new IllegalValueException(
                        String.format("Entity '%s' does not exist in the entity list.", entity)));

        if (statistics == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Statistics"));
        }
        final Statistics modelStatistics = statistics.toModelType();

        return new PlayerInMatch(modelName, modelStatistics, modelEntity);

    }

}
