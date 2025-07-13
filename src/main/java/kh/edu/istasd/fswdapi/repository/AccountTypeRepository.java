package kh.edu.istasd.fswdapi.repository;

import kh.edu.istasd.fswdapi.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
}
