package ru.geekbrain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrain.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    List<User> findByAgeGreaterThan(Integer minAge);
    List<User> findByAgeLessThan (Integer maxAge);
    List<User> findByAgeGreaterThanEqualAndAgeLessThanEqual(Integer minAge, Integer maxAge);

}
