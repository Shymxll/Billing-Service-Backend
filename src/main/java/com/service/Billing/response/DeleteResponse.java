package com.service.Billing.response;

import lombok.Data;

@Data
public class DeleteResponse {
    boolean isDeleted;

    public DeleteResponse(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
}

