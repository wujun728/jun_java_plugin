package com.jujung.validator.controller;

import com.jujung.validator.dto.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

@RestController
public class ValidateRequestBodyController {

    @PostMapping("/validateBody")

    public ResponseEntity<String> validateBody(@Validated @RequestBody RequestParam param) {
        return ResponseEntity.ok("valid");
    }
}
