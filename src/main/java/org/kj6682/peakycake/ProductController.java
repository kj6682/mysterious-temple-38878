package org.kj6682.peakycake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by luigi on 30/07/2017.
 */
@RestController
@RequestMapping("/api")
class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    List<Product> listProducts() {
        return productRepository.findAll()
                .orElseThrow(() -> new ProductController.ProductNotFoundException("all"));

    }

    private static class ProductNotFoundException extends RuntimeException {
        ProductNotFoundException(String id) {
            super("could not find product '" + id + "'.");
        }
    }


    @PostMapping(value = "/products")
    ResponseEntity<?> create(@RequestBody Product product) {

        Assert.notNull(product, "Product can not be empty");

        Product result = productRepository.save(product);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
