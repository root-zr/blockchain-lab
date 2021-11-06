#!/bin/bash

echo "1.Deploy Chaincode"
echo "skip"
############################ EXAMPLE 01 ###########################################
######## deploy example01(golang version) on v1.0
. scripts/deploy_chaincode.sh
. scripts/test_example01.sh

######## upgrade example01(golang version) to v1.1
# . scripts/deploy_chaincode.sh golang ${PWD}/chaincode/chaincode_example01/go mycc v1.1 2
# . scripts/test_example01.sh

######## deploy example01(java version) on v1.0
. scripts/deploy_chaincode.sh java ${PWD}/chaincode/chaincode_example01/java mycc_java
. scripts/test_example01.sh mycc_java

######## upgrade example01(java version) to v1.1
# . scripts/deploy_chaincode.sh java ${PWD}/chaincode/chaincode_example01/java mycc_java v1.1 2
# . scripts/test_example01.sh mycc_java

######## deploy example01(node version) on v1.0
# . scripts/deploy_chaincode.sh node ${PWD}/chaincode/chaincode_example01/node mycc_node
# . scripts/test_example01.sh mycc_node

######## upgrade example01(node version) to v1.1
# . scripts/deploy_chaincode.sh node ${PWD}/chaincode/chaincode_example01/node mycc_node v1.1 2
# . scripts/test_example01.sh mycc_node

###################################################################################


############################ EXAMPLE 02(golang version)  ##########################
######## deploy example02(golang version) on v1.0
. scripts/deploy_chaincode.sh golang ${PWD}/chaincode/chaincode_example02/go mycc02_go
. scripts/test_example02.sh mycc02_go

######## upgrade example02(golang version) to v1.1
# . scripts/deploy_chaincode.sh golang ${PWD}/chaincode/chaincode_example02/go mycc02_go v1.1 2
# . scripts/test_example02.sh mycc02_go upgrade
###################################################################################


############################ EXAMPLE 02(java version)  ############################
######## deploy example02(java version) on v1.0
# . scripts/deploy_chaincode.sh java ${PWD}/chaincode/chaincode_example02/java mycc02_java v1.0 1 "OR('Org1MSP.peer', 'Org2MSP.peer')"
# . scripts/test_example02.sh mycc02_java

######## upgrade example02(java version) to v1.1
# . scripts/deploy_chaincode.sh java ${PWD}/chaincode/chaincode_example02/java mycc02_java v1.1 2 "OR('Org1MSP.peer', 'Org2MSP.peer')"
# . scripts/test_example02.sh mycc02_java upgrade
###################################################################################


############################ EXAMPLE 02(node.js version)  ############################
######## deploy example02(node.js version) on v1.0
# . scripts/deploy_chaincode.sh node ${PWD}/chaincode/chaincode_example02/node mycc02_node v1.0 1 "OR('Org1MSP.peer', 'Org2MSP.peer')"
# . scripts/test_example02.sh mycc02_node

######## upgrade example02(node.js version) to v1.1
# . scripts/deploy_chaincode.sh node ${PWD}/chaincode/chaincode_example02/node mycc02_node v1.1 2 "OR('Org1MSP.peer', 'Org2MSP.peer')"
# . scripts/test_example02.sh mycc02_node upgrade
###################################################################################



############################ EXAMPLE 03(golang version)  ##########################
######## deploy example02(java version) on v1.0
# . scripts/deploy_chaincode.sh golang ${PWD}/chaincode/chaincode_example03/go mycc03_go v1.0 1 "AND('Org1MSP.peer', 'Org2MSP.peer')"
# . scripts/test_example03.sh mycc03_go

######## upgrade example02(java version) to v1.1
# . scripts/deploy_chaincode.sh golang ${PWD}/chaincode/chaincode_example03/go mycc03_go v1.1 2 "AND('Org1MSP.peer', 'Org2MSP.peer')"
# . scripts/test_example03.sh mycc03_go upgrade
###################################################################################


############################ EXAMPLE 03(java version)  ############################
######## deploy example03(java version) v1.0
# . scripts/deploy_chaincode.sh java ${PWD}/chaincode/chaincode_example03/java mycc03_java
# . scripts/test_example03.sh mycc03_java

######## upgrade example03(java version) to v1.1
# . scripts/deploy_chaincode.sh java ${PWD}/chaincode/chaincode_example03/java mycc03_java v1.1 2
# . scripts/test_example03.sh mycc03_java upgrade
###################################################################################


echo

echo "Done."