package com.example.javadynamo.controller;

import com.example.javadynamo.models.ExamplePojo;
import com.example.javadynamo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/toys")
public class ToyController {
    final static Logger LOG = LoggerFactory.getLogger(ToyController.class);

    private final ProductService productService;

    public ToyController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<ExamplePojo>> getProductText(@RequestParam String productId) {
        return productService.getProductCopy(productId)
                .map(ResponseEntity::ok);
    }
}
