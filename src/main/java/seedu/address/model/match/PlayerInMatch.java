package seedu.address.model.match;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.entity.Entity;
import seedu.address.model.person.InGameName;
import seedu.address.model.person.statistics.Statistics;

/**
 * Represents a player involved a match.
 */
public class PlayerInMatch {

    private final Statistics statistics;
    private final InGameName ign;
    private final Entity entity;

    /**
     * Constructs a {@code PlayerInMatch}
     *
     * @param ign The ign of the player.
     * @param statistics The statistics of the player in this match.
     * @param entity The entity used by the player in this match.
     */
    public PlayerInMatch(InGameName ign, Statistics statistics, Entity entity) {
        requireAllNonNull(ign, statistics, entity);
        this.ign = ign;
        this.statistics = statistics;
        this.entity = entity;
    }

    public InGameName getInGameName() {
        return ign;
    }

    public Entity getEntity() {
        return entity;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PlayerInMatch otherPlayer)) {
            return false;
        }

        return this.ign.equals(otherPlayer.ign) && this.statistics.equals(otherPlayer.statistics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ign, statistics);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(ign.toString())
                .add("statistics", statistics)
                .toString();
    }

}
