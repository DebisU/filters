#!/bin/bash

set -xe

# Publish release
./gradlew release -Prelease.disableRemoteCheck -Prelease.pushTagsOnly  -Prelease.disableUncommittedCheck
./gradlew publish

# Publish to sonarqube
./gradlew sonarqube -Dsonar.host.url=https://sonarqube.spain.schibsted.io -Dsonar.login=${SONAR_TOKEN} -Dsonar.analysis.mode=publish
