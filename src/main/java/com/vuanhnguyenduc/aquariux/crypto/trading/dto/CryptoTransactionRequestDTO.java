package com.vuanhnguyenduc.aquariux.crypto.trading.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CryptoTransactionRequestDTO {
    @JsonProperty(value = "transaction_type")
    private TransactionType transactionType;

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "crypto_currency")
    private String cryptoCurrency;

    @JsonProperty(value = "amount")
    private double amount;
}
