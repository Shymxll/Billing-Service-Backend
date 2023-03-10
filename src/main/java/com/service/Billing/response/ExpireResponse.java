package com.service.Billing.response;

import java.util.Date;

import lombok.Data;

@Data
public class ExpireResponse {
    private boolean isExpire;
    private Date expireDate;
    public ExpireResponse(boolean isExpire, Date expireDate) {
        this.isExpire = isExpire;
        this.expireDate = expireDate;
    }
    
}
