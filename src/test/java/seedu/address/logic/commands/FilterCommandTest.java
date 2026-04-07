package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CompositePredicate;
import seedu.address.model.person.EntityContainsKeywordsPredicate;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.model.person.TagsContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<java.util.function.Predicate<seedu.address.model.person.Person>> firstPredicates =
                Arrays.asList(new TagsContainsKeywordsPredicate(Collections.singletonList("first")));
        List<java.util.function.Predicate<seedu.address.model.person.Person>> secondPredicates =
                Arrays.asList(new TagsContainsKeywordsPredicate(Collections.singletonList("second")));

        CompositePredicate firstPredicate = new CompositePredicate(firstPredicates);
        CompositePredicate secondPredicate = new CompositePredicate(secondPredicates);

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_tagFilter_filtersPersonsByTag() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        CompositePredicate predicate = prepareTagPredicate("friends owesMoney");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_roleFilter_filtersPersonsByRole() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        CompositePredicate predicate = prepareRolePredicate("jungle");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_combinedFilter_filtersPersonsByTagAndRole() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        CompositePredicate predicate = prepareCombinedPredicate("friends", "jungle");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void toStringMethod() {
        TagsContainsKeywordsPredicate tagPredicate = new TagsContainsKeywordsPredicate(Arrays.asList("keyword"));
        List<java.util.function.Predicate<seedu.address.model.person.Person>> predicates =
                Arrays.asList(tagPredicate);
        CompositePredicate compositePredicate = new CompositePredicate(predicates);
        FilterCommand filterCommand = new FilterCommand(compositePredicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + compositePredicate + "}";
        assertEquals(expected, filterCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code CompositePredicate} with tag filter.
     */
    private CompositePredicate prepareTagPredicate(String userInput) {
        List<java.util.function.Predicate<seedu.address.model.person.Person>> predicates =
                new ArrayList<>();
        predicates.add(new TagsContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        return new CompositePredicate(predicates);
    }

    /**
     * Parses {@code userInput} into a {@code CompositePredicate} with role filter.
     */
    private CompositePredicate prepareRolePredicate(String userInput) {
        List<java.util.function.Predicate<seedu.address.model.person.Person>> predicates =
                new ArrayList<>();
        predicates.add(new RoleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        return new CompositePredicate(predicates);
    }

    /**
     * Parses {@code userInput} into a {@code CompositePredicate} with entity filter.
     */
    private CompositePredicate prepareEntityPredicate(String userInput) {
        List<java.util.function.Predicate<seedu.address.model.person.Person>> predicates =
                new ArrayList<>();
        predicates.add(new EntityContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        return new CompositePredicate(predicates);
    }

    /**
     * Parses {@code userInput} into a {@code CompositePredicate} with multiple filters.
     */
    private CompositePredicate prepareCombinedPredicate(String tagInput, String roleInput) {
        List<java.util.function.Predicate<seedu.address.model.person.Person>> predicates =
                new ArrayList<>();
        predicates.add(new TagsContainsKeywordsPredicate(Arrays.asList(tagInput.split("\\s+"))));
        predicates.add(new RoleContainsKeywordsPredicate(Arrays.asList(roleInput.split("\\s+"))));
        return new CompositePredicate(predicates);
    }
}
