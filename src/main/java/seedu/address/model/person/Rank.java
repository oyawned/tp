package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's League of Legends rank in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRank(String)}
 * <p>
 * Rank format: "TIER [DIVISION]" (e.g., "GOLD I", "SILVER IV", "MASTER", "CHALLENGER")
 * Divisions I, II, III, IV are for ranks IRON through DIAMOND.
 * MASTER, GRANDMASTER, and CHALLENGER have no divisions.
 */
public class Rank {

    public static final String MESSAGE_CONSTRAINTS = "Rank should be a valid League of Legends rank with optional division. "
            + "Valid ranks are: IRON, BRONZE, SILVER, GOLD, PLATINUM, DIAMOND (each with I, II, III, or IV), "
            + "and MASTER, GRANDMASTER, CHALLENGER (without divisions).";

    /**
     * Represents the set of supported League of Legends rank tiers.
     * Ordered from lowest to highest rank.
     */
    public enum RankTier {
        IRON(0), BRONZE(1), SILVER(2), GOLD(3), PLATINUM(4),
        DIAMOND(5), MASTER(6), GRANDMASTER(7), CHALLENGER(8);

        private final int tierValue;

        RankTier(int tierValue) {
            this.tierValue = tierValue;
        }

        public int getTierValue() {
            return tierValue;
        }

        /**
         * Returns true if this tier has divisions (IRON through DIAMOND).
         */
        public boolean hasDivisions() {
            return this.tierValue <= DIAMOND.tierValue;
        }
    }

    /**
     * Represents divisions within a tier (I, II, III, IV).
     * Ordered from lowest to highest division within a tier.
     */
    public enum Division {
        I(0), II(1), III(2), IV(3);

        private final int divisionValue;

        Division(int divisionValue) {
            this.divisionValue = divisionValue;
        }

        public int getDivisionValue() {
            return divisionValue;
        }
    }

    public final RankTier tier;
    public final Division division; // null for MASTER, GRANDMASTER, CHALLENGER

    /**
     * Constructs a {@code Rank} with the specified tier and optional division.
     * Format: "TIER" or "TIER DIVISION" (e.g., "GOLD I", "MASTER")
     *
     * @param rankString A valid League of Legends rank string.
     */
    public Rank(String rankString) {
        requireNonNull(rankString);
        String trimmed = rankString.trim().toUpperCase();
        checkArgument(isValidRank(trimmed), MESSAGE_CONSTRAINTS);

        String[] parts = trimmed.split("\\s+");
        this.tier = RankTier.valueOf(parts[0]);

        if (parts.length == 2) {
            this.division = Division.valueOf(parts[1]);
        } else {
            this.division = null;
        }
    }

    /**
     * Returns true if a given string is a valid rank (tier with optional division).
     */
    public static boolean isValidRank(String test) {
        try {
            String trimmed = test.trim().toUpperCase();
            String[] parts = trimmed.split("\\s+");

            if (parts.length < 1 || parts.length > 2) {
                return false;
            }

            RankTier tier = RankTier.valueOf(parts[0]);

            if (parts.length == 1) {
                // No division specified - only valid for top 3 ranks
                return !tier.hasDivisions();
            } else {
                // Division specified - only valid for ranks with divisions
                if (!tier.hasDivisions()) {
                    return false;
                }
                Division.valueOf(parts[1]); // Will throw if invalid
                return true;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        if (division != null) {
            return tier + " " + division;
        }
        return tier.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Rank)) {
            return false;
        }

        Rank otherRank = (Rank) other;
        return tier.equals(otherRank.tier) && 
               ((division == null && otherRank.division == null) || 
                (division != null && division.equals(otherRank.division)));
    }

    @Override
    public int hashCode() {
        return tier.hashCode() * 31 + (division != null ? division.hashCode() : 0);
    }

}
