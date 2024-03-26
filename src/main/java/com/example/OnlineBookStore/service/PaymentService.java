package com.example.OnlineBookStore.service;

import com.example.OnlineBookStore.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.example.OnlineBookStore.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;


    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }


    public boolean deletePayment(Long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Iterable<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }


    public Page<Payment> getAllPaymentsPage(int page, int size) {
        return paymentRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Payment> getAllPaymentsPageSortByField(int page, int size, String field) {
        return paymentRepository.findAll(PageRequest.of(page, size, Sort.by(field)));
    }


}
