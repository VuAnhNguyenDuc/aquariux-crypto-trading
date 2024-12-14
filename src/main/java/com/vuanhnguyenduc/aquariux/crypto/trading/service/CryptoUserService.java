package com.vuanhnguyenduc.aquariux.crypto.trading.service;

import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoUser;

public interface CryptoUserService {
    CryptoUser findByUserId(Integer userId);

    void updateUserBalance(CryptoUser cryptoUser, double updatedBalance);

    CryptoUser findByUsername(String username);
}
