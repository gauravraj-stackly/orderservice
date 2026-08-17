package com.gaurav.orderservice;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class OrderService {

    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;

    @Value("${user.service.base-url}")
    private String userServiceBaseUrl;

    public OrderService(RestClient restClient,DiscoveryClient discoveryClient)
    {
        this.restClient = restClient;
        this.discoveryClient = discoveryClient;
    }
    public List<ServiceInstance> getOrderInstances() {
        return discoveryClient.getInstances("order-service");
    }


    @Retry(
            name = "paymentService",
            fallbackMethod = "paymentFallback"
    )
    public String getPaymentStatus() {
        ServiceInstance instance =
                discoveryClient.getInstances("PaymentService")
                        .get(0);
        String url = instance.getUri().toString();
        return restClient.get()
                .uri(url + "/payments/" )
                .retrieve()
                .body(String.class);

    }
     public String paymentFallback(Exception e) {
        return "Payment service is currently unavailable";
    }

    public OrderResponseDto getOrderById(Long orderId) {


        // For demonstration, order 101 belongs to user 1
        Long userId = 1L;

        try {

            UserResponseDto user = restClient.get()
                    .uri(userServiceBaseUrl + "/users/" + userId)
                    .retrieve()
                    .body(UserResponseDto.class);

            return new OrderResponseDto(
                    orderId,
                    user.getId(),
                    user.getName(),
                    "User information received from User Service"
            );

        } catch (HttpClientErrorException.NotFound exception) {

            return new OrderResponseDto(
                    orderId,
                    userId,
                    null,
                    "User not found in User Service"
            );
        }
    }
}
