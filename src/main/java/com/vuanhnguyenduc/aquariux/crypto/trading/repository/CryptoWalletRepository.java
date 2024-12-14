package com.vuanhnguyenduc.aquariux.crypto.trading.repository;

import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoUser;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoWalletRepository extends JpaRepository<CryptoWallet, Integer> {
    CryptoWallet findByCryptoUserAndCryptoCurrency(CryptoUser cryptoUser, String currencyName);
    List<CryptoWallet> findByCryptoUser(CryptoUser cryptoUser);
}
