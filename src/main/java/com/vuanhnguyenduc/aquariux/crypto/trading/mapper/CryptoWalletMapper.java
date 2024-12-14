package com.vuanhnguyenduc.aquariux.crypto.trading.mapper;

import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoWalletResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoWallet;
import org.springframework.stereotype.Component;

@Component
public class CryptoWalletMapper {
    public CryptoWalletResponseDTO toCryptoWalletResponseDTO(CryptoWallet cryptoWallet) {
        return CryptoWalletResponseDTO.builder()
                .cryptoCurrency(cryptoWallet.getCryptoCurrency())
                .balance(cryptoWallet.getBalance())
                .build();
    }
}
