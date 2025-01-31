package com.flag.pam.resources.transactions;

import com.flag.pam.common.AppResponse;
import com.flag.pam.resources.accounts.Account;
import com.flag.pam.resources.accounts.AccountsRepository;
import com.flag.pam.resources.categories.CategoriesRespository;
import com.flag.pam.resources.categories.Category;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;
import java.util.Optional;

/**
 * @author rameshalagumalai
 * @Date 30/01/2025
 * */

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    @Autowired
    private TransactionsRespository transactionsRespository;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CategoriesRespository categoriesRespository;

    @GetMapping
    public AppResponse getAllTransactions() {
        return new AppResponse(transactionsRespository.getTransactionDataList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse createNewTransaction(@Valid @RequestBody TransactionPayload payload) throws Exception {

        Optional<Account> accountResult = accountsRepository.findById(payload.getAccountId());
        if (!accountResult.isPresent()) {
            throw new EntityNotFoundException("Given account doesn't exist");
        }

        Optional<Category> categoryResult = categoriesRespository.findById(payload.getCategoryId());
        if (!accountResult.isPresent()) {
            throw new EntityNotFoundException("Given category doesn't exist");
        }

        Transaction transaction = Transaction.builder()
                .name(payload.getName())
                .type(payload.getTypeValue())
                .amount(payload.getAmount())
                .createdAt(payload.getCreatedAtValue())
                .account(accountResult.get())
                .category(categoryResult.get())
                .build();
        transactionsRespository.save(transaction);

        Optional<TransactionData> transactionDataResult = transactionsRespository.getTransactionDataById(transaction.getId());
        return new AppResponse("Transaction created successfully", transactionDataResult);
    }

    @GetMapping("/{transactionId:\\d+}")
    public AppResponse getTransactionById(@PathVariable long transactionId) throws Exception {
        Optional<TransactionData> transactionDataResult = transactionsRespository.getTransactionDataById(transactionId);
        if (!transactionDataResult.isPresent()) {
            throw new EntityNotFoundException("Transaction doesn't exist");
        }
        return new AppResponse(transactionDataResult.get());
    }

    @PutMapping("/{transactionId:\\d+}")
    public AppResponse editTransactionById(@PathVariable long transactionId, @Valid @RequestBody TransactionPayload payload) throws Exception {
        Optional<Transaction> transactionResult = transactionsRespository.findById(transactionId);
        if (!transactionResult.isPresent()) {
            throw new EntityNotFoundException("Transaction doesn't exist");
        }

        Optional<Account> accountResult = accountsRepository.findById(payload.getAccountId());
        if (!accountResult.isPresent()) {
            throw new EntityNotFoundException("Given account doesn't exist");
        }

        Optional<Category> categoryResult = categoriesRespository.findById(payload.getCategoryId());
        if (!accountResult.isPresent()) {
            throw new EntityNotFoundException("Given category doesn't exist");
        }

        Transaction transaction = transactionResult.get();
        transaction.setName(payload.getName());
        transaction.setType(payload.getTypeValue());
        transaction.setAmount(payload.getAmount());
        transaction.setCreatedAt(payload.getCreatedAtValue());
        transaction.setAccount(accountResult.get());
        transaction.setCategory(categoryResult.get());
        transactionsRespository.save(transaction);

        Optional<TransactionData> transactionDataResult = transactionsRespository.getTransactionDataById(transaction.getId());
        return new AppResponse("Transaction edited successfully", transactionDataResult.get());
    }

    @DeleteMapping("/{transactionId:\\d+}")
    public AppResponse deleteTransactionById(@PathVariable long transactionId) throws Exception {
        Optional<Transaction> transactionResult = transactionsRespository.findById(transactionId);
        if (!transactionResult.isPresent()) {
            throw new EntityNotFoundException("Transaction doesn't exist");
        }
        transactionsRespository.delete(transactionResult.get());

        return new AppResponse("Transaction deleted successfully");
    }

}
