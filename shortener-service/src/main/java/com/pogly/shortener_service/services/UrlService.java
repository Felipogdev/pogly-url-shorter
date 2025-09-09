package com.pogly.shortener_service.services;

import com.pogly.shortener_service.entities.UrlsEntity;
import com.pogly.shortener_service.repositories.UrlRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        url.setShortUrlSlug(createShortnerSlug(nextId));
        url.setLongUrl(longUrl);

        urlRepository.save(url);

        return longUrl + url.getShortUrlSlug();
    }


    private String createShortnerSlug(Long id) {
        StringBuilder slug = new StringBuilder();

        while (id > 0) {
            int remainder = (int)(id % 62);
            slug.append(BASE62.charAt(remainder));
            id /= 62;
        }

        return slug.reverse().toString();
    }
}
