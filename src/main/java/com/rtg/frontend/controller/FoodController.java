package com.rtg.frontend.controller;

import com.rtg.frontend.dto.FoodDTO;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FoodController {
    public static final String BACKEND_SERVICE_ID = "backend";

    private final RestTemplate restTemplate;
    private final LoadBalancerClient loadBalancer;

    @GetMapping("/rtg/food")
    @Cacheable(value = "foodInfo")
    public ResponseEntity<List<FoodDTO>> getAll() throws InterruptedException {
        Thread.sleep(7000);
        log.info("Request [GET] was executed on " + loadBalancer.choose("backend").getInstanceId());
        URI backendUrl = URI.create("http://" + BACKEND_SERVICE_ID).resolve("/api/food");
        return new ResponseEntity<>(restTemplate.getForObject(backendUrl, List.class), HttpStatus.OK);
    }

    @PostMapping("/rtg/food")
    public ResponseEntity<FoodDTO> addFood(@RequestBody FoodDTO foodDTO) {
        log.info("Request [POST] was executed on " + loadBalancer.choose("backend").getInstanceId());
        URI backendUrl = URI.create("http://" + BACKEND_SERVICE_ID).resolve("/api/food");
        ResponseEntity<FoodDTO> result =
            restTemplate.postForEntity(backendUrl, foodDTO, FoodDTO.class);
        return result;
    }

    @PutMapping("/rtg/food")
    public ResponseEntity<FoodDTO> updateFood(@RequestParam String uuid, @RequestBody FoodDTO foodDTO) {
        log.info("Request [PUT] was executed on " + loadBalancer.choose("backend").getInstanceId());
        URI backendUrl = URI.create("http://" + BACKEND_SERVICE_ID).resolve("/api/food?uuid=" + uuid);
        restTemplate.put(backendUrl, foodDTO);
        return new ResponseEntity<>(foodDTO, HttpStatus.OK);
    }

    @DeleteMapping("/rtg/food")
    public ResponseEntity<String> deleteFood(@RequestParam String uuid) {
        log.info("Request [DELETE] was executed on " + loadBalancer.choose("backend").getInstanceId());
        URI backendUrl = URI.create("http://" + BACKEND_SERVICE_ID).resolve("/api/food?uuid=" + uuid);
        restTemplate.delete(backendUrl);
        return new ResponseEntity<>(String
            .format("Food with uuid %s was succesfully deleted", uuid), HttpStatus.OK);
    }
}
