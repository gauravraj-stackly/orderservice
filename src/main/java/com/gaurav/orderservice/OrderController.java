package com.gaurav.orderservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

        @GetMapping("/{orderId}")
        public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long orderId) {
            if (orderId <= 0) {
                return ResponseEntity.badRequest().build();
            }

            OrderResponseDto response =
                    orderService.getOrderById(orderId);

            return ResponseEntity.ok(response);
        }
        @GetMapping
        public ResponseEntity<String> getPaymentStatus(@PathVariable Long orderId) {

        String response =
                orderService.getPaymentStatus();

        return ResponseEntity.ok(response);
    }

    }

