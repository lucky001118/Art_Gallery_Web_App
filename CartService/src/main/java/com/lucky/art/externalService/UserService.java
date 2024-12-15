package com.lucky.art.externalService;

import com.lucky.art.externalModel.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AUTH-SERVICE",url = "http://localhost:8082")
public interface UserService {

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) throws Exception;

    @GetMapping("/getUser")
    public User getUserFromJwtToken(@RequestHeader("Authorization") String jwt) throws Exception;

}
