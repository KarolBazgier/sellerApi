Do uruchomienia: Java 17, Intelij, Maven 3.9

1. Pobierz projekt
2. Zbuduj 'common-library' (cd common library && mvn clean install)
3. Uruchom run-api.bat lub kolejno 'discovery-service', 'account-service', 'campaign-service', 'gateway-api' (eg. cd discovery-service && mvn clean install && mvn spring-boot:run)
4. ApiGateway + Frontend dostępne pod adresem : 'localhost:8080'
