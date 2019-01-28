package mn.foreman.prospector.blackminer;

import mn.foreman.prospector.model.MinerImpl;
import mn.foreman.prospector.util.AbstractProspectorTest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Verifies that a Blackminer F1 can be queried and found. */
public class BlackminerF1ITest
        extends AbstractProspectorTest {

    /** Constructor. */
    public BlackminerF1ITest() {
        super(
                "127.0.0.1",
                4028,
                4029,
                new BlackminerProspector(),
                () -> new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"version\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1544107423,\"Code\":70,\"Msg\":\"CGMiner stats\",\"Description\":\"ccminer 2.3.3\"}],\"VERSION\":[{\"CGMiner\":\"2.3.3\",\"Miner\":\"1.3.0.8\",\"CompileTime\":\"2018-11-19 20-59-09 CST\",\"Type\":\"Blackminer F1\"}],\"id\":1}"))),
                new MinerImpl.Builder()
                        .setType(BlackminerType.BLACKMINER_F1)
                        .setIpAddress("127.0.0.1")
                        .setApiPort(4028)
                        .build());
    }
}