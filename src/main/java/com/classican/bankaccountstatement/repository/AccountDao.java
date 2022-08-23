package com.classican.bankaccountstatement.repository;

import com.classican.bankaccountstatement.model.Account;

/**
 *
 * Account DAO Layer
 */
public interface AccountDao {

    Account getAccount(long accountId);
}
