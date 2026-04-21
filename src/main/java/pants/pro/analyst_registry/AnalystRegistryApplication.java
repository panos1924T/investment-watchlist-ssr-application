package pants.pro.analyst_registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AnalystRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalystRegistryApplication.class, args);
	}

}
