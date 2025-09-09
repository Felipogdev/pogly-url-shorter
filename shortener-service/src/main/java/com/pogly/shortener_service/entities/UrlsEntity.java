package com.pogly.shortener_service.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "urls")
public class UrlsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "long_url", nullable = false, length = 2048)
    private String longUrl;

    @Column(name = "short_url_slug", nullable = false, unique = true, length = 7)
    private String shortUrlSlug;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "expires_at")
    private Timestamp expiresAt;

    private Long clicks = 0L;

}
