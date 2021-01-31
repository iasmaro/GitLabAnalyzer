# Project structure

```
+- database
+- src
    +- main
        +- java/com/haumea
            +- gitanalyzer
                +- GitanalyzerApplication.java
                |
                +- student
                |   +- Student.java
                |   +- StudentController.java
                |   +- StudentService.java
                |   +- StudentRepository.java
                |   +- StudentDAL.java
                |   +- StudentDALImpl.java
            +- resources
            |   +- application.properties
    +- test/java/com/haumea/gitanalyzer
+- gradle
+- build.gradle
+- gradlew
+- gradlew.bat
+- settings.gradle
+- .gitignore
```

We will the [Spring Boot recommended project structure](https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-locating-the-main-class)

- `GitanalyzerApplication.java`: top level class where the `main` function resides.
- For each entity/model, we create a subpackage under the top level package `com.haumea.gitanalyzer`, for example, for the student entity, put all related class/interface in the `com.haumea.gitanalyzer.student` package
    - `Entity.java`: define how Entity is stored as a collection in a mongodb database
    - `EntityController.java`: expose REST API requests to client and decide what service(s) to call the handle the 
      each request (i.
      e., calling relevant 
      methods from
      `EntityService.java`)
    - `EntityService.java`: all business logics concerning Entity go here, including interactions with the database 
      and making 
      API 
      call to GitLab server, and any computations needed to handle an API request
    - `EntityRepository.java`: an interface extending the `MongoRepository` interface 
    - `EntityDAL.java`: DAL (Data Access Layer) interface for Entity
    - `EntityDALImpl.java`: implement the associated interface, making use of `MongoTemplate` internally
    - FYI: `MongoRepository` is much simpler to use than `MongoTemplate`, but template gives you more fine grained 
      control over the queries that you're implementing. They are multually excludsive, we can always use both, or 
      just pick one other the other, depending on the complexity of the database query you want to implement. For 
      more details, check [this discussion out](https://stackoverflow.
      com/questions/17008947/whats-the-difference-between-spring-datas-mongotemplate-and-mongorepository?).
- `application.properties`: Spring Boot app configs, including how to connect to the database
- `gradle/`, `build.gradle`, `gradlew`, `gradlew.bat`, `settings.gradle`: build files
- `.gitignore`: track only necessary files/folder for other to build & run this project

# Build instructions 

## Prerequisites

- IDE: **Intellij** or your preferred IDE
- Database GUI: **MongoDB Compass Community**
- Testing REST API: **Postman**
- Database: **MongoDB 4.2** 
- DevOps: **docker** & **docker-compose** (if you want to run a mongo DB server in side a container)

Please google how to install these tools on your OS. 

## 1. Prepare the database server

- Remote server: Already set-up and is always running, no further action required, if you decide to go with this 
  option, just go to step 2 now.  
  
- Local MongoDB: [install version 4.2](https://docs.mongodb.com/v4.2/administration/install-on-linux/) and start the 
  server 

- MongoDB container: 
  
    ```
    cd database 
    # pull image & build container 
    docker-compose up --build -d
    # check if the container is running
    docker ps 
    # stop the container
    docker-compose stop 
    # destroy the container (the named volumn is not deleted though)
    docker-compose down
    ```
  
    If you see this, it mean your container is running:
  
    <img src="images/sc2.png" width="90%">

## 2. Check that you connect to the database

- Open MongoDB Compass Community:
    - Enter connection string and click `CONNECT`:
      
        <img src="images/sc1.png" width="90%">
      
        - Remote server connection string: `mongodb+srv://haumea:cmpt3732021@cmpt373-project.unzqm.mongodb.net/test?
      authSource=admin&replicaSet=atlas-tadx14-shard-0&readPreference=primary&appname=MongoDB%20Compass%20Community&ssl=true`
        - Local server: `mongodb://localhost:27017/`
        - Local container: `mongodb://localhost:27017/`
 
## 3. Build the project

- Open `application.properties` and decide how to connect to the database server:
    - Remote server: uncomment the line start with `spring.data.mongodb.uri` and comment out all other lines.
    - Local or docker container, no change needed. Note, the config is for default settings of mongodb (port 27017 on 
  local machine, 
  authentication disabled)

- Run `GitanalyzerApplication.java` to start up the server. You should see something similar to this:

    <img src="images/sc3.png" width="90%">

## 4. Test out the APIs

- Open Postman

    - Add some students:

    <img src="images/sc4.png" width="90%">
    
    - Monitor your database through MongoDB Compass (see [section 2](#2) for how to connect to a database server):
    
    <img src="images/sc5.png" width="90%">

    - Retrieve students:

    <img src="images/sc5.png" width="90%">
    


