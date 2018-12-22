package mn.foreman.prospector.antminer;

import mn.foreman.prospector.model.MinerType;

import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * An {@link AntminerType} provides a {@link MinerType} implementation that
 * contains all of the known Antminer types.
 */
public enum AntminerType
        implements MinerType {

    /** The Antminer A3. */
    ANTMINER_A3("Antminer A3"),

    /** The Antminer B3. */
    ANTMINER_B3("Antminer B3"),

    /** The Antminer D3. */
    ANTMINER_D3("Antminer D3"),

    /** The Antminer DR3. */
    ANTMINER_DR3("Antminer DR3"),

    /** The Antminer E3. */
    ANTMINER_E3("Antminer E3"),

    /** The Antminer L3. */
    ANTMINER_L3("Antminer L3"),

    /** The Antminer S7. */
    ANTMINER_S7("Antminer S7"),

    /** The Antminer S9. */
    ANTMINER_S9("Antminer S9"),

    /** The Antminer S15. */
    ANTMINER_S15("Antminer S15"),

    /** The Antminer T9. */
    ANTMINER_T9("Antminer T9"),

    /** The Antminer T15. */
    ANTMINER_T15("Antminer T15"),

    /** The Antminer X3. */
    ANTMINER_X3("Antminer X3"),

    /** The Antminer Z9. */
    ANTMINER_Z9("Antminer Z9"),

    /** An unknown ASIC. */
    UNKNOWN("Unknown");

    /** All of the types, by string, mapped to their type. */
    private static final NavigableMap<String, AntminerType> TYPE_MAP =
            new TreeMap<>();

    static {
        for (final AntminerType asicType : values()) {
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
    AntminerType(final String name) {
        this.name = name;
    }

    /**
     * Converts the provided model to an {@link AntminerType}.
     *
     * @param model The model.
     *
     * @return The corresponding {@link AntminerType}.
     */
    public static AntminerType forModel(final String model) {
        final SortedMap<String, AntminerType> types =
                TYPE_MAP.subMap(model, model + Character.MAX_VALUE);
        if (!types.isEmpty()) {
            return types.get(types.firstKey());
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