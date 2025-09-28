package com.pogly.shortener_service.services;

import com.pogly.shortener_service.entities.UrlsEntity;
import com.pogly.shortener_service.repositories.UrlRepository;
import com.pogly.shortener_service.enums.TimeEnum;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

//TODO: Checar a url é segura antes de encurtar ela, para não salvar sites maliciosos. Provavelmente usar a API Do Google de safe browsing
@Service
public class ShortnerService {

    @Value("${BASE_URL}")
    private String baseUrl;

    private final UrlRepository urlRepository;

    private EntityManager entityManager;

    ShortnerService(UrlRepository urlRepository, EntityManager entityManager) {
        this.urlRepository = urlRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public String createShortUrl(String longUrl) {
        UrlsEntity url = new UrlsEntity();
        url.setLongUrl(longUrl);
        url.setExpiresAt(Timestamp.from(
                Instant.now().plusSeconds(TimeEnum.FIVE_YEARS.getTime())
        ));

        urlRepository.save(url);

        return baseUrl + "/" + url.getShortUrlSlug();
    }

    @Transactional
    public boolean hasDuplicateIds() {
        String sql = """
                    SELECT COUNT(id) - COUNT(DISTINCT id)
                    FROM urls
                """;

        Number duplicates = (Number) entityManager
                .createNativeQuery(sql)
                .getSingleResult();

        return duplicates.longValue() > 0;
    }

}
