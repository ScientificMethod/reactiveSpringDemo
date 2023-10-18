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
@RequestMapping("/v1/toys/garage")
public class GarageController {
    final static Logger LOG = LoggerFactory.getLogger(GarageController.class);

    private final ProductService productService;

    public GarageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/{plate}/plate", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<ExamplePojo>> getByPlate(String plate) {
        return productService.getProductCopy(plate)
                .map(ResponseEntity::ok);
    }

    @GetMapping(path = "/{makeModel}/model", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<ExamplePojo>> getByMakeAndModel(@PathVariable String makeModel, @RequestParam String year) {
        return productService.getProductCopy(makeModel)
                .map(ResponseEntity::ok);
    }
}
