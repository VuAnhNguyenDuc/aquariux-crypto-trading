package com.vuanhnguyenduc.aquariux.crypto.trading.repository;

import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoUserRepository extends JpaRepository<CryptoUser, Integer> {
    CryptoUser findByUsername(String username);
}
