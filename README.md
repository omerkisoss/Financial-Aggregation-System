# Financial Aggregation System

This project is built with 5 Microservices, each microservice is Spring Boot Standalone App
Microservices are:
1. cloud-gateway
2. aggregation-service
3. api-fetch-service
4. web-fetch-service
5. eureka-server

![](https://github.com/omerkisoss/Financial-Aggregation-System/blob/master/Aggregation%20System.jpg)

cloud gateway routes rest calls to aggregation service, when request routed 
to aggregation service it sends the request with RabbitMQ to desired fetch service depending on request channel (API,WEB).
fetch service is fetching Data from API or WEB and forwards Data Response back to response queue consumed by aggregation service and sends response back to user.

Instructions for consuming apis:

GET REQUEST:
```
/ondemand?userName={user_name}}&userId={user_id}&channel={channel}&lsad={timestamp}
```

where user_name shall be the name of the user's account we wish to update. 
user_id shall be the id of the user's account we wish to update.
channel shall be one of the sources (API, WEB or STATEMENT).
lsad shall be the last successful aggregation date.

GET REQUEST:
```
/data?userName={user_name}}&userId={user_id}&channel={channel}
```

user_name shall be the name of the user's account we wish to update.
user_id shall be the id of the user's account we wish to update.
channel shall be one of the sources (API, WEB or STATEMENT).

Configurations:
all configurations are within application.yml file.
**api-fetch-service, web-fetch-service -** 
under 'server' property there is 'concurrent-sessions' that excepts integer, for example:
this configuration controls the number of concurrent sessions permitted to all users.
```
server:
  concurrent-sessions: 20
  ```

**aggregation-service :** 
under 'threshold' property there are 2 configurations that controlls each ondemend service time interval:
1.api-threshold: 7200000
2.web-threshold: 14400000

for example:
```
threshold:
    api-threshold: 7200000
    web-threshold: 14400000
```

these configurations of type long and represent milliseconds of interval allowed between 2 ondemend requests per user.

prerequisites:
* docker-compose installed

in order to run all services run command:
```
## build docker images
mvn clean install

##should display three freshly built docker images
docker images

##start up all instances
docker-compose up
```
