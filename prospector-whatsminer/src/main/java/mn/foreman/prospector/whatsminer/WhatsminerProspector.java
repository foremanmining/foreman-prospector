package mn.foreman.prospector.whatsminer;

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
import java.util.concurrent.TimeUnit;

/**
 * A {@link WhatsminerProspector} provides a {@link Prospector} implementation
 * that will query the provided IP and port, determining the Whatsminer model
 * that's present if a response is returned.
 */
public class WhatsminerProspector
        implements Prospector {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(WhatsminerProspector.class);

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
                                            .setCommand(CgMinerCommand.STATS)
                                            .build(),
                                    (builder, response) ->
                                            responseValues.putAll(
                                                    response.getValues()))
                            .build();

            // Attempt to query the miner for stats.  If there's a response,
            // then we found something
            cgMiner.getStats();

            final Optional<WhatsminerType> type =
                    WhatsminerType.fromChips(
                            getNumChips(
                                    responseValues));
            if (type.isPresent()) {
                miner =
                        new MinerImpl.Builder()
                                .setIpAddress(ipAddress)
                                .setApiPort(apiPort)
                                .setType(type.get())
                                .build();
            }
        } catch (final MinerException me) {
            LOG.debug("No Whatsminer found on {}:{}", ipAddress, apiPort);
        }

        return Optional.ofNullable(miner);
    }

    /**
     * Gets the total number of chips from the provided response.
     *
     * @param values The values to parse.
     *
     * @return The total number of chips.
     */
    private static int getNumChips(
            final Map<String, List<Map<String, String>>> values) {
        return values
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals("STATS"))
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .filter(map -> map.containsKey("chip_num"))
                .mapToInt(map -> Integer.parseInt(map.get("chip_num")))
                .sum();
    }
}