package ru.geekbrain.repositories;

import org.springframework.stereotype.Repository;
import ru.geekbrain.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private final AtomicLong identityGen;

    private final Map<Long, User> users;

    public UserRepository() {
        this.identityGen = new AtomicLong(0);
        this.users = new ConcurrentHashMap<>();
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public void save(User user) {
        long id = identityGen.incrementAndGet();
        user.setId(id);
        users.put(id, user);
    }

    public User findById(long id) {
        return users.get(id);
    }
}
