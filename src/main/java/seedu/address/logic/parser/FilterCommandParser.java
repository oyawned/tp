package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CompositePredicate;
import seedu.address.model.person.EntityContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.model.person.TagsContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    public FilterCommandParser() {}

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.PARAMETERS));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_TAG, CliSyntax.PREFIX_ROLE, CliSyntax.PREFIX_ENTITY);

        List<Predicate<Person>> predicates = new ArrayList<>();

        // Parse tag predicates (OR all tag keywords from all occurrences)
        List<String> allTagKeywords = new ArrayList<>();
        List<String> allTagValues = argMultimap.getAllValues(CliSyntax.PREFIX_TAG);
        for (String tagArgs : allTagValues) {
            if (!tagArgs.trim().isEmpty()) {
                allTagKeywords.addAll(Arrays.asList(tagArgs.trim().split("\\s+")));
            }
        }
        if (!allTagKeywords.isEmpty()) {
            predicates.add(new TagsContainsKeywordsPredicate(allTagKeywords));
        }

        // Parse role predicates (OR all role keywords from all occurrences)
        List<String> allRoleKeywords = new ArrayList<>();
        List<String> allRoleValues = argMultimap.getAllValues(CliSyntax.PREFIX_ROLE);
        for (String roleArgs : allRoleValues) {
            if (!roleArgs.trim().isEmpty()) {
                allRoleKeywords.addAll(Arrays.asList(roleArgs.trim().split("\\s+")));
            }
        }
        if (!allRoleKeywords.isEmpty()) {
            predicates.add(new RoleContainsKeywordsPredicate(allRoleKeywords));
        }

        // Parse entity predicates (OR all entity keywords from all occurrences)
        List<String> allEntityKeywords = new ArrayList<>();
        List<String> allEntityValues = argMultimap.getAllValues(CliSyntax.PREFIX_ENTITY);
        for (String entityArgs : allEntityValues) {
            if (!entityArgs.trim().isEmpty()) {
                allEntityKeywords.addAll(Arrays.asList(entityArgs.trim().split("\\s+")));
            }
        }
        if (!allEntityKeywords.isEmpty()) {
            predicates.add(new EntityContainsKeywordsPredicate(allEntityKeywords));
        }

        // Check if at least one filter was provided
        if (predicates.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.PARAMETERS));
        }

        return new FilterCommand(new CompositePredicate(predicates));
    }

}
