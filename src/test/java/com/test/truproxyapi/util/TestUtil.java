package com.test.truproxyapi.util;

import com.test.truproxyapi.model.Address;
import com.test.truproxyapi.model.Companies;
import com.test.truproxyapi.model.Company;
import com.test.truproxyapi.model.Officer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

public class TestUtil {

    public static String TEST_API_KEY = "testKey";

    public static Companies mockCompanies() {
        Address address = Address.builder()
                .premises("1")
                .postal_code("TestPostCode")
                .country("United Kingdom")
                .build();
        Company company = Company.builder()
                .company_number("123")
                .title("TEST COMPANY LTD")
                .company_status("active")
                .company_type("ltd")
                .date_of_creation("2023-09-23")
                .address(address)
                .officers(List.of(Officer
                        .builder()
                        .officer_role("director")
                        .address(address)
                        .build()))
                .build();
        return Companies.builder().total_results(1).items(List.of(company)).build();
    }

    public static HttpEntity<Void> httpEntity(String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", apiKey);
        return new HttpEntity<>(headers);
    }
}
