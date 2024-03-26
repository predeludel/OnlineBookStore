package com.example.OnlineBookStore.repository;

import com.example.OnlineBookStore.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    Page<Payment> findAll(Pageable pageable);
}
