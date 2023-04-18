package com.gf.test;

import com.gf.entity.Bar;
import com.mongodb.client.result.UpdateResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author GF
 * @since 2023/4/17
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardTest {

    @Resource
    private MongoOperations mongoOperations;

    @Test
    public void testFind() {
        MatchOperation matchOperation = Aggregation.match(Criteria.where("_id").is("64123aff95ac3a0071444ca9"));
//        MatchOperation matchOperation = Aggregation.match(Criteria.where("i").is(0));
        Aggregation aggregation = Aggregation.newAggregation(matchOperation);
        AggregationResults<Map> result = mongoOperations.aggregate(aggregation, Bar.class, Map.class);
        System.out.println();
    }


    @Test
    @Transactional
    public void testTx() {
        Criteria criteria = Criteria.where("_id").is("64123aff95ac3a0071444ca9");
        UpdateResult result = mongoOperations.updateFirst(Query.query(criteria), Update.update("i", 11), Bar.class);
        System.out.println();
    }

}
