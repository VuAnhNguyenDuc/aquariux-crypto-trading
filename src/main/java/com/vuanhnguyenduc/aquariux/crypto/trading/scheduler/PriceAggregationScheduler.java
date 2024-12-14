package com.vuanhnguyenduc.aquariux.crypto.trading.scheduler;

import com.vuanhnguyenduc.aquariux.crypto.trading.config.BinanceProperties;
import com.vuanhnguyenduc.aquariux.crypto.trading.config.CryptoProperties;
import com.vuanhnguyenduc.aquariux.crypto.trading.config.HuobiProperties;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.BinanceCurrencyResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.HuobiCurrencyResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.HuobiCurrencyListResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.PriceSource;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class PriceAggregationScheduler {

    private final CryptoProperties cryptoProperties;

    private final BinanceProperties binanceProperties;

    private final HuobiProperties huobiProperties;

    private final RestTemplate restTemplate;

    private final CryptoCurrencyService cryptoCurrencyService;

    @Scheduled(fixedDelay = 10000)
    public void aggregatePrices() {
        Map<String, BinanceCurrencyResponseDTO> binanceCurrencyPriceMap = fetchPricesFromBinance();
        Map<String, HuobiCurrencyResponseDTO> huobiCurrencyPriceMap = fetchPricesFromHuobi();

        cryptoProperties.getSupportedCurrencies().forEach(currency -> {
            log.info("Computing the best aggregated prices for the currency {}", currency);

            BinanceCurrencyResponseDTO binancePrice = binanceCurrencyPriceMap.get(currency);
            HuobiCurrencyResponseDTO huobiPrice = huobiCurrencyPriceMap.get(currency);

            computeAggregatedPriceForSingleCurrency(binancePrice, huobiPrice, currency);
        });
    }

    private void computeAggregatedPriceForSingleCurrency(BinanceCurrencyResponseDTO binancePrice, HuobiCurrencyResponseDTO huobiPrice, String currency) {
        double maxBid, minAsk;
        PriceSource bidSource, askSource;
        if (binancePrice == null && huobiPrice == null) {
            log.info("The currency {} is not found on both Binance and Huobi", currency);
        } else {
            if (binancePrice == null) {
                maxBid = huobiPrice.getBid();
                minAsk = huobiPrice.getAsk();
                bidSource = PriceSource.HUOBI;
                askSource = PriceSource.HUOBI;
            } else if (huobiPrice == null) {
                maxBid = Double.parseDouble(binancePrice.getBidPrice());
                minAsk = Double.parseDouble(binancePrice.getAskPrice());
                bidSource = PriceSource.BINANCE;
                askSource = PriceSource.BINANCE;
            } else {
                maxBid = Math.max(Double.parseDouble(binancePrice.getBidPrice()), huobiPrice.getBid());
                minAsk = Math.min(Double.parseDouble(binancePrice.getAskPrice()), huobiPrice.getAsk());
                bidSource = (maxBid == huobiPrice.getBid()) ? PriceSource.HUOBI : PriceSource.BINANCE;
                askSource = (minAsk == huobiPrice.getAsk()) ? PriceSource.HUOBI : PriceSource.BINANCE;
            }
            cryptoCurrencyService.updateCurrencyPrices(currency, maxBid, bidSource, minAsk, askSource);
            log.info("Currency {} has been updated", currency);
        }
    }

    private Map<String, BinanceCurrencyResponseDTO> fetchPricesFromBinance() {
        log.info("Fetching prices from Binance with the given url {}", binanceProperties.getUrl());
        ResponseEntity<BinanceCurrencyResponseDTO[]> responseEntity = restTemplate.getForEntity(binanceProperties.getUrl(), BinanceCurrencyResponseDTO[].class);
        if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
            List<BinanceCurrencyResponseDTO> binanceCurrencyResponseDTOS = Arrays.asList(responseEntity.getBody());
            return binanceCurrencyResponseDTOS
                    .stream()
                        .map(currency -> {
                            currency.setSymbol(currency.getSymbol().toUpperCase());
                            return currency;
                        })
                        .filter(currency -> cryptoProperties.getSupportedCurrencies().contains(currency.getSymbol()))
                        .collect(Collectors.toMap(BinanceCurrencyResponseDTO::getSymbol, currency -> currency));
        } else {
            log.error("Invalid status code or empty response received from Binance, returning empty list");
            return Collections.EMPTY_MAP;
        }
    }

    private Map<String, HuobiCurrencyResponseDTO> fetchPricesFromHuobi() {
        log.info("Fetching prices from Huobi with the given url {}", binanceProperties.getUrl());
        ResponseEntity<HuobiCurrencyListResponseDTO> responseEntity = restTemplate.getForEntity(huobiProperties.getUrl(), HuobiCurrencyListResponseDTO.class);
        if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
            List<HuobiCurrencyResponseDTO> binanceCurrencyPrices = responseEntity.getBody().getData();
            return binanceCurrencyPrices
                    .stream()
                    .map(currency -> {
                        currency.setSymbol(currency.getSymbol().toUpperCase());
                        return currency;
                    })
                    .filter(currency -> cryptoProperties.getSupportedCurrencies().contains(currency.getSymbol()))
                    .collect(Collectors.toMap(HuobiCurrencyResponseDTO::getSymbol, currency -> currency));
        } else {
            log.error("Invalid status code or empty response received from Huobi, returning empty list");
            return Collections.EMPTY_MAP;
        }
    }
}
