#!/bin/bash
mvn clean package dockerfile:build -f GatewayModule/GatewayModule/
mvn clean package dockerfile:build -f ImageModule/imageModule/
mvn clean package dockerfile:build -f InteractionModule/interactionModel/
mvn clean package dockerfile:build -f PostModule/
mvn clean package dockerfile:build -f ServiceRegistryModule/
mvn clean package dockerfile:build -f UserModule/
