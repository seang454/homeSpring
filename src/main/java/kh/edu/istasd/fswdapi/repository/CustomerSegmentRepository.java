package kh.edu.istasd.fswdapi.repository;

import kh.edu.istasd.fswdapi.domain.CustomerSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSegmentRepository extends JpaRepository<CustomerSegment,Integer> {
    CustomerSegment findByCustomerSegmentId(Integer customerSegmentId);
}
