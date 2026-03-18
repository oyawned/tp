package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role.RoleType;

/**
 * Drafts a team by validating the composition of selected players.
 * Players are selected by their indices in the filtered person list.
 * A valid draft requires exactly 5 players with one player per role.
 */
public class DraftCommand extends Command {

    public static final String COMMAND_WORD = "draft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Validates team composition using selected players. "
            + "Parameters: INDEX [MORE_INDICES]...\n"
            + "Example: " + COMMAND_WORD + " 1 4 5 7 8";

    public static final String MESSAGE_SUCCESS = "Draft validation complete: %1$s";

    private static final int REQUIRED_TEAM_SIZE = 5;
    private static final int REQUIRED_PLAYERS_PER_ROLE = 1;

    private final List<Index> indices;

    /**
     * Creates a DraftCommand with the specified player indices.
     *
     * @param indices list of indices of players to draft
     */
    public DraftCommand(List<Index> indices) {
        requireNonNull(indices);
        this.indices = new ArrayList<>(indices);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Validate all indices and collect players
        List<Person> selectedPlayers = new ArrayList<>();
        for (Index index : indices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            selectedPlayers.add(lastShownList.get(index.getZeroBased()));
        }

        // Validate composition and generate result message
        String validationMessage = generateValidationMessage(selectedPlayers);
        return new CommandResult(String.format(MESSAGE_SUCCESS, validationMessage));
    }

    /**
     * Generates a user-friendly validation message for the selected players.
     * Checks team size and role composition.
     *
     * @param players the selected players
     * @return formatted validation message
     */
    private String generateValidationMessage(List<Person> players) {
        String result = "";
        Map<RoleType, Integer> roleCounts = buildRoleCountMap(players);
        List<String> errors = validateComposition(players, roleCounts);

        // Validity indicator
        if (errors.isEmpty()) {
            result += "✓ Draft Valid!\n";
        } else {
            result += "✗ Invalid Draft Composition\n";
        }

        // Composition summary on one line
        result += "Composition: ";
        boolean isFirst = true;
        for (RoleType role : RoleType.values()) {
            if (!isFirst) {
                result += " | ";
            }
            result += role + " (" + roleCounts.get(role) + ")";
            isFirst = false;
        }

        // Issues on one line (if any)
        if (!errors.isEmpty()) {
            result += "\nIssues: " + String.join(", ", errors);
        }

        return result;
    }

    /**
     * Validates the composition of selected players.
     * Checks for exact team size and one player per role.
     *
     * @param players the selected players
     * @param roleCount map of roles to player counts
     * @return list of validation errors (empty if valid)
     */
    private List<String> validateComposition(List<Person> players, Map<RoleType, Integer> roleCount) {
        List<String> errors = new ArrayList<>();

        // Check team size
        if (players.size() != REQUIRED_TEAM_SIZE) {
            errors.add("Invalid team size (" + players.size() + ")");
        }

        // Check role requirements
        for (RoleType role : RoleType.values()) {
            int count = roleCount.get(role);
            if (count == 0) {
                errors.add("Missing " + role + " player");
            } else if (count > REQUIRED_PLAYERS_PER_ROLE) {
                errors.add("Too many " + role + " players");
            }
        }

        return errors;
    }

    /**
     * Builds a map of player counts by their roles.
     *
     * @param players the list of players
     * @return map of role to player count
     */
    private Map<RoleType, Integer> buildRoleCountMap(List<Person> players) {
        Map<RoleType, Integer> roleCounts = new HashMap<>();

        // Initialize all roles to 0
        for (RoleType role : RoleType.values()) {
            roleCounts.put(role, 0);
        }

        // Count players by role
        for (Person player : players) {
            RoleType role = player.getRole().value;
            roleCounts.put(role, roleCounts.get(role) + 1);
        }

        return roleCounts;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DraftCommand)) {
            return false;
        }

        DraftCommand otherDraftCommand = (DraftCommand) other;
        return indices.equals(otherDraftCommand.indices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("indices", indices)
                .toString();
    }
}
