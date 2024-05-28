package com.test.truproxyapi.service;

import com.test.truproxyapi.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.test.truproxyapi.util.TestUtil.mockCompanies;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TruProxyServiceTest {

    @InjectMocks
    private TruProxyService sut;
    private static String TEST_API_KEY = "testKey";
    @Mock
    private CompanySearchService companySearchService;
    private RestTemplate resetTemplate;

    @Value("${officers.search.url}")
    private String officersSearchUrl;

    @Test
    public void searchCompanies()  {
        CompanySearch customerSearch = new CompanySearch();
        customerSearch.setCompanyNumber("123");
        when(companySearchService.searchCompanies(TEST_API_KEY, true)).thenReturn(mockCompanies());
        Companies companies = sut.searchCompanies(TEST_API_KEY, customerSearch, true);
        assertNotNull(companies);
        assertThat(companies, is(notNullValue()));
        assertThat(companies.getItems(), hasSize(1));
        assertThat(companies.getItems().get(0).getOfficers(), hasSize(1));
    }
}