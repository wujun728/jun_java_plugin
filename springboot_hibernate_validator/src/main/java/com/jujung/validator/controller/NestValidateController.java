package com.jujung.validator.controller;

import com.jujung.validator.dto.Input;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/nest")
public class NestValidateController {

    @PostMapping("/validate")
    public ResponseEntity<String> validateNestingAttr(@Validated @RequestBody Input input) {
        return ResponseEntity.ok("valid");
    }
}
