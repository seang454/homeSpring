package kh.edu.istasd.fswdapi.dto.account;

public record UpdateAccountRequest(
        String accountNumber,
        String accountCurrency,
        Integer accountTypeId,
        Integer customerId
) {
}
