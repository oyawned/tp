package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.match.Match;
import seedu.address.testutil.TypicalMatches;
import seedu.address.testutil.TypicalPersons;

public class ResultCommandTest {

    private final Model typicalModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    private final Model emptyModel = new ModelManager();
    private final Match validMatch = TypicalMatches.WINNING_MATCH_4;

    @Test
    public void constructor_nullMatch_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ResultCommand(null));
    }

    @Test
    public void execute_matchAcceptedByModel_addSuccessful() {
        CommandResult commandResult = new ResultCommand(validMatch).execute(typicalModel);

        assertEquals(String.format(ResultCommand.MESSAGE_SUCCESS, Messages.format(validMatch)),
                commandResult.getFeedbackToUser());
        assertEquals(List.of(validMatch), typicalModel.getMatchRecord().getMatchList());
    }

    @Test
    public void execute_missingPerson_throwsCommandException() {
        CommandResult commandResult = new ResultCommand(validMatch).execute(emptyModel);

        assertEquals(String.format(ResultCommand.MESSAGE_PLAYER_DOES_NOT_EXIST, Messages.format(validMatch)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void toStringMethod() {
        ResultCommand resultCommand = new ResultCommand(validMatch);
        String expected = ResultCommand.class.getCanonicalName() + "{toAdd=" + validMatch + "}";
        assertEquals(expected, resultCommand.toString());
    }

}
