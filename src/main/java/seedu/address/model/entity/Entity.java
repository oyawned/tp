package seedu.address.model.entity;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Entity (champion/character) in the system.
 * Guarantees: details are present and not null, immutable.
 */
public class Entity {

    public static final String NOT_FOUND = "Entity %s not found!";

    // Identity fields
    private final String name;

    /**
     * Every field must be present and not null.
     */
    public Entity(String name) {
        requireNonNull(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns true if both entities have the same identity.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Entity)) {
            return false;
        }

        Entity otherEntity = (Entity) other;
        return name.equals(otherEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .toString();
    }
}
