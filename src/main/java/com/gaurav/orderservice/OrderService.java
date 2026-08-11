package com.gaurav.orderservice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class OrderService {

    private final RestClient restClient;

    @Value("${user.service.base-url}")
    private String userServiceBaseUrl;

    public OrderService(RestClient restClient) {
        this.restClient = restClient;
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
