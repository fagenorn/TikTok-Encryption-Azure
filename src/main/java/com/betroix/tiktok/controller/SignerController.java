package com.betroix.tiktok.controller;

import com.betroix.tiktok.service.IEmulatorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SignerController {
    @Autowired
    IEmulatorService emulatorService;

    @PostMapping("/ttEncrypt")
    public byte[] ttEncrypt(@RequestBody byte[] body) {
        return emulatorService.ttEncrypt(body);
    }

    @PostMapping("/leviathan")
    public byte[] leviathan(@RequestBody byte[] body, @RequestParam int time) {
        return emulatorService.leviathan(time, body);
    }
}
