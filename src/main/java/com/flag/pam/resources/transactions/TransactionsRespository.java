package com.flag.pam.resources.transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author rameshalagumalai
 * @Date 30/01/2025
 * */

public interface TransactionsRespository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT transactions.id, transactions.name, " +
            "transactions.type, transactions.amount, " +
            "transactions.created_at, " +
            "accounts.id as accountId, accounts.name as accountName, " +
            "categories.id as categoryId, categories.name as categoryName, " +
            "categories.color AS categoryColor, categories.icon AS categoryIcon " +
            "FROM transactions " +
            "INNER JOIN accounts ON transactions.account_id = accounts.id " +
            "INNER JOIN categories ON transactions.category_id = categories.id", nativeQuery = true)
    List<TransactionData> getTransactionDataList();

    @Query(value = "SELECT transactions.id, transactions.name, " +
            "transactions.type, transactions.amount, " +
            "transactions.created_at, " +
            "accounts.id as accountId, accounts.name as accountName, " +
            "categories.id as categoryId, categories.name as categoryName, " +
            "categories.color AS categoryColor, categories.icon AS categoryIcon " +
            "FROM transactions " +
            "INNER JOIN accounts ON transactions.account_id = accounts.id " +
            "INNER JOIN categories ON transactions.category_id = categories.id " +
            "WHERE transactions.id = ?", nativeQuery = true)
    Optional<TransactionData> getTransactionDataById(long transactionId);

}
