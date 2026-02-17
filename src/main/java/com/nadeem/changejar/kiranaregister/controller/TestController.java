package com.nadeem.changejar.kiranaregister.controller;

import com.nadeem.changejar.kiranaregister.service.auth.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final CurrencyService currencyService;

    @GetMapping("/fxrate/{base}/{payment}")
    public ResponseEntity<?> getFxRate(@PathVariable String base, @PathVariable String payment){
        return new ResponseEntity<>(currencyService.getRate(base, payment), HttpStatus.OK);
    }
}
