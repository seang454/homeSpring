package kh.edu.istasd.fswdapi.repository;

import kh.edu.istasd.fswdapi.domain.CustomerSegment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerSegmentRepository extends JpaRepository<CustomerSegment,Integer> {
    CustomerSegment findByCustomerSegmentId(Integer customerSegmentId);
}
