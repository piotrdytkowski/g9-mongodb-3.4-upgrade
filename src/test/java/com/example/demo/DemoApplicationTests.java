package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DemoApplicationTests extends MongoIntegrationTest {

	@Autowired
	private DemoRepository demoRepository;

	@Test
	public void contextLoads() {
		String id = UUID.randomUUID().toString();
		String name = UUID.randomUUID().toString();
		demoRepository.insert(new Demo(id, name));
		Demo demo = demoRepository.findOne(id);
		assertEquals(name, demo.getName());
	}

}
