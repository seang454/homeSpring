package kh.edu.istasd.fswdapi.controller;

import kh.edu.istasd.fswdapi.dto.CreateCustomerRequest;
import kh.edu.istasd.fswdapi.dto.CustomerResponse;
import kh.edu.istasd.fswdapi.dto.UpdateCustomerRequest;
import kh.edu.istasd.fswdapi.dto.account.AccountResponse;
import kh.edu.istasd.fswdapi.dto.account.CreateNewAccount;
import kh.edu.istasd.fswdapi.dto.account.UpdateAccountRequest;
import kh.edu.istasd.fswdapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    AccountResponse createAccount(@RequestBody CreateNewAccount createNewAccount) {
        log.info("Create new account {}", createNewAccount);
        return accountService.createAccount(createNewAccount);
    }

    @GetMapping("/{accountNumber}")
    AccountResponse findAccountByAccountNumber(@PathVariable String accountNumber) {
        log.info("Find account by account number {}", accountNumber);
        return accountService.findAccountByAccountNumber(accountNumber);
    }

    AccountResponse findAccountByCustomerId(Integer customerId) {
        return null;
    }

    void deleteAccountByAccountNumber(Integer accountNumber) {

    }

    AccountResponse updateAccount(UpdateAccountRequest dto) {
        return null;
    }

    void disableAccountByAccountNumber(Integer accountNumber, boolean disable) {

    }
}
