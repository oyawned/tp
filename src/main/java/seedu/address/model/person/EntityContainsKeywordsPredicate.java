package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code EntityStatisticMap} contains entities matching any of the keywords given.
 */
public class EntityContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public EntityContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns true if a {@code Person}'s entities match any of the keywords given.
     * The matching is case insensitive.
     */
    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword ->
                    person.getOverallEntityStatistics().getUnmodifiableMap().keySet().stream()
                    .anyMatch(entity ->
                        StringUtil.containsWordIgnoreCase(entity.getName(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EntityContainsKeywordsPredicate)) {
            return false;
        }

        EntityContainsKeywordsPredicate otherEntityContainsKeywordsPredicate = (EntityContainsKeywordsPredicate) other;
        return keywords.equals(otherEntityContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
