# Dependency tree from spring-boot-starter-data-mongodb
Run it using `mvn dependency:tree` command.

```
[INFO] \- org.springframework.boot:spring-boot-starter-data-mongodb:jar:1.5.22.RELEASE:compile
[INFO]    +- org.mongodb:mongodb-driver:jar:3.4.3:compile
[INFO]    |  +- org.mongodb:mongodb-driver-core:jar:3.4.3:compile
[INFO]    |  \- org.mongodb:bson:jar:3.4.3:compile
[INFO]    \- org.springframework.data:spring-data-mongodb:jar:1.10.23.RELEASE:compile
[INFO]       +- org.springframework:spring-tx:jar:4.3.25.RELEASE:compile
[INFO]       +- org.springframework:spring-beans:jar:4.3.25.RELEASE:compile
[INFO]       +- org.springframework:spring-expression:jar:4.3.25.RELEASE:compile
[INFO]       \- org.springframework.data:spring-data-commons:jar:1.13.23.RELEASE:compile
```

Shows mongodb-driver:jar:3.4.3 driver is in use.

# Run both docker images:
docker run -d -p 27017:27017 -p 28017:28017 -e MONGODB_PASS="mypass" --name mongo-3.4 dubc/mongodb-3.4
docker run -d -p 27018:27017 -p 28018:28017 -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD="mypass" --name mongo-4.4 mongo:4.4

This lets us run tests against the current version of the mongoDB and the version after platform update (4.1).

With the old driver (3.4.3) tests run with MongoDB 3.4, and with MongoDB 4.1.12.

# Approaches to update the driver and spring data (or one of them at time)

## Spring bom
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-bom</artifactId>
            <version>2020.0.0</version>
            <scope>import</scope>
            <type>pom</type>
        </dependency>
    </dependencies>
</dependencyManagement>
```
We can't simply update the Spring Data MongoDB to 4.x version, because of it's requirements:
The Spring Data MongoDB 4.x binaries require JDK level 17 and above and Spring Framework 6.0.9 and above.
https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/

So updating the release train is not an option:
https://stackoverflow.com/questions/26141346/how-can-i-specify-the-spring-data-mongodb-version-using-spring-boot

Updating the bom in dependency management works as follows:
```
[INFO] \- org.springframework.boot:spring-boot-starter-data-mongodb:jar:1.5.22.RELEASE:compile
[INFO]    +- org.mongodb:mongodb-driver:jar:3.4.3:compile
[INFO]    |  +- org.mongodb:mongodb-driver-core:jar:3.4.3:compile
[INFO]    |  \- org.mongodb:bson:jar:3.4.3:compile
[INFO]    \- org.springframework.data:spring-data-mongodb:jar:4.1.0:compile
[INFO]       +- org.springframework:spring-tx:jar:4.3.25.RELEASE:compile
[INFO]       +- org.springframework:spring-beans:jar:4.3.25.RELEASE:compile
[INFO]       +- org.springframework:spring-expression:jar:4.3.25.RELEASE:compile
[INFO]       \- org.springframework.data:spring-data-commons:jar:3.1.0:compile
```

It compiles despite the requirements, but it doesn't update the mongodb driver anyway.

## Setting the property `<spring-data-bom.version>2023.0.0</spring-data-bom.version>`
makes no change in dependencies.

## Explicit exclusion:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.mongodb</groupId>
            <artifactId>mongodb-driver</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongodb-driver-sync</artifactId>
    <version>4.1.2</version>
</dependency>
```

Just updating the driver doesn't help, because mongo template can't be instantiated by spring.
Updating the driver and spring data, gives more errors.