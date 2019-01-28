package mn.foreman.prospector.antminer;

import mn.foreman.prospector.model.MinerImpl;
import mn.foreman.prospector.util.AbstractProspectorTest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Verifies that an Antminer B3 can be queried and found. */
public class BraiinsS9ITest
        extends AbstractProspectorTest {

    /** Constructor. */
    public BraiinsS9ITest() {
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
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1545884756,\"Code\":22,\"Msg\":\"BMMiner versions\",\"Description\":\"bmminer bOS_am1-s9-20181127-0_c34516b0\"}],\"VERSION\":[{\"BMMiner\":\"bOS_am1-s9-20181127-0_c34516b0\",\"API\":\"3.1\",\"Miner\":\"bOS_am1-s9-20181127-0_c34516b0\",\"CompileTime\":\"\",\"Type\":\"braiins-am1-s9\"}],\"id\":1}")
                        )),
                new MinerImpl.Builder()
                        .setType(AntminerType.BRAIINS_S9)
                        .setIpAddress("127.0.0.1")
                        .setApiPort(4028)
                        .build());
    }
}
