package mn.foreman.prospector.prospect;

import mn.foreman.prospector.model.Miner;

import java.util.Optional;

/**
 * A {@link Prospector} provides a mechanism to scan an IP and port for a {@link
 * Miner} if one exists.
 */
public interface Prospector {

    /**
     * Scans the provided candidate IP and port combination for a {@link
     * Miner}.
     *
     * @param ipAddress The candidate IP address.
     * @param apiPort   The candidate API port.
     *
     * @return The {@link Miner}, if one was found.
     */
    Optional<Miner> scan(
            String ipAddress,
            int apiPort);
}