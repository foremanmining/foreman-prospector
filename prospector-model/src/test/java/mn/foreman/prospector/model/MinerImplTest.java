package mn.foreman.prospector.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;

/** Unit tests for {@link MinerImpl}. */
public class MinerImplTest {

    /**
     * Tests JSON serialization.
     *
     * @throws IOException on failure to (de)serialize.
     */
    @Test
    public void testSerialization()
            throws IOException {
        final Miner miner =
                new MinerImpl.Builder()
                        .setName("name")
                        .setIpAddress("127.0.0.1")
                        .setApiPort(4028)
                        .setType(Type.TEST)
                        .addParameter(
                                "key1",
                                "value1")
                        .addParameter(
                                "key2",
                                "value2")
                        .build();

        final ObjectMapper objectMapper =
                new ObjectMapper();

        final String json =
                objectMapper.writeValueAsString(
                        miner);
        assertFalse(json.isEmpty());
    }

    /** A test {@link MinerType}. */
    private enum Type implements MinerType {

        /** Test. */
        TEST;

        @Override
        public String getCategory() {
            return "category";
        }

        @Override
        public String getName() {
            return "name";
        }
    }
}