package ru.geekbrain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrain.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);
    Optional<User> findByNameAndEmail(String name, String email);

    Page<User> findByAgeGreaterThan(Integer minAge, Pageable pageable);
    Page<User> findByAgeLessThan (Integer maxAge, Pageable pageable);
    Page<User> findByAgeGreaterThanEqualAndAgeLessThanEqual(Integer minAge, Integer maxAge, Pageable pageable);

}
