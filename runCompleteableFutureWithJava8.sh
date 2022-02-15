#!/bin/bash
. ~/.sdkman/bin/sdkman-init.sh
sdk use java $JDK8
./gradlew clean shadowJar
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -javaagent:app/build/libs/app-all.jar=java/util/concurrent/CompletableFuture -jar app/build/libs/app-all.jar java.util.concurrent.CompletableFuture
