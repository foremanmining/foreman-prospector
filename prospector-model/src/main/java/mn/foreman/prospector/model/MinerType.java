package mn.foreman.prospector.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A {@link MinerType} provides a definition for a miner software that would be
 * set on a miner in Foreman.
 */
public interface MinerType {

    /**
     * Returns the category.
     *
     * @return The category.
     */
    String getCategory();

    /**
     * Returns the name.
     *
     * @return The name.
     */
    @JsonValue
    String getName();
}