package com.oleh.chui.model.service;

import com.oleh.chui.model.entity.Status;
import com.oleh.chui.model.entity.Tour;
import com.oleh.chui.model.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderServiceTest {

    @MockBean
    private TourService tourService;
    @MockBean
    private StatusService statusService;
    @MockBean
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    private static final Long ID = 1L;

    private static final Tour TOUR = Tour.builder()
            .id(ID)
            .name("NAME")
            .price(BigDecimal.valueOf(100))
            .maxDiscount(20)
            .discountStep(2)
            .build();

    @Test
    void checkCreateOrderShouldWorkWithoutException() {
        when(tourService.getById(ID)).thenReturn(TOUR);

        assertDoesNotThrow(() -> orderService.createOrder(ID, ID));
    }

    @Test
    void checkCreateOrderShouldThrowRuntimeException() {
        when(tourService.getById(ID)).thenReturn(TOUR);
        when(statusService.getByValue(Status.StatusEnum.REGISTERED)).thenThrow(RuntimeException.class);

        assertThrows(
                RuntimeException.class,
                () -> orderService.createOrder(ID, ID)
        );
    }

    @Test
    void checkCalculateFinalPrice() {
        when(orderRepository.countByUserId(ID)).thenReturn(2);
        when(tourService.getById(ID)).thenReturn(TOUR);

        assertEquals(0, orderService.calculateFinalPrice(ID, ID).compareTo(BigDecimal.valueOf(96)));
    }

}
