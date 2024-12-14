package com.vuanhnguyenduc.aquariux.crypto.trading.mapper;

import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoCurrencyResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoCurrency;
import org.springframework.stereotype.Component;

@Component
public class CryptoCurrencyMapper {
    public CryptoCurrencyResponseDTO toCurrencyDTO(CryptoCurrency cryptoCurrency) {
        return CryptoCurrencyResponseDTO
                .builder()
                .currencyName(cryptoCurrency.getCurrencyName())
                .askPrice(cryptoCurrency.getAskPrice())
                .askSource(cryptoCurrency.getAskSource())
                .bidPrice(cryptoCurrency.getBidPrice())
                .bidSource(cryptoCurrency.getBidSource())
                .lastUpdatedOn(cryptoCurrency.getUpdatedOn())
                .build();
    }
}
