package com.test.truproxyapi.controller;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.test.truproxyapi.model.CompanySearch;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class TruProxyControllerTest {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebTestClient wtc;


    @Test
    void test() throws Exception {
        this.restTemplate = new RestTemplate();
        WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration
                .wireMockConfig()
                .dynamicPort());
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
       // configureFor("localhost", 8080);
        // given:
        String body = "{\"companyName\" : \"test\",\"companyNumber\": \"14372657\"}";

        // when:
        WireMock.stubFor(post(urlEqualTo("/api/v1/company/search"))
                .withRequestBody(matchingJsonPath(body))
                        .withHeader("x-api-key",containing("test"))
                        .withQueryParam("active", containing("true"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(response())
                        .withStatus(OK.value())));


        // then:
        String url =  String.format("http://localhost:%d/api/v1/company/search?active=true", wireMockServer.port());
              ResponseEntity<String> test = restTemplate.exchange(url, HttpMethod.POST, httpEntity("test"), String.class);
        String body1 = test.getBody();
        CompanySearch companySearch = new CompanySearch();
        companySearch.setCompanyNumber("15510777");
        companySearch.setCompanyName("test");
      //  ResponseEntity<Companies> response = (ResponseEntity<Companies>) restTemplate.searchCompany(true,"test",companySearch);
       // assertThat(response).isEqualTo(response);




        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);
        request.addHeader("Content-Type", "application/json");
        request.addHeader("x-api-key","test");
        String body2 = "{\"companyName\" : \"test\",\"companyNumber\": \"14372657\"}";
        CompanySearch companySearch1 = new CompanySearch();
        companySearch1.setCompanyName("test");
        companySearch1.setCompanyNumber("14372657");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", "test");
        org.apache.http.HttpEntity httpEntity = new StringEntity(body2);

        request.setEntity(httpEntity );
        HttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());
        response.getEntity();
    }

    public static HttpEntity<String> httpEntity(String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", apiKey);
        CompanySearch companySearch = new CompanySearch();
        companySearch.setCompanyNumber("14372657");
        companySearch.setCompanyName("test");
        String body = "{\"companyName\" : \"test\",\"companyNumber\": \"14372657\"}";
        HttpEntity<String> voidHttpEntity = new HttpEntity<>(body,headers);
        return voidHttpEntity;
    }

    private String response() {
        return "{\n" +
                "    \"total_results\": 1,\n" +
                "    \"items\": [\n" +
                "        {\n" +
                "            \"company_number\": \"14372657\",\n" +
                "            \"company_type\": \"ltd\",\n" +
                "            \"title\": \"COLLEGE OF CONSTRUCTION AND REAL ESTATE MANAGEMENT LTD\",\n" +
                "            \"company_status\": \"active\",\n" +
                "            \"date_of_creation\": \"2022-09-23\",\n" +
                "            \"address\": {\n" +
                "                \"locality\": \"London\",\n" +
                "                \"postal_code\": \"N1 7GU\",\n" +
                "                \"premises\": \"20-22?\",\n" +
                "                \"address_line1\": \"Wenlock Road\",\n" +
                "                \"country\": \"England\"\n" +
                "            },\n" +
                "            \"officers\": [\n" +
                "                {\n" +
                "                    \"name\": \"AHMED, Asmaa Elsayed\",\n" +
                "                    \"officer_role\": \"director\",\n" +
                "                    \"appointed_on\": \"2022-09-23\",\n" +
                "                    \"address\": {\n" +
                "                        \"locality\": \"London\",\n" +
                "                        \"postal_code\": \"N1 7GU\",\n" +
                "                        \"premises\": \"20-22\",\n" +
                "                        \"address_line1\": \"Wenlock Road\",\n" +
                "                        \"country\": \"England\"\n" +
                "                    }\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }
}