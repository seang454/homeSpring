package kh.edu.istasd.fswdapi.controller;

import kh.edu.istasd.fswdapi.dto.CreateCustomerRequest;
import kh.edu.istasd.fswdapi.dto.CustomerResponse;
import kh.edu.istasd.fswdapi.dto.UpdateCustomerRequest;
import kh.edu.istasd.fswdapi.dto.account.AccountResponse;
import kh.edu.istasd.fswdapi.dto.account.CreateNewAccount;
import kh.edu.istasd.fswdapi.dto.account.DisableAccount;
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
    public AccountResponse createAccount(@RequestBody CreateNewAccount createNewAccount) {
        log.info("Create new account {}", createNewAccount);
        return accountService.createAccount(createNewAccount);
    }

    @GetMapping("/number/{accountNumber}")
    public AccountResponse findAccountByAccountNumber(@PathVariable String accountNumber) {
        log.info("Find account by account number {}", accountNumber);
        return accountService.findAccountByAccountNumber(accountNumber);
    }


    @GetMapping("/customerId/{customerId}")
    public List<AccountResponse> findAccountByCustomerId(@PathVariable Integer customerId) {
        return accountService.findAccountByCustomerId(customerId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{accountNumber}")
    public void deleteAccountByAccountNumber(@PathVariable String accountNumber) {
        accountService.deleteAccountByAccountNumber(accountNumber);

    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{accountNumber}")
    public AccountResponse updateAccount(@PathVariable String accountNumber, @RequestBody UpdateAccountRequest dto) {
        return accountService.updateAccount(accountNumber,dto);
    }

    @PatchMapping("/disable/{accountNumber}")
    public AccountResponse disableAccountByAccountNumber(@PathVariable String accountNumber, @RequestBody DisableAccount disableAccount) {
       return accountService.disableAccountByAccountNumber(accountNumber,disableAccount.is_deleted());
    }
}
