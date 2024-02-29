package org.zydd.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class OrderDataRes {
    private Long id;
    private Long customerId;
    private int qty;
    private List<OrderDetailDataRes> orders;
    private String paymentMethod;
    private BigDecimal totalPrice;
    private BigDecimal totalPaid;
    private BigDecimal totalChange;
    private LocalDate orderDate;
}
