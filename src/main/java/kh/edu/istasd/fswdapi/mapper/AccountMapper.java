package kh.edu.istasd.fswdapi.mapper;

import kh.edu.istasd.fswdapi.domain.Account;
import kh.edu.istasd.fswdapi.domain.AccountType;
import kh.edu.istasd.fswdapi.domain.Customer;
import kh.edu.istasd.fswdapi.dto.account.AccountResponse;
import kh.edu.istasd.fswdapi.dto.account.CreateNewAccount;
import kh.edu.istasd.fswdapi.dto.account.UpdateAccountRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse fromAccount(Account account);

    @Mapping(target = "accountType", expression = "java(toAccountType(dto.accountTypeId()))")
    @Mapping(target = "customer", expression = "java(toCustomer(dto.customerId()))")
    Account toAccount(CreateNewAccount dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "accountType", expression = "java(toAccountType(dto.accountTypeId()))")
    @Mapping(target = "customer", expression = "java(toCustomer(dto.customerId()))")
    void updateAccount(UpdateAccountRequest dto, @MappingTarget Account account);

    // === Mapping helpers ===

    default Integer fromAccountType(AccountType type) {
        return type != null ? type.getId() : null;
    }

    default AccountType toAccountType(Integer id) {
        if (id == null) return null;
        AccountType type = new AccountType();
        type.setId(id);
        return type;
    }

    default Integer fromCustomer(Customer customer) {
        return customer != null ? customer.getId() : null;
    }

    default Customer toCustomer(Integer id) {
        if (id == null) return null;
        Customer c = new Customer();
        c.setId(id);
        return c;
    }
}
