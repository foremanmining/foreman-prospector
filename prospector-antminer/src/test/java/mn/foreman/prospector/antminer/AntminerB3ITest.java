package mn.foreman.prospector.antminer;

import mn.foreman.prospector.model.MinerImpl;
import mn.foreman.prospector.util.AbstractProspectorTest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Verifies that an Antminer B3 can be queried and found. */
public class AntminerB3ITest
        extends AbstractProspectorTest {

    /** Constructor. */
    public AntminerB3ITest() {
        super(
                "127.0.0.1",
                4028,
                4029,
                new AntminerProspector(),
                new FakeRpcMinerServer(
                        4028,
                        ImmutableMap.of(
                                "{\"command\":\"version\"}",
                                new RpcHandler(
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":395315,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"bmminer 1.0.0\"}],\"VERSION\":[{\"CGMiner\":\"2.0.0\",\"API\":\"3.1\",\"Miner\":\"A.0.0.1\",\"CompileTime\":\"Wed Apr 11 22:21:40 CST 2018\",\"Type\":\"Antminer B3\"}],\"id\":1}")
                        )),
                new MinerImpl.Builder()
                        .setType(AntminerType.ANTMINER_B3)
                        .setIpAddress("127.0.0.1")
                        .setApiPort(4028)
                        .build());
    }
}
