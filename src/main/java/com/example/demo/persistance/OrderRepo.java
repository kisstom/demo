package com.example.demo.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    @Override
    @Query("select o from order_table o left join fetch o.orderItems where o.id = ?1")
//    @Query("select o from order_table o where o.id = ?1")
    Optional<Order> findById(Long id);
}
