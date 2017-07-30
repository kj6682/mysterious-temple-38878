package org.kj6682.peakycake;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by luigi on 30/07/2017.
 */
interface ProductRepository extends Repository<Product, Long> {

    Optional<List<Product>> findAll();
    Product save(Product order);


}
