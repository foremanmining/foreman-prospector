package mn.foreman.prospector.model;

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
    String getName();

    /**
     * Returns whether or not the type is known.
     *
     * @return Whether or not the type is known.
     */
    boolean isKnown();
}