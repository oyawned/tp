package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.statistics.Deaths;
import seedu.address.model.person.statistics.Kills;
import seedu.address.model.person.statistics.Statistics;

public class JsonAdaptedStatisticsTest {

    private static final String INVALID_KILLS = "-1";
    private static final String VALID_KILLS = "10";
    private static final String INVALID_DEATHS = "-2";
    private static final String VALID_DEATHS = "5";

    @Test
    public void toModelType_validStatisticsDetails_returnsStatistics() throws Exception {
        JsonAdaptedStatistics statistics = new JsonAdaptedStatistics(VALID_KILLS, VALID_DEATHS);
        Statistics expectedStatistics = new Statistics.Builder()
                .withKills(new Kills(VALID_KILLS))
                .withDeaths(new Deaths(VALID_DEATHS))
                .build();
        assertEquals(expectedStatistics, statistics.toModelType());
    }

    @Test
    public void toModelType_nullKills_throwsIllegalValueException() {
        JsonAdaptedStatistics statistics = new JsonAdaptedStatistics((String) null, VALID_DEATHS);
        String expectedMessage = String.format("Statistics's kills field is missing!");
        assertThrows(IllegalValueException.class, expectedMessage, statistics::toModelType);
    }

    @Test
    public void toModelType_invalidKills_throwsIllegalValueException() {
        JsonAdaptedStatistics statistics = new JsonAdaptedStatistics(INVALID_KILLS, VALID_DEATHS);
        String expectedMessage = Kills.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, statistics::toModelType);
    }

    @Test
    public void toModelType_nullDeaths_throwsIllegalValueException() {
        JsonAdaptedStatistics statistics = new JsonAdaptedStatistics(VALID_KILLS, (String) null);
        String expectedMessage = String.format("Statistics's deaths field is missing!");
        assertThrows(IllegalValueException.class, expectedMessage, statistics::toModelType);
    }

    @Test
    public void toModelType_invalidDeaths_throwsIllegalValueException() {
        JsonAdaptedStatistics statistics = new JsonAdaptedStatistics(VALID_KILLS, INVALID_DEATHS);
        String expectedMessage = Deaths.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, statistics::toModelType);
    }
}
