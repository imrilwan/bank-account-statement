package com.classican.bankaccountstatement.repository;

import com.classican.bankaccountstatement.model.Statement;

import java.util.List;

/**
 * Statement Data Access layer
 */
public interface StatementDao {

    List<Statement> getStatement(long accountId);
}
