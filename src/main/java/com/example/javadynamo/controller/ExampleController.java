package com.example.javadynamo.controller;

import com.example.javadynamo.config.SqsConfig;
import com.example.javadynamo.models.ExamplePojo;
import com.example.javadynamo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/products/v1")
public class ExampleController {
    final static Logger LOG = LoggerFactory.getLogger(ExampleController.class);

    private final ProductService productService;

    public ExampleController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/copy", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<ExamplePojo>> getProductText(String productId) {
        return productService.getProductCopy(productId)
                .map(ResponseEntity::ok);
    }
}
