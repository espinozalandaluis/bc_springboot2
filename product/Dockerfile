FROM openjdk:11
VOLUME /tmp
EXPOSE 8081
COPY "./target/product.jar" "product.jar"
ENTRYPOINT ["java","-jar","product.jar"]