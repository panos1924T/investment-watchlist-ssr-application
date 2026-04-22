package pants.pro.analyst_registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
/**
 * Spring Boot entry point for the Analyst Registry application and JPA auditing setup.
 */
public class AnalystRegistryApplication {

	/**
	 * Starts the application.
	 * @param args args value.
	 */
	public static void main(String[] args) {
		SpringApplication.run(AnalystRegistryApplication.class, args);
	}

}
