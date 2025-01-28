package com.flag.pam.resources.accounts;

import com.flag.pam.common.AppResponse;
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

import java.util.Optional;

/**
 * @author rameshalagumalai
 * @Date 27/01/2025
 * */

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @GetMapping
    public AppResponse getAllAccounts() {
        return new AppResponse(accountsRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse createNewAccount(@Valid @RequestBody AccountPayload payload) throws Exception {
        Account account = Account.builder()
                .name(payload.getName())
                .type(payload.getTypeValue())
                .balance(payload.getBalance())
                .build();
        accountsRepository.save(account);

        return new AppResponse("Account created successfully", account);
    }

    @GetMapping("/{accountId:\\d+}")
    public AppResponse getAccountById(@PathVariable long accountId) throws Exception {
        Optional<Account> accountResult = accountsRepository.findById(accountId);
        if (!accountResult.isPresent()) {
            throw new NoResourceFoundException(null, null);
        }
        return new AppResponse(accountResult.get());
    }

    @PutMapping(path = "/{accountId:\\d+}")
    public AppResponse editAccountById(@PathVariable long accountId, @Valid @RequestBody AccountPayload payload) throws Exception {
        Optional<Account> accountResult = accountsRepository.findById(accountId);
        if (!accountResult.isPresent()) {
            throw new NoResourceFoundException(null, null);
        }

        Account account = accountResult.get();
        account.setName(payload.getName());
        account.setType(payload.getTypeValue());
        account.setBalance(payload.getBalance());
        accountsRepository.save(account);

        return new AppResponse("Account edited successfully", account);
    }

    @DeleteMapping("/{accountId:\\d+}")
    public AppResponse deleteAccountById(@PathVariable long accountId) throws Exception {
        Optional<Account> accountResult = accountsRepository.findById(accountId);
        if (!accountResult.isPresent()) {
            throw new NoResourceFoundException(null, null);
        }
        accountsRepository.delete(accountResult.get());

        return new AppResponse("Account deleted successfully");
    }

}
