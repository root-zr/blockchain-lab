package com.scut.soul.config;


import com.scut.soul.service.sdkService.EnrollAdmin;
import com.scut.soul.service.sdkService.RegisterUser;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.gateway.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Configuration
@EnableAutoConfiguration
public class SDKConfig {

    @Autowired
    private EnrollAdmin enrollAdmin;

    @Autowired
    private RegisterUser registerUser;

    private  final String ORGNAME_ORG1 = "Org1";
    private  final String USERNAME_ORG1 = "user01";

    @Bean(name = "gateway")
    public Gateway gateway() throws Exception {

        enrollAdmin.enroll();
        registerUser.registerUser();

        //get user identity from wallet.
        Path walletPath = Paths.get("wallet", ORGNAME_ORG1);
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        Identity identity = wallet.get(USERNAME_ORG1);

        //check identity existence in wallet
        if (identity == null) {
            System.out.println("The identity \"" + USERNAME_ORG1 + "@"+ ORGNAME_ORG1 + "\" doesn't exists in the wallet");
            throw new Exception();
        }

        //load connection profile
        Path networkConfigPath = Paths.get( "profiles", ORGNAME_ORG1, "connection.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, USERNAME_ORG1).networkConfig(networkConfigPath).discovery(true);

        //create a gateway connection
        return builder.connect();
    }



}
