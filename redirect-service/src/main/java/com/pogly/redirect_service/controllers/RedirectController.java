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
    public Mono<ResponseEntity<Void>> redirect(@PathVariable String slug) {
        return Mono.fromCallable(() -> {
            String longUrl = redirectService.redirectUrl(slug);
            if (longUrl == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", longUrl)
                    .build();
        });
    }
}
