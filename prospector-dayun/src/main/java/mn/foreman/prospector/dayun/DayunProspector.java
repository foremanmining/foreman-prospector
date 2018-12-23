package mn.foreman.prospector.dayun;

import mn.foreman.dayun.DayunFactory;
import mn.foreman.model.error.MinerException;
import mn.foreman.model.miners.MinerStats;
import mn.foreman.prospector.model.Miner;
import mn.foreman.prospector.model.MinerImpl;
import mn.foreman.prospector.prospect.Prospector;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * A {@link DayunProspector} provides a {@link Prospector} implementation that
 * will query the provided IP and port, determining the Baikal model that's
 * present if a response is returned.
 *
 * <h2>Limitations</h2>
 *
 * <p>At this point in time, only a Dayun Zig Z1 will be returned.  There's
 * no information in the API response that differentiates between the Z1 and the
 * Z1+.  The API responses are the same, which lets either type be used in
 * Foreman.</p>
 */
public class DayunProspector
        implements Prospector {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(DayunFactory.class);

    @Override
    public Optional<Miner> scan(
            final String ipAddress,
            final int apiPort) {
        Miner miner = null;

        final mn.foreman.model.Miner dayun =
                new DayunFactory().create(
                        ImmutableMap.of(
                                "apiIp",
                                ipAddress,
                                "apiPort",
                                Integer.toString(apiPort)));

        // Attempt to query the miner - if the result comes back with an ASIC,
        // then there's a Dayun
        try {
            final MinerStats stats =
                    dayun.getStats();
            if (!stats.getAsics().isEmpty()) {
                miner =
                        new MinerImpl.Builder()
                                .setIpAddress(ipAddress)
                                .setApiPort(apiPort)
                                .setType(DayunType.DAYUN)
                                .build();
            }
        } catch (final MinerException me) {
            LOG.debug("No Dayun found on {}:{}", ipAddress, apiPort);
        }

        return Optional.ofNullable(miner);
    }
}
