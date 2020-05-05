#!/bin/bash
set -ev
cd backend
mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
mvn test -B
