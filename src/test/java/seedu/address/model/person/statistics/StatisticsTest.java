package seedu.address.model.person.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StatisticsTest {

    @Test
    public void createDefault_success() {
        Statistics defaultStats = Statistics.createDefault();
        assertEquals(new Kills("0"), defaultStats.getKills());
    }

    @Test
    public void builder_withKills_success() {
        Kills kills = new Kills("50");
        Statistics stats = new Statistics.Builder().withKills(kills).build();
        assertEquals(kills, stats.getKills());
    }

    @Test
    public void equals() {
        Statistics stats = new Statistics.Builder().withKills(new Kills("10")).build();

        // same values -> returns true
        assertTrue(stats.equals(new Statistics.Builder().withKills(new Kills("10")).build()));

        // same object -> returns true
        assertTrue(stats.equals(stats));

        // null -> returns false
        assertFalse(stats.equals(null));

        // different types -> returns false
        assertFalse(stats.equals("10"));

        // different values -> returns false
        assertFalse(stats.equals(new Statistics.Builder().withKills(new Kills("20")).build()));
    }

    @Test
    public void toStringMethod() {
        Statistics stats = new Statistics.Builder().withKills(new Kills("10")).build();
        assertEquals("Kills: 10", stats.toString());
    }
}
