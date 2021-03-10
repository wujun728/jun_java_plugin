package com.jujung.validator.controller;

import com.jujung.validator.annotation.EnumValue;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/def")
@Validated // 这个注解一定不能忘
public class DefinationValidator {

    @GetMapping("/save")
    public ResponseEntity<String> save(
            @EnumValue(value = {"F","M"},message = "性别只能传F,M")
            @RequestParam("gender") String gender,
            @EnumValue(value = {"1","2","3","4","5","6","7"},message = "星期只能传1-7")
            @RequestParam("week") String week
    ) {
        return ResponseEntity.ok("valid");
    }
}
