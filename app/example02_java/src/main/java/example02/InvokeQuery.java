package example02;

import org.hyperledger.fabric.gateway.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InvokeQuery {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    private static final String ORGNAME_ORG1 = "Org1";
    private static final String USERNAME_ORG1 = "user01";
    private static final String CHANNEL_NAME = "mychannel";
    private static final String CONTRACT_NAME = "mycc02_java";

    private static void doQuery(String orgName, String userName, String functionName, String key)
            throws IOException, ContractException {
        //get user identity from wallet.
        Path walletPath = Paths.get("wallet", orgName);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        Identity identity = wallet.get(userName);

        //check identity existence in wallet
        if (identity == null) {
            System.out.println("The identity \"" + userName + "@"+ orgName + "\" doesn't exists in the wallet");
            return;
        }

        //load connection profile
        Path networkConfigPath = Paths.get( "profiles", orgName, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, userName).networkConfig(networkConfigPath).discovery(true);

        //create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);

            byte[] result = contract.evaluateTransaction(functionName, key);
            System.out.println(new String(result));
        }
    }

    public static void main(String[] args) {
        try {
            doQuery(ORGNAME_ORG1, USERNAME_ORG1, "Query", "a");
        } catch (IOException | ContractException e) {
            e.printStackTrace();
        }
    }
}
