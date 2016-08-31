#!/bin/bash

set -xe

./gradlew check

if [[ ${TRAVIS_BRANCH} = "master" && ${TRAVIS_PULL_REQUEST} != "false" ]]; then
    ./gradlew publish -Ppr.number=${TRAVIS_PULL_REQUEST}
fi