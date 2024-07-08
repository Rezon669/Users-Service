package com.ecom.usersservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "jwt-service")//, url = "${jwt.service.url}")// Name of the JWT service
public interface JwtService {
  @PostMapping("/easybuy/api/tokens") // Path to the token generation endpoint in JWT service
  String generateToken(@RequestParam("email") String email, 
                       @RequestParam("role") String role) throws Exception;
}

