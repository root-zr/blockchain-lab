. scripts/utils.sh

echo '######## - (COMMON) setup variables - ########'
setupCommonENV
export CC_NAME=mycc

if [[ $# -ge 1 ]]; then
    export CC_NAME=$1
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
    -C $CHANNEL_NAME -n ${CC_NAME}  \
    --isInit -c '{"Function":"Init","Args":[]}'
else
    peer chaincode invoke \
    -o ${ORDERER_ADDRESS} \
    -C $CHANNEL_NAME -n ${CC_NAME}  \
    --isInit -c '{"Function":"Init","Args":[]}'
fi
set +x
sleep 5

echo '######## - (ORG1) query chaincode - ########'
setupPeerENV1
set -x
peer chaincode query -C $CHANNEL_NAME -n $CC_NAME -c '{"Function":"Hi", "Args":[]}'
set +x

echo '######## - (ORG2) query chaincode - ########'
setupPeerENV2
set -x
peer chaincode query -C $CHANNEL_NAME -n $CC_NAME -c '{"Function":"Hi", "Args":[]}'
set +x
echo '############# END ###############'


echo '######## - (ORG1) setValue - ########'
setupPeerENV1
set -x
if [[ "$CORE_PEER_TLS_ENABLED" == "true" ]]; then
    peer chaincode invoke \
    -o ${ORDERER_ADDRESS} --ordererTLSHostnameOverride orderer.example.com --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA \
    $CC_ENDORSERS \
    -C $CHANNEL_NAME -n ${CC_NAME}  \
    -c '{"Function":"setValue","Args":["110","zhangsan"]}'

else
    peer chaincode invoke
    -o ${ORDERER_ADDRESS} \
    $CC_ENDORSERS \
    -C $CHANNEL_NAME -n ${CC_NAME}  \
    -c '{"Function":"setValue","Args":["110","zhangsan"]}'
fi

set +x

sleep 5

echo '######## - (ORG1) setValue has finished- ########'

echo '######## - (ORG2) query chaincode - ########'
setupPeerENV2
set -x
peer chaincode query -C $CHANNEL_NAME -n $CC_NAME -c '{"Function":"getValue", "Args":["110"]}'
set +x
echo '############# END ###############'
