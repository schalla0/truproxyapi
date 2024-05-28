package com.test.truproxyapi.service;

import com.test.truproxyapi.model.Companies;
import com.test.truproxyapi.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static com.test.truproxyapi.util.RequestUtil.httpEntity;
import static com.test.truproxyapi.util.TestUtil.TEST_API_KEY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanySearchServiceTest {

    @InjectMocks
    private CompanySearchService sut;
    @Mock
    private RestTemplate resetTemplate;
    @Mock
    private OfficeSearchService officeSearchService;

    @Mock
    private CompanyRepository companyRepository;


    @Test
    public void searchCompanies() {
        ReflectionTestUtils.setField(sut, "companiesSearchUrl", "http://localhost");
        ResponseEntity<String> responseEntity = new ResponseEntity<>(mockCompaniesResponse(), HttpStatus.OK);
        String url = "http://localhost?Query=companies";
        when(resetTemplate.exchange(url,HttpMethod.GET,httpEntity(TEST_API_KEY), String.class)).thenReturn(responseEntity);
       // doNothing(). when(officeSearchService).searchOfficers(anyString(), any(Companies.class));
        Companies companies = sut.searchCompanies(TEST_API_KEY, true);
        assertNotNull(companies);
        assertThat(companies, is(notNullValue()));
        verify(resetTemplate, times(1)).exchange(url,HttpMethod.GET,httpEntity(TEST_API_KEY), String.class);
    }

    private String mockCompaniesResponse() {
       return "{\n" +
               "  \"page_number\": 1,\n" +
               "  \"kind\": \"search#companies\",\n" +
               "  \"total_results\": 20,\n" +
               "  \"items\": [\n" +
               "    {\n" +
               "      \"company_status\": \"dissolved\",\n" +
               "      \"address_snippet\": \"9 Heads Mount, Keswick, United Kingdom, CA12 5EY\",\n" +
               "      \"date_of_creation\": \"2021-03-19\",\n" +
               "      \"matches\": {\n" +
               "        \"title\": [\n" +
               "          1,\n" +
               "          9\n" +
               "        ]\n" +
               "      },\n" +
               "      \"description\": \"13277187 - Dissolved on 30 August 2022\",\n" +
               "      \"links\": {\n" +
               "        \"self\": \"/company/13277187\"\n" +
               "      },\n" +
               "      \"company_number\": \"13277187\",\n" +
               "      \"title\": \"COMPANIES LIMITED\",\n" +
               "      \"company_type\": \"private-limited-guarant-nsc\",\n" +
               "      \"address\": {\n" +
               "        \"premises\": \"9\",\n" +
               "        \"postal_code\": \"CA12 5EY\",\n" +
               "        \"country\": \"United Kingdom\",\n" +
               "        \"locality\": \"Keswick\",\n" +
               "        \"address_line_1\": \"Heads Mount\"\n" +
               "      },\n" +
               "      \"kind\": \"searchresults#company\",\n" +
               "      \"description_identifier\": [\n" +
               "        \"dissolved-on\"\n" +
               "      ],\n" +
               "      \"date_of_cessation\": \"2022-08-30\"\n" +
               "    }\n" +
               "  ]\n" +
               "}";
    }
}