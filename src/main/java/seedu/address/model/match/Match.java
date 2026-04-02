package seedu.address.model.match;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents details of a Match.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Match {

    private final LocalDate date;
    private final Result result;
    private final PlayersInMatch players;

    /**
     * Every field must be present and not null.
     */
    public Match(LocalDate date, Result result, PlayersInMatch players) {
        requireAllNonNull(date, result, players);

        this.result = result;
        this.date = date;
        this.players = players;
    }

    /**
     * Every field must be present and not null.
     * The date is set to the current time when the match is created.
     */
    public Match(Result result, PlayersInMatch players) {
        this(LocalDate.now(), result, players);
    }

    public LocalDate getDate() {
        return date;
    }

    public Result getResult() {
        return result;
    }

    public PlayersInMatch getPlayers() {
        return players;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Match otherMatch)) {
            return false;
        }

        return date.equals(otherMatch.date)
                && result.equals(otherMatch.result)
                && players.equals(otherMatch.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, result, players);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("creation date", date)
                .add("result", result)
                .add("players", players)
                .toString();
    }

}
