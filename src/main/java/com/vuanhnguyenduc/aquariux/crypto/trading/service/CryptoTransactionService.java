package com.vuanhnguyenduc.aquariux.crypto.trading.service;

import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoTransactionRequestDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoTransactionResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.exception.CryptoTransactionException;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoTransaction;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.TransactionStatus;

import java.util.List;

public interface CryptoTransactionService {
    CryptoTransactionResponseDTO executeTransaction(CryptoTransactionRequestDTO cryptoTransactionRequestDTO);

    CryptoTransaction registerTransaction(CryptoTransactionRequestDTO cryptoTransactionRequestDTO) throws CryptoTransactionException;

    void updateTransactionDetails(CryptoTransaction cryptoTransaction, double price, double total);

    void updateTransactionStatus(CryptoTransaction cryptoTransaction, TransactionStatus status);

    void updateTransactionWithException(CryptoTransaction cryptoTransaction, String errorMessage);

    List<CryptoTransactionResponseDTO> fetchTransactionHistory(String username);
}
