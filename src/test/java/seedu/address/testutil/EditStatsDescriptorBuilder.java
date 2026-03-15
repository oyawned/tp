package seedu.address.testutil;

import seedu.address.logic.commands.StatsCommand.EditStatsDescriptor;
import seedu.address.model.person.statistics.Kills;
import seedu.address.model.person.statistics.Statistics;

/**
 * A utility class to help with building EditStatsDescriptor objects.
 */
public class EditStatsDescriptorBuilder {

    private EditStatsDescriptor descriptor;

    public EditStatsDescriptorBuilder() {
        descriptor = new EditStatsDescriptor();
    }

    public EditStatsDescriptorBuilder(EditStatsDescriptor descriptor) {
        this.descriptor = new EditStatsDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditStatsDescriptor} with fields containing {@code person}'s statistics.
     */
    public EditStatsDescriptorBuilder(Statistics statistics) {
        descriptor = new EditStatsDescriptor();
        descriptor.setKills(statistics.getKills());
    }

    /**
     * Sets the {@code Kills} of the {@code EditStatsDescriptor} that we are building.
     */
    public EditStatsDescriptorBuilder withKills(String kills) {
        descriptor.setKills(new Kills(kills));
        return this;
    }

    // Add withDeaths, withWins, etc. here as you expand the Statistics model.

    public EditStatsDescriptor build() {
        return descriptor;
    }
}
