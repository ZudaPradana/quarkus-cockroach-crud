package org.zydd.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodDataReq {
    private String foodName;
    private String category;
    private BigDecimal price;
}
