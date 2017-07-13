package org.kj6682.peakycake;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by luigi on 13/07/2017.
 */

interface OrderRepository extends Repository<Order, Long> {

    Optional<List<Order>> findAll();

    Optional<List<Order>> findByShop(String shop);

    Order save(Order order);

}
