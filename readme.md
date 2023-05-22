1. Dependency tree from spring-boot-starter-data-mongodb
Run it using `mvn dependency:tree` command.

org.springframework.boot:spring-boot-starter-data-mongodb:jar:1.5.22.RELEASE:compile
[INFO]    +- org.mongodb:mongodb-driver:jar:3.4.3:compile
[INFO]    |  +- org.mongodb:mongodb-driver-core:jar:3.4.3:compile
[INFO]    |  \- org.mongodb:bson:jar:3.4.3:compile
[INFO]    \- org.springframework.data:spring-data-mongodb:jar:1.10.23.RELEASE:compile
[INFO]       +- org.springframework:spring-tx:jar:4.3.25.RELEASE:compile
[INFO]       +- org.springframework:spring-beans:jar:4.3.25.RELEASE:compile
[INFO]       +- org.springframework:spring-expression:jar:4.3.25.RELEASE:compile
[INFO]       \- org.springframework.data:spring-data-commons:jar:1.13.23.RELEASE:compile

Shows mongodb-driver:jar:3.4.3 driver is in use.

2. Run both docker images:
sudo docker run -d -p 27017:27017 -p 28017:28017 -e MONGODB_PASS="mypass" --name mongo-3.4 dubc/mongodb-3.4
sudo docker run -d -p 27018:27017 -p 28018:28017 -e MONGODB_PASS="mypass" --name mongo-4.1.12 excellalabs/mongo:4.1.12

This lets us run tests against the current version of the mongoDB and the version after platform update (4.1).

With the old driver (3.4.3) tests run against MongoDB 3.4, but fail with MongoDB 4.1.12 with errors:
```
Exception in monitor thread while connecting to server localhost:27018
org.springframework.dao.DataAccessResourceFailureException: Timed out after 30000 ms while waiting for a server that matches WritableServerSelector.
Client view of cluster state is {type=UNKNOWN, servers=[{address=localhost:27018, type=UNKNOWN, state=CONNECTING,
exception={com.mongodb.MongoSocketReadException: Exception receiving message}, caused by {java.net.SocketException: Connection reset}
```

3. Update mongo driver:

