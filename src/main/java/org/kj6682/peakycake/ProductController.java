package org.kj6682.peakycake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
