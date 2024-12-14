package com.vuanhnguyenduc.aquariux.crypto.trading.service.impl;

import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoCurrencyResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.mapper.CryptoCurrencyMapper;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoCurrency;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.PriceSource;
import com.vuanhnguyenduc.aquariux.crypto.trading.repository.CryptoCurrencyRepository;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoCurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    private final CryptoCurrencyMapper cryptoCurrencyMapper;


    @Override
    @Transactional
    public List<CryptoCurrencyResponseDTO> listAllCryptoCurrencies() {
        return cryptoCurrencyRepository
                .findAll()
                .stream()
                .map(cryptoCurrencyMapper::toCurrencyDTO)
                .toList();
    }

    @Override
    @Transactional
    public CryptoCurrencyResponseDTO findCurrencyDTOByName(String currencyName) {
        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findByCurrencyName(currencyName);
        return cryptoCurrency != null ? cryptoCurrencyMapper.toCurrencyDTO(cryptoCurrency) : null;
    }

    @Override
    @Transactional
    public CryptoCurrency findCurrencyByName(String currencyName) {
        return cryptoCurrencyRepository.findByCurrencyName(currencyName);
    }

    @Override
    @Transactional
    public CryptoCurrency updateCurrencyPrices(String currencyName, double maxBid, PriceSource bidSource, double minAsk, PriceSource askSource) {
        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findByCurrencyName(currencyName);
        if (cryptoCurrency == null) {
            cryptoCurrency = CryptoCurrency.builder()
                    .currencyName(currencyName)
                    .askPrice(minAsk)
                    .askSource(askSource)
                    .bidPrice(maxBid)
                    .bidSource(bidSource)
                    .build();
        } else {
            cryptoCurrency.setBidPrice(maxBid);
            cryptoCurrency.setBidSource(bidSource);
            cryptoCurrency.setAskPrice(minAsk);
            cryptoCurrency.setAskSource(askSource);
        }
        return cryptoCurrencyRepository.save(cryptoCurrency);
    }
}
