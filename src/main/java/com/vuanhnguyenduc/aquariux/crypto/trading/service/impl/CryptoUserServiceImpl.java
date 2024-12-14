package com.vuanhnguyenduc.aquariux.crypto.trading.service.impl;

import com.vuanhnguyenduc.aquariux.crypto.trading.constant.ErrorCodes;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoTransactionResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.dto.CryptoWalletResponseDTO;
import com.vuanhnguyenduc.aquariux.crypto.trading.exception.BaseException;
import com.vuanhnguyenduc.aquariux.crypto.trading.mapper.CryptoTransactionMapper;
import com.vuanhnguyenduc.aquariux.crypto.trading.mapper.CryptoWalletMapper;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoTransaction;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoUser;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.CryptoWallet;
import com.vuanhnguyenduc.aquariux.crypto.trading.repository.CryptoUserRepository;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoUserServiceImpl implements CryptoUserService {

    private final CryptoUserRepository cryptoUserRepository;

    @Override
    @Transactional
    public CryptoUser findByUserId(Integer userId) {
        return cryptoUserRepository.findById(userId).orElse(null);
    }

    @Override
    @Transactional
    public void updateUserBalance(CryptoUser cryptoUser, double updatedBalance) {
        if (cryptoUser != null) {
            cryptoUser.setBalance(updatedBalance);
            cryptoUserRepository.save(cryptoUser);
        }
    }

    @Override
    @Transactional
    public CryptoUser findByUsername(String username) {
        return cryptoUserRepository.findByUsername(username);
    }
}
