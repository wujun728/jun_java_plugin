package com.jujung.validator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@RestController
@Validated // 告诉Spring校验方法参数上的约束
public class ValidateParametersController {

    /**
     * @param id
     * @return
     */
    @GetMapping("/validatePathVariable/{id}")
    public ResponseEntity<String> validatePathVariable(
            @PathVariable("id") @Min(value = 5,message = "id不能小于5") Integer id,
            @Valid @RequestParam("email") @Email(message = "邮箱格式不对") String email
    ) {
        return ResponseEntity.ok("valid");
    }

    @GetMapping("/validate/string")
    public ResponseEntity<String> validateString(@NotEmpty @RequestParam("name")String name) {
        return ResponseEntity.ok("valid");
    }
}
