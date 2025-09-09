package com.pogly.shortener_service.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "urls")
@Setter
@Getter
public class UrlsEntity {

    @Id
    @SequenceGenerator(name = "url_seq", sequenceName = "url_seq", allocationSize = 1)
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

    @Column(name = "is_expired", nullable = false)
    private boolean isExpired = false;

}
