package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's in-game name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIgn(String)}
 */
public class InGameName {

    public static final int MAX_LENGTH = 16;
    public static final String MESSAGE_CONSTRAINTS = "IGN (In-Game Name) can take any values, "
            + "it should not be blank, it should not contain whitespace, and it must be at most "
            + MAX_LENGTH + " characters long";

    /*
     * IGN must contain at least one non-whitespace character and
     * must not contain any whitespace characters.
     */
    public static final String VALIDATION_REGEX = "\\S+";

    public final String value;

    /**
     * Constructs an {@code InGameName}.
     *
     * @param ign A valid in-game name.
     */
    public InGameName(String ign) {
        requireNonNull(ign);
        checkArgument(isValidIgn(ign), MESSAGE_CONSTRAINTS);
        value = ign;
    }

    /**
     * Returns true if a given string is a valid IGN.
     */
    public static boolean isValidIgn(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= MAX_LENGTH;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InGameName)) {
            return false;
        }

        InGameName otherIgn = (InGameName) other;
        return value.equalsIgnoreCase(otherIgn.value);
    }

    @Override
    public int hashCode() {
        return value.toLowerCase().hashCode();
    }

}
