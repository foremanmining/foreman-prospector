package mn.foreman.prospector.util;

import mn.foreman.util.FakeMinerServer;

import java.io.IOException;

/**
 * A {@link MinerServerFactory} provides a factory for making {@link
 * FakeMinerServer FakeMinerServers} to be used during integration testing.
 */
public interface MinerServerFactory {

    /**
     * Creates a new {@link FakeMinerServer}.
     *
     * @return The new {@link FakeMinerServer}.
     *
     * @throws IOException on failure to create.
     */
    FakeMinerServer create()
            throws IOException;
}
