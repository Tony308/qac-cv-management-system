CV Management System 
![Node.js CI](https://github.com/Tony308/qac-cv-management-system/workflows/Node.js%20CI/badge.svg) 
![Java CI with Maven](https://github.com/Tony308/qac-cv-management-system/workflows/Java%20CI%20with%20Maven/badge.svg)
[![Build Status](https://travis-ci.org/Tony308/qac-cv-management-system.svg?branch=developer)](https://travis-ci.org/Tony308/qac-cv-management-system)
[![codecov](https://codecov.io/gh/Tony308/qac-cv-management-system/branch/developer/graph/badge.svg)](https://codecov.io/gh/Tony308/qac-cv-management-system)
====


The CV application is a small project for educational purposes and not meant for actual use.

## Application Deployment

-  Run locally
-  Docker deployment

## Front-end: ReactJS
_____

Note: _Requires Node.js installed on machine. Does not support version 8.x_
To start the React frontend:
  1. Open a terminal in the frontend directory
  2. Execute: 
      - `npm install`
      - `npm start`


## Database: MongoDb
----

MongoDb requires additional setup when starting for the first time. 

Note: Recommended you change the login credentials.

  1. Start MongoDb; type `mongod`
  2. Open another terminal/command line, execute: `mongo mongo-init.js`
  3. IF successful. Ctrl+C the mongo terminal then, start mongo using `mongod --auth`

---
Note: Environment variables have been setup for MongoDb

_Without environment variables, simply navigate to your MongoDb bin directory to execute the commands._
  
- To access the database. Open another terminal and type `mongo -u <username> --authenticationDatabase <db>`

- Example `mongo -u mongo --authenticationDatabase Customers`

More detail @ [MongoDb Website](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/#start-mdb-edition-from-the-command-interpreter)


## Backend: Spring-Boot
___

Note: Requires mongodb to be started first and successfully running

Login credentials are located in the src/main/application.properties. Change as necessary.

To start the Spring Boot project:
  1. Navigate to the backend directory
  2. Execute `mvn clean install; mvn spring-boot:run`

### Introduction of Spring profiles
| Dev (Default) | Test | Production |
| --- | --- | --- |
| No flag | `-Ptest` | `-Pprod` |

Append the flag to use the desired spring profile.

# Docker Deployment

#### Pre-requisite:
  - Create a docker network with name `cv-network`
    - `docker network create cv-network`

#### Deployment:
  1. Open terminal in the app root folder.
  2. Execute `docker-compose up`.

#### **Spring Profile with Docker-Compose:**

Edit `SPRING_PROFILES_ACTIVE` environment variable

| Test | Production |
| --- | --- |
| `test` | `prod` |

Images can also be found on [Docker Hub](https://hub.docker.com/u/tonyh308).

Testing
----

Introduction of continuous integration using Travis CI and Github Workflows.

Travis builds and tests backend using JUnit4 framework:

`.travis.yml`

Github Workflows CI for Maven & React:

`.github/workflows/`
- `maven.yml`
- `node.yml`
