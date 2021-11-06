. scripts/utils.sh

echo '######## - (COMMON) setup variables - ########'
setupCommonENV

# echo '######## - (ORG1) create channel - ########'
setupPeerENV1
pushd ./channel-artifacts
if [[ "$CORE_PEER_TLS_ENABLED" == "true" ]]; then
    peer channel create -o ${ORDERER_ADDRESS} -c ${CHANNEL_NAME} -f ${CHANNEL_NAME}.tx --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA
else
    peer channel create -o ${ORDERER_ADDRESS} -c ${CHANNEL_NAME} -f ${CHANNEL_NAME}.tx
fi
popd

echo '######## - (ORG1) join channel - ########'
setupPeerENV1
peer channel join -b ./channel-artifacts/${CHANNEL_NAME}.block

echo '######## - (ORG1) update anchor - ########'
setupPeerENV1
if [[ "$CORE_PEER_TLS_ENABLED" == "true" ]]; then
    peer channel update -o ${ORDERER_ADDRESS} -c ${CHANNEL_NAME} -f ./channel-artifacts/Org1MSPanchors.tx --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA
else
    peer channel update -o ${ORDERER_ADDRESS} -c ${CHANNEL_NAME} -f ./channel-artifacts/Org1MSPanchors.tx
fi

echo '######## - (ORG2) join channel - ########'
setupPeerENV2
peer channel join -b ./channel-artifacts/${CHANNEL_NAME}.block

echo '######## - (ORG2) update anchor - ########'
setupPeerENV2
if [[ "$CORE_PEER_TLS_ENABLED" == "true" ]]; then
    peer channel update -o ${ORDERER_ADDRESS} -c ${CHANNEL_NAME} -f ./channel-artifacts/Org2MSPanchors.tx --tls $CORE_PEER_TLS_ENABLED --cafile $ORDERER_CA
else
    peer channel update -o ${ORDERER_ADDRESS} -c ${CHANNEL_NAME} -f ./channel-artifacts/Org2MSPanchors.tx
fi