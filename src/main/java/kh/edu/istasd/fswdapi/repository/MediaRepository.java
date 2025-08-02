package kh.edu.istasd.fswdapi.repository;

import kh.edu.istasd.fswdapi.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
    Media findByNameAndExtensionAndIsDeletedFalse(String name, String extension);
}
