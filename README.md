# Flight Status Service Rest Full API Service

This service will provide CRUD for flight status operations.

The service is developed using Spring reactive and functional programming techniques

## Tested On
Mac OS, 6GB heap memory, 1 TB Hard drive

<br/>

## Build the project
```
mvn clean package
or
mvn clean install
```

<br/>

## Run the project
### Pre-requisites

PreRequisites:
 * Clone the mock server (https://github.com/mudassirshahzad/VertxMockWebServer)
 * cd VertxMockWebServer
 * mvn package
 * mvn exec:java
 * The mock server will start on port 9090

### Running the flight service
 ```
mvn spring-boot:run 
```
```
Can also be run using
docker-compose up
```
The service will start on port 8083

<br/>

## APIs
### Flight Status
HTTP POST http://localhost:8083/flightStatus/ﬂight
with body
```
Request
{
    "data":{
        "flightDate": "2021-07-03",
        "departureAirport": "DXB",
        "arrival": "LHR"
    }
}
```
```
Response
{
    "flightNumber": "DummyFlightNumberInformationFromMockServer1"
}
```
```
* Gets flightDate, departureAirport and arrival as input request Data
* Calls 5 independent downstream connections (VertxMockWebServer - port 9090)
* Each with 500-800 ms delay which is set randomly in the test
* Responds within the required SLA time (850 ms)

For the purpose to achieve the SLA, I have called the webclient two times. 
First is for the spring boot warmup and the second is actual call.
```
[Flight Status Test Screenshot](./src/site/screenshots/FlightStatus.png)

<br/>

### Price
HTTP GET http://localhost:8083/flightStatus/price/12345/2021-07-03
```
Response
{
    "price": 500.0
}
```
```
 * Gets flightNumber and date as api input parameters
 * Calls a price engine to get the price
 * In Test class, price engine is mocked to consume 3GB of RAM
 * SLA I could achieve is on average 100 ms
```
[Price Test Screenshot](./src/site/screenshots/Price.png)

<br/>

[PostmanCollection](./src/main/resources/Emirates_STE_Assessment.postman_collection.json)

[Documentation](./src/site/markdown/index.md)
[FAQ](./src/site/markdown/faq.md)
