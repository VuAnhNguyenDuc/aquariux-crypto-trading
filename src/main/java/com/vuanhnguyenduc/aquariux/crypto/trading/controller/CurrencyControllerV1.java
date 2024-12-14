package com.vuanhnguyenduc.aquariux.crypto.trading.controller;

import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoCurrencyResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CurrencyControllerV1 {
    private final CryptoCurrencyService cryptoCurrencyService;

    @GetMapping("/v1/currency")
    public ResponseEntity<List<CryptoCurrencyResponseDTO>> getCurrencyPrices() {
        List<CryptoCurrencyResponseDTO> currencies = cryptoCurrencyService.listAllCryptoCurrencies();
        return ResponseEntity.status(HttpStatus.OK).body(currencies);
    }

    @GetMapping("/v1/currency/{currencyName}")
    public ResponseEntity<CryptoCurrencyResponseDTO> getCurrencyPricesByName(@PathVariable("currencyName") String currencyName) {
        CryptoCurrencyResponseDTO currency = cryptoCurrencyService.findCurrencyDTOByName(currencyName);
        return ResponseEntity.status(HttpStatus.OK).body(currency);
    }
}
