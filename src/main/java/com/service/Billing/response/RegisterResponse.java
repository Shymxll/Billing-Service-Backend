package com.service.Billing.response;

import lombok.Data;

@Data
public class RegisterResponse {
    boolean isCreate;

    public RegisterResponse(boolean isCreate) {
        this.isCreate = isCreate;
    }
    
}
