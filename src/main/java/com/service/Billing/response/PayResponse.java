package com.service.Billing.response;



import java.util.Date;

import lombok.Data;

@Data
public class PayResponse{
    private String trancitionId;
    private int taxNumber;
    private int productId;
    private Date datetime;
    private int status;
    
}
