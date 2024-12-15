package com.lucky.art.externalService;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "AUTH-SERVICE",url = "http://localhost:8002")
public interface UserService {
}
