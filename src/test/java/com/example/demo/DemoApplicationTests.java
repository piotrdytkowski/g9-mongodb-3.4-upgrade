package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DemoApplicationTests extends MongoIntegrationTest {

	@Autowired
	private DemoRepository demoRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void contextLoads() {
		String id = UUID.randomUUID().toString();
		String name = UUID.randomUUID().toString();
		mongoTemplate.insert(new Demo(id, name));
		Demo demo = mongoTemplate.findById(id, Demo.class);
		assertEquals(name, demo.getName());
	}

}
