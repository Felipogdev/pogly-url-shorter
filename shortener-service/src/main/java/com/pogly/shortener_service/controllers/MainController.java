package com.pogly.shortener_service.controllers;


import com.pogly.shortener_service.dtos.RequestBodyDto;
import com.pogly.shortener_service.dtos.ResponseBodyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @PostMapping("/")
    public ResponseEntity<?> shortnerUrlEndpoint(
            @RequestBody RequestBodyDto dto
    ) {
        String longUrl = dto.longUrl();
        return ResponseEntity.ok(longUrl);
    }

}
