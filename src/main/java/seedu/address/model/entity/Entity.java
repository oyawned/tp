package seedu.address.model.entity;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Entity (champion/character) in the system.
 * Guarantees: details are present and not null, immutable.
 */
public class Entity {

    // Identity fields
    private final String name;
    private final String iconPath;

    /**
     * Every field must be present and not null.
     */
    public Entity(String name, String iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    public String getName() {
        return name;
    }

    public String getIconPath() {
        return iconPath;
    }

    /**
     * Returns true if both entities have the same name.
     * This defines a weaker notion of equality between two entities.
     */
    public boolean isSameEntity(Entity otherEntity) {
        if (otherEntity == this) {
            return true;
        }

        return otherEntity != null
                && otherEntity.getName().equals(getName());
    }

    /**
     * Returns true if both entities have the same identity and data fields.
     * This defines a stronger notion of equality between two entities.
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
        return name.equals(otherEntity.name)
                && iconPath.equals(otherEntity.iconPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, iconPath);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("iconPath", iconPath)
                .toString();
    }

    public static Entity createDefault() {
        return new Entity("Default Entity", "path/to/default/icon.png");
    }

}