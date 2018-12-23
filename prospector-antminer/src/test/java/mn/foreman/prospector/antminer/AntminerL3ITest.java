package mn.foreman.prospector.antminer;

import mn.foreman.prospector.model.MinerImpl;
import mn.foreman.prospector.util.AbstractProspectorTest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Verifies that an Antminer B3 can be queried and found. */
public class AntminerL3ITest
        extends AbstractProspectorTest {

    /** Constructor. */
    public AntminerL3ITest() {
        super(
                "127.0.0.1",
                42069,
                42070,
                new AntminerProspector(),
                new FakeRpcMinerServer(
                        42069,
                        ImmutableMap.of(
                                "{\"command\":\"version\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1526315502,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"1.0.1.3\",\"CompileTime\":\"Fri Aug 25 17:28:57 CST 2017\",\"Type\":\"Antminer L3+\"}],\"id\":1}")
                        )),
                new MinerImpl.Builder()
                        .setType(AntminerType.ANTMINER_L3)
                        .setIpAddress("127.0.0.1")
                        .setApiPort(42069)
                        .build());
    }
}
