package mn.foreman.prospector.baikal;

import mn.foreman.prospector.model.MinerType;

/**
 * A {@link BaikalType} provides a {@link MinerType} implementation that
 * contains all of the known Baikal types.
 */
public enum BaikalType
        implements MinerType {

    /** A Baikal. */
    BAIKAL("Baikal BK-X");

    /** The miner name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param name The miner name.
     */
    BaikalType(final String name) {
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
