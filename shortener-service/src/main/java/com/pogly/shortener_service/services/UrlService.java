package com.pogly.shortener_service.services;

import com.pogly.shortener_service.entities.UrlsEntity;
import com.pogly.shortener_service.repositories.UrlRepository;
import enums.TimeEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class UrlService {

    @Value("${BASE_URL}")
    private String baseUrl;

    @PersistenceContext
    private EntityManager entityManager;

    private UrlRepository urlRepository;

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";


    UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String createShortUrl(String longUrl) {
        UrlsEntity url = new UrlsEntity();

        Long nextId = ((Number) entityManager
                .createNativeQuery("SELECT nextval('url_seq')")
                .getSingleResult()).longValue();

        url.setId(nextId);
        url.setShortUrlSlug(generateShortnerSlug(nextId));
        url.setLongUrl(longUrl);
        url.setExpiresAt(Timestamp.from(Instant.now().plusSeconds(TimeEnum.FIVE_YEARS.getTime())));

        urlRepository.save(url);

        return baseUrl + "/" + url.getShortUrlSlug();
    }


    private String generateShortnerSlug(Long id) {
        StringBuilder slug = new StringBuilder();

        while (id > 0) {
            int remainder = (int)(id % 62);
            slug.append(BASE62.charAt(remainder));
            id /= 62;
        }

        return slug.reverse().toString();
    }
}
