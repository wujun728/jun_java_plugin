package com.jun.plugin.hibernate.validator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jun.plugin.hibernate.validator.dto.RequestParam;

import javax.validation.Valid;

@RestController
public class ValidateRequestBodyController {

    @PostMapping("/validateBody")

    public ResponseEntity<String> validateBody(@Validated @RequestBody RequestParam param) {
        return ResponseEntity.ok("valid");
    }
}
