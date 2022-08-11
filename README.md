# WeatherService
WeatherService

Just a weather service with an API.

To add or remove a new city, you need to change cities.json

Features:
- Periodic collection and averaging of temperature data in cities using three free weather services;
- REST Endpoint API.

HOW TO RUN:
1. Clone this project;
2. Open/Import project in IDEA;
3. Create 'weatherservice' MySQL database for runtime;
4. Create 'weatherservicetest' MySQL database for test;
5. Select 'WeatherServiceApplication' in Run/Debug Configurations and run.

If your IDEA didn't generate it on its own when importing the project create SpringBoot run configuration manually: 
![runconfiguration.png](runconfiguration.png)

EDIT DATABASE SETTINGS:

You can change the data to connect to the database:
for runtime in application.properties
for test in application-test.properties
   