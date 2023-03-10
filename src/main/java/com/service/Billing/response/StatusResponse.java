package com.service.Billing.response;

import java.util.Date;

import lombok.Data;

@Data
public class StatusResponse {
    Date datetime;
    String transactionId;
    int amount;
}
