package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class RoleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("JUNGLE");
        List<String> secondPredicateKeywordList = Arrays.asList("JUNGLE", "MID");

        RoleContainsKeywordsPredicate firstPredicate = new RoleContainsKeywordsPredicate(firstPredicateKeywordList);
        RoleContainsKeywordsPredicate secondPredicate = new RoleContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoleContainsKeywordsPredicate firstPredicateCopy = new RoleContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_roleContainsKeywords_returnsTrue() {
        // One keyword
        RoleContainsKeywordsPredicate predicate =
            new RoleContainsKeywordsPredicate(Collections.singletonList("JUNGLE"));
        assertTrue(predicate.test(new PersonBuilder().withRole("JUNGLE").build()));

        // Multiple keywords
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("JUNGLE", "MID"));
        assertTrue(predicate.test(new PersonBuilder().withRole("MID").build()));

        // Only one matching keyword
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("TOP", "JUNGLE"));
        assertTrue(predicate.test(new PersonBuilder().withRole("JUNGLE").build()));

        // Mixed-case keywords
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("jUnGlE", "MiD"));
        assertTrue(predicate.test(new PersonBuilder().withRole("JUNGLE").build()));
    }

    @Test
    public void test_roleDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withRole("JUNGLE").build()));

        // Non-matching keyword
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("TOP"));
        assertFalse(predicate.test(new PersonBuilder().withRole("JUNGLE").build()));

        // Keywords match name but not role
        predicate = new RoleContainsKeywordsPredicate(Arrays.asList("Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withRole("JUNGLE").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(keywords);

        String expected = RoleContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
