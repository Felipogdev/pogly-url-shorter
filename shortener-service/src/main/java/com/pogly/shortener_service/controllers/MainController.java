package com.pogly.shortener_service.controllers;


import com.pogly.shortener_service.dtos.RequestBodyDto;
import com.pogly.shortener_service.dtos.ResponseBodyDto;
import com.pogly.shortener_service.services.UrlService;
import org.aspectj.bridge.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private UrlService urlService;

    MainController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/api/urls/shorten")
    public ResponseEntity<?> shortnerUrlEndpoint(
            @RequestBody RequestBodyDto dto
    ) {
        String longUrl = dto.longUrl();
        return ResponseEntity.ok(urlService.createShortUrl(longUrl));
    }

}
