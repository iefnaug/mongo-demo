package com.gf.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author GF
 * @since 2023/4/14
 */
@Data
@Document(collection = "person")
public class Person {

    private String id;
    private String name;
    private int age;


}
