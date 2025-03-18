package com.petsCare.petsCare;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.TreeMap;

@Controller
public class HomeController {

    @Value("${server.env}")
    private String env;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.name}")
    private String serverName;

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/hc")
    @ResponseBody
    public ResponseEntity<?> healthCheck() {
        Map<String, String> responseData = new TreeMap<>();
        responseData.put("serverName", serverName);
        responseData.put("serverAddress", serverAddress);
        responseData.put("serverPort", serverPort);
        responseData.put("env", env);

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/env")
    @ResponseBody
    public ResponseEntity<?> getEnv() {
        return ResponseEntity.ok(env);
    }

}
