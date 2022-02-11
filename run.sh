./gradlew clean shadowJar
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -javaagent:app/build/libs/app-all.jar -jar app/build/libs/app-all.jar
