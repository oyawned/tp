package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.match.PlayerInMatch;
import seedu.address.model.person.InGameName;
import seedu.address.model.person.statistics.Statistics;

/**
 * Jackson-friendly version of {@link PlayerInMatch}
 */
public class JsonAdaptedPlayerInMatch {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Player's %s field is missing!";

    private final String ign;
    private final JsonAdaptedStatistics statistics;

    /**
     * Constructs a {@code JsonAdaptedPlayerInMatch} with the given player ign and statistics.
     */
    @JsonCreator
    public JsonAdaptedPlayerInMatch(@JsonProperty("ign") String ign,
            @JsonProperty("statistics") JsonAdaptedStatistics statistics) {
        this.ign = ign;
        this.statistics = statistics;
    }

    /**
     * Converts a given {@code PlayerInMatch} into this class for Jackson use.
     */
    public JsonAdaptedPlayerInMatch(PlayerInMatch source) {
        ign = source.getInGameName().toString();
        statistics = new JsonAdaptedStatistics(source.getStatistics());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code PersonInMatch} object.
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

        if (statistics == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Statistics"));
        }
        final Statistics modelStatistics = statistics.toModelType();

        return new PlayerInMatch(modelIgn, modelStatistics);

    }

}
