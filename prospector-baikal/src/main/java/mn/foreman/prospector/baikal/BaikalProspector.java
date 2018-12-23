package mn.foreman.prospector.baikal;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.error.MinerException;
import mn.foreman.prospector.model.Miner;
import mn.foreman.prospector.model.MinerImpl;
import mn.foreman.prospector.prospect.Prospector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link BaikalProspector} provides a {@link Prospector} implementation that
 * will query the provided IP and port, determining the Baikal model that's
 * present if a response is returned.
 *
 * <h2>Limitations</h2>
 *
 * <p>At this point in time, due to limited test data, all Baikal proposals
 * while prospecting will indicate a {@link BaikalType#BAIKAL}, which is a BK-X.
 * The API response may contain more uniquely identifying information to help
 * suggest a better miner type, but to determine that, more test data is
 * needed.</p>
 *
 * <p>This "incorrect" default value will have no negative functional impacts
 * due to all Baikal miners returning their hash rates in MHs.</p>
 */
public class BaikalProspector
        implements Prospector {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(BaikalProspector.class);

    @Override
    public Optional<Miner> scan(
            final String ipAddress,
            final int apiPort) {
        Miner miner = null;
        try {
            final Map<String, List<Map<String, String>>> responseValues =
                    new HashMap<>();
            final CgMiner cgMiner =
                    new CgMiner.Builder()
                            .setApiIp(ipAddress)
                            .setApiPort(Integer.toString(apiPort))
                            .addRequest(
                                    new CgMinerRequest.Builder()
                                            .setCommand(CgMinerCommand.DEVS)
                                            .build(),
                                    (builder, response) ->
                                            responseValues.putAll(
                                                    response.getValues()))
                            .build();

            // Attempt to query the miner for stats.  If there's a response,
            // then we found something
            cgMiner.getStats();

            // It's a baikal if there's a BKL device
            if (isBaikal(responseValues)) {
                miner =
                        new MinerImpl.Builder()
                                .setIpAddress(ipAddress)
                                .setApiPort(apiPort)
                                .setType(BaikalType.BAIKAL)
                                .build();
            }
        } catch (final MinerException me) {
            LOG.debug("No Baikal found on {}:{}", ipAddress, apiPort);
        }

        return Optional.ofNullable(miner);
    }

    /**
     * Checks to see if the response contains a Baikal.
     *
     * @param response The response to evaluate.
     *
     * @return Whether or not the response indicates a Baikal miner.
     */
    private static boolean isBaikal(
            final Map<String, List<Map<String, String>>> response) {
        return response
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals("DEVS"))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .anyMatch(map ->
                        map.getOrDefault("Name", "").startsWith("BKL"));
    }
}