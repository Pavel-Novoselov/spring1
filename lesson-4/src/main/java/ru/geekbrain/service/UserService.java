package ru.geekbrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrain.entities.User;
import ru.geekbrain.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }

    public List<User> filterByAge(Integer minAge, Integer maxAge) {
//        if (minAge==0 && maxAge==0)
//            return repository.findAll();
//        if (minAge==0)
//            return repository.findByAgeGreaterThan(minAge);
//        if (maxAge==0)
//            return repository.findByAgeLessThan(maxAge);
        return repository.findByAgeGreaterThanEqualAndAgeLessThanEqual(minAge, maxAge);
    }

    @Transactional
    public void save(User user) {
        repository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(long id) {
        return repository.findById(id);
    }
}
