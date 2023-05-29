package com.jun.plugin.hibernate.validator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jun.plugin.hibernate.validator.dto.Input;

import javax.validation.Valid;

@RestController()
@RequestMapping("/nest")
public class NestValidateController {

    @PostMapping("/validate")
    public ResponseEntity<String> validateNestingAttr(@Validated @RequestBody Input input) {
        return ResponseEntity.ok("valid");
    }
}
