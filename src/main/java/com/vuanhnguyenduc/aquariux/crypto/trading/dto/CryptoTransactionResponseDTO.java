package com.vuanhnguyenduc.aquariux.crypto.trading.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.TransactionStatus;
import com.vuanhnguyenduc.aquariux.crypto.trading.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CryptoTransactionResponseDTO {
    @JsonProperty(value = "transaction_action")
    private TransactionType transactionType;

    @JsonProperty(value = "crypto_currency")
    private String cryptoCurrency;

    @JsonProperty(value = "amount")
    private double amount;

    @JsonProperty(value = "total")
    private double total;

    @JsonProperty(value = "price")
    private double price;

    @JsonProperty(value = "transaction_status")
    private TransactionStatus transactionStatus;

    @JsonProperty(value = "error_info")
    private String errorInfo;

    @JsonProperty(value = "transaction_date")
    private Timestamp transactionDate;
}
