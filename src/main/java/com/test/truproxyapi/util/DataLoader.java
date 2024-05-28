package com.test.truproxyapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.truproxyapi.exception.JsonResultsProcessingException;
import com.test.truproxyapi.model.Address;
import com.test.truproxyapi.model.Companies;
import com.test.truproxyapi.model.Company;
import com.test.truproxyapi.model.Officer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataLoader {

    public static Companies buildCompanies(String jsonResults, boolean active) {
        List<Company> results;
        results = DataLoader.buildCompaniesDetails(jsonResults, active);
        return Companies
                .builder()
                .total_results(results.size())
                .items(results).build();
    }

    public static  List<Officer> buildOfficer(String jsonResults)  {
        List<Officer> officers = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = readJsonResults(objectMapper, jsonResults);
        JsonNode items = getItems(json);
        if (items != null) {
            for (JsonNode item : items) {
                String resigned_on = getFieldValue(items, "resigned_on");
                JsonNode jsonNodeAddress = item.get("address");
                if (resigned_on.isBlank() && jsonNodeAddress != null) {
                    Officer officer = Officer.builder()
                            .name(getFieldValue(item, "name"))
                            .officer_role(getFieldValue(item, "officer_role"))
                            .appointed_on(getFieldValue(item, "appointed_on"))
                            .address(buildAddress(jsonNodeAddress))
                            .build();
                    officers.add(officer);
                }
            }
        }
        return officers;
    }

    private static  List<Company> buildCompaniesDetails(String jsonResults, boolean active) {
        List<Company> companies = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = readJsonResults(objectMapper, jsonResults);
        JsonNode items = getItems(json);
        for (JsonNode item : items) {
            JsonNode jsonNodeAddress = item.get("address");
            String status = getFieldValue(item, "company_status");
            boolean isActive = active ?  "active".equals(status) : !active;
            if(isActive) {
                Company company = Company
                        .builder()
                        .company_number(getFieldValue(item, "company_number"))
                        .title(getFieldValue(item, "title"))
                        .company_type(getFieldValue(item, "company_type"))
                        .company_status(getFieldValue(item, "company_status"))
                        .date_of_creation(getFieldValue(item, "date_of_creation"))
                        .address(buildAddress(jsonNodeAddress))
                        .build();
                companies.add(company);
            }
        }
        return companies;
    }

   private static Address buildAddress(JsonNode jsonNodeAddress) {
      return   Address.builder()
                .address_line1(getFieldValue(jsonNodeAddress, "address_line_1"))
                .premises(getFieldValue(jsonNodeAddress, "premises"))
                .country(getFieldValue(jsonNodeAddress, "country"))
                .locality(getFieldValue(jsonNodeAddress, "locality"))
                .postal_code(getFieldValue(jsonNodeAddress, "postal_code"))
                .build();
    }

    private static String getFieldValue(JsonNode node, String fieldName) {
        JsonNode jsonNode = node.get(fieldName);
        return jsonNode != null ? jsonNode.asText() : "";
    }

    private static JsonNode getItems(JsonNode json) {
        Optional<JsonNode> items = Optional.ofNullable(json)
                .map(j -> j.get("items"));
        return items.isPresent() ? items.get() : null;

    }

    private static JsonNode readJsonResults(ObjectMapper objectMapper, String jsonResults) {
        JsonNode json;
        try {
            json = objectMapper.readValue(jsonResults, JsonNode.class);
        } catch (JsonProcessingException e) {
            throw new JsonResultsProcessingException("Error processing jsonResults", e);
        }
        return json;
    }
}

