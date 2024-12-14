package com.vuanhnguyenduc.aquariux.crypto.trading.service.impl;

import com.vuanhnguyenduc.aquariux.crypto.trading.constant.ErrorCodes;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoUserBalanceResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoWalletResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.exception.BaseException;
import com.vuanhnguyenduc.aquariux.crypto.trading.exception.CryptoTransactionException;
import com.vuanhnguyenduc.aquariux.crypto.trading.mapper.CryptoWalletMapper;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoTransaction;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoUser;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoWallet;
import com.vuanhnguyenduc.aquariux.crypto.trading.repository.CryptoWalletRepository;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoUserService;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoWalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.vuanhnguyenduc.aquariux.crypto.trading.constant.ErrorCodes.CRYPTO_USER_INSUFFICIENT_BALANCE_TO_BUY;
import static com.vuanhnguyenduc.aquariux.crypto.trading.constant.ErrorCodes.CRYPTO_USER_INSUFFICIENT_CURRENCY_AMOUNT_TO_SELL;
import static com.vuanhnguyenduc.aquariux.crypto.trading.constant.ErrorCodes.CRYPTO_USER_NO_WALLET_OF_CURRENCY;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoWalletServiceImpl implements CryptoWalletService {

    private final CryptoWalletRepository cryptoWalletRepository;

    private final CryptoUserService cryptoUserService;

    private final CryptoWalletMapper cryptoWalletMapper;

    @Override
    @Transactional
    public void buyCurrencyToWallet(CryptoTransaction cryptoTransaction) throws CryptoTransactionException {
        CryptoUser cryptoUser = cryptoTransaction.getCryptoUser();
        String currencyName = cryptoTransaction.getCryptoCurrency();

        double remainingUSDTBalance = cryptoUser.getBalance();
        if (remainingUSDTBalance < cryptoTransaction.getTotal()) {
            log.error("User doesn't have sufficient balance to buy currency");
            throw new CryptoTransactionException(CRYPTO_USER_INSUFFICIENT_BALANCE_TO_BUY, cryptoUser.getUsername());
        }
        CryptoWallet cryptoWallet = cryptoWalletRepository.findByCryptoUserAndCryptoCurrency(cryptoUser, currencyName);
        if (cryptoWallet == null) {
            cryptoWallet = CryptoWallet
                    .builder()
                    .cryptoCurrency(currencyName)
                    .balance(cryptoTransaction.getAmount())
                    .cryptoUser(cryptoUser)
                    .build();
            cryptoWalletRepository.save(cryptoWallet);
        } else {
            updateWalletBalance(cryptoWallet, cryptoWallet.getBalance() + cryptoTransaction.getAmount());
        }
        cryptoUserService.updateUserBalance(cryptoUser, remainingUSDTBalance - cryptoTransaction.getTotal());
    }

    @Override
    @Transactional
    public void sellCurrencyFromWallet(CryptoTransaction cryptoTransaction) throws CryptoTransactionException {
        CryptoUser cryptoUser = cryptoTransaction.getCryptoUser();
        String currencyName = cryptoTransaction.getCryptoCurrency();

        CryptoWallet cryptoWallet = cryptoWalletRepository.findByCryptoUserAndCryptoCurrency(cryptoUser, currencyName);
        if (cryptoWallet == null) {
            log.error("User doesn't have a wallet of the given currency");
            throw new CryptoTransactionException(CRYPTO_USER_NO_WALLET_OF_CURRENCY, cryptoUser.getUsername(), currencyName);
        }
        double remainingCryptoBalance = cryptoWallet.getBalance();
        if (remainingCryptoBalance < cryptoTransaction.getAmount()) {
            log.error("User doesn't have sufficient crypto balance to sell");
            throw new CryptoTransactionException(CRYPTO_USER_INSUFFICIENT_CURRENCY_AMOUNT_TO_SELL, cryptoUser.getUsername(), currencyName);
        }
        updateWalletBalance(cryptoWallet, remainingCryptoBalance - cryptoTransaction.getAmount());
        cryptoUserService.updateUserBalance(cryptoUser, cryptoUser.getBalance() + cryptoTransaction.getTotal());
    }

    @Override
    @Transactional
    public void updateWalletBalance(CryptoWallet cryptoWallet, double updatedBalance) {
        if (cryptoWallet != null) {
            cryptoWallet.setBalance(updatedBalance);
            cryptoWalletRepository.save(cryptoWallet);
        }
    }

    @Override
    public CryptoUserBalanceResponseDTO getCryptoUserBalance(String username) {
        CryptoUser cryptoUser = cryptoUserService.findByUsername(username);
        if (cryptoUser == null) {
            log.error("Invalid Transaction - Provided User doesn't exist");
            throw new BaseException(ErrorCodes.CRYPTO_USER_NOT_FOUND, username);
        }
        List<CryptoWalletResponseDTO> cryptoWalletResponseDTOs = fetchUserCurrencyWalletDTOs(username);
        return CryptoUserBalanceResponseDTO.builder()
                .remainingBalance(cryptoUser.getBalance())
                .cryptoWallets(cryptoWalletResponseDTOs)
                .build();
    }

    @Override
    public List<CryptoWalletResponseDTO> fetchUserCurrencyWalletDTOs(String username) {
        CryptoUser cryptoUser = cryptoUserService.findByUsername(username);
        if (cryptoUser == null) {
            throw new BaseException(ErrorCodes.CRYPTO_USER_NOT_FOUND, username);
        }
        List<CryptoWallet> cryptoWallets = cryptoWalletRepository.findByCryptoUser(cryptoUser);
        return cryptoWallets.stream().map(cryptoWalletMapper::toCryptoWalletResponseDTO).toList();
    }
}
