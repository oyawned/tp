package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.CompositePredicate;
import seedu.address.model.person.EntityContainsKeywordsPredicate;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.model.person.TagsContainsKeywordsPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.PARAMETERS));
    }

    @Test
    public void parse_noPrefix_throwsParseException() {
        assertParseFailure(parser, "friends owesMoney",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.PARAMETERS));
    }

    @Test
    public void parse_tagPrefix_returnsFilterCommand() {
        // no leading and trailing whitespaces
        List<java.util.function.Predicate<seedu.address.model.person.Person>> predicates =
                new ArrayList<>();
        predicates.add(new TagsContainsKeywordsPredicate(Arrays.asList("friends", "owesMoney")));
        FilterCommand expectedFilterCommand = new FilterCommand(new CompositePredicate(predicates));
        assertParseSuccess(parser, " t/friends owesMoney", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n t/friends \n \t owesMoney  \t", expectedFilterCommand);
    }

    @Test
    public void parse_rolePrefix_returnsFilterCommand() {
        List<java.util.function.Predicate<seedu.address.model.person.Person>> predicates =
                new ArrayList<>();
        predicates.add(new RoleContainsKeywordsPredicate(Arrays.asList("jungle")));
        FilterCommand expectedFilterCommand = new FilterCommand(new CompositePredicate(predicates));
        assertParseSuccess(parser, " r/jungle", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n r/jungle \n \t", expectedFilterCommand);
    }

    @Test
    public void parse_entityPrefix_returnsFilterCommand() {
        List<java.util.function.Predicate<seedu.address.model.person.Person>> predicates =
                new ArrayList<>();
        predicates.add(new EntityContainsKeywordsPredicate(Arrays.asList("ahri", "yasuo")));
        FilterCommand expectedFilterCommand = new FilterCommand(new CompositePredicate(predicates));
        assertParseSuccess(parser, " ent/ahri yasuo", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n ent/ahri \n \t yasuo  \t", expectedFilterCommand);
    }

    @Test
    public void parse_multiplePrefixes_returnsFilterCommand() {
        List<java.util.function.Predicate<seedu.address.model.person.Person>> predicates =
                new ArrayList<>();
        predicates.add(new TagsContainsKeywordsPredicate(Arrays.asList("friends")));
        predicates.add(new RoleContainsKeywordsPredicate(Arrays.asList("jungle")));
        predicates.add(new EntityContainsKeywordsPredicate(Arrays.asList("ahri")));
        FilterCommand expectedFilterCommand = new FilterCommand(new CompositePredicate(predicates));
        assertParseSuccess(parser, " t/friends r/jungle ent/ahri", expectedFilterCommand);

        // different order
        assertParseSuccess(parser, " r/jungle t/friends ent/ahri", expectedFilterCommand);

        // with extra whitespaces
        assertParseSuccess(parser, " \n t/friends \n \t r/jungle \n \t ent/ahri  \t", expectedFilterCommand);
    }

    @Test
    public void parse_emptyPrefixValue_throwsParseException() {
        assertParseFailure(parser, " t/    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.PARAMETERS));
    }
}
