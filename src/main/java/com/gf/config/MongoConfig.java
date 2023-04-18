package com.gf.config;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.concurrent.TimeUnit;


/**
 * @author GF
 * @since 2023/4/16
 */
@Slf4j
@Configuration
public class MongoConfig {

    @Bean
    MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MappingMongoConverter mappingMongoConverter) {

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, mappingMongoConverter);
        mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        mongoTemplate.setWriteConcernResolver(action -> {
            //根据不同类型使用不同的WriteConcern
//            if (action.getEntityType() == Person.class) {
//                return WriteConcern.MAJORITY.withJournal(true);
//            } else {
//                return action.getDefaultWriteConcern();
//            }
//            return new WriteConcern(4).withJournal(true).withWTimeout(5, TimeUnit.SECONDS);
            return WriteConcern.MAJORITY.withJournal(true).withWTimeout(5, TimeUnit.SECONDS);
        });
        return mongoTemplate;
    }

    /**
     * 解决插入 _class
     */
    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDbFactory mongoDbFactory,
            MongoMappingContext context,
            CustomConversions conversions
    ) {
        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        // remove _class field
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        mappingConverter.setCustomConversions(conversions);
        return mappingConverter;
    }


    /**
     * 事务配置
     */
    @Bean
    public MongoTransactionManager mongoTransactionManager(MongoDbFactory mongoDbFactory) {
        MongoTransactionManager mongoTransactionManager = new MongoTransactionManager(mongoDbFactory);
        TransactionOptions transactionOptions = TransactionOptions.builder()
                .readConcern(ReadConcern.MAJORITY)
                .writeConcern(WriteConcern.MAJORITY.withWTimeout(10, TimeUnit.SECONDS))
//                .writeConcern(new WriteConcern(3).withWTimeout(10, TimeUnit.SECONDS))
                .readPreference(ReadPreference.secondaryPreferred())
                .build();
        mongoTransactionManager.setOptions(transactionOptions);
        return mongoTransactionManager;
    }


}
