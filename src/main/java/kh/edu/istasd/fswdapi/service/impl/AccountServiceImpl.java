package kh.edu.istasd.fswdapi.service.impl;

import kh.edu.istasd.fswdapi.domain.Account;
import kh.edu.istasd.fswdapi.dto.account.AccountResponse;
import kh.edu.istasd.fswdapi.dto.account.CreateNewAccount;
import kh.edu.istasd.fswdapi.dto.account.UpdateAccountRequest;
import kh.edu.istasd.fswdapi.mapper.AccountMapper;
import kh.edu.istasd.fswdapi.repository.AccountRepository;
import kh.edu.istasd.fswdapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountResponse createAccount(CreateNewAccount createNewAccount) {
        Account account = accountMapper.toAccount(createNewAccount);
        account.setIsDeleted(false);
        accountRepository.save(account);
        return accountMapper.fromAccount(account);
    }

    @Override
    public AccountResponse findAccountByAccountNumber(String accountNumber) {
        log.info("Find account by account number {}", accountNumber);
        Account account = accountRepository.findAccountsByAccountNumberIgnoreCase(accountNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
        log.info("Found account by account number {}", account);
        return accountMapper.fromAccount(account);
    }

    @Override
    public AccountResponse findAccountByCustomerId(Integer customerId) {
        Account account = accountRepository.findAccountsByCustomer_Id(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
        return accountMapper.fromAccount(account);
    }

    @Override
    public void deleteAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findAccountsByAccountNumberIgnoreCase(accountNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
        accountRepository.delete(account);
    }

    @Override
    public AccountResponse updateAccount(String accountNumber,UpdateAccountRequest dto) {
        Account account = accountRepository.findAccountsByAccountNumberIgnoreCase(accountNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
        accountMapper.updateAccount(dto, account);
        Account newAccount = accountRepository.save(account);
        return accountMapper.fromAccount(newAccount);
    }

    @Override
    public void disableAccountByAccountNumber(String accountNumber, boolean disable) {
        Account account = accountRepository.findAccountsByAccountNumberIgnoreCase(accountNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
        account.setIsDeleted(disable);
    }
}
