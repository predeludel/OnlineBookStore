package com.example.OnlineBookStore.repository;

import com.example.OnlineBookStore.model.MyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<MyUser, Long> {
    Page<MyUser> findAll(Pageable pageable);

    Optional<MyUser> findByUsername(String username);

}
