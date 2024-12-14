package com.vuanhnguyenduc.aquariux.crypto.trading.repository;

import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Integer> {
    CryptoCurrency findByCurrencyName(String currencyName);
}
