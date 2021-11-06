package example03;

import org.hyperledger.fabric.gateway.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class InvokeTopup {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    private static final String ORGNAME_ORG1 = "Org1";
    private static final String USERNAME_ORG1 = "user01";
    private static final String CHANNEL_NAME = "mychannel";
    private static final String CONTRACT_NAME = "mycc03_java";

    private static final String MSP_ID = ORGNAME_ORG1 + "MSP";
    private static final String EVENT_TOPUP = "TOP-UP_" + MSP_ID;
    private static String TRANSIENT_PAYMENT_JSON = "PAYMENT_JSON";

    enum Message {
        JSON_PAYMENT("{\"transaction_id\":\"%s\", \"amount\":\"%s\", \"date\":\"%s\"}"),
        EVENT_EMPTY("Event payload is empty.");

        private String tmpl;

        private Message(String tmpl) {
            this.tmpl = tmpl;
        }

        public String template() {
            return this.tmpl;
        }
    }

    private static void doTopup(String orgName, String userName, String functionName, String keyTopup, String valueTopup) throws IOException, ContractException, TimeoutException, InterruptedException {
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

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {
            // get the network and contract
            Network network = gateway.getNetwork(CHANNEL_NAME);
            Contract contract = network.getContract(CONTRACT_NAME);

            // listen contract event of "TOP-UP_Org1MSP"
            contract.addContractListener(event -> {
                System.out.println("event triggered: <<" + event.getName() + ">>");
                System.out.println(new String(event.getPayload().orElse(Message.EVENT_EMPTY.template().getBytes(StandardCharsets.UTF_8))));
            }, EVENT_TOPUP);

            // make transient map with json format of payment information
            String dummyTrxID = System.currentTimeMillis() + "";
            String trxDate = new Date().toString();
            String json = String.format(Message.JSON_PAYMENT.template(), dummyTrxID, valueTopup, trxDate);
            Map<String, byte[]> transientMap = new HashMap<>();
            transientMap.put(TRANSIENT_PAYMENT_JSON, json.getBytes(StandardCharsets.UTF_8));
            Transaction trx = contract.createTransaction(functionName).setTransient(transientMap);

            // submit transaction
            byte[] result = trx.submit(keyTopup, valueTopup);
            System.out.println(new String(result));
        }
    }

    public static void main(String[] args) {
        String key = "a";
        String value = "150";
        if (args.length >= 1) {
            key = args[0];
        }

        if (args.length >= 2) {
            value = args[1];
        }

        System.out.println("Top-up account: " + key);
        System.out.println("Top-up amount: " + value);

        try {
            doTopup(ORGNAME_ORG1, USERNAME_ORG1, "Top-up", key, value);
            System.out.println("Completed.");
        } catch (IOException | ContractException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
