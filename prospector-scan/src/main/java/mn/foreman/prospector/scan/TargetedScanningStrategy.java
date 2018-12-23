package mn.foreman.prospector.scan;

import mn.foreman.prospector.model.Miner;
import mn.foreman.prospector.prospect.Prospector;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * A {@link TargetedScanningStrategy} provides a {@link ScanningStrategy}
 * implementation that scans a provided subnet pattern and candidate API ports
 * for {@link Miner miners} that pass the provided {@link Prospector
 * Prospectors} query and validation criteria.  This implementation will only
 * find {@link Miner miners} of one type, as defined by the {@link Prospector}
 * that's provided.
 *
 * <h2>Behavior</h2>
 *
 * <p>This class accepts a subnet pattern that matches a pattern similar to
 * <code>xxx.yyy.zzz</code>.  It will then query all of the IP address in the
 * range.  As an example, to query all of the miners in the
 * <code>192.168.1</code> subnet, providing <code>192.168.1</code> will result
 * in querying of the following IP address (all 256 possible combinations):
 *
 * <pre>
 *     192.168.1.0
 *     192.168.1.1
 *     192.168.1.2
 *     ...
 *     192.168.1.243
 *     192.168.1.244
 *     192.168.1.255
 * </pre>
 */
public class TargetedScanningStrategy
        implements ScanningStrategy {

    /** The logger for this class. */
    private static final Logger LOG =
            LoggerFactory.getLogger(TargetedScanningStrategy.class);

    /** The {@link Prospector} to use for evaluating miners. */
    private final Prospector prospector;

    /**
     * Constructor.
     *
     * @param prospector The {@link Prospector}.
     */
    public TargetedScanningStrategy(final Prospector prospector) {
        Validate.notNull(
                prospector,
                "prospector cannot be null");
        this.prospector = prospector;
    }

    @Override
    public List<Miner> scan(
            final String subnetPattern,
            final int apiPort) {
        Validate.isTrue(
                StringUtils.countMatches(subnetPattern, ".") == 2,
                "subnetPattern must be of the form xxx.yyy.zzz");

        final List<Miner> miners = new LinkedList<>();
        for (int i = 0; i < 255; i++) {
            final String ipAddress =
                    String.format(
                            "%s.%d",
                            subnetPattern,
                            i);

            LOG.info("Scanning {}:{}",
                    ipAddress,
                    apiPort);

            this.prospector.scan(
                    ipAddress,
                    apiPort).ifPresent(miner -> {
                LOG.info("Found {}", miner);
                miners.add(miner);
            });
        }

        return miners;
    }
}