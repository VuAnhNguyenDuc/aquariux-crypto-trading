package com.vuanhnguyenduc.aquariux.crypto.trading.service.impl;

import com.vuanhnguyenduc.aquariux.crypto.trading.config.CryptoProperties;
import com.vuanhnguyenduc.aquariux.crypto.trading.constant.ErrorCodes;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoTransactionRequestDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoTransactionResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.exception.BaseException;
import com.vuanhnguyenduc.aquariux.crypto.trading.exception.CryptoTransactionException;
import com.vuanhnguyenduc.aquariux.crypto.trading.mapper.CryptoTransactionMapper;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoCurrency;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoTransaction;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoUser;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.TransactionStatus;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.TransactionType;
import com.vuanhnguyenduc.aquariux.crypto.trading.repository.CryptoTransactionRepository;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoCurrencyService;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoTransactionService;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoUserService;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoWalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoTransactionServiceImpl implements CryptoTransactionService {

    private final CryptoTransactionRepository cryptoTransactionRepository;

    private final CryptoCurrencyService cryptoCurrencyService;
    private final CryptoUserService cryptoUserService;

    private final CryptoWalletService cryptoWalletService;

    private final CryptoProperties cryptoProperties;

    private final CryptoTransactionMapper cryptoTransactionMapper;

    @Override
    @Transactional
    public CryptoTransactionResponseDTO executeTransaction(CryptoTransactionRequestDTO cryptoTransactionRequestDTO) {
        CryptoTransaction registeredTransaction = null;
        try {
            registeredTransaction = registerTransaction(cryptoTransactionRequestDTO);
            CryptoUser cryptoUser = cryptoUserService.findByUsername(cryptoTransactionRequestDTO.getUsername());
            if (cryptoUser == null) {
                log.error("Invalid Transaction - Provided User doesn't exist");
                throw new CryptoTransactionException(ErrorCodes.CRYPTO_USER_NOT_FOUND, cryptoTransactionRequestDTO.getUsername());
            }
            registeredTransaction.setCryptoUser(cryptoUser);
            String currencyName = cryptoTransactionRequestDTO.getCryptoCurrency().toUpperCase();
            CryptoCurrency cryptoCurrency = cryptoCurrencyService.findCurrencyByName(currencyName);
            TransactionType transactionAction = cryptoTransactionRequestDTO.getTransactionType();
            double total;
            if (transactionAction.equals(TransactionType.BUY)) {
                total = cryptoCurrency.getAskPrice() * cryptoTransactionRequestDTO.getAmount();
                updateTransactionDetails(registeredTransaction, cryptoCurrency.getAskPrice(), total);
                cryptoWalletService.buyCurrencyToWallet(registeredTransaction);
            } else {
                total = cryptoCurrency.getBidPrice() * cryptoTransactionRequestDTO.getAmount();
                updateTransactionDetails(registeredTransaction, cryptoCurrency.getBidPrice(), total);
                cryptoWalletService.sellCurrencyFromWallet(registeredTransaction);
            }
            updateTransactionStatus(registeredTransaction, TransactionStatus.SUCCEEDED);
            return cryptoTransactionMapper.toCryptoTransactionResponseDTO(registeredTransaction);
        } catch (CryptoTransactionException e) {
            updateTransactionWithException(registeredTransaction, e.getErrorMessage());
            return cryptoTransactionMapper.toCryptoTransactionResponseDTO(registeredTransaction);
        }
    }
    @Override
    @Transactional
    public CryptoTransaction registerTransaction(CryptoTransactionRequestDTO cryptoTransactionRequestDTO) throws CryptoTransactionException {
        String currencyName = cryptoTransactionRequestDTO.getCryptoCurrency().toUpperCase();
        if (!cryptoProperties.getSupportedCurrencies().contains(currencyName)) {
            log.error("Invalid Transaction - Currency {} is not supported", currencyName);
            throw new CryptoTransactionException(ErrorCodes.CURRENCY_NOT_SUPPORTED, currencyName);
        }
        CryptoTransaction cryptoTransaction = CryptoTransaction
                .builder()
                .transactionType(cryptoTransactionRequestDTO.getTransactionType())
                .transactionStatus(TransactionStatus.CREATED)
                .cryptoCurrency(currencyName)
                .amount(cryptoTransactionRequestDTO.getAmount())
                .build();
        return cryptoTransactionRepository.save(cryptoTransaction);
    }

    @Override
    @Transactional
    public void updateTransactionDetails(CryptoTransaction cryptoTransaction, double price, double total) {
        if (cryptoTransaction != null) {
            cryptoTransaction.setPrice(price);
            cryptoTransaction.setTotal(total);
            cryptoTransactionRepository.save(cryptoTransaction);
        }
    }

    @Override
    @Transactional
    public void updateTransactionStatus(CryptoTransaction cryptoTransaction, TransactionStatus status) {
        if (cryptoTransaction != null) {
            cryptoTransaction.setTransactionStatus(status);
            cryptoTransactionRepository.save(cryptoTransaction);
        }
    }

    @Override
    @Transactional
    public void updateTransactionWithException(CryptoTransaction cryptoTransaction, String errorMessage) {
        if (cryptoTransaction != null) {
            cryptoTransaction.setErrorInfo(errorMessage);
            cryptoTransaction.setTransactionStatus(TransactionStatus.FAILED);
            cryptoTransactionRepository.save(cryptoTransaction);
        }
    }

    @Override
    public List<CryptoTransactionResponseDTO> fetchTransactionHistory(String username) {
        CryptoUser cryptoUser = cryptoUserService.findByUsername(username);
        if (cryptoUser == null) {
            throw new BaseException(ErrorCodes.CRYPTO_USER_NOT_FOUND, username);
        }
        List<CryptoTransaction> cryptoTransactions = cryptoTransactionRepository.findByCryptoUser(cryptoUser);
        return cryptoTransactions.stream().map(cryptoTransactionMapper::toCryptoTransactionResponseDTO).toList();
    }
}
