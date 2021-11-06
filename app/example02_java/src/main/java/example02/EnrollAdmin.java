package example02;

import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;

public class EnrollAdmin {

	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}

	private static final String ORGNAME_ORG1 = "Org1";
	private static final String ADMINNAME_ORG1 = "admin";
	private static final String ADMINPWD_ORG1 = "adminpw";
	private static final String CA_CERT_ORG1 = "profiles/" + ORGNAME_ORG1 + "/tls/" + "ca.org1.example.com-cert.pem";
	private static final String MSPID_ORG1 = "Org1MSP";

	private static void doEnroll(String tlsCert, String orgName, String orgMSP, String adminName, String adminSecret) throws Exception {
		//Load Connection Profile
		String filePath = Paths.get( "profiles", orgName, "connection.json").toString();
		NetworkConfig config = NetworkConfig.fromJsonFile(new File(filePath));
		NetworkConfig.CAInfo caInfo = config.getOrganizationInfo(orgName).getCertificateAuthorities().get(0);
		String caURL = caInfo.getUrl();
		Properties props = new Properties();
		props.put("pemFile", tlsCert);
		props.put("allowAllHostNames", "true");
		//Connect to CA
		HFCAClient caClient = HFCAClient.createNewInstance(caURL, props);
		CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
		caClient.setCryptoSuite(cryptoSuite);
		Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet", orgName));
		//Check user existence in wallet
		Identity adminExists = wallet.get(adminName);
		if (adminExists != null) {
			System.out.println("An identity for the admin user \"admin@"+ orgName + "\" already exists in the wallet");
			return;
		}
		//enroll admin
		final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
		enrollmentRequestTLS.addHost("localhost");
		enrollmentRequestTLS.setProfile("tls");
		Enrollment enrollment = caClient.enroll(adminName, adminSecret, enrollmentRequestTLS);
		Identity user = Identities.newX509Identity(orgMSP, enrollment);
		wallet.put(adminName, user);
		System.out.println("Successfully enrolled user \"admin@"+ orgName + "\" and imported into the wallet");
	}

	public static void main(String[] args) throws Exception {
		doEnroll(CA_CERT_ORG1, ORGNAME_ORG1, MSPID_ORG1, ADMINNAME_ORG1, ADMINPWD_ORG1);
	}
}
