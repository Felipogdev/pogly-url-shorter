package com.pogly.redirect_service.services;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RedirectService {

    @Value("${BASE_URL}")
    private String baseUrl;

    private final EntityManager entityManager;

    public RedirectService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String redirectUrl(String slug) {
        Long id = decodeSlugToId(slug);

        String sql = "SELECT long_url FROM urls WHERE id = :id";

        Object result = entityManager.createNativeQuery(sql)
                .setParameter("id", id)
                .getSingleResult();

        return result != null ? result.toString() : null;
    }

    private Long decodeSlugToId(String slug) {
        String base62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        long id = 0;
        for (int i = 0; i < slug.length(); i++) {
            char c = slug.charAt(i);
            int value = base62.indexOf(c);
            if (value == -1) throw new IllegalArgumentException("Caractere inválido no slug");
            id = id * 62 + value;
        }
        return id;
    }
}
