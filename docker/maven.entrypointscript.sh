#!/bin/bash
mvn clean package -e -f /usr/src/quiz-app  &&
find . -name "*.war" -exec cp '{}' /opt/jboss/wildfly/standalone/deployments \;