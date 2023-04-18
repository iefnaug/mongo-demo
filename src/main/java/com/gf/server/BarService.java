package com.gf.server;

import com.gf.entity.Bar;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author GF
 * @since 2023/4/18
 */
@Slf4j
@Service
public class BarService {

    @Resource
    private MongoOperations mongoOperations;


    @Transactional
    public void writeConcernInTx() {
        Criteria criteria = Criteria.where("_id").is("64123aff95ac3a0071444ca9");
        UpdateResult result = mongoOperations.updateFirst(Query.query(criteria), Update.update("i", 11), Bar.class);
        log.info("result: {}", result);
    }

}
