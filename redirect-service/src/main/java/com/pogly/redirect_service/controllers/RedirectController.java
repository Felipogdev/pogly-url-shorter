package com.pogly.redirect_service.controllers;

import com.pogly.redirect_service.services.RedirectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/urls")
public class RedirectController {

    private final RedirectService redirectService;

    public RedirectController(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    @GetMapping("/{slug}")
    public Mono<ResponseEntity<Object>> redirect(@PathVariable String slug) {
        return redirectService.redirectUrl(slug)
                .map(longUrl -> ResponseEntity.status(HttpStatus.FOUND)
                        .header("Location", longUrl)
                        .build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
