package com.classican.bankaccountstatement.model.dto.request;

import lombok.Data;

/**
 * JWT Auth Request
 */
@Data
public class AuthUser {
    private String username;
    private String password;
}
