package com.oleh.chui.model.service;

import com.oleh.chui.model.entity.Order;
import com.oleh.chui.model.entity.Status;
import com.oleh.chui.model.entity.Tour;
import com.oleh.chui.model.entity.User;
import com.oleh.chui.model.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 * Manages business logic related with Order.
 *
 * @author Oleh Chui
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class OrderService {

    private static final int MAX_PERCENTAGE = 100;
    private static final int PRICE_SCALE = 2;

    private final TourService tourService;
    private final StatusService statusService;
    private final OrderRepository orderRepository;

    /**
     * Process creating Order.
     *
     * @param userId Long representing id of selected User.
     * @param tourId Long representing id of selected Tour.
     */
    @Transactional()
    public void createOrder(Long userId, Long tourId) {
        User user = User.builder().id(userId).build();
        Tour tour = Tour.builder().id(tourId).build();
        Status status = statusService.getByValue(Status.StatusEnum.REGISTERED);

        BigDecimal finalPrice = calculateFinalPrice(userId, tourId);

        Order order = Order.builder()
                .user(user)
                .tour(tour)
                .status(status)
                .creationDate(LocalDate.now())
                .finalPrice(finalPrice)
                .build();

        orderRepository.save(order);
        log.info("New order '{}' has been created", order);
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

    /**
     * Process changing status of the Order to the opposite.
     *
     * @param newStatus String representing new status of Order.
     * @param orderId Long representing id of selected Order.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void changeStatus(String newStatus, Long orderId) {
        Order order = orderRepository.getById(orderId);
        Status.StatusEnum newStatusValue = Status.StatusEnum.valueOf(newStatus);
        Status newStatusInstance = statusService.getByValue(newStatusValue);

        order.setStatus(newStatusInstance);

        orderRepository.save(order);
        log.info("Status of order (id = {}) has been changed to <{}>", orderId, newStatus);
    }

    /**
     * Calculates FINAL_PRICE based on formula:
     * FINAL_DISCOUNT = DISCOUNT_STEP * ORDERS_COUNT.
     * FINAL_DISCOUNT = TOUR_MAX_DISCOUNT if FINAL_DISCOUNT > TOUR_MAX_DISCOUNT
     *
     * FINAL_PRICE = TOUR_PRICE * (100 - FINAL_DISCOUNT) / 100.
     *
     * @param userId Long representing id of selected User.
     * @param tourId Long representing id of selected Tour.
     * @return BigDecimal representing final price.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public BigDecimal calculateFinalPrice(Long userId, Long tourId) {
        int ordersCount = orderRepository.countByUserId(userId);
        Tour tour = tourService.getById(tourId);

        double totalDiscount = tour.getDiscountStep() * ordersCount;

        double finalDiscount = (totalDiscount > tour.getMaxDiscount())
                ? tour.getMaxDiscount()
                : totalDiscount;

        double FINAL_PRICE_IN_PERCENTAGE = MAX_PERCENTAGE - finalDiscount;

        return tour.getPrice().multiply(BigDecimal.valueOf(FINAL_PRICE_IN_PERCENTAGE / MAX_PERCENTAGE))
                .setScale(PRICE_SCALE, RoundingMode.HALF_UP);
    }

}
