package org.example.order;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonProperty;

import java.util.List;

public class GetOrderResponse {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("orders")
    private List<Order> orders;

    @JsonProperty("total")
    private int total;

    @JsonProperty("totalToday")
    private int totalToday;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(int totalToday) {
        this.totalToday = totalToday;
    }
}