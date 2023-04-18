package com.gf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author GF
 * @since 2023/4/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bar")
public class Bar {

    @Id
    private String id;

    private Long i;

}
