package com.switchfully.order.domain.orders;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.switchfully.order.domain.orders.OrderTestBuilder.anOrder;

public class OrderRepositoryTest {

    private OrderRepository orderRepository;

    @Before
    public void setupRepository() {
        orderRepository = new OrderRepository(Mockito.mock(ApplicationEventPublisher.class));
    }

    @Test
    public void getOrdersForCustomer()  {
        UUID customerId = UUID.randomUUID();
        HashMap<UUID, Order> orders = new HashMap<>();
        Order order1 = anOrder().withCustomerId(customerId).withId(UUID.randomUUID()).build();
        Order order2 = anOrder().withCustomerId(UUID.randomUUID()).withId(UUID.randomUUID()).build();
        Order order3 = anOrder().withCustomerId(customerId).withId(UUID.randomUUID()).build();
        orders.put(order1.getId(), order1);
        orders.put(order2.getId(), order2);
        orders.put(order3.getId(), order3);

        List<Order> ordersForCustomer = orderRepository.getOrdersForCustomer(customerId);

        Assertions.assertThat(ordersForCustomer).containsExactlyInAnyOrder(order1, order3);

    }

}