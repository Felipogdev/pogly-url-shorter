package com.pogly.shortener_service.repositories;

import com.pogly.shortener_service.entities.UrlsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<UrlsEntity, Long> {
}
