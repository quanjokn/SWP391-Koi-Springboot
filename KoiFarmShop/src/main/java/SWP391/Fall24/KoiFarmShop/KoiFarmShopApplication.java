package SWP391.Fall24.KoiFarmShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

@ComponentScan({"SWP391.Fall24.controller", "SWP391.Fall24.service"})
@EnableJpaRepositories(basePackages = "SWP391.Fall24.repository")
@EntityScan(basePackages = "SWP391.Fall24.pojo")
public class KoiFarmShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoiFarmShopApplication.class, args);
	}

}
