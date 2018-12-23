package mn.foreman.prospector.dragonmint;

import mn.foreman.dragonmint.json.Summary;
import mn.foreman.io.Query;
import mn.foreman.model.error.MinerException;
import mn.foreman.prospector.model.Miner;
import mn.foreman.prospector.model.MinerImpl;
import mn.foreman.prospector.prospect.Prospector;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * A {@link DragonmintProspector} provides a {@link Prospector} implementation
 * that will query the provided IP and port, determining the Dragonmint model
 * that's present if a response is returned.
 */
public class DragonmintProspector
        implements Prospector {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(DragonmintProspector.class);

    /** The password. */
    private final String password;

    /** The username. */
    private final String username;

    /**
     * Constructor.
     *
     * @param username The username.
     * @param password The password.
     */
    public DragonmintProspector(
            final String username,
            final String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Miner> scan(
            final String ipAddress,
            final int apiPort) {
        Miner miner = null;
        try {
            final Summary summary =
                    Query.restQuery(
                            ipAddress,
                            apiPort,
                            "/api/summary",
                            this.username,
                            this.password,
                            new TypeReference<Summary>() {
                            });

            DragonmintType dragonmintType = DragonmintType.UNKNOWN;
            if (!summary.devs.isEmpty()) {
                dragonmintType =
                        DragonmintType.forModel(
                                summary.devs.get(0).name);
            }

            miner =
                    new MinerImpl.Builder()
                            .setIpAddress(ipAddress)
                            .setApiPort(apiPort)
                            .setType(dragonmintType)
                            .addParameter(
                                    "username",
                                    this.username)
                            .addParameter(
                                    "password",
                                    this.password)
                            .build();
        } catch (final MinerException me) {
            LOG.debug("No Dragonmint found on {}:{}", ipAddress, apiPort);
        }

        return Optional.ofNullable(miner);
    }
}
