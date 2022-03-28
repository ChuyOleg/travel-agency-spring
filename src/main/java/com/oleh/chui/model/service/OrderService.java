package com.oleh.chui.model.service;

import com.oleh.chui.model.entity.Order;
import com.oleh.chui.model.entity.Status;
import com.oleh.chui.model.entity.Tour;
import com.oleh.chui.model.entity.User;
import com.oleh.chui.model.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final int MAX_PERCENTAGE = 100;

    private final UserService userService;
    private final TourService tourService;
    private final StatusService statusService;
    private final OrderRepository orderRepository;

    public void createOrder(Long userId, Long tourId) {
        User user = userService.getById(userId);
        Tour tour = tourService.getById(tourId);

        BigDecimal finalPrice = calculateFinalPrice(userId, tour);
        Status status = statusService.getByValue(Status.StatusEnum.REGISTERED);

        Order order = Order.builder()
                .user(user)
                .tour(tour)
                .status(status)
                .creationDate(LocalDate.now())
                .finalPrice(finalPrice)
                .build();

        orderRepository.save(order);
    }

    public List<Order> getAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    public boolean isExistedByUserIdAndTourId(Long userId, Long tourId) {
        return orderRepository.existsByUserIdAndTourId(userId, tourId);
    }

    public boolean isExistedByTourId(Long tourId) {
        return orderRepository.existsByTourId(tourId);
    }

    public void changeStatus(String newStatus, Long orderId) {
        Order order = orderRepository.getById(orderId);
        Status.StatusEnum newStatusValue = Status.StatusEnum.valueOf(newStatus);
        Status newStatusInstance = statusService.getByValue(newStatusValue);

        order.setStatus(newStatusInstance);

        orderRepository.save(order);
    }

    public BigDecimal calculateFinalPrice(Long userId, Tour tour) {
        int ordersCount = findCountByUserId(userId);

        double totalDiscount = tour.getDiscountStep() * ordersCount;

        double finalDiscount = (totalDiscount > tour.getMaxDiscount())
                ? tour.getMaxDiscount()
                : totalDiscount;

        double FINAL_PRICE_IN_PERCENTAGE = MAX_PERCENTAGE - finalDiscount;

        return tour.getPrice().multiply(BigDecimal.valueOf(FINAL_PRICE_IN_PERCENTAGE / MAX_PERCENTAGE))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private int findCountByUserId(Long userId) {
        return orderRepository.countByUserId(userId);
    }

}
