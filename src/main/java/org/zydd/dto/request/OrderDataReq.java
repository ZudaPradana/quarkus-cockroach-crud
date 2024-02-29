package org.zydd.dto.request;

import lombok.Data;
import org.zydd.dto.response.OrderDetailDataRes;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDataReq {
    private Long customerId;
    private List<OrderDetailDataRes> orderDetail;
    private String paymentMethod;
    private BigDecimal totalPaid;
}
