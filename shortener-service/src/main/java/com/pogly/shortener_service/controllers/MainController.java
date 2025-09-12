package com.pogly.shortener_service.controllers;


import com.pogly.shortener_service.dtos.RequestBodyDto;
import com.pogly.shortener_service.services.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private UrlService urlService;

    MainController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/api/urls/duplicate")
    public ResponseEntity<?> seeDuplicates() {
        return ResponseEntity.ok(urlService.hasDuplicateIds());
    }

    @PostMapping("/api/urls/shorten")
    public ResponseEntity<?> shortnerUrlEndpoint(
            @RequestBody RequestBodyDto dto
    ) {
        String longUrl = dto.longUrl();
        return ResponseEntity.ok(urlService.createShortUrl(longUrl));
    }

}
