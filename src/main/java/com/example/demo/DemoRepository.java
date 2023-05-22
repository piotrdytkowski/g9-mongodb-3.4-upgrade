package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemoRepository extends MongoRepository<Demo, String> {
    //List<Demo> findByName(String name);
}
