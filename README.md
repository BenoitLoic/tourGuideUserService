# tourGuideUserService
User Service for TourGuide APP

### Start application

* copy/clone this project on your computer.
* cd PATH/TO/PROJECT/ROOT where build.gradle is present
* run: ./gradlew bootJar
* run: docker build . -t tourguide/userservice
* Access endpoints on localhost:8082/
* test run: ./gradlew test

## Technical:

1. Java : 17
2. Gradle 7+
3. SpringBoot : 2.6.6
4. Docker

## EndPoints

* POST: "/addUser"
    * parameter: user
    * return: the user saved
* GET: "/getAllUsers"
    * parameter: no_parameter
    * return: List<user>
* GET: "/getUserByUsername"
    * parameter: username
    * return: the user


