package com.classican.bankaccountstatement.model;

import lombok.Builder;
import lombok.Data;

/**
 * App User model
 */
@Data
@Builder
public class ApplicationUser {

    private long id;
    private String username;
    private String password;
    private String role;

}
