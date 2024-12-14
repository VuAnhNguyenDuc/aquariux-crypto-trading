package com.vuanhnguyenduc.aquariux.crypto.trading.repository;

import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoTransaction;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoTransactionRepository extends JpaRepository<CryptoTransaction, Integer> {
    List<CryptoTransaction> findByCryptoUser(CryptoUser cryptoUser);
}
