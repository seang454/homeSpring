package kh.edu.istasd.fswdapi.service;


import kh.edu.istasd.fswdapi.dto.account.AccountResponse;
import kh.edu.istasd.fswdapi.dto.account.CreateNewAccount;
import kh.edu.istasd.fswdapi.dto.account.UpdateAccountRequest;

import java.util.List;

public interface AccountService {
    void disableByPhoneNumber(String phoneNumber);
    AccountResponse createAccount(CreateNewAccount createNewAccount);
    AccountResponse findAccountByAccountNumber(String accountNumber);
    List<AccountResponse> findAccountByCustomerId(Integer customerId);
    void deleteAccountByAccountNumber(String accountNumber);
    AccountResponse updateAccount(String accountNumber,UpdateAccountRequest dto);
    AccountResponse disableAccountByAccountNumber(String accountNumber, boolean disable);

}
