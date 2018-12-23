package mn.foreman.prospector.dragonmint;

import mn.foreman.prospector.model.MinerType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link DragonmintType} provides a {@link MinerType} implementation that
 * contains all of the known Dragonmint types.
 */
public enum DragonmintType
        implements MinerType {

    /** The Dragonmint T1. */
    DRAGONMINT_T1("DT1", "DragonMint T1"),

    /** The Dragonmint B52. */
    DRAGONMINT_B52("DB52", "DragonMint B52"),

    /** An unknown type. */
    UNKNOWN("", "Unknown");

    /** All of the types, by indicator, mapped to their type. */
    private static final Map<String, DragonmintType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final DragonmintType asicType : values()) {
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
    DragonmintType(
            final String indicator,
            final String name) {
        this.indicator = indicator;
        this.name = name;
    }

    /**
     * Converts the provided model to an {@link DragonmintType}.
     *
     * @param model The model.
     *
     * @return The corresponding {@link DragonmintType}.
     */
    public static DragonmintType forModel(final String model) {
        if (model != null && !model.isEmpty()) {
            return TYPE_MAP.entrySet()
                    .stream()
                    .filter(entry -> model.startsWith(entry.getKey()))
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

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isKnown() {
        return this != UNKNOWN;
    }
}