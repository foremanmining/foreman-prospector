package mn.foreman.prospector.aixin;

import mn.foreman.cgminer.CgMiner;
import mn.foreman.cgminer.request.CgMinerCommand;
import mn.foreman.cgminer.request.CgMinerRequest;
import mn.foreman.model.error.MinerException;
import mn.foreman.prospector.model.Miner;
import mn.foreman.prospector.model.MinerImpl;
import mn.foreman.prospector.prospect.Prospector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * An {@link AixinProspector} provides a {@link Prospector} implementation that
 * will query the provided IP and port, determining the Aixin model that's
 * present if a response is returned.
 */
public class AixinProspector
        implements Prospector {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(AixinProspector.class);

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
                            .setConnectTimeout(
                                    1,
                                    TimeUnit.SECONDS)
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

            final List<Map<String, String>> values =
                    responseValues.getOrDefault(
                            "DEVS",
                            Collections.emptyList());
            if (!values.isEmpty() &&
                    values.get(0).containsKey("fminerled")) {
                miner =
                        new MinerImpl.Builder()
                                .setIpAddress(ipAddress)
                                .setApiPort(apiPort)
                                .setType(AixinType.AIXIN_A1)
                                .build();
            }
        } catch (final MinerException e) {
            LOG.debug("No Aixin found on {}:{}", ipAddress, apiPort);
        }

        return Optional.ofNullable(miner);
    }
}