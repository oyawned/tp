package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DraftCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DraftCommand object.
 */
public class DraftCommandParser implements Parser<DraftCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DraftCommand
     * and returns a DraftCommand object for execution.
     *
     * @param args the arguments string containing space-separated indices
     * @return a DraftCommand object
     * @throws ParseException if the user input does not conform the expected format
     */
    public DraftCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DraftCommand.MESSAGE_USAGE));
        }

        String[] indexStrings = trimmedArgs.split("\\s+");
        List<Index> indices = new ArrayList<>();

        for (String indexString : indexStrings) {
            Index index = ParserUtil.parseIndex(indexString);
            indices.add(index);
        }

        return new DraftCommand(indices);
    }
}
