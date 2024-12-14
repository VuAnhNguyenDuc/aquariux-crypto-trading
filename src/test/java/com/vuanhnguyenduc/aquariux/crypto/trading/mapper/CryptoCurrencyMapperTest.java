package com.vuanhnguyenduc.aquariux.crypto.trading.mapper;

import com.vuanhnguyenduc.aquariux.crypto.trading.AquariuxCryptoTradingApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoCurrencyFixture.givenCryptoCurrency;
import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = AquariuxCryptoTradingApplication.class)
@RunWith(SpringRunner.class)
public class CryptoCurrencyMapperTest {
    @InjectMocks
    private CryptoCurrencyMapper cryptoCurrencyMapper;

    @Test
    public void shouldMapCurrencyToDTOSuccessfully() {
        var givenCurrency = givenCryptoCurrency();
        var mappedCurrency = cryptoCurrencyMapper.toCurrencyDTO(givenCurrency);
        assertEquals(givenCurrency.getCurrencyName(), mappedCurrency.getCurrencyName());
        assertEquals(givenCurrency.getAskPrice(), mappedCurrency.getAskPrice(), 0.001);
        assertEquals(givenCurrency.getAskSource(), mappedCurrency.getAskSource());
        assertEquals(givenCurrency.getBidPrice(), mappedCurrency.getBidPrice(), 0.001);
        assertEquals(givenCurrency.getBidSource(), mappedCurrency.getBidSource());
    }
}
