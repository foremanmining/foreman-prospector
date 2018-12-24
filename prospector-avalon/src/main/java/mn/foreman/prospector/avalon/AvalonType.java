package mn.foreman.prospector.avalon;

import mn.foreman.prospector.model.MinerType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An {@link AvalonType} provides a {@link MinerType} implementation that
 * contains all of the known Avalon types.
 */
public enum AvalonType
        implements MinerType {

    /** The Avalon 741. */
    AVALON_741("Ver[741", "AvalonMiner 741"),

    /** The Avalon 842. */
    AVALON_821("Ver[821", "AvalonMiner 821"),

    /** The Avalon 841. */
    AVALON_841("Ver[841", "AvalonMiner 841"),

    /** The Avalon 851. */
    AVALON_851("Ver[851", "AvalonMiner 851"),

    /** The Avalon 911. */
    AVALON_911("Ver[911", "AvalonMiner 911"),

    /** The Avalon 921. */
    AVALON_921("Ver[921", "AvalonMiner 921"),

    /** An unknown ASIC. */
    UNKNOWN("Unknown", "Unknown");

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, AvalonType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final AvalonType asicType : values()) {
            TYPE_MAP.put(asicType.indicator, asicType);
        }
    }

    /** The indicator. */
    private final String indicator;

    /** The miner name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param indicator The indicator.
     * @param name      The miner name.
     */
    AvalonType(
            final String indicator,
            final String name) {
        this.indicator = indicator;
        this.name = name;
    }

    /**
     * Converts the provided model to an {@link AvalonType}.
     *
     * @param id The ID.
     *
     * @return The corresponding {@link AvalonType}.
     */
    public static AvalonType forId(final String id) {
        if (id != null && !id.isEmpty()) {
            return TYPE_MAP.entrySet()
                    .stream()
                    .filter(entry -> id.startsWith(entry.getKey()))
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .orElse(UNKNOWN);
        }
        return UNKNOWN;
    }

    @Override
    public String getCategory() {
        return "ASIC";
    }

    /**
     * Returns the indicator.
     *
     * @return The indicator.
     */
    public String getIndicator() {
        return this.indicator;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isKnown() {
        return this != UNKNOWN;
    }
}