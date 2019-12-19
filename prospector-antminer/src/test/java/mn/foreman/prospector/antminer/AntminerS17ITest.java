package mn.foreman.prospector.antminer;

import mn.foreman.prospector.model.MinerImpl;
import mn.foreman.prospector.util.AbstractProspectorTest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Verifies that an Antminer S17 can be queried and found. */
public class AntminerS17ITest
        extends AbstractProspectorTest {

    /** Constructor. */
    public AntminerS17ITest() {
        super(
                "127.0.0.1",
                4028,
                4029,
                new AntminerProspector(),
                () -> new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"version\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1576765845,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"1.0.0\",\"API\":\"3.1\",\"Miner\":\"19.10.1.3\",\"CompileTime\":\"Tue Aug 20 10:37:07 CST 2019\",\"Type\":\"Antminer S17 Pro\"}],\"id\":1}")
                        )),
                new MinerImpl.Builder()
                        .setType(AntminerType.ANTMINER_S17)
                        .setIpAddress("127.0.0.1")
                        .setApiPort(4028)
                        .build());
    }
}
