package com.gf.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author GF
 * @since 2023/4/20
 */
@Data
@Document(collection = "neighborhoods")
public class Neighborhood {

    @Id
    private String id;

    private GeoJsonPolygon geometry;

    private String name;
}
