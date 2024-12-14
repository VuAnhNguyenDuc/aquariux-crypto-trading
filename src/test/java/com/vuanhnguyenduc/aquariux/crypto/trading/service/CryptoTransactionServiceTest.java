package com.vuanhnguyenduc.aquariux.crypto.trading.service;

import com.vuanhnguyenduc.aquariux.crypto.trading.AquariuxCryptoTradingApplication;
import com.vuanhnguyenduc.aquariux.crypto.trading.config.CryptoProperties;
import com.vuanhnguyenduc.aquariux.crypto.trading.exception.BaseException;
import com.vuanhnguyenduc.aquariux.crypto.trading.exception.CryptoTransactionException;
import com.vuanhnguyenduc.aquariux.crypto.trading.mapper.CryptoTransactionMapper;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.TransactionStatus;
import com.vuanhnguyenduc.aquariux.crypto.trading.repository.CryptoTransactionRepository;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.impl.CryptoTransactionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.vuanhnguyenduc.aquariux.crypto.trading.constant.ErrorCodes.CRYPTO_USER_NOT_FOUND;
import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoCurrencyFixture.givenCryptoCurrency;
import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoTransactionFixture.givenBuyCryptoTransaction;
import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoTransactionFixture.givenBuyCryptoTransactionRequestDTO;
import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoTransactionFixture.givenBuyCryptoTransactionResponseDTO;
import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoTransactionFixture.givenSellCryptoTransaction;
import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoTransactionFixture.givenSellCryptoTransactionRequestDTO;
import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoUserFixture.givenCryptoUser;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AquariuxCryptoTradingApplication.class)
@RunWith(SpringRunner.class)
public class CryptoTransactionServiceTest {
    @InjectMocks
    private CryptoTransactionServiceImpl cryptoTransactionService;

    @Mock
    private CryptoTransactionRepository cryptoTransactionRepository;

    @Mock
    private CryptoUserService cryptoUserService;

    @Mock
    private CryptoWalletService cryptoWalletService;

    @Mock
    private CryptoCurrencyService cryptoCurrencyService;

    @Mock
    private CryptoProperties cryptoProperties;

    @Mock
    private CryptoTransactionMapper cryptoTransactionMapper;

    @Before
    public void setup() {
        when(cryptoProperties.getSupportedCurrencies()).thenReturn(List.of("BTCUSDT", "ETHUSDT"));
    }

    @Test
    public void shouldRegisterTransactionSuccessfully() throws CryptoTransactionException {
        var requestDTO = givenBuyCryptoTransactionRequestDTO();
        var expectedTransaction = givenBuyCryptoTransaction();

        when(cryptoTransactionRepository.save(any())).thenReturn(expectedTransaction);

        var actualTransaction = cryptoTransactionService.registerTransaction(requestDTO);
        assertEquals(expectedTransaction.getCryptoCurrency(), actualTransaction.getCryptoCurrency());
        assertEquals(expectedTransaction.getTransactionType(), actualTransaction.getTransactionType());
        assertEquals(expectedTransaction.getTransactionStatus(), actualTransaction.getTransactionStatus());
        assertEquals(expectedTransaction.getAmount(), actualTransaction.getAmount(), 0.001);
        assertEquals(expectedTransaction.getTotal(), actualTransaction.getTotal(), 0.001);
        assertEquals(expectedTransaction.getPrice(), actualTransaction.getPrice(), 0.001);
        assertEquals(expectedTransaction.getCryptoUser().getUsername(), actualTransaction.getCryptoUser().getUsername());
    }

    @Test(expected = CryptoTransactionException.class)
    public void shouldNotRegisterTransactionDueToUnsupportedCurrency() throws CryptoTransactionException {
        var requestDTO = givenBuyCryptoTransactionRequestDTO();
        requestDTO.setCryptoCurrency("ABCDE");
        cryptoTransactionService.registerTransaction(requestDTO);
    }

    @Test
    public void shouldUpdateTransactionDetails() {
        var transaction = givenBuyCryptoTransaction();
        cryptoTransactionService.updateTransactionDetails(transaction, 1, 1);
        verify(cryptoTransactionRepository, times(1)).save(any());
    }

    @Test
    public void shouldUpdateTransactionStatus() {
        var transaction = givenBuyCryptoTransaction();
        cryptoTransactionService.updateTransactionStatus(transaction, TransactionStatus.SUCCEEDED);
        verify(cryptoTransactionRepository, times(1)).save(any());
    }

    @Test
    public void shouldUpdateTransactionWithException() {
        var transaction = givenBuyCryptoTransaction();
        cryptoTransactionService.updateTransactionWithException(transaction, CRYPTO_USER_NOT_FOUND.getErrorMessage());
        verify(cryptoTransactionRepository, times(1)).save(any());
    }

    @Test
    public void shouldExecuteBuyTransaction() throws CryptoTransactionException {
        when(cryptoTransactionRepository.save(any())).thenReturn(givenBuyCryptoTransaction());
        when(cryptoUserService.findByUsername(any())).thenReturn(givenCryptoUser());
        when(cryptoCurrencyService.findCurrencyByName(any())).thenReturn(givenCryptoCurrency());
        when(cryptoTransactionMapper.toCryptoTransactionResponseDTO(any())).thenReturn(givenBuyCryptoTransactionResponseDTO());
        doNothing().when(cryptoWalletService).buyCurrencyToWallet(any());
        cryptoTransactionService.executeTransaction(givenBuyCryptoTransactionRequestDTO());
        verify(cryptoWalletService, times(1)).buyCurrencyToWallet(any());
        verify(cryptoTransactionRepository, times(3)).save(any());
    }

    @Test
    public void shouldExecuteSellTransaction() throws CryptoTransactionException {
        when(cryptoTransactionRepository.save(any())).thenReturn(givenSellCryptoTransaction());
        when(cryptoUserService.findByUsername(any())).thenReturn(givenCryptoUser());
        when(cryptoCurrencyService.findCurrencyByName(any())).thenReturn(givenCryptoCurrency());
        when(cryptoTransactionMapper.toCryptoTransactionResponseDTO(any())).thenReturn(givenBuyCryptoTransactionResponseDTO());
        doNothing().when(cryptoWalletService).sellCurrencyFromWallet(any());
        cryptoTransactionService.executeTransaction(givenSellCryptoTransactionRequestDTO());
        verify(cryptoWalletService, times(1)).sellCurrencyFromWallet(any());
        verify(cryptoTransactionRepository, times(3)).save(any());
    }

    @Test
    public void shouldNotExecuteTransactionDueToInvalidUser() throws CryptoTransactionException {
        when(cryptoUserService.findByUsername(any())).thenReturn(null);
        cryptoTransactionService.executeTransaction(givenBuyCryptoTransactionRequestDTO());
        verify(cryptoWalletService, times(0)).sellCurrencyFromWallet(any());
    }

    @Test
    public void shouldNotExecuteTransactionDueToUnsupportedCurrency() throws CryptoTransactionException {
        var requestDTO = givenBuyCryptoTransactionRequestDTO();
        requestDTO.setCryptoCurrency("ABCDE");
        cryptoTransactionService.executeTransaction(requestDTO);
        verify(cryptoWalletService, times(0)).sellCurrencyFromWallet(any());
    }

    @Test
    public void shouldFetchTransactionHistory() {
        when(cryptoUserService.findByUsername(any())).thenReturn(givenCryptoUser());
        when(cryptoTransactionRepository.findByCryptoUser(any())).thenReturn(List.of(givenBuyCryptoTransaction()));
        when(cryptoTransactionMapper.toCryptoTransactionResponseDTO(any())).thenReturn(givenBuyCryptoTransactionResponseDTO());
        var result = cryptoTransactionService.fetchTransactionHistory("user1");
        assertEquals(1, result.size());
    }

    @Test(expected = BaseException.class)
    public void shouldNotFetchTransactionHistoryDueToInvalidUser() {
        when(cryptoUserService.findByUsername(any())).thenReturn(null);
        cryptoTransactionService.fetchTransactionHistory("user1");
    }
}
