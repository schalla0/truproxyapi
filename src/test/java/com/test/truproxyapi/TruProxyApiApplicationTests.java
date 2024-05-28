package com.test.truproxyapi;

import com.test.truproxyapi.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class TruProxyApiApplicationTests {

	@Mock
	private CompanyRepository companyRepository;

	@Mock
	private RestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

}
