package org.zydd.dto.request;

import lombok.Data;

@Data
public class CustomerDataReq {
    private String customerName;
    private String customerEmail;
    private String customerPhone;
}
