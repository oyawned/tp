package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.EntityReference;
import seedu.address.model.match.PlayerInMatch;
import seedu.address.model.person.InGameName;
import seedu.address.model.person.statistics.Statistics;

/**
 * Jackson-friendly version of {@link PlayerInMatch}
 */
public class JsonAdaptedPlayerInMatch {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Player's %s field is missing!";

    private final String ign;
    private final String entity;
    private final JsonAdaptedStatistics statistics;

    /**
     * Constructs a {@code JsonAdaptedPlayerInMatch} with the given player ign, entity, and statistics.
     */
    @JsonCreator
    public JsonAdaptedPlayerInMatch(@JsonProperty("ign") String ign,
            @JsonProperty("entity") String entity,
            @JsonProperty("statistics") JsonAdaptedStatistics statistics) {
        this.ign = ign;
        this.entity = entity;
        this.statistics = statistics;
    }

    /**
     * Converts a given {@code PlayerInMatch} into this class for Jackson use.
     */
    public JsonAdaptedPlayerInMatch(PlayerInMatch source) {
        ign = source.getInGameName().toString();
        entity = source.getEntity().getName();
        statistics = new JsonAdaptedStatistics(source.getStatistics());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code PlayerInMatch} object.
     */
    public PlayerInMatch toModelType() throws IllegalValueException {

        if (ign == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    InGameName.class.getSimpleName()));
        }
        if (!InGameName.isValidIgn(ign)) {
            throw new IllegalValueException(InGameName.MESSAGE_CONSTRAINTS);
        }
        final InGameName modelIgn = new InGameName(ign);

        if (entity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Entity"));
        }
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

        return new PlayerInMatch(modelIgn, modelStatistics, modelEntity);

    }

}
