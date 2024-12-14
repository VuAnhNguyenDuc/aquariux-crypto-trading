package com.vuanhnguyenduc.aquariux.crypto.trading.fixtures;

import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoCurrencyResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoCurrency;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.PriceSource;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class CryptoCurrencyFixture {
    public static CryptoCurrency givenCryptoCurrency() {
        return CryptoCurrency.builder()
                .currencyName("BTCUSDT")
                .bidSource(PriceSource.BINANCE)
                .bidPrice(123.345)
                .askSource(PriceSource.HUOBI)
                .askPrice(132.435)
                .createdOn(Timestamp.from(Instant.now()))
                .build();
    }

    public static CryptoCurrencyResponseDTO givenCryptoCurrencyResponseDTO() {
        return CryptoCurrencyResponseDTO.builder()
                .currencyName("BTCUSDT")
                .bidSource(PriceSource.BINANCE)
                .bidPrice(123.345)
                .askSource(PriceSource.HUOBI)
                .askPrice(132.435)
                .build();
    }

    public static List<CryptoCurrency> givenListOfCryptoCurrencies() {
        return List.of(
                CryptoCurrency.builder()
                        .currencyName("BTCUSDT")
                        .bidSource(PriceSource.BINANCE)
                        .bidPrice(123.345)
                        .askSource(PriceSource.HUOBI)
                        .askPrice(132.435)
                        .createdOn(Timestamp.from(Instant.now()))
                        .build(),
                CryptoCurrency.builder()
                        .currencyName("ETHUSDT")
                        .bidSource(PriceSource.HUOBI)
                        .bidPrice(123.345)
                        .askSource(PriceSource.BINANCE)
                        .askPrice(132.435)
                        .createdOn(Timestamp.from(Instant.now()))
                        .build()
        );
    }
}
