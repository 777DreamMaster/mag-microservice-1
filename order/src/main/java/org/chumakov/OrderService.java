package org.chumakov;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        Order newOrder = orderRepository.save(order);
        log.info("Order created id={}", newOrder.getId());
        return newOrder;
    }

    public Optional<Order> getOrder(Long id) {
        log.info("GET Order {}", id);
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        log.info("GET Orders");
        return orderRepository.findAll();
    }
}

