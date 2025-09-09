package com.pogly.shortener_service.repositories;

import com.pogly.shortener_service.entities.UrlsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Long, UrlsEntity> {
}
