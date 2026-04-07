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

        // Parse tag predicates
        if (argMultimap.getValue(CliSyntax.PREFIX_TAG).isPresent()) {
            String tagArgs = argMultimap.getValue(CliSyntax.PREFIX_TAG).get();
            if (!tagArgs.trim().isEmpty()) {
                String[] tagKeywords = tagArgs.trim().split("\\s+");
                predicates.add(new TagsContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
            }
        }

        // Parse role predicates
        if (argMultimap.getValue(CliSyntax.PREFIX_ROLE).isPresent()) {
            String roleArgs = argMultimap.getValue(CliSyntax.PREFIX_ROLE).get();
            if (!roleArgs.trim().isEmpty()) {
                String[] roleKeywords = roleArgs.trim().split("\\s+");
                predicates.add(new RoleContainsKeywordsPredicate(Arrays.asList(roleKeywords)));
            }
        }

        // Parse entity predicates
        if (argMultimap.getValue(CliSyntax.PREFIX_ENTITY).isPresent()) {
            String entityArgs = argMultimap.getValue(CliSyntax.PREFIX_ENTITY).get();
            if (!entityArgs.trim().isEmpty()) {
                String[] entityKeywords = entityArgs.trim().split("\\s+");
                predicates.add(new EntityContainsKeywordsPredicate(Arrays.asList(entityKeywords)));
            }
        }

        // Check if at least one filter was provided
        if (predicates.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.PARAMETERS));
        }

        return new FilterCommand(new CompositePredicate(predicates));
    }

}
