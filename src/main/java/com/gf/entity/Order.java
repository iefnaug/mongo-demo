package com.gf.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author GF
 * @since 2023/4/14
 */
@Data
@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    private String street;
    private String state;
    private String country;
    private String zip;
    private String phone;
    private String name;
    private Long userId;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal shippingFee;

    private List<OrderItem> orderLines;

    private BigDecimal total;

    @Data
    static class OrderItem {
        private String product;
        private String sku;
        private Long qty;
        private BigDecimal price;
        private BigDecimal cost;
    }


}
