package mn.foreman.prospector.antminer;

import mn.foreman.prospector.model.MinerImpl;
import mn.foreman.prospector.util.AbstractProspectorTest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Verifies that an Antminer B3 can be queried and found. */
public class AntminerS9ITest
        extends AbstractProspectorTest {

    /** Constructor. */
    public AntminerS9ITest() {
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
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1516328450,\"Code\":22,\"Msg\":\"BMMiner versions\",\"Description\":\"bmminer 1.0.0\"}],\"VERSION\":[{\"BMMiner\":\"2.0.0\",\"API\":\"3.1\",\"Miner\":\"16.8.1.3\",\"CompileTime\":\"Fri Nov 17 17:37:49 CST 2017\",\"Type\":\"Antminer S9\"}],\"id\":1}")
                        )),
                new MinerImpl.Builder()
                        .setType(AntminerType.ANTMINER_S9)
                        .setIpAddress("127.0.0.1")
                        .setApiPort(4028)
                        .build());
    }
}
