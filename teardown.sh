#!/bin/bash

echo "Clearing"
docker rm -f `docker ps -qa`
docker rmi -f $(docker images | awk '($1 ~ /dev-peer.*/) {print $3}')
docker volume prune -f
docker network prune -f

rm -rf system-genesis-block/*.block
rm -rf channel-artifacts
rm -rf organizations/peerOrganizations
rm -rf organizations/ordererOrganizations
rm -rf tmp
sudo rm -rf organizations/fabric-ca/org1/*
sudo rm -rf organizations/fabric-ca/org2/*
sudo rm -rf organizations/fabric-ca/ordererOrg/*