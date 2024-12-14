package com.vuanhnguyenduc.aquariux.crypto.trading.service;

import com.vuanhnguyenduc.aquariux.crypto.trading.AquariuxCryptoTradingApplication;
import com.vuanhnguyenduc.aquariux.crypto.trading.mapper.CryptoCurrencyMapper;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.PriceSource;
import com.vuanhnguyenduc.aquariux.crypto.trading.repository.CryptoCurrencyRepository;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.impl.CryptoCurrencyServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoCurrencyFixture.givenCryptoCurrency;
import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoCurrencyFixture.givenCryptoCurrencyResponseDTO;
import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoCurrencyFixture.givenListOfCryptoCurrencies;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AquariuxCryptoTradingApplication.class)
@RunWith(SpringRunner.class)
public class CryptoCurrencyServiceTest {
    @InjectMocks
    private CryptoCurrencyServiceImpl cryptoCurrencyService;

    @Mock
    private CryptoCurrencyRepository cryptoCurrencyRepository;

    @Mock
    private CryptoCurrencyMapper cryptoCurrencyMapper;

    @Test
    public void shouldListAllCryptoCurrencyDTOsSuccessfully() {
        when(cryptoCurrencyRepository.findAll()).thenReturn(givenListOfCryptoCurrencies());
        when(cryptoCurrencyMapper.toCurrencyDTO(any())).thenReturn(givenCryptoCurrencyResponseDTO());

        var allCurrencyDTOs = cryptoCurrencyService.listAllCryptoCurrencies();
        assertEquals(2, allCurrencyDTOs.size());
    }

    @Test
    public void shouldFindCurrencyDTOByNameSuccessfully() {
        var givenCurrency = givenCryptoCurrency();
        when(cryptoCurrencyRepository.findByCurrencyName(any())).thenReturn(givenCurrency);
        when(cryptoCurrencyMapper.toCurrencyDTO(any())).thenReturn(givenCryptoCurrencyResponseDTO());

        var currencyDTO = cryptoCurrencyService.findCurrencyDTOByName("BTCUSDT");
        assertEquals(givenCurrency.getCurrencyName(), currencyDTO.getCurrencyName());
    }

    @Test
    public void shouldUpdateExistingCurrencyPricesSuccessfully() {
        var givenCurrency = givenCryptoCurrency();
        when(cryptoCurrencyRepository.findByCurrencyName(any())).thenReturn(givenCurrency);

        var expectedCurrency = givenCryptoCurrency();
        expectedCurrency.setBidPrice(1);
        expectedCurrency.setAskPrice(1);
        when(cryptoCurrencyRepository.save(any())).thenReturn(expectedCurrency);

        var actualCurrency = cryptoCurrencyService.updateCurrencyPrices("BTCUSDT", 1, PriceSource.BINANCE, 1, PriceSource.BINANCE);

        assertEquals(expectedCurrency.getBidPrice(), actualCurrency.getBidPrice(), 0.001);
        assertEquals(expectedCurrency.getAskPrice(), actualCurrency.getAskPrice(), 0.001);
    }

    @Test
    public void shouldPopulateNewCurrencyPricesSuccessfully() {
        when(cryptoCurrencyRepository.findByCurrencyName(any())).thenReturn(null);

        var expectedCurrency = givenCryptoCurrency();
        expectedCurrency.setBidPrice(1);
        expectedCurrency.setAskPrice(1);
        when(cryptoCurrencyRepository.save(any())).thenReturn(expectedCurrency);

        var actualCurrency = cryptoCurrencyService.updateCurrencyPrices("BTCUSDT", 1, PriceSource.BINANCE, 1, PriceSource.BINANCE);

        assertEquals(expectedCurrency.getBidPrice(), actualCurrency.getBidPrice(), 0.001);
        assertEquals(expectedCurrency.getAskPrice(), actualCurrency.getAskPrice(), 0.001);
    }

    @Test
    public void shouldFindCurrencyByName() {
        var givenCurrency = givenCryptoCurrency();
        when(cryptoCurrencyRepository.findByCurrencyName(any())).thenReturn(givenCryptoCurrency());

        var actualCurrency = cryptoCurrencyService.findCurrencyByName("BTCUSDT");
        assertEquals(givenCurrency.getBidPrice(), actualCurrency.getBidPrice(), 0.001);
        assertEquals(givenCurrency.getAskPrice(), actualCurrency.getAskPrice(), 0.001);
    }
}
