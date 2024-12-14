package com.vuanhnguyenduc.aquariux.crypto.trading.constant;

import lombok.Getter;

@Getter
public enum ErrorCodes {
    CRYPTO_USER_NOT_FOUND("001", "The user %s doesn't exist"),
    CURRENCY_NOT_SUPPORTED("002", "The currency %s is not supported"),
    CRYPTO_USER_INSUFFICIENT_BALANCE_TO_BUY("003", "User %s doesn't have sufficient balance to buy currency"),
    CRYPTO_USER_INSUFFICIENT_CURRENCY_AMOUNT_TO_SELL("004", "User %s doesn't have sufficient amount of %s to sell"),
    CRYPTO_USER_NO_WALLET_OF_CURRENCY("005", "User %s doesn't have a wallet of the given currency %s");

    private final String errorCode;
    private final String errorMessage;

    ErrorCodes(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
