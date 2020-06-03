package ru.geekbrain.lesson6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrain.lesson6.entities.User;
import ru.geekbrain.lesson6.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<User> filterByAge(Integer minAge, Integer maxAge, Pageable pageable) {
//        if (minAge==0 && maxAge==0)
//            return repository.findAll();
//        if (minAge==0)
//            return repository.findByAgeGreaterThan(minAge);
//        if (maxAge==0)
//            return repository.findByAgeLessThan(maxAge);
        return repository.findByAgeGreaterThanEqualAndAgeLessThanEqual(minAge, maxAge, pageable);
    }

    @Transactional
    public void save(User user) {
        repository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(long id) {
        return repository.findById(id);
    }

    @Transactional
    public void editUser(User user){
        Optional<User> userFromDB = repository.findByName(user.getName());
        if(userFromDB.isPresent()){
            User u = userFromDB.get();
            u.setAge(user.getAge());
            u.setEmail(user.getEmail());
            u.setName(user.getName());
            System.out.println("id="+u.getId());
            repository.save(u);
        }
    }
}
