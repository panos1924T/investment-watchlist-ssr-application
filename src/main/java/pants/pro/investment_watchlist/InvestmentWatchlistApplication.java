package pants.pro.investment_watchlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InvestmentWatchlistApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestmentWatchlistApplication.class, args);
	}

}
