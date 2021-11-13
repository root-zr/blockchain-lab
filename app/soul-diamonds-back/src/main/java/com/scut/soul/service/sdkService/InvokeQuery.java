package com.scut.soul.service.sdkService;

import com.scut.soul.config.SDKConfig;
import org.apache.commons.math3.ml.neuralnet.oned.NeuronString;
import org.hyperledger.fabric.gateway.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Service
public class InvokeQuery {

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    @Autowired
    Gateway gateway;

    private final String ORGNAME_ORG1 = "Org1";
    private final String USERNAME_ORG1 = "user01";
    private final String CHANNEL_NAME = "mychannel";
    private final String CONTRACT_NAME = "mycc_java";

    public InvokeQuery() throws Exception {
    }


    private String doQuery(String orgName, String userName, String functionName, String key)
            throws IOException, ContractException {


            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);

            byte[] result = contract.evaluateTransaction(functionName, key);

            //byte[] result = contract.submitTransaction(functionName, key);
            System.out.println(new String(result));
            return new String(result);

    }


    public String query(String functionName,
                        String key) throws Exception {
        String res = "";

        try {
             res = doQuery(ORGNAME_ORG1, USERNAME_ORG1, functionName, key);
            //doQuery(ORGNAME_ORG1, USERNAME_ORG1, "Hi", "NAME");
        } catch (IOException | ContractException e) {
            e.printStackTrace();
        }
        return res;
    }
}
