package mn.foreman.prospector.aixin;

import mn.foreman.prospector.model.MinerType;

/**
 * A {@link AixinType} provides a {@link MinerType} implementation that contains
 * all of the known Aixin types.
 */
public enum AixinType
        implements MinerType {

    /** An A1. */
    AIXIN_A1("Aixin A1");

    /** The miner name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param name The miner name.
     */
    AixinType(final String name) {
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
