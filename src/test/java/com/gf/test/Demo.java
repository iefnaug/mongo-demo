package com.gf.test;

import com.alibaba.fastjson.JSON;
import com.gf.entity.Person;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ExecutableRemoveOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author GF
 * @since 2023/4/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Demo {

    @Resource
    private MongoTemplate mongoTemplate;

    @Test
//    @Transactional
    public void testInsertOne() {
        Person person = new Person();
        person.setId("10118");
        person.setName("afei");
        person.setAge(12);
        mongoTemplate.save(person, "person");
    }

    @Test
    public void testInsertMany() {
        testRemove();
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Person person = new Person();
            person.setId("100" + i);
            person.setName("afei" + i);
            person.setAge(20 + i);
            list.add(person);
        }
        mongoTemplate.insert(list, Person.class);
    }

    @Test
    public void testUpdate() {
        UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("name").is("afei2")), Update.update("age", 19), Person.class);
        log.info("result: {}", updateResult);

    }

    @Test
    public void testRemove() {
        ExecutableRemoveOperation.ExecutableRemove<Person> remove = mongoTemplate.remove(Person.class);
        remove.findAndRemove();
    }

    @Test
    public void testFind() {
        Person person = mongoTemplate.findById("10117", Person.class);
        log.info("person: {}", person);


//        List<Person> list = mongoTemplate.find(Query.query(Criteria.where("name").is("afei1")), Person.class);
//        log.info("person list: {}", list);
    }



}
