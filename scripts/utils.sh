function setGoCC() {
    export CC_LANG=golang
    export CC_PATH=${PWD}/chaincode/chaincode_example01/go
}

function setupCommonENV() {
    export FABRIC_CFG_PATH=${PWD}/fabric-bin/${FABRIC_VERSION}/config
    export ORDERER_ADDRESS=localhost:7050
    export PEER0_ORG1_ADDRESS=localhost:7051
    export PEER0_ORG2_ADDRESS=localhost:9051
    export PEER0_ORG1_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
    export PEER0_ORG2_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt
    
    export ORDERER_CA=${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem
    export CHANNEL_NAME=mychannel
}

function setupPeerENV1() {
    export CORE_PEER_LOCALMSPID=Org1MSP
    export CORE_PEER_ADDRESS=$PEER0_ORG1_ADDRESS
    export CORE_PEER_TLS_ENABLED=true
    export CORE_PEER_TLS_CERT_FILE=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/server.crt
    export CORE_PEER_TLS_KEY_FILE=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/server.key
    export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
    export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
}

function setupPeerENV2() {
    export CORE_PEER_LOCALMSPID=Org2MSP
    export CORE_PEER_ADDRESS=$PEER0_ORG2_ADDRESS
    export CORE_PEER_TLS_ENABLED=true
    export CORE_PEER_TLS_CERT_FILE=${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/server.crt
    export CORE_PEER_TLS_KEY_FILE=${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/server.key
    export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt
    export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp
}