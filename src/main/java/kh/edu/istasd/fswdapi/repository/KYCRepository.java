package kh.edu.istasd.fswdapi.repository;

import kh.edu.istasd.fswdapi.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KYCRepository extends JpaRepository<KYC,Integer> {
    boolean existsByNationalCardId(String nationalCardId);
}
