package com.example.OnlineBookStore.service;

import com.example.OnlineBookStore.model.MyUser;
import com.example.OnlineBookStore.model.Payment;
import com.example.OnlineBookStore.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Page<MyUser> getAllUsersPage(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    public Page<MyUser> getAllUsersPageSortByField(int page, int size, String field) {
        return userRepository.findAll(PageRequest.of(page, size, Sort.by(field)));
    }

    public Iterable<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public MyUser save(MyUser myUser) {
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        return userRepository.save(myUser);
    }

    @PostConstruct
    public void createUser() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            MyUser myUser = new MyUser();
            myUser.setPassword(passwordEncoder.encode("admin"));
            myUser.setUsername("admin");
            myUser.setIsAdmin(true);
            userRepository.save(myUser);
        }
        ;
    }

    public Optional<MyUser> getMyUserById(Long id) {
        return userRepository.findById(id);
    }

}
