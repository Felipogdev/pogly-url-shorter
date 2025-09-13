package com.pogly.redirect_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RedirectService {

    @Value("${BASE_URL}")
    private String baseUrl;

    private final DatabaseClient databaseClient;

    public RedirectService(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public Mono<String> redirectUrl(String slug) {
        Long id = decodeSlugToId(slug);
        return fetchUrl(id);
    }

    private Long decodeSlugToId(String slug) {
        String base62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        long id = 0;
        for (int i = 0; i < slug.length(); i++) {
            char c = slug.charAt(i);
            int value = base62.indexOf(c);
            if (value == -1) throw new IllegalArgumentException("Invalid slug character");
            id = id * 62 + value;
        }
        return id;
    }

    private Mono<String> fetchUrl(Long id) {
        return incrementClickColumn(id)
                .then(databaseClient.sql("SELECT long_url FROM urls WHERE id = :id")
                        .bind("id", id)
                        .map(row -> row.get("long_url", String.class))
                        .one());
    }

    private Mono<Void> incrementClickColumn(Long id) {
        return databaseClient.sql("UPDATE urls SET clicks = clicks + 1 WHERE id = :id")
                .bind("id", id)
                .then();
    }
}
