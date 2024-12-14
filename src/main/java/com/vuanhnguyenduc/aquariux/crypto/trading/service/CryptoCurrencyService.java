package com.vuanhnguyenduc.aquariux.crypto.trading.service;

import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoCurrencyResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoCurrency;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.PriceSource;

import java.util.List;

public interface CryptoCurrencyService {
    List<CryptoCurrencyResponseDTO> listAllCryptoCurrencies();

    CryptoCurrencyResponseDTO findCurrencyDTOByName(String currencyName);

    CryptoCurrency findCurrencyByName(String currencyName);

    CryptoCurrency updateCurrencyPrices(String currencyName, double maxBid, PriceSource bidSource, double minAsk, PriceSource askSource);
}
