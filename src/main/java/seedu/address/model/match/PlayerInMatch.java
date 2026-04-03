package seedu.address.model.match;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.entity.Entity;
import seedu.address.model.person.Name;
import seedu.address.model.person.statistics.Statistics;

/**
 * Represents a player involved a match.
 */
public class PlayerInMatch {

    private final Statistics statistics;
    private final Name name;
    private final Entity entity;

    /**
     * Constructs a {@code PlayerInMatch}
     *
     * @param name The name of the player.
     * @param statistics The statistics of the player in this match.
     * @param entity The entity used by the player in this match.
     */
    public PlayerInMatch(Name name, Statistics statistics, Entity entity) {
        requireAllNonNull(name, statistics, entity);
        this.name = name;
        this.statistics = statistics;
        this.entity = entity;
    }

    public Name getName() {
        return name;
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

        return this.name.equals(otherPlayer.name) && this.statistics.equals(otherPlayer.statistics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, statistics);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("statistics", statistics)
                .toString();
    }

}
