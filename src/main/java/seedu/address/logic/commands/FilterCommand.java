package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.person.CompositePredicate;

/**
 * Filters and lists all persons in address book based on tag, role, entity played, or any combination.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = "Filters all persons whose tags/roles/entities match "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.";

    public static final String PARAMETERS = "Parameters: "
            + "[" + CliSyntax.PREFIX_TAG + "KEYWORD [MORE_KEYWORDS]...] "
            + "[" + CliSyntax.PREFIX_ROLE + "KEYWORD [MORE_KEYWORDS]...] "
            + "[" + CliSyntax.PREFIX_ENTITY + "KEYWORD [MORE_KEYWORDS]...]\n";

    public static final String EXAMPLE = "Example: " + COMMAND_WORD + " " + CliSyntax.PREFIX_TAG + "mid top "
            + CliSyntax.PREFIX_ROLE + "jungle " + CliSyntax.PREFIX_ENTITY + "ahri yasuo";

    private final CompositePredicate predicate;

    public FilterCommand(CompositePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size())
                + "\n" + Messages.MESSAGE_GLOBAL_INDEX_CUE);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
