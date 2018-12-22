package mn.foreman.prospector.scan;

import mn.foreman.prospector.model.Miner;

import java.util.List;

/**
 * A {@link ScanningStrategy} provides a mechanism for scanning a subnet and
 * candidate API port for all reachable {@link Miner Miners}.
 */
public interface ScanningStrategy {

    /**
     * Scans for miners.
     *
     * @return The {@link Miner miners} that were found.
     */
    List<Miner> scan(
            String subnetPattern,
            int apiPort);
}