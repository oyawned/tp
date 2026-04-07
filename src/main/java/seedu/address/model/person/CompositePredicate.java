package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Predicate that is a combination of multiple predicates using AND logic.
 * All predicates must return true for the composite predicate to return true.
 */
public class CompositePredicate implements Predicate<Person> {
    private final List<Predicate<Person>> predicates;

    public CompositePredicate(List<Predicate<Person>> predicates) {
        this.predicates = predicates;
    }

    /**
     * Returns true if all predicates in the composite return true for the given person.
     * Returns true if the list of predicates is empty (no filters applied).
     */
    @Override
    public boolean test(Person person) {
        if (predicates.isEmpty()) {
            return true;
        }
        return predicates.stream().allMatch(predicate -> predicate.test(person));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompositePredicate)) {
            return false;
        }

        CompositePredicate otherCompositePredicate = (CompositePredicate) other;
        return predicates.equals(otherCompositePredicate.predicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("predicates", predicates).toString();
    }
}
