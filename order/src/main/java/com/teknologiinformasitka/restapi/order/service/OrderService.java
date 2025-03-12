package com.teknologiinformasitka.restapi.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teknologiinformasitka.restapi.order.model.Order;
import com.teknologiinformasitka.restapi.order.repository.OrderRepository;


import java.util.List;
import java.util.Optional;




@Service
public class OrderService {


   @Autowired
   private OrderRepository orderRepository;


   public List<Order> getAllOrders() {
        return orderRepository.findAll();
   }


   public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
   }


   public Order createOrder(Order order) {
        return orderRepository.save(order);
   }


   public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id)
             .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
        order.setProductId(orderDetails.getProductId());
        order.setQuantity(orderDetails.getQuantity());
        order.setOrderDate(orderDetails.getOrderDate());
        order.setOrderStatus(orderDetails.getOrderStatus());
        return orderRepository.save(order);
   }


   public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
             .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
        orderRepository.delete(order);
   }
}

