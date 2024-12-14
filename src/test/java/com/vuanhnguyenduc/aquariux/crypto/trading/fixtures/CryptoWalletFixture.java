package com.vuanhnguyenduc.aquariux.crypto.trading.fixtures;

import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoWalletResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoWallet;

import static com.vuanhnguyenduc.aquariux.crypto.trading.fixtures.CryptoUserFixture.givenCryptoUser;

public class CryptoWalletFixture {
    public static CryptoWallet givenCryptoWallet() {
        return CryptoWallet.builder()
                .walletId(1)
                .cryptoCurrency("BTCUSDT")
                .cryptoUser(givenCryptoUser())
                .balance(1)
                .build();
    }

    public static CryptoWalletResponseDTO givenCryptoWalletResponseDTO() {
        return CryptoWalletResponseDTO.builder()
                .cryptoCurrency("BTCUSDT")
                .balance(1)
                .build();
    }
}
