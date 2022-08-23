package com.classican.bankaccountstatement.repository;

import com.classican.bankaccountstatement.model.ApplicationUser;

/**
 * User Data Access layer
 */
public interface UserDao {

    ApplicationUser getApplicationUserByUsername(String username);
}
