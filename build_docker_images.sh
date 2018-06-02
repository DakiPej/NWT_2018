#!/bin/bash
mvn package dockerfile:build -f GatewayModule/GatewayModule/
mvn package dockerfile:build -f ImageModule/imageModule/
mvn package dockerfile:build -f InteractionModule/interactionModel/
mvn package dockerfile:build -f PostModule/
mvn package dockerfile:build -f ServiceRegistryModule/
mvn package dockerfile:build -f UserModule/

docker-compose up