#!/bin/bash

set -xe

#./gradlew check
# Skipping checkstyle, findbug and pmd temporarily
./gradlew check -x checkstyleMain -x checkstyleTest -x findbugsMain -x findbugsTest -x pmdMain -x pmdTest


if [[ ${TRAVIS_BRANCH} = "master" && ${TRAVIS_PULL_REQUEST} != "false" ]]; then
    # Publicamos una versión beta para a esa branch
    ./gradlew publish -Ppr.number=${TRAVIS_PULL_REQUEST}
fi