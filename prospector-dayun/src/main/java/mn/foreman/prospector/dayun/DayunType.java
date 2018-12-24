package mn.foreman.prospector.dayun;

import mn.foreman.prospector.model.MinerType;

/**
 * A {@link DayunType} provides a {@link MinerType} implementation that contains
 * all of the known Baikal types.
 */
public enum DayunType
        implements MinerType {

    /** A Baikal. */
    DAYUN("Dayun Zig Z1");

    /** The miner name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param name The miner name.
     */
    DayunType(final String name) {
        this.name = name;
    }

    @Override
    public String getCategory() {
        return "ASIC";
    }

    @Override
    public String getName() {
        return this.name;
    }
}
