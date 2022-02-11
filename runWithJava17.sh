#!/bin/bash
. ~/.sdkman/bin/sdkman-init.sh
sdk use java $JDK8
./gradlew clean shadowJar
sdk use java $JDK17
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -javaagent:app/build/libs/app-all.jar -jar app/build/libs/app-all.jar
