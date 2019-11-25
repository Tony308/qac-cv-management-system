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


Back-end
======
### MongoDb

#### Windows OS
____
Assuming you have everything setup for MongoDb

To start MongoDb, you'll need to:
1. Navigate to your MongoDb install bin folder: "C:\Program Files\MongoDb\Server\3.0\bin" (Default).
2. Start "mongod.exe"
3. Start "mongo.exe"

More detail @ [MongoDb Website](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/#start-mdb-edition-from-the-command-interpreter)

#### Linux 
___
_With environment variables setup_

To start MongoDb:
1. Open a bash terminal
2. Type "`mongod`" or "`mongod --auth`" once you've setup login and want security
- To access the database. Open another terminal and type "mongo"


##### Spring Boot 
____
Assuming you have Maven installed and setup.

Note: ***REQUIRES MONGODB TO BE STARTED FIRST AND SUCCESSFULLY RUNNING***

To start the Spring Boot project simply:
1. Navigate to the backend directory
2. Open a BASH/command line terminal for this directory
3. Type and run "`mvn spring-boot:run`"

Dev-ops
========

## Docker

RECENT UPDATE:
Can now deploy the application using Docker.

### Packaging Spring Boot App
*_Required before deploying as container_*

`mvn clean package` to package the project into a deployable jar file.

### Deploying the containers 

***Requires MongoDb container to be started first***

### MongoDb
1. Navigate to mongo directory
2. Open a terminal for this directory
3. Type "`docker build -t <name-your-image> --rm .`"
4. Type "`docker run --name <name-your-container> --net <user-defined-bridge> -v $PWD/mongodb:/data/db -d --rm <name-of-your-image>`

Example:

`docker build -t mongodb --rm .`

`docker run --name mongodb --net cv_bridge -v $PWD/mongodb:/data/db -d --rm mongodb`

 ### Spring Boot App
 1. Navigate to backend directory
 2. Open terminal in this directory
 3. Type "`docker build -t <name-your-image> --rm .`"
 4. Type "`docker run --name <name-your-container> --net <user-defined-bridge> -d --rm -p 8081:8081 <name-of-your-image>`"
 
 Example:
 
 `docker build -t cv-backend --rm .`
 
 `docker run --name cv-backend --net cv_bridge -d --rm -p 8081:8081 cv-frontend`
 
 ### React.JS
 1. Navigate to the frontend directory
 2. Open a terminal to this directory
 3. Type "`docker build -t <name-your-image> --rm .`"
 4. Type "`docker run --name <name-your-container> --net <user-defined-bridge> -d --rm -p 3000:3000 <name-of-your-image>`"
 
 Example:
 
 `docker build -t cv-frontend --rm .`
 
 `docker run --name cv-backend --net cv_bridge -d --rm -p 3000:3000 cv-frontend`
