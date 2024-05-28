package com.test.truproxyapi.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

public class RequestUtil {

    public static  HttpEntity<Void> httpEntity(String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", apiKey);
        return new HttpEntity<>(headers);
    }

    public static String appendUriQueryParam(String url, String key, String value) {
        return UriComponentsBuilder
                .fromUriString(url)
                .queryParam(key, value)
                .build()
                .encode()
                .toUriString();
    }
}
