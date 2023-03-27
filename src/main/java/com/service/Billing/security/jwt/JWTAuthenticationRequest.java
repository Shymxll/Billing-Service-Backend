package com.service.Billing.security.jwt;

import lombok.Data;

/**
 * @author Samson Effes
 */

@Data
public class JWTAuthenticationRequest {
    private String userName;
    private String password;
}
