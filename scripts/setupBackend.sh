#!/bin/bash
set -ev
cd backend
mvn clean install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
mvn test -Dtest="com.qa.tests.unit.**" && mvn test -Dtest="com.qa.tests.integration.**"
