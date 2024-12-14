package com.vuanhnguyenduc.aquariux.crypto.trading.controller;

import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoTransactionRequestDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoTransactionResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionControllerV1 {
    private final CryptoTransactionService cryptoTransactionService;

    @GetMapping("/v1/transaction/history/{username}")
    public ResponseEntity<List<CryptoTransactionResponseDTO>> getUserTransactionHistory(@PathVariable("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(cryptoTransactionService.fetchTransactionHistory(username));
    }

    @PostMapping("/v1/transaction")
    public ResponseEntity<CryptoTransactionResponseDTO> executeTransaction(@RequestBody CryptoTransactionRequestDTO cryptoTransactionRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(cryptoTransactionService.executeTransaction(cryptoTransactionRequestDTO));
    }
}
