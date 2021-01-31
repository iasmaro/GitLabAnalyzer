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