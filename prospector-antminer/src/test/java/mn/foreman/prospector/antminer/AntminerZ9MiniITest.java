package mn.foreman.prospector.antminer;

import mn.foreman.prospector.model.MinerImpl;
import mn.foreman.prospector.util.AbstractProspectorTest;
import mn.foreman.util.rpc.FakeRpcMinerServer;
import mn.foreman.util.rpc.RpcHandler;

import com.google.common.collect.ImmutableMap;

/** Verifies that an Antminer B3 can be queried and found. */
public class AntminerZ9MiniITest
        extends AbstractProspectorTest {

    /** Constructor. */
    public AntminerZ9MiniITest() {
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
                                        "{\"STATUS\":[{\"STATUS\":\"S\",\"When\":1545582055,\"Code\":22,\"Msg\":\"CGMiner versions\",\"Description\":\"cgminer 4.9.0\"}],\"VERSION\":[{\"CGMiner\":\"4.9.0\",\"API\":\"3.1\",\"Miner\":\"9.0.0.5\",\"CompileTime\":\"Sat May 26 20:42:30 CST 2018\",\"Type\":\"Antminer Z9-Mini\"}],\"id\":1}")
                        )),
                new MinerImpl.Builder()
                        .setType(AntminerType.ANTMINER_Z9)
                        .setIpAddress("127.0.0.1")
                        .setApiPort(4028)
                        .build());
    }
}