package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.entity.Entity;
import seedu.address.model.entity.EntityStatisticMap;
import seedu.address.model.person.statistics.Statistics;
import seedu.address.testutil.PersonBuilder;

public class EntityContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Ahri");
        List<String> secondPredicateKeywordList = Arrays.asList("Ahri", "Yasuo");

        EntityContainsKeywordsPredicate firstPredicate = new EntityContainsKeywordsPredicate(firstPredicateKeywordList);
        EntityContainsKeywordsPredicate secondPredicate =
            new EntityContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EntityContainsKeywordsPredicate firstPredicateCopy =
            new EntityContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_entityContainsKeywords_returnsTrue() {
        // One keyword
        EntityContainsKeywordsPredicate predicate =
            new EntityContainsKeywordsPredicate(Collections.singletonList("Ahri"));
        EntityStatisticMap entityMap = new EntityStatisticMap();
        entityMap.addStatistics(new Entity("Ahri"), Statistics.createDefault());
        assertTrue(predicate.test(new PersonBuilder().withEntityStatistics(entityMap).build()));

        // Multiple keywords
        predicate = new EntityContainsKeywordsPredicate(Arrays.asList("Ahri", "Yasuo"));
        entityMap = new EntityStatisticMap();
        entityMap.addStatistics(new Entity("Yasuo"), Statistics.createDefault());
        assertTrue(predicate.test(new PersonBuilder().withEntityStatistics(entityMap).build()));

        // Only one matching keyword
        predicate = new EntityContainsKeywordsPredicate(Arrays.asList("Zed", "Ahri"));
        entityMap = new EntityStatisticMap();
        entityMap.addStatistics(new Entity("Ahri"), Statistics.createDefault());
        assertTrue(predicate.test(new PersonBuilder().withEntityStatistics(entityMap).build()));

        // Mixed-case keywords
        predicate = new EntityContainsKeywordsPredicate(Arrays.asList("aHrI", "YaSuO"));
        entityMap = new EntityStatisticMap();
        entityMap.addStatistics(new Entity("Ahri"), Statistics.createDefault());
        assertTrue(predicate.test(new PersonBuilder().withEntityStatistics(entityMap).build()));
    }

    @Test
    public void test_entityDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EntityContainsKeywordsPredicate predicate = new EntityContainsKeywordsPredicate(Collections.emptyList());
        EntityStatisticMap entityMap = new EntityStatisticMap();
        entityMap.addStatistics(new Entity("Ahri"), Statistics.createDefault());
        assertFalse(predicate.test(new PersonBuilder().withEntityStatistics(entityMap).build()));

        // Non-matching keyword
        predicate = new EntityContainsKeywordsPredicate(Arrays.asList("Zed"));
        entityMap = new EntityStatisticMap();
        entityMap.addStatistics(new Entity("Ahri"), Statistics.createDefault());
        assertFalse(predicate.test(new PersonBuilder().withEntityStatistics(entityMap).build()));

        // Keywords match name but not entity
        predicate = new EntityContainsKeywordsPredicate(Arrays.asList("Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Person has no entities
        predicate = new EntityContainsKeywordsPredicate(Arrays.asList("Ahri"));
        assertFalse(predicate.test(new PersonBuilder().build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        EntityContainsKeywordsPredicate predicate = new EntityContainsKeywordsPredicate(keywords);

        String expected = EntityContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
