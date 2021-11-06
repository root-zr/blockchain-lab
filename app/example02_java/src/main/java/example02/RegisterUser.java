package example02;

import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

import java.io.File;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Properties;
import java.util.Set;

public class RegisterUser {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    private static final String ORGNAME_ORG1 = "Org1";
    private static final String CA_CERT_ORG1 = "profiles/" + ORGNAME_ORG1 + "/tls/" + "ca.org1.example.com-cert.pem";
    //private static final String CA_URL_ORG1 = "http://localhost:7054";
    private static final String MSPID_ORG1 = "Org1MSP";
    private static final String ADMINNAME_ORG1 = "admin";
    private static final String USERNAME_ORG1 = "user01";
    private static final String USERPWD_ORG1 = "user01pw";

    private static void doRegisterUser(String tlsCert, String orgName, String orgMSP, String adminName, String userName, String userSecret) throws Exception {
        //Load Connection Profile
        String filePath = Paths.get( "profiles", orgName, "connection.json").toString();
        NetworkConfig config = NetworkConfig.fromJsonFile(new File(filePath));
        NetworkConfig.CAInfo caInfo = config.getOrganizationInfo(orgName).getCertificateAuthorities().get(0);
        String caURL = caInfo.getUrl();
        Properties props = new Properties();
        props.put("pemFile", tlsCert);
        props.put("allowAllHostNames", "true");

        //Connect CA
        HFCAClient caClient = HFCAClient.createNewInstance(caURL, props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet", orgName));

        //Check admin existence in wallet
        X509Identity adminIdentity = (X509Identity) wallet.get(adminName);
        if (adminIdentity == null) {
            System.out.println("\"" + adminName + "@" + orgName + "\" needs to be enrolled and added to the wallet first");
            return;
        }

        //Check admin to be created existence in wallet
        if (wallet.get(userName) != null) {
            System.out.println("An identity for the user \"" + userName + "@" + orgName + "\" already exists in the wallet");
            return;
        }

        //Get admin's UserContext
        Enrollment adminKeys = new Enrollment() {
            @Override
            public PrivateKey getKey() {
                return adminIdentity.getPrivateKey();
            }
            @Override
            public String getCert() {
                return Identities.toPemString(adminIdentity.getCertificate());
            }
        };
        User admin = new UserContext(adminName, orgMSP, adminKeys);

        //Register user
        RegistrationRequest registrationRequest = new RegistrationRequest(userName);
        registrationRequest.setSecret(userSecret);
        caClient.register(registrationRequest, admin);
        //Enroll user
        Enrollment enrollment = caClient.enroll(userName, userSecret);
        Identity user = Identities.newX509Identity(orgMSP, enrollment);
        wallet.put(userName, user);
        System.out.println("Successfully enrolled user \"" + userName + "@" + orgName + "\" and imported into the wallet");
    }

    public static void main(String[] args) throws Exception {
        doRegisterUser(CA_CERT_ORG1, ORGNAME_ORG1, MSPID_ORG1, ADMINNAME_ORG1, USERNAME_ORG1, USERPWD_ORG1);
    }
}
