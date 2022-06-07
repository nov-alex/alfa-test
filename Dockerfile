FROM openjdk:17.0
EXPOSE 8100
WORKDIR /alfa-test
COPY ./alfa-test.jar /alfa-test
CMD java -jar /alfa-test/alfa-test.jar
