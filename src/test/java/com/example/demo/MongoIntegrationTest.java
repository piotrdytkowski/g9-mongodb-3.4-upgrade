package com.example.demo;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@Profile("test")
@ActiveProfiles("test")
@ComponentScan(basePackages = {"com.example.demo"})
@DataMongoTest
@PropertySource("classpath:mongo.properties")
//@TestPropertySource(properties = "spring.mongodb.embedded.version=4.4.6")
// DirtiesContext is needed since we are using a custom replica set deployment of Mongo to allow for testing transactions
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class MongoIntegrationTest {

}