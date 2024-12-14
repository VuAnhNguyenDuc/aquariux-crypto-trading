package com.vuanhnguyenduc.aquariux.crypto.trading.mapper;

import com.vuanhnguyenduc.aquariux.crypto.trading.AquariuxCryptoTradingApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoWalletFixture.givenCryptoWallet;
import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = AquariuxCryptoTradingApplication.class)
@RunWith(SpringRunner.class)
public class CryptoWalletMapperTest {
    @InjectMocks
    private CryptoWalletMapper cryptoWalletMapper;

    @Test
    public void shouldMapWalletToDTOSuccessfully() {
        var givenWallet = givenCryptoWallet();
        var mappedWallet = cryptoWalletMapper.toCryptoWalletResponseDTO(givenWallet);

        assertEquals(givenWallet.getCryptoCurrency(), mappedWallet.getCryptoCurrency());
        assertEquals(givenWallet.getBalance(), mappedWallet.getBalance(), 0.001);
    }
}
