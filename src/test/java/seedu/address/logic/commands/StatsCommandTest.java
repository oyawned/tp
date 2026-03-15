package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.STATS_DESC_SET_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_KILLS_SET_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATS_SET_1;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.StatsCommand.EditStatsDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditStatsDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class StatsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_statisticsSpecifiedUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withStatistics(VALID_STATS_SET_1).build();
        EditStatsDescriptor descriptor = new EditStatsDescriptorBuilder().withKills(VALID_KILLS_SET_1).build();
        StatsCommand statsCommand = new StatsCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(StatsCommand.MESSAGE_STATS_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(statsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final StatsCommand statsFirstCommand = new StatsCommand(INDEX_FIRST_PERSON, STATS_DESC_SET_1);

        // same values -> returns true
        StatsCommand statsFirstCommandCopy = new StatsCommand(INDEX_FIRST_PERSON, STATS_DESC_SET_1);
        org.junit.jupiter.api.Assertions.assertTrue(statsFirstCommand.equals(statsFirstCommandCopy));

        // different index -> returns false
        org.junit.jupiter.api.Assertions.assertFalse(statsFirstCommand
            .equals(new StatsCommand(INDEX_SECOND_PERSON, STATS_DESC_SET_1)));

        // different descriptor -> returns false
        EditStatsDescriptor differentDescriptor = new EditStatsDescriptorBuilder().withKills("999").build();
        org.junit.jupiter.api.Assertions.assertFalse(statsFirstCommand
            .equals(new StatsCommand(INDEX_FIRST_PERSON, differentDescriptor)));
    }
}
