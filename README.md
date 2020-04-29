# CV Management System

Front-end
======
#### React.JS
____
Note: _Requires Node.js installed on machine_
To start the React frontend:
  1. Navigate to the frontend directory
  2. Type "`npm install`"
  3. Once complete, "`npm start`"

Javascript frontend using React.JS and Node.JS

Axios library for HTTP requests. More info @ [Axios](https://github.com/axios/axios)

Database
======
### MongoDb
MongoDb requires additional setup when starting for the first time. Using `mongorestore` command to restore the database with the login credentials. username: mongo, pwd: trivago.

Note: Recommended you change the login credentials.

  1. Start MongoDb; type `mongod`
  2. Open another terminal/command line, type: `mongorestore -d Customers --restoreDbUsersAndRoles path/to/Customers-directory`
  3. IF successful. Ctrl+C the mongo terminal then, start mongo using `mongod --auth`
  
Note: Customers directory is located in qac-cv-management-system/mongo

Command by default `mongorestore -d Customers --restoreDbUsersAndRoles ./mongo/Customers`

#### Windows OS
______
Assuming you have everything setup for MongoDb

_Without environment variables, simply navigate to your MongoDb bin directory to execute the commands._

To start MongoDb, you'll need to:
  1. Navigate to your MongoDb install bin folder: "C:\Program Files\MongoDb\Server\3.0\bin" (Default).
  2. Start "mongod.exe"
  3. In a new cmd, execute `mongorestore -d Customers --restoreDbUsersAndRoles path/to/Customers-directory`
  4. Ctrl+C the mongod cmd
  5. Execute `mongod --auth` in cmd.
  
#### Linux 
_____
Note: Environment variables have been setup for MongoDb

_Without environment variables, simply navigate to your MongoDb bin directory to execute the commands._

To start MongoDb:
  1. In a terminal, execute `mongod`
  2. In a new terminal, execute `mongorestore -d Customers --restoreDbUsersAndRoles path/to/Customers-directory`
  
- To access the database. Open another terminal and type `mongo -u <username> --authenticationDatabase <db>`

- Example `mongo -u mongo --authenticationDatabase Customers`

More detail @ [MongoDb Website](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/#start-mdb-edition-from-the-command-interpreter)

Spring Boot/Backend
=======

Assuming you have Maven installed and setup.

Note: ***REQUIRES MONGODB TO BE STARTED FIRST AND SUCCESSFULLY RUNNING***

***Login credentials are located in the src/main/application.properties. Change as necessary.***

To start the Spring Boot project simply:
  1. Navigate to the backend directory
  2. Open a BASH/command line terminal for this directory
  3. Execute `mvn clean install; mvn spring-boot:run`

Dev-ops
========

## Docker
***Two methods to start docker containers; docker-compose or docker run from each image.***

#### Packaging Spring Boot App

*_Required BEFORE building image_*

Navigate to the backend directory. Type `mvn clean package` to package the project into a deployable jar file. 
It is automatically saved to the `target/` folder.

### Docker-compose
  1. Open up terminal to root directory.
  2. Execute `docker-compose build;docker-compose up`. Will fail but it will create a default network bridge to use.
  3. Execute `docker network inspect <name-of-network>`. Remember the IP
  4. In the backend, application.properties; replace the localhost/IP with the IPv4 of the docker network: X.X.X.2
  5. Execute `mvn clean package`
  6. Repeat step 2.

- User-defined network bridge can be defined but it would have to be explicitly defined in docker-compose.yml.

### Normal docker start 
#### Packaging Spring Boot App

*_Required BEFORE building image_*

Navigate to the backend directory. Type `mvn clean package` to package the project into a deployable jar file. 
It is automatically saved to the `target/` folder.
### Deploying the Docker containers 

***Requires MongoDb container to be started first***

#### MongoDb

***First time MongoDb container database setup.*** 
* Navigate to the mongo directory.
  1. To build docker image. Type `docker build -t <name-your-image> --rm .`
  2. In terminal Type: `docker run --name <name-your-container> --net <user-defined-bridge> -v $PWD/mongodb:/data/db -d --rm --entrypoint mongod <name-of-your-image>`
  3. Connect to the container: `docker exec -it <name-of-your-container> bash`
  4. `mongorestore -d Customers --restoreDbUsersAndRoles /Customers`
  5. Exit and stop the container. `docker stop <name-of-your-container>`
  6. Normal docker deploy below.

***Normal docker deployment***
  1. Navigate to mongo directory
  2. Open a terminal to this directory
  * To rebuild docker image if changes are made. Type `docker build -t <name-your-image> --rm .`
  3. To deploy docker container from image. Type `docker run --name <name-your-container> --net <user-defined-bridge> -v $PWD/mongodb:/data/db -d --rm <name-of-your-image>`

Example:

`docker build -t mongodb --rm .`

`docker run --name mongodb --net cv_bridge -v $PWD/mongodb:/data/db -d --rm mongodb`

 #### Spring Boot App
   1. Navigate to backend directory
   2. Open terminal in this directory
   3. Type `docker build -t <name-your-image>:<version> --rm .`
   4. Type `docker run --name <name-your-container> --net <user-defined-bridge> -d --rm -p 8081:8081 <name-of-your-image>`
 
 Example:
 
 `docker build -t cv-backend --rm .`
 
 `docker run --name cv-backend --net cv_bridge -d --rm -p 8081:8081 cv-backend`
 
 
 #### React.JS
   1. Navigate to the frontend directory
   2. Open a terminal to this directory
   3. Type `docker build -t <name-your-image> --rm .`
   4. Type `docker run --name <name-your-container> --net <user-defined-bridge> -d --rm -p 3000:3000 <name-of-your-image>`
 
 Example:
 
 `docker build -t cv-react --rm .`
 
 `docker run --name cv-react --net cv_bridge -d --rm -p 3000:3000 cv-react`


