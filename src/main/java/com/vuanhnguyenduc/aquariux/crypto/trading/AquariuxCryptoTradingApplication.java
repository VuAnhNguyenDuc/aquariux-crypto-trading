package com.vuanhnguyenduc.aquariux.crypto.trading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.vuanhnguyenduc.aquariux.crypto.trading")
@EnableScheduling
public class AquariuxCryptoTradingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AquariuxCryptoTradingApplication.class, args);
	}

}