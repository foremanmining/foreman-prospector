package mn.foreman.prospector.model;

/** A {@link Miner} provides a representation of a miner in Foreman. */
public interface Miner {

    /**
     * Returns the API port.
     *
     * @return The API port.
     */
    int getApiPort();

    /**
     * Returns the IP address.
     *
     * @return The IP address.
     */
    String getIpAddress();

    /**
     * Returns the type.
     *
     * @return The type.
     */
    MinerType getMinerType();

    /**
     * Returns the miner name.
     *
     * @return The miner name.
     */
    String getName();
}