package ru.geekbrain.lesson6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrain.lesson6.entities.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
