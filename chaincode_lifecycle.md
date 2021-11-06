# Chaincode Lifecycle

## Environment Varibles

- FABRIC_CFG_PATH
path of `configtx.yaml` file

- CORE_PEER_LOCALMSPID
ID of peer to be accessed

- CORE_PEER_ADDRESS
IP/FQDN+PORT of peer to be accessed

- CORE_PEER_TLS_ENABLED (required only for TLS)

- CORE_PEER_TLS_CERT_FILE

- CORE_PEER_TLS_KEY_FILE

- CORE_PEER_TLS_ROOTCERT_FILE

- CORE_PEER_MSPCONFIGPATH

```bash
export FABRIC_CFG_PATH=${PWD}/fabric-bin/${FABRIC_VERSION}/config
export CORE_PEER_LOCALMSPID=Org1MSP
export CORE_PEER_ADDRESS=$PEER0_ORG1_ADDRESS
export CORE_PEER_TLS_ENABLED=true
export CORE_PEER_TLS_CERT_FILE=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/server.crt
export CORE_PEER_TLS_KEY_FILE=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/server.key
export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp

```

## Steps

- packaging chaincode （make *.tar.gz）

`peer lifecycle chaincode package`

- installing chaincode （upload chaincode to peer）

`peer lifecycle chaincode install`

- getting package ID

`peer lifecycle chaincode queryinstalled`

- sign chaincode

`peer lifecycle chaincode approveformyorg`

- check chaincode status

`peer lifecycle chaincode checkcommitreadiness`

- commit chaincode
`peer lifecycle chaincode commit`

## Example

```bash
#1.0 switch to PEER0@ORG1
#1.1. package
peer lifecycle chaincode package tmp/${CC_LABEL}.tar.gz --path ${CC_PATH} --lang $CC_LANG --label ${CC_LABEL}
#1.2. install
peer lifecycle chaincode install tmp/${CC_LABEL}.tar.gz
#1.3. get packageID
peer lifecycle chaincode queryinstalled --output json | jq -r '.installed_chaincodes[] | select(.label == env.CC_LABEL) | .package_id'
#1.4. sign-ORG1
peer lifecycle chaincode approveformyorg \
-o ${ORDERER_ADDRESS} --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA \
--channelID $CHANNEL_NAME --name ${CC_NAME} --version ${CC_VERSION} \
--init-required --package-id ${PACKAGE_ID} --sequence $CC_SEQ --waitForEvent \
--signature-policy "$CC_POLICY"

#2.0 switch to PEER0@ORG2
#2.1. package
peer lifecycle chaincode package tmp/${CC_LABEL}.tar.gz --path ${CC_PATH} --lang $CC_LANG --label ${CC_LABEL}
#2.2. install
peer lifecycle chaincode install tmp/${CC_LABEL}.tar.gz
#2.3. get packageID
peer lifecycle chaincode queryinstalled --output json | jq -r '.installed_chaincodes[] | select(.label == env.CC_LABEL) | .package_id'
#2.4. sign-ORG2
peer lifecycle chaincode approveformyorg \
-o ${ORDERER_ADDRESS} --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA \
--channelID $CHANNEL_NAME --name ${CC_NAME} --version ${CC_VERSION} \
--init-required --package-id ${PACKAGE_ID} --sequence $CC_SEQ --waitForEvent \
--signature-policy "$CC_POLICY"

#3.0. switch to PEER0@ORG1
#3.1. check readiness
peer lifecycle chaincode checkcommitreadiness \
--channelID $CHANNEL_NAME --name ${CC_NAME} \
--version ${CC_VERSION} --sequence $CC_SEQ --output json --init-required \
--signature-policy "$CC_POLICY"

#4. commit
peer lifecycle chaincode commit -o ${ORDERER_ADDRESS} --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA \
--peerAddresses $PEER0_ORG1_ADDRESS --tlsRootCertFiles $PEER0_ORG1_TLS_ROOTCERT_FILE \
--peerAddresses $PEER0_ORG2_ADDRESS --tlsRootCertFiles $PEER0_ORG2_TLS_ROOTCERT_FILE \
-C $CHANNEL_NAME --name ${CC_NAME} \
--version ${CC_VERSION} --sequence $CC_SEQ --init-required \
--signature-policy "$CC_POLICY"

#5. init
peer chaincode invoke \
-o ${ORDERER_ADDRESS} --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA \
-C $CHANNEL_NAME -n ${CC_NAME}  \
--isInit -c '{"Function":"Init","Args":[]}'

#6. query
peer chaincode query -C $CHANNEL_NAME -n $CC_NAME -c '{"Function":"XXX", "Args":[]}'

#7. invoke
peer chaincode invoke \
-o ${ORDERER_ADDRESS} --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA \
$CC_ENDORSERS \
-C $CHANNEL_NAME -n ${CC_NAME}  \
-c '{"Function":"XXX","Args":["b","a","12"]}'
```
