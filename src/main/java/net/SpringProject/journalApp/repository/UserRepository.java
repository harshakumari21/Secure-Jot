package net.SpringProject.journalApp.repository;

import net.SpringProject.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String userName); // query method dsl: springboot khud bna leta query using jpa and hibernate

    void deleteByUserName(String username);
}
