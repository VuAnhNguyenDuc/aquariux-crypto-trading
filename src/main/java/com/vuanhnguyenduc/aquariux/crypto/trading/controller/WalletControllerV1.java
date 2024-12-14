package com.vuanhnguyenduc.aquariux.crypto.trading.controller;

import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoUserBalanceResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WalletControllerV1 {
    private final CryptoWalletService cryptoWalletService;

    @GetMapping("/v1/wallet/{username}")
    public ResponseEntity<CryptoUserBalanceResponseDTO> getCryptoUserBalance(@PathVariable("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(cryptoWalletService.getCryptoUserBalance(username));
    }
}
