package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.statistics.Kills;
import seedu.address.model.person.statistics.Statistics;

/**
 * Jackson-friendly version of {@link Statistics}.
 */
class JsonAdaptedStatistics {

    private final String kills;

    /**
     * Constructs a {@code JsonAdaptedStatistics} with the given details.
     */
    @JsonCreator
    public JsonAdaptedStatistics(@JsonProperty("kills") String kills) {
        this.kills = kills;
    }

    /**
     * Converts a given {@code Statistics} into this class for Jackson use.
     */
    public JsonAdaptedStatistics(Statistics source) {
        kills = source.getKills().value.toString();
    }

    /**
     * Converts this Jackson-friendly adapted statistics object into the model's {@code Statistics} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted statistics.
     */
    public Statistics toModelType() throws IllegalValueException {
        if (kills == null) {
            throw new IllegalValueException(String.format("Statistics's kills field is missing!"));
        }
        if (!Kills.isValidKills(kills)) {
            throw new IllegalValueException(Kills.MESSAGE_CONSTRAINTS);
        }
        final Kills modelKills = new Kills(kills);
        return new Statistics.Builder().withKills(modelKills).build();
    }
}
