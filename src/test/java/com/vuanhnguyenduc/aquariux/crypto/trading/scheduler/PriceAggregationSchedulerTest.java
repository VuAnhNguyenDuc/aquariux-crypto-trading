package com.vuanhnguyenduc.aquariux.crypto.trading.scheduler;

import com.vuanhnguyenduc.aquariux.crypto.trading.AquariuxCryptoTradingApplication;
import com.vuanhnguyenduc.aquariux.crypto.trading.config.BinanceProperties;
import com.vuanhnguyenduc.aquariux.crypto.trading.config.CryptoProperties;
import com.vuanhnguyenduc.aquariux.crypto.trading.config.HuobiProperties;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.BinanceCurrencyResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.HuobiCurrencyListResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.HuobiCurrencyResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoCurrencyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AquariuxCryptoTradingApplication.class)
@RunWith(SpringRunner.class)
public class PriceAggregationSchedulerTest {
    @InjectMocks
    private PriceAggregationScheduler priceAggregationScheduler;

    @Mock
    private CryptoProperties cryptoProperties;

    @Mock
    private BinanceProperties binanceProperties;

    @Mock
    private HuobiProperties huobiProperties;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CryptoCurrencyService cryptoCurrencyService;
    private static final String BINANCE_URL = "http://binance.com";
    private static final String HUOBI_URL = "http://huobi.com";


    @Before
    public void setup() {
        when(cryptoProperties.getSupportedCurrencies()).thenReturn(List.of("BTCUSDT", "ETHUSDT"));
        when(binanceProperties.getUrl()).thenReturn(BINANCE_URL);
        when(huobiProperties.getUrl()).thenReturn(HUOBI_URL);
    }

    @Test
    public void shouldAggregatePricesWithBothBinanceAndHuobiDataAvailable() {
        when(restTemplate.getForEntity(BINANCE_URL, BinanceCurrencyResponseDTO[].class))
                .thenReturn(new ResponseEntity<>(givenBinanceResponse(), HttpStatus.OK));
        when(restTemplate.getForEntity(HUOBI_URL, HuobiCurrencyListResponseDTO.class))
                .thenReturn(new ResponseEntity<>(givenHuobiCurrencyListResponse(), HttpStatus.OK));
        priceAggregationScheduler.aggregatePrices();
        verify(cryptoCurrencyService, times(2)).updateCurrencyPrices(any(), anyDouble(), any(), anyDouble(), any());
    }

    @Test
    public void shouldAggregatePricesWithBinanceDataOnly() {
        when(restTemplate.getForEntity(BINANCE_URL, BinanceCurrencyResponseDTO[].class))
                .thenReturn(new ResponseEntity<>(givenBinanceResponse(), HttpStatus.OK));
        when(restTemplate.getForEntity(HUOBI_URL, HuobiCurrencyListResponseDTO.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE));
        priceAggregationScheduler.aggregatePrices();
        verify(cryptoCurrencyService, times(2)).updateCurrencyPrices(any(), anyDouble(), any(), anyDouble(), any());
    }

    @Test
    public void shouldAggregatePricesWithHuobiDataOnly() {
        when(restTemplate.getForEntity(BINANCE_URL, BinanceCurrencyResponseDTO[].class))
                .thenReturn(new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE));
        when(restTemplate.getForEntity(HUOBI_URL, HuobiCurrencyListResponseDTO.class))
                .thenReturn(new ResponseEntity<>(givenHuobiCurrencyListResponse(), HttpStatus.OK));
        priceAggregationScheduler.aggregatePrices();
        verify(cryptoCurrencyService, times(2)).updateCurrencyPrices(any(), anyDouble(), any(), anyDouble(), any());
    }

    @Test
    public void shouldNotAggregatePricesDueToBothBinanceAndHuobiAreDown() {
        when(restTemplate.getForEntity(BINANCE_URL, BinanceCurrencyResponseDTO[].class))
                .thenReturn(new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE));
        when(restTemplate.getForEntity(HUOBI_URL, HuobiCurrencyListResponseDTO.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE));
        priceAggregationScheduler.aggregatePrices();
        verify(cryptoCurrencyService, times(0)).updateCurrencyPrices(any(), anyDouble(), any(), anyDouble(), any());
    }

    private BinanceCurrencyResponseDTO[] givenBinanceResponse() {
        return new BinanceCurrencyResponseDTO[]{
                BinanceCurrencyResponseDTO.builder()
                        .askPrice("1")
                        .bidPrice("1")
                        .symbol("BTCUSDT")
                        .build(),
                BinanceCurrencyResponseDTO.builder()
                        .askPrice("1")
                        .bidPrice("1")
                        .symbol("ETHUSDT")
                        .build()
        };
    }

    private HuobiCurrencyListResponseDTO givenHuobiCurrencyListResponse() {
        return HuobiCurrencyListResponseDTO.builder()
                .data(List.of(
                        HuobiCurrencyResponseDTO.builder()
                                .ask(2.0)
                                .bid(2.0)
                                .symbol("BTCUSDT")
                                .build(),
                        HuobiCurrencyResponseDTO.builder()
                                .ask(2.0)
                                .bid(2.0)
                                .symbol("ETHUSDT")
                                .build()
                ))
                .build();
    }
}
