FROM java:8

WORKDIR /

ADD news-0.0.1-SNAPSHOT.jar news.jar

EXPOSE 8080

CMD java -jar -Dspring.profiles.active=aws news.jar
