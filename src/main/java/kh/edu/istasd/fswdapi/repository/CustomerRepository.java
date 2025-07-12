package kh.edu.istasd.fswdapi.repository;

import kh.edu.istasd.fswdapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    boolean existsByEmail(String email); //name Email must same as domain model
    boolean existsByPhoneNumber(String phone);
    Optional<Customer> findByPhoneNumber(String phoneNumber);


}
