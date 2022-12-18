FROM openjdk:11
VOLUME /tmp
EXPOSE 8888
COPY "./target/config-server.jar" "ms-config-server.jar"
ENTRYPOINT ["java","-jar","ms-config-server.jar"]