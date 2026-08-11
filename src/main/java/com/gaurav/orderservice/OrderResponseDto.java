package com.gaurav.orderservice;

public class OrderResponseDto {
    private Long orderId;
    private Long userId;
    private String userName;
    private String message;

    public OrderResponseDto() {
    }

    public OrderResponseDto(
            Long orderId,
            Long userId,
            String userName,
            String message) {

        this.orderId = orderId;
        this.userId = userId;
        this.userName = userName;
        this.message = message;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }
}
