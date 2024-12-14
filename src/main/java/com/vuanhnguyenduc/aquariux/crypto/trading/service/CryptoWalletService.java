package com.vuanhnguyenduc.aquariux.crypto.trading.service;

import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoUserBalanceResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoWalletResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.exception.CryptoTransactionException;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoTransaction;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoWallet;

import java.util.List;

public interface CryptoWalletService {

    void buyCurrencyToWallet(CryptoTransaction cryptoTransaction) throws CryptoTransactionException;

    void sellCurrencyFromWallet(CryptoTransaction cryptoTransaction) throws CryptoTransactionException;

    void updateWalletBalance(CryptoWallet cryptoWallet, double updatedBalance);

    CryptoUserBalanceResponseDTO getCryptoUserBalance(String username);
    List<CryptoWalletResponseDTO> fetchUserCurrencyWalletDTOs(String username);
}
