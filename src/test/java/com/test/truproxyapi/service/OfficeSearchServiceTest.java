package com.test.truproxyapi.service;

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

import static com.test.truproxyapi.util.TestUtil.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfficeSearchServiceTest {

    @InjectMocks
    private OfficeSearchService sut;

    @Mock
    private RestTemplate resetTemplate;

    @Test
    public void searchOfficers() {
        ReflectionTestUtils.setField(sut, "officersSearchUrl", "http://localhost");
        ResponseEntity<String> responseEntity = new ResponseEntity<>(mockOfficerResponse(), HttpStatus.OK);
        String url = "http://localhost?CompanyNumber=123";
        when(resetTemplate.exchange(url,HttpMethod.GET,httpEntity(TEST_API_KEY), String.class)).thenReturn(responseEntity);
        sut.searchOfficers(TEST_API_KEY,mockCompanies());
        verify(resetTemplate, times(1)).exchange(url,HttpMethod.GET,httpEntity(TEST_API_KEY), String.class);
    }




    private String mockOfficerResponse() {
        return "{\n" +
                "    \"etag\": \"15bb9b5a7a1a23136c72d328eeae1b3d4fa88e3e\",\n" +
                "    \"inactive_count\": 1,\n" +
                "    \"links\": {\n" +
                "        \"self\": \"/company/13277187/officers\"\n" +
                "    },\n" +
                "    \"kind\": \"officer-list\",\n" +
                "    \"items_per_page\": 35,\n" +
                "    \"items\": [\n" +
                "        {\n" +
                "            \"address\": {\n" +
                "                \"premises\": \"9\",\n" +
                "                \"postal_code\": \"CA12 5EY\",\n" +
                "                \"country\": \"United Kingdom\",\n" +
                "                \"locality\": \"Keswick\",\n" +
                "                \"address_line_1\": \"Heads Mount\"\n" +
                "            },\n" +
                "            \"name\": \"BURTON, James Harold\",\n" +
                "            \"appointed_on\": \"2021-03-19\",\n" +
                "            \"officer_role\": \"director\",\n" +
                "            \"links\": {\n" +
                "                \"officer\": {\n" +
                "                    \"appointments\": \"/officers/_h--z4gyiskuBn7HWKkkoeU4y5k/appointments\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"date_of_birth\": {\n" +
                "                \"month\": 10,\n" +
                "                \"year\": 1992\n" +
                "            },\n" +
                "            \"occupation\": \"Director\",\n" +
                "            \"country_of_residence\": \"United Kingdom\",\n" +
                "            \"nationality\": \"British\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"total_results\": 1\n" +
                "}";
    }
}