@echo off

echo === Uruchamiam discovery-service ===
start cmd /k "cd discovery-service && mvn spring-boot:run"
timeout /t 30 /nobreak

echo === Uruchamiam account-service ===
start cmd /k "cd account-service && mvn spring-boot:run"
timeout /t 30 /nobreak

echo === Uruchamiam campaign-service ===
start cmd /k "cd campaign-service && mvn spring-boot:run"
timeout /t 30 /nobreak

echo === Uruchamiam gateway ===
start cmd /k "cd gateway-api && mvn spring-boot:run"
timeout /t 60 /nobreak

echo === Wszystkie serwisy wystartowały! ===
pause
