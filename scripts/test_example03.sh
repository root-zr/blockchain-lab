. scripts/utils.sh

echo '######## - (COMMON) setup variables - ########'
setupCommonENV
export CC_NAME=mycc03

if [[ $# -ge 1 ]]; then
    export CC_NAME=$1
fi

if [[ $# -ge 2 && "$2" == "true" ]]; then
    if [[ "$CORE_PEER_TLS_ENABLED" == "true" ]]; then
        export CC_ENDORSERS="--peerAddresses $PEER0_ORG1_ADDRESS --tlsRootCertFiles $PEER0_ORG1_TLS_ROOTCERT_FILE --peerAddresses $PEER0_ORG2_ADDRESS --tlsRootCertFiles $PEER0_ORG2_TLS_ROOTCERT_FILE"
    else
        export CC_ENDORSERS="--peerAddresses $PEER0_ORG1_ADDRESS --peerAddresses $PEER0_ORG2_ADDRESS"
    fi
fi

echo "'CHAINCODE_NAME' set to '$CC_NAME'"
echo "'CHAINCODE_LANG' set to '$CC_LANG'"
echo "'CHAINCODE_PATH' set to '$CC_PATH'"

# echo '######## - (ORG1) init chaincode - ########'
setupPeerENV1
set -x
if [[ "$CORE_PEER_TLS_ENABLED" == "true" ]]; then
    peer chaincode invoke \
    -o ${ORDERER_ADDRESS} --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA \
    $CC_ENDORSERS \
    -C $CHANNEL_NAME -n ${CC_NAME}  \
    --isInit -c '{"Function":"Init","Args":["a","100","b","200"]}'
    $CC_ENDORSERS
else
    peer chaincode invoke \
    -o ${ORDERER_ADDRESS} \
    $CC_ENDORSERS \
    -C $CHANNEL_NAME -n ${CC_NAME}  \
    --isInit -c '{"Function":"Init","Args":["a","100","b","200"]}'
fi
set +x
sleep 10

echo '######## - (ORG1) query chaincode - ########'
setupPeerENV1
set -x
peer chaincode query -C $CHANNEL_NAME -n $CC_NAME -c '{"Function":"Query", "Args":["a"]}'
set +x

echo '######## - (ORG2) query chaincode - ########'
setupPeerENV2
set -x
peer chaincode query -C $CHANNEL_NAME -n $CC_NAME -c '{"Function":"Query", "Args":["b"]}'
set +x

echo '######## - (ORG1) invoke chaincode - ########'
setupPeerENV1
set -x
if [[ "$CORE_PEER_TLS_ENABLED" == "true" ]]; then
    peer chaincode invoke \
    -o ${ORDERER_ADDRESS} --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA \
    $CC_ENDORSERS \
    -C $CHANNEL_NAME -n ${CC_NAME}  \
    -c '{"Function":"Transfer","Args":["a","b","10"]}'
    
else
    peer chaincode invoke 
    -o ${ORDERER_ADDRESS} \
    $CC_ENDORSERS \
    -C $CHANNEL_NAME -n ${CC_NAME}  \
    -c '{"Function":"Transfer","Args":["a","b","10"]}'
fi
set +x
# sleep 5

echo '######## - (ORG2) invoke chaincode - ########'
set -x
setupPeerENV2
if [[ "$CORE_PEER_TLS_ENABLED" == "true" ]]; then
    peer chaincode invoke \
    -o ${ORDERER_ADDRESS} --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA \
    $CC_ENDORSERS \
    -C $CHANNEL_NAME -n ${CC_NAME}  \
    -c '{"Function":"Transfer","Args":["b","a","12"]}'
else
    peer chaincode invoke -o ${ORDERER_ADDRESS} -C $CHANNEL_NAME -n ${CC_NAME}  \
    --peerAddresses $PEER0_ORG1_ADDRESS \
    $CC_ENDORSERS \
    -c '{"Function":"Transfer","Args":["b","a","12"]}'
fi
set +x
sleep 5

echo '######## - (ORG1) query chaincode - ########'
setupPeerENV1
set -x
peer chaincode query -C $CHANNEL_NAME -n $CC_NAME -c '{"Function":"Query", "Args":["a"]}'
set +x

echo '######## - (ORG2) query chaincode - ########'
setupPeerENV2
set -x
peer chaincode query -C $CHANNEL_NAME -n $CC_NAME -c '{"Function":"Query", "Args":["b"]}'
set +x
echo '############# END ###############'