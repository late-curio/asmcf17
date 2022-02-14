#!/bin/bash
. ~/.sdkman/bin/sdkman-init.sh
sdk use java $JDK8
./gradlew clean shadowJar
sdk use java $JDK17
java -Xmx1536M --add-exports=java.base/sun.nio.ch=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED --add-opens=java.base/java.io=ALL-UNNAMED --add-exports=jdk.unsupported/sun.misc=ALL-UNNAMED -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -javaagent:app/build/libs/app-all.jar -jar app/build/libs/app-all.jar
