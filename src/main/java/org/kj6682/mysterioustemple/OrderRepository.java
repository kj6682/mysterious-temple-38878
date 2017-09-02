package org.kj6682.mysterioustemple;

import org.springframework.data.repository.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by luigi on 13/07/2017.
 */

interface OrderRepository extends Repository<Order, Long> {

    Optional<List<Order>> findAll();

    Optional<Order> findById(Long id);

    Optional<List<Order>> findByShop(String shop);

    Optional<List<Order>> findByDue(LocalDate due);

    Order save(Order order);

    void delete(Long id);
}
