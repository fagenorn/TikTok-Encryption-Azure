package com.betroix.tiktok.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class TempController {

    @GetMapping("/")
    public String home() {
        return "";
    }

    @GetMapping("/loaderio-ad92a0f2017f4665f7f7f03023c1fe71/")
    public String temp() {
        return "loaderio-ad92a0f2017f4665f7f7f03023c1fe71";
    }
}
