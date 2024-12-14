package com.vuanhnguyenduc.aquariux.crypto.trading.service;

import com.vuanhnguyenduc.aquariux.crypto.trading.AquariuxCryptoTradingApplication;
import com.vuanhnguyenduc.aquariux.crypto.trading.exception.BaseException;
import com.vuanhnguyenduc.aquariux.crypto.trading.exception.CryptoTransactionException;
import com.vuanhnguyenduc.aquariux.crypto.trading.mapper.CryptoWalletMapper;
import com.vuanhnguyenduc.aquariux.crypto.trading.repository.CryptoWalletRepository;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.impl.CryptoWalletServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoTransactionFixture.givenBuyCryptoTransaction;
import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoTransactionFixture.givenBuyCryptoTransactionResponseDTO;
import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoTransactionFixture.givenSellCryptoTransaction;
import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoUserFixture.givenCryptoUser;
import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoWalletFixture.givenCryptoWallet;
import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoWalletFixture.givenCryptoWalletResponseDTO;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AquariuxCryptoTradingApplication.class)
@RunWith(SpringRunner.class)
public class CryptoWalletServiceTest {
    @InjectMocks
    private CryptoWalletServiceImpl cryptoWalletService;

    @Mock
    private CryptoWalletRepository cryptoWalletRepository;

    @Mock
    private CryptoUserService cryptoUserService;

    @Mock
    private CryptoWalletMapper cryptoWalletMapper;

    @Test
    public void shouldUpdateWalletBalance() {
        var cryptoWallet = givenCryptoWallet();
        cryptoWalletService.updateWalletBalance(cryptoWallet, 100);
        verify(cryptoWalletRepository, times(1)).save(any());
    }

    @Test
    public void shouldBuyCurrencyToNewWallet() throws CryptoTransactionException {
        var givenTransaction = givenBuyCryptoTransaction();
        var givenCryptoUser = givenTransaction.getCryptoUser();
        var currencyName = givenTransaction.getCryptoCurrency();
        givenCryptoUser.setBalance(2);
        when(cryptoWalletRepository.findByCryptoUserAndCryptoCurrency(givenCryptoUser, currencyName)).thenReturn(null);
        doNothing().when(cryptoUserService).updateUserBalance(any(), anyDouble());
        cryptoWalletService.buyCurrencyToWallet(givenTransaction);

        verify(cryptoWalletRepository, times(1)).save(any());
        verify(cryptoUserService, times(1)).updateUserBalance(any(), anyDouble());
    }

    @Test
    public void shouldBuyCurrencyToExistingWallet() throws CryptoTransactionException {
        var givenTransaction = givenBuyCryptoTransaction();
        var givenCryptoUser = givenTransaction.getCryptoUser();
        var givenWallet = givenCryptoWallet();
        var currencyName = givenTransaction.getCryptoCurrency();
        givenCryptoUser.setBalance(2);
        when(cryptoWalletRepository.findByCryptoUserAndCryptoCurrency(givenCryptoUser, currencyName)).thenReturn(givenWallet);
        doNothing().when(cryptoUserService).updateUserBalance(any(), anyDouble());
        cryptoWalletService.buyCurrencyToWallet(givenTransaction);

        verify(cryptoWalletRepository, times(1)).save(any());
        verify(cryptoUserService, times(1)).updateUserBalance(any(), anyDouble());
    }

    @Test(expected = CryptoTransactionException.class)
    public void shouldNotBuyCurrencyToExistingWalletDueToInsufficientFund() throws CryptoTransactionException {
        var givenTransaction = givenBuyCryptoTransaction();
        var givenCryptoUser = givenTransaction.getCryptoUser();
        var givenWallet = givenCryptoWallet();
        var currencyName = givenTransaction.getCryptoCurrency();
        givenCryptoUser.setBalance(0);
        when(cryptoWalletRepository.findByCryptoUserAndCryptoCurrency(givenCryptoUser, currencyName)).thenReturn(givenWallet);
        cryptoWalletService.buyCurrencyToWallet(givenTransaction);
    }

    @Test
    public void shouldSellCurrencyFromExistingWallet() throws CryptoTransactionException {
        var givenWallet = givenCryptoWallet();
        givenWallet.setBalance(2);
        var givenTransaction = givenSellCryptoTransaction();
        when(cryptoWalletRepository.findByCryptoUserAndCryptoCurrency(any(), any())).thenReturn(givenWallet);
        cryptoWalletService.sellCurrencyFromWallet(givenTransaction);
        verify(cryptoWalletRepository, times(1)).save(any());
        verify(cryptoUserService, times(1)).updateUserBalance(any(), anyDouble());
    }

    @Test(expected = CryptoTransactionException.class)
    public void shouldNotSellCurrencyDueToNonExistingWallet() throws CryptoTransactionException {
        var givenTransaction = givenSellCryptoTransaction();
        when(cryptoWalletRepository.findByCryptoUserAndCryptoCurrency(any(), any())).thenReturn(null);
        cryptoWalletService.sellCurrencyFromWallet(givenTransaction);
    }

    @Test(expected = CryptoTransactionException.class)
    public void shouldNotSellCurrencyDueToInsufficientBalance() throws CryptoTransactionException {
        var givenWallet = givenCryptoWallet();
        givenWallet.setBalance(0);
        var givenTransaction = givenSellCryptoTransaction();
        when(cryptoWalletRepository.findByCryptoUserAndCryptoCurrency(any(), any())).thenReturn(givenWallet);
        cryptoWalletService.sellCurrencyFromWallet(givenTransaction);
    }

    @Test
    public void shouldFindUserCurrencyWalletDTOs() {
        var givenUser = givenCryptoUser();
        var givenWallet = givenCryptoWallet();
        givenUser.setCryptoWallets(Set.of(givenWallet));

        when(cryptoUserService.findByUsername(any())).thenReturn(givenUser);
        when(cryptoWalletRepository.findByCryptoUser(any())).thenReturn(List.of(givenCryptoWallet()));
        when(cryptoWalletMapper.toCryptoWalletResponseDTO(any())).thenReturn(givenCryptoWalletResponseDTO());

        var result = cryptoWalletService.fetchUserCurrencyWalletDTOs(givenUser.getUsername());
        assertEquals(1, result.size());
        assertEquals(givenWallet.getCryptoCurrency(), result.get(0).getCryptoCurrency());
        assertEquals(givenWallet.getBalance(), result.get(0).getBalance(), 0.001);
    }

    @Test(expected = BaseException.class)
    public void shouldNotFindUserCurrencyWalletsDueToInvalidUser() {
        when(cryptoUserService.findByUsername(any())).thenReturn(null);
        cryptoWalletService.fetchUserCurrencyWalletDTOs("1234");
    }

    @Test
    public void shouldGetCryptoUserBalance() {
        var givenUser = givenCryptoUser();
        when(cryptoUserService.findByUsername(any())).thenReturn(givenUser);
        when(cryptoWalletRepository.findByCryptoUser(any())).thenReturn(List.of(givenCryptoWallet()));
        cryptoWalletService.getCryptoUserBalance(any());
        verify(cryptoWalletRepository, times(1)).findByCryptoUser(any());
    }

    @Test(expected = BaseException.class)
    public void shouldNotGetCryptoUserBalanceDueToInvalidUser() {
        when(cryptoUserService.findByUsername(any())).thenReturn(null);
        cryptoWalletService.getCryptoUserBalance(any());
    }
}
