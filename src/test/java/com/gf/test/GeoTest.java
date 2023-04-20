package com.gf.test;

import com.gf.entity.Neighborhood;
import com.gf.entity.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GeoNearOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author GF
 * @since 2023/4/20
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class GeoTest {

    @Resource
    private MongoOperations mongoOperations;

    @Test
    public void testFind() {
        Restaurant restaurant = mongoOperations.findOne(Query.query(new Criteria()), Restaurant.class);
        log.info("restaurant: {}", restaurant);

        Neighborhood neighborhood = mongoOperations.findOne(Query.query(new Criteria()), Neighborhood.class);
        log.info("neighborhood: {}", neighborhood);
    }

    @Test
    public void testQuery() {
        Circle circle = new Circle(-73.93414657, 40.82302903, 0.5 / Metrics.KILOMETERS.getMultiplier());
        //返回随机的点
        List<Restaurant> list = mongoOperations.find(Query.query(Criteria.where("location").withinSphere(circle)), Restaurant.class);
        log.info("list: {}", list);
    }


    @Test
    public void testQuery2() {
        GeoJsonPoint point = new GeoJsonPoint(-73.93414657, 40.82302903);
        //返回距离从近到远的点
        Criteria criteria = Criteria.where("location").nearSphere(point).minDistance(1000000).maxDistance(3000000);
        List<Restaurant> list = mongoOperations.find(Query.query(criteria), Restaurant.class);
        System.out.println();
    }

    @Test
    public void testQuery3() {
        GeoJsonPoint point = new GeoJsonPoint(-73.93414657, 40.82302903);
        NearQuery nearQuery = NearQuery.near(point).minDistance(new Distance(0.3, Metrics.KILOMETERS)).maxDistance(new Distance(0.5, Metrics.KILOMETERS));
        GeoResults<Restaurant> results = mongoOperations.query(Restaurant.class).near(nearQuery).all();
        System.out.println();
    }

    @Test
    public void testInsert() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Beijing");
        restaurant.setLocation(new GeoJsonPoint(116.23, 39.54)); //北京
        mongoOperations.insert(restaurant);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("Chengdu");
        restaurant2.setLocation(new GeoJsonPoint(104.04, 30.4)); //成都
        mongoOperations.insert(restaurant2);
    }


    @Test
    public void testQuery4() {
        GeoJsonPoint point = new GeoJsonPoint(116.23, 39.54);
        NearQuery nearQuery = NearQuery.near(point).minDistance(new Distance(1000, Metrics.KILOMETERS)).maxDistance(new Distance(3000, Metrics.KILOMETERS));
        GeoResults<Restaurant> results = mongoOperations.query(Restaurant.class).near(nearQuery).all();
        System.out.println();
    }


}
