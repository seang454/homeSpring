package kh.edu.istasd.fswdapi.service;


import kh.edu.istasd.fswdapi.dto.account.AccountResponse;
import kh.edu.istasd.fswdapi.dto.account.CreateNewAccount;
import kh.edu.istasd.fswdapi.dto.account.UpdateAccountRequest;

public interface AccountService {
    AccountResponse createAccount(CreateNewAccount createNewAccount);
    AccountResponse findAccountByAccountNumber(String accountNumber);
    AccountResponse findAccountByCustomerId(Integer customerId);
    void deleteAccountByAccountNumber(String accountNumber);
    AccountResponse updateAccount(String accountNumber,UpdateAccountRequest dto);
    void disableAccountByAccountNumber(String accountNumber, boolean disable);

}
