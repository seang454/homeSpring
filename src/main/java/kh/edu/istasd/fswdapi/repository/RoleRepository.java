package kh.edu.istasd.fswdapi.repository;

import kh.edu.istasd.fswdapi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
