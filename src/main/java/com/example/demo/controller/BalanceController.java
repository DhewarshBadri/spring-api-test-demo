package com.example.demo.controller;

import com.example.demo.model.Balance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BalanceController {

    @GetMapping("/getBalance/{accountId}")
    public ResponseEntity<Balance> getBalance(@PathVariable String accountId) {
        // In a real application, this would fetch data from a database
        // For demonstration, we're returning mock data
        Balance balance = new Balance(accountId, 1250.75, "USD");
        return ResponseEntity.ok(balance);
    }
}
