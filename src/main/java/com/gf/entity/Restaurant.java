package com.gf.entity;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author GF
 * @since 2023/4/20
 */
@Data
@Document(collection = "restaurants")
public class Restaurant {

    private String id;
    private GeoJsonPoint location;
    private String name;

    @BsonIgnore
    private Double distance;
}
