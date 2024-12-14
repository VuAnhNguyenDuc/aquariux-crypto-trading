package com.vuanhnguyenduc.aquariux.crypto.trading.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "huobi")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HuobiProperties {
    private String url;
}
