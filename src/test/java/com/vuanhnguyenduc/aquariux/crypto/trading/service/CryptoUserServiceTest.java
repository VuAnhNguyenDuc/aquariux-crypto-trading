package com.vuanhnguyenduc.aquariux.crypto.trading.service;

import com.vuanhnguyenduc.aquariux.crypto.trading.AquariuxCryptoTradingApplication;
import com.vuanhnguyenduc.aquariux.crypto.trading.mapper.CryptoTransactionMapper;
import com.vuanhnguyenduc.aquariux.crypto.trading.mapper.CryptoWalletMapper;
import com.vuanhnguyenduc.aquariux.crypto.trading.repository.CryptoUserRepository;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.impl.CryptoUserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoUserFixture.givenCryptoUser;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AquariuxCryptoTradingApplication.class)
@RunWith(SpringRunner.class)
public class CryptoUserServiceTest {
    @InjectMocks
    private CryptoUserServiceImpl cryptoUserService;

    @Mock
    private CryptoUserRepository cryptoUserRepository;

    @Test
    public void shouldFindUserById() {
        var givenUser = givenCryptoUser();
        when(cryptoUserRepository.findById(any())).thenReturn(Optional.of(givenUser));

        var actualUser = cryptoUserService.findByUserId(givenUser.getUserId());
        assertEquals(givenUser.getUsername(), actualUser.getUsername());
        assertEquals(givenUser.getBalance(), actualUser.getBalance(), 0.001);
    }

    @Test
    public void shouldUpdateUserBalance() {
        var givenUser = givenCryptoUser();
        cryptoUserService.updateUserBalance(givenUser, 123);
        verify(cryptoUserRepository, times(1)).save(any());
    }

    @Test
    public void shouldFindByUsername() {
        var givenUser = givenCryptoUser();
        when(cryptoUserRepository.findByUsername(any())).thenReturn(givenUser);

        var actualUser = cryptoUserService.findByUsername(givenUser.getUsername());
        assertEquals(givenUser.getUsername(), actualUser.getUsername());
        assertEquals(givenUser.getBalance(), actualUser.getBalance(), 0.001);
    }
}
