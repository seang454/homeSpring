package kh.edu.istasd.fswdapi.dto.account;

public record  CreateNewAccount(
        Integer segment_id,
        Integer customerId,
        Integer accountTypeId,
        String accountNumber,
        String accountCurrency,
        Double balance
) {
}
