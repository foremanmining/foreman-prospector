package mn.foreman.prospector.blackminer;

import mn.foreman.prospector.model.MinerType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An {@link BlackminerType} provides a {@link MinerType} implementation that
 * contains all of the known Blackminer types.
 */
public enum BlackminerType
        implements MinerType {

    /** The Blackminer F1. */
    BLACKMINER_F1("Blackminer F1");

    /** All of the types, by string, mapped to their type. */
    private static final Map<String, BlackminerType> TYPE_MAP =
            new ConcurrentHashMap<>();

    static {
        for (final BlackminerType asicType : values()) {
            TYPE_MAP.put(asicType.name, asicType);
        }
    }

    /** The miner name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param name The miner name.
     */
    BlackminerType(final String name) {
        this.name = name;
    }

    /**
     * Converts the provided model to an {@link BlackminerType}.
     *
     * @param model The model.
     *
     * @return The corresponding {@link BlackminerType}.
     */
    public static Optional<BlackminerType> forModel(final String model) {
        if (model != null && !model.isEmpty()) {
            return TYPE_MAP.entrySet()
                    .stream()
                    .filter(entry -> model.startsWith(entry.getKey()))
                    .map(Map.Entry::getValue)
                    .findFirst();
        }
        return Optional.empty();
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