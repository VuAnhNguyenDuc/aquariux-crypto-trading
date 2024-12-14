package com.vuanhnguyenduc.aquariux.crypto.trading.mapper;

import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoTransactionResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoTransaction;
import org.springframework.stereotype.Component;

@Component
public class CryptoTransactionMapper {
    public CryptoTransactionResponseDTO toCryptoTransactionResponseDTO(CryptoTransaction cryptoTransaction) {
        return CryptoTransactionResponseDTO.builder()
                .transactionType(cryptoTransaction.getTransactionType())
                .cryptoCurrency(cryptoTransaction.getCryptoCurrency())
                .transactionStatus(cryptoTransaction.getTransactionStatus())
                .amount(cryptoTransaction.getAmount())
                .total(cryptoTransaction.getTotal())
                .price(cryptoTransaction.getPrice())
                .errorInfo(cryptoTransaction.getErrorInfo())
                .transactionDate(cryptoTransaction.getCreatedOn())
                .build();
    }
}
