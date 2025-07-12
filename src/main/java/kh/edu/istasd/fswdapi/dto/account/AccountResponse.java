package kh.edu.istasd.fswdapi.dto.account;

import kh.edu.istasd.fswdapi.domain.Customer;

public record AccountResponse(
        Integer id,
        Customer customer,
        Double balance,
        Integer accountType,
        String accountNumber,
        String accountCurrency
) {
}
