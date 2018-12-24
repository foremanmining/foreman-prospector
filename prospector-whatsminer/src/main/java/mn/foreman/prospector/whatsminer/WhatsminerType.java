package mn.foreman.prospector.whatsminer;

import mn.foreman.prospector.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An {@link WhatsminerType} provides a {@link MinerType} implementation that
 * contains all of the known Whatsminer types.
 */
public enum WhatsminerType
        implements MinerType {

    /** The Whatsminer D1V1. */
    WHATSMINER_D1V1(180, "Whatsminer D1"),

    /** The Whatsminer M3V1. */
    WHATSMINER_M3V1(189, "Whatsminer M3"),

    /** The Whatsminer M3V2. */
    WHATSMINER_M3V2(198, "Whatsminer M3"),

    /** The Whatsminer M10V1. */
    WHATSMINER_M10V1(315, "Whatsminer M10");

    /** All of the types, by number of chips, mapped to their type. */
    private static final Map<Integer, WhatsminerType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final WhatsminerType asicType : values()) {
            TYPE_MAP.put(asicType.numChips, asicType);
        }
    }

    /** The miner name. */
    private final String name;

    /** The number of chips. */
    private final int numChips;

    /**
     * Constructor.
     *
     * @param numChips The number of chips.
     * @param name     The miner name.
     */
    WhatsminerType(
            final int numChips,
            final String name) {
        this.numChips = numChips;
        this.name = name;
    }

    /**
     * Converts the provided number of chips to a {@link WhatsminerType}.
     *
     * @param numChips The number of chips.
     *
     * @return The corresponding {@link WhatsminerType}.
     */
    public static Optional<WhatsminerType> fromChips(final int numChips) {
        return Optional.ofNullable(TYPE_MAP.get(numChips));
    }

    @Override
    public String getCategory() {
        return "ASIC";
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the number of chips.
     *
     * @return The number of chips.
     */
    public int getNumChips() {
        return numChips;
    }
}