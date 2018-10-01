#!/bin/bash
mvn clean install -e -f /usr/src/quiz-app  &&
find . -name "*.war" -exec cp '{}' /opt/jboss/wildfly/standalone/deployments \;