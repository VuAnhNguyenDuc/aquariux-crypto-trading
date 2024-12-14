package com.vuanhnguyenduc.aquariux.crypto.trading.mapper;

import com.vuanhnguyenduc.aquariux.crypto.trading.AquariuxCryptoTradingApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoTransactionFixture.givenBuyCryptoTransaction;
import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = AquariuxCryptoTradingApplication.class)
@RunWith(SpringRunner.class)
public class CryptoTransactionMapperTest {
    @InjectMocks
    private CryptoTransactionMapper cryptoTransactionMapper;

    @Test
    public void shouldMapTransactionToDTOSuccessfully() {
        var givenTransaction = givenBuyCryptoTransaction();
        var mappedTransaction = cryptoTransactionMapper.toCryptoTransactionResponseDTO(givenTransaction);

        assertEquals(givenTransaction.getTransactionStatus(), mappedTransaction.getTransactionStatus());
        assertEquals(givenTransaction.getTransactionType(), mappedTransaction.getTransactionType());
        assertEquals(givenTransaction.getAmount(), mappedTransaction.getAmount(), 0.001);
        assertEquals(givenTransaction.getPrice(), mappedTransaction.getPrice(), 0.001);
        assertEquals(givenTransaction.getTotal(), mappedTransaction.getTotal(), 0.001);
        assertEquals(givenTransaction.getCryptoCurrency(), mappedTransaction.getCryptoCurrency());
    }
}
