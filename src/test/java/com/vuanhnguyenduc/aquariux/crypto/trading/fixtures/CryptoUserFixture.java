package com.vuanhnguyenduc.aquariux.crypto.trading.fixtures;

import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoUser;

public class CryptoUserFixture {
    public static CryptoUser givenCryptoUser() {
        return CryptoUser
                .builder()
                .userId(1)
                .username("user1")
                .balance(50000)
                .build();
    }
}
