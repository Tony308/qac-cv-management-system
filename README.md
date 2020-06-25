CV Management System
====

The CV application is a small project for educational purposes and not meant for actual.

## Application Deployment

-  Run locally
-  Docker deployment


## Front-end: ReactJS
_____

Note: _Requires Node.js installed on machine_
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

  1. Open terminal in the app roor folder.
  2. Execute `docker-compose up`. Will fail but it will create a default network bridge to use.
  3. Execute `docker network inspect <name-of-network>`. Remember the IP
  4. In the backend, application.properties; replace the localhost/IP with the IPv4 of the docker network: X.X.X.2
  6. Repeat step 2.


Testing
----

Introduction of continuous integration using Travis CI and Github Workflows.

Travis builds and tests backend using JUnit4 framework.

`.travis.yml`

Github Workflows tests backend as well and frontend using Jest framework.

`.github/workflows/`
- `maven.yml`
- `node.yml`