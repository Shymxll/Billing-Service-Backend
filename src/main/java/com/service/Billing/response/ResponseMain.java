package com.service.Billing.response;

import java.util.List;

import lombok.Data;

@Data
public class ResponseMain{
    public int code;
    public String message;
    List<Object> response;
    
    public ResponseMain(int code, String message, List<Object> response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    public ResponseMain() {
    }
    
    
}

