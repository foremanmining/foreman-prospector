package mn.foreman.prospector.whatsminer;

import mn.foreman.prospector.model.MinerImpl;
import mn.foreman.prospector.util.AbstractProspectorTest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/** Verifies that a Whatsminer M3V1 can be queried and found. */
public class WhatsminerM3V1ITest
        extends AbstractProspectorTest {

    /**
     * Constructor.
     *
     * @throws IOException on failure to read the M3 stats file.
     */
    public WhatsminerM3V1ITest()
            throws IOException {
        super(
                "127.0.0.1",
                4028,
                4029,
                new WhatsminerProspector(),
                () -> new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"stats\"}",
                                new RpcHandler(
                                        IOUtils.toString(
                                                WhatsminerM3V1ITest.class.getResourceAsStream(
                                                        "/m3.stats.json"),
                                                Charset.defaultCharset())))),
                new MinerImpl.Builder()
                        .setType(WhatsminerType.WHATSMINER_M3V1)
                        .setIpAddress("127.0.0.1")
                        .setApiPort(4028)
                        .build());
    }
}
