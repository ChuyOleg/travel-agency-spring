package com.oleh.chui.model.repository;

import com.oleh.chui.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    boolean existsByUserIdAndTourId(Long userId, Long tourId);

    boolean existsByTourId(Long tourId);

    List<Order> findAllByUserId(Long userId);

    int countByUserId(Long userId);

}
