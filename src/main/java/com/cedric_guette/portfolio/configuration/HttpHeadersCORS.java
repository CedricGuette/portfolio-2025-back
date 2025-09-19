package com.cedric_guette.portfolio.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

public class HttpHeadersCORS {

    @Value("${app.url-front}")
    private String urlFront;

    public HttpHeaders headers() {
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.set("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token");
        httpHeader.set("Access-Control-Allow-Origin",urlFront);
        httpHeader.set("Access-Control-Allow-Methods", "DELETE,GET,HEAD,OPTIONS,PUT,POST,PATCH");

        return httpHeader;
    }
}
