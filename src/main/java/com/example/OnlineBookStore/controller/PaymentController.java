package com.example.OnlineBookStore.controller;

import com.example.OnlineBookStore.helper.UserHelper;
import com.example.OnlineBookStore.model.Payment;
import com.example.OnlineBookStore.repository.PaymentRepository;
import com.example.OnlineBookStore.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/payment")
@Tag(name = "Оплата заказа",
        description = "Все методы для работы с оплатой заказов")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/read")
    @Operation(summary = "Получить все заказы")
    public Iterable<Payment> read() throws Exception {
        return paymentService.getAllPayments();
    }
    @GetMapping("/read/{id}")
    @Operation(summary = "Получить оплату по ID")
    public Optional<Payment> read(@Parameter(description = "Id Оплаты") @PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @PostMapping()
    @Operation(summary = "Сохранить оплату заказа")
    public Payment save(@Parameter(description = "Оплата") @RequestBody Payment payment) throws Exception {
        return paymentService.save(payment);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить оплату заказа")
    public Boolean delete(@AuthenticationPrincipal User user,
                          @Parameter(description = "Id Оплаты") @PathVariable Long id) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return paymentService.deletePayment(id);
    }


    @GetMapping("/{page}/{size}")
    @Operation(summary = "Получить страницы с оплатами заказов (пагинация)")
    public Page<Payment> read(@AuthenticationPrincipal User user,
                              @Parameter(description = "Количество страниц") @PathVariable Integer page,
                              @Parameter(description = "Количество книг на странице") @PathVariable Integer size) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return paymentService.getAllPaymentsPage(page, size);
    }

    @GetMapping("/sort/{page}/{size}/{field}")
    @Operation(summary = "Получить страницы с оплатми заказов по полю (пагинация)")
    public Page<Payment> read(@AuthenticationPrincipal User user,
                              @Parameter(description = "Количество страниц") @PathVariable Integer page,
                              @Parameter(description = "Количество книг на странице") @PathVariable Integer size,
                              @Parameter(description = "Поле сортировки") @PathVariable String field) throws Exception {
        if (!UserHelper.isAdmin(user)) {
            throw new Exception("Not admin!");
        }
        return paymentService.getAllPaymentsPageSortByField(page, size, field);
    }


}
