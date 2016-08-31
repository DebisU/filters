#!/bin/bash

set -xe

./gradlew release -Prelease.disableRemoteCheck -Prelease.pushTagsOnly  -Prelease.disableUncommittedCheck
./gradlew publish