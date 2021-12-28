package com.scut.soul.service.sdkService;

import com.scut.soul.config.SDKConfig;
import org.hyperledger.fabric.gateway.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class InvokeUpdate {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    @Autowired
    Gateway gateway;

    private final String CHANNEL_NAME = "mychannel";
    private final String CONTRACT_NAME = "mycc_java";

    public InvokeUpdate() throws Exception {
    }

    private void doUpdate(String functionName, String keyFrom, String keyTo) throws ContractException, TimeoutException, InterruptedException, IOException {

        // get the network and contract
        Network network = gateway.getNetwork(CHANNEL_NAME);
        Contract contract = network.getContract(CONTRACT_NAME);

        byte[] result = contract.submitTransaction(functionName, keyFrom, keyTo);
        System.out.println(new String(result));
    }

    public void update(String keyFrom, String keyTo) {
        try {
            doUpdate("setValue", keyFrom, keyTo);
        } catch (IOException | ContractException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
