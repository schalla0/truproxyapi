package com.test.truproxyapi;

import com.test.truproxyapi.model.CompanyInfo;
import com.test.truproxyapi.repository.CompanyRepository;
import com.test.truproxyapi.util.UtilityHelpler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@EnableJpaRepositories
public class TruProxyApiApplication {

	@Autowired
	private CompanyRepository companyRepository;

	public static void main(String[] args) {
		SpringApplication.run(TruProxyApiApplication.class, args);
	}

	@Bean(name="restTemplate")
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


	//@Bean
	CommandLineRunner runner() {
		return args -> {
			List<CompanyInfo> companyInfos = companyRepository.findAll();
			if (companyInfos.isEmpty()) {
				companyRepository.saveAll(UtilityHelpler.companyInfoSupplier.get());
			}
		};
	}
}
