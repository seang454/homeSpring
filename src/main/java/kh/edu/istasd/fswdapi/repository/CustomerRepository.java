package kh.edu.istasd.fswdapi.repository;

import kh.edu.istasd.fswdapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    List<Customer> findAllByIsDeletedFalse();

    @Modifying
    @Query("""
            UPDATE Customer c SET c.isDeleted = TRUE WHERE c.phoneNumber=:phoneNumber
            """)
    void disableByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email); //name Email must same as domain model

    //Customer must same as domain and ?1 is follow the order of parameters
    @Query(value = """
            SELECT EXISTS(SELECT c 
                  FROM Customer c WHERE
                                 c.phoneNumber = ?1)
            """,nativeQuery = false)
    boolean existsByPhoneNumber(String phone);
    boolean existsByNationalCardId(String nationalCardId);
    boolean existsById(Integer id);

    Optional<Customer> findByPhoneNumberAndIsDeletedFalse(String phoneNumber);


}
