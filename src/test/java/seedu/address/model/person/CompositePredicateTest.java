package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class CompositePredicateTest {

    @Test
    public void equals() {
        List<Predicate<Person>> firstPredicateList = Arrays.asList(
                new TagsContainsKeywordsPredicate(Collections.singletonList("friends")));
        List<Predicate<Person>> secondPredicateList = Arrays.asList(
                new TagsContainsKeywordsPredicate(Collections.singletonList("owesMoney")));

        CompositePredicate firstPredicate = new CompositePredicate(firstPredicateList);
        CompositePredicate secondPredicate = new CompositePredicate(secondPredicateList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CompositePredicate firstPredicateCopy = new CompositePredicate(firstPredicateList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_allPredicatesMatch_returnsTrue() {
        // Single predicate matches
        List<Predicate<Person>> predicates = Arrays.asList(
                new TagsContainsKeywordsPredicate(Collections.singletonList("friends")));
        CompositePredicate predicate = new CompositePredicate(predicates);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Multiple predicates all match
        predicates = Arrays.asList(
                new TagsContainsKeywordsPredicate(Collections.singletonList("friends")),
                new RoleContainsKeywordsPredicate(Collections.singletonList("JUNGLE")));
        predicate = new CompositePredicate(predicates);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").withRole("JUNGLE").build()));

        // Predicates in different order
        predicates = Arrays.asList(
                new RoleContainsKeywordsPredicate(Collections.singletonList("JUNGLE")),
                new TagsContainsKeywordsPredicate(Collections.singletonList("friends")));
        predicate = new CompositePredicate(predicates);
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").withRole("JUNGLE").build()));
    }

    @Test
    public void test_notAllPredicatesMatch_returnsFalse() {
        // First predicate matches, second doesn't
        List<Predicate<Person>> predicates = Arrays.asList(
                new TagsContainsKeywordsPredicate(Collections.singletonList("friends")),
                new RoleContainsKeywordsPredicate(Collections.singletonList("MID")));
        CompositePredicate predicate = new CompositePredicate(predicates);
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").withRole("JUNGLE").build()));

        // First predicate doesn't match, second does
        predicates = Arrays.asList(
                new TagsContainsKeywordsPredicate(Collections.singletonList("owesMoney")),
                new RoleContainsKeywordsPredicate(Collections.singletonList("JUNGLE")));
        predicate = new CompositePredicate(predicates);
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").withRole("JUNGLE").build()));

        // Neither predicate matches
        predicates = Arrays.asList(
                new TagsContainsKeywordsPredicate(Collections.singletonList("owesMoney")),
                new RoleContainsKeywordsPredicate(Collections.singletonList("MID")));
        predicate = new CompositePredicate(predicates);
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").withRole("JUNGLE").build()));
    }

    @Test
    public void test_emptyPredicates_returnsTrue() {
        // No predicates - should return true for any person
        List<Predicate<Person>> predicates = Collections.emptyList();
        CompositePredicate predicate = new CompositePredicate(predicates);
        assertTrue(predicate.test(new PersonBuilder().build()));
    }

    @Test
    public void toStringMethod() {
        List<Predicate<Person>> predicates = Arrays.asList(
                new TagsContainsKeywordsPredicate(Collections.singletonList("keyword")));
        CompositePredicate compositePredicate = new CompositePredicate(predicates);

        String expected = CompositePredicate.class.getCanonicalName() + "{predicates=" + predicates + "}";
        assertEquals(expected, compositePredicate.toString());
    }
}
