package com.gf.test;

import com.gf.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;

import static org.springframework.data.mongodb.core.query.Criteria.*;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author GF
 * @since 2023/4/14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderTest {

    @Resource
    private MongoTemplate mongoTemplate;

    private MongoOperations mongoOperations;

    @Test
    public void testFind() {
//        Query query = Query.query(where("userId").lt(2500)).skip(10).limit(1);
        Query query = Query.query(where("_id").is("1002"));
        List<Order> orders = mongoTemplate.find(query, Order.class);
        for (Order order : orders) {
            log.info("order: {}", order);
        }

//        Order order = new Order();
//        order.setUserId(11001100L);
//        order.setOrderDate(LocalDateTime.of(2023, 4, 15, 2, 0, 0));
//        mongoTemplate.insert(order);

    }

    @Test
    public void testTimeQuery() {
        Criteria matchCriteria =
                        where("orderDate")
                        .gt(LocalDateTime.of(2023, 4, 15, 15, 0))
                ;

        Query query = Query.query(matchCriteria).limit(2);
        List<Order> orders = mongoTemplate.find(query, Order.class);
        System.out.println();
    }

    @Test
    public void testAggregation() {
        //
        Criteria matchCriteria = where("status").is("completed")
//                .and("orderDate").gte(LocalDateTime.of(2019, 1, 1, 0, 0, 0)).lt(LocalDateTime.of(2019, 4, 1, 0, 0,0))
                ;


        MatchOperation matchOperation = Aggregation.match(matchCriteria);
        //
        GroupOperation groupOperation = Aggregation.group()
                .sum("total").as("total")
                .sum("shippingFee").as("shippingFee")
                .count().as("count");

        ProjectionOperation projectionOperation = Aggregation.project()
                .and(context -> {
                    Document document = new Document();
                    document.put("$add", Arrays.asList("$total", "$shippingFee"));
                    return document;
                }).as("grantTotal")
                .andInclude("count")
                .andExclude("_id")
                ;


        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation, projectionOperation);
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, Order.class, Map.class);
        System.out.println();
    }

}
