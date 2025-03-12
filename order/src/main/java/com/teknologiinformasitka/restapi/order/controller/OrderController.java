package com.teknologiinformasitka.restapi.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.teknologiinformasitka.restapi.order.event.OrderCreatedEvent;
import com.teknologiinformasitka.restapi.order.model.Order;
import com.teknologiinformasitka.restapi.order.model.OrderResponse;
import com.teknologiinformasitka.restapi.order.model.Produk;
import com.teknologiinformasitka.restapi.order.service.OrderMessageProducer;
import com.teknologiinformasitka.restapi.order.service.OrderService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMessageProducer orderMessageProducer;

    @Autowired
    private RestTemplate restTemplate;

    // GET semua order
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Get Order berdasarkan ID dan ambil detail order service
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id).orElse(null);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        // Panggil Product Service untuk mendapatkan data produk terkait
        String productServiceUrl = "http://localhost:8081/api/produk/" + order.getProductId();
        Produk product = restTemplate.getForObject(productServiceUrl, Produk.class);

        OrderResponse response = new OrderResponse();
        response.setOrder(order);
        response.setProduct(product);
        return ResponseEntity.ok(response);
    }

    // POST buat order baru
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        // Set informasi order
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("CREATED");
        Order createdOrder = orderService.createOrder(order);

        // Buat dan kirim event
        OrderCreatedEvent event = new OrderCreatedEvent(
                createdOrder.getId(),
                createdOrder.getProductId(),
                createdOrder.getQuantity(),
                createdOrder.getOrderDate(),
                createdOrder.getOrderStatus());
        orderMessageProducer.sendOrderCreatedEvent(event);

        return ResponseEntity.ok(createdOrder);
    }

    // PUT update order
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        try {
            Order updatedOrder = orderService.updateOrder(id, orderDetails);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE order
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
