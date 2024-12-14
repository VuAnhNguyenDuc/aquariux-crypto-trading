package com.vuanhnguyenduc.aquariux.crypto.trading.fixtures;

import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoTransactionRequestDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoTransactionResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoTransaction;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.TransactionStatus;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.TransactionType;

import java.sql.Timestamp;
import java.time.Instant;

import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoUserFixture.givenCryptoUser;

public class CryptoTransactionFixture {
    public static CryptoTransaction givenBuyCryptoTransaction() {
        return CryptoTransaction.builder()
                .cryptoCurrency("BTCUSDT")
                .amount(1)
                .transactionType(TransactionType.BUY)
                .cryptoUser(givenCryptoUser())
                .transactionStatus(TransactionStatus.CREATED)
                .price(1)
                .total(1)
                .createdOn(Timestamp.from(Instant.now()))
                .build();
    }

    public static CryptoTransaction givenSellCryptoTransaction() {
        return CryptoTransaction.builder()
                .cryptoCurrency("BTCUSDT")
                .amount(1)
                .transactionType(TransactionType.SELL)
                .cryptoUser(givenCryptoUser())
                .transactionStatus(TransactionStatus.CREATED)
                .price(1)
                .total(1)
                .createdOn(Timestamp.from(Instant.now()))
                .build();
    }

    public static CryptoTransactionRequestDTO givenBuyCryptoTransactionRequestDTO() {
        return CryptoTransactionRequestDTO.builder()
                .username(givenCryptoUser().getUsername())
                .transactionType(TransactionType.BUY)
                .amount(1)
                .cryptoCurrency("BTCUSDT")
                .build();
    }

    public static CryptoTransactionRequestDTO givenSellCryptoTransactionRequestDTO() {
        return CryptoTransactionRequestDTO.builder()
                .username(givenCryptoUser().getUsername())
                .transactionType(TransactionType.SELL)
                .amount(1)
                .cryptoCurrency("BTCUSDT")
                .build();
    }

    public static CryptoTransactionResponseDTO givenBuyCryptoTransactionResponseDTO() {
        return CryptoTransactionResponseDTO.builder()
                .cryptoCurrency("BTCUSDT")
                .total(1)
                .amount(1)
                .price(1)
                .transactionStatus(TransactionStatus.CREATED)
                .transactionType(TransactionType.BUY)
                .build();
    }
}
