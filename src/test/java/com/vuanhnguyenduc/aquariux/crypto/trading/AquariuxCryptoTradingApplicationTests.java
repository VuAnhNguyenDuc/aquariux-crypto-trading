package com.vuanhnguyenduc.aquariux.crypto.trading;

import com.vuanhnguyenduc.aquariux.crypto.trading.mapper.CryptoCurrencyMapperTest;
import com.vuanhnguyenduc.aquariux.crypto.trading.mapper.CryptoTransactionMapperTest;
import com.vuanhnguyenduc.aquariux.crypto.trading.mapper.CryptoWalletMapperTest;
import com.vuanhnguyenduc.aquariux.crypto.trading.scheduler.PriceAggregationSchedulerTest;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoCurrencyServiceTest;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoTransactionServiceTest;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoUserServiceTest;
import com.vuanhnguyenduc.aquariux.crypto.trading.service.CryptoWalletServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
		CryptoCurrencyMapperTest.class,
		CryptoTransactionMapperTest.class,
		CryptoWalletMapperTest.class,

		PriceAggregationSchedulerTest.class,

		CryptoCurrencyServiceTest.class,
		CryptoTransactionServiceTest.class,
		CryptoUserServiceTest.class,
		CryptoWalletServiceTest.class
})
class AquariuxCryptoTradingApplicationTests {

	@Test
	void contextLoads() {
	}

}
