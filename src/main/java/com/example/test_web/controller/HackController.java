package com.example.test_web.controller;

import com.example.test_web.db.DBConnection;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
public class HackController {
    @Autowired
    public DBConnection dbConnection;

    private JsonParser jsonParser;

    @PostMapping(value = "/test/keylogger")
    public String authenticateUser(@RequestBody String body, HttpSession session) throws Exception{
        String fixedString = new String(body.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        System.out.println(body);
        System.out.println("fixedString : "+fixedString);
        return  null;
    }
}
