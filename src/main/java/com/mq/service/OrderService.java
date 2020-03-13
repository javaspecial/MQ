package com.mq.service;

import java.util.Map;

import com.mq.model.InventoryResponse;
import com.mq.model.Order;

public interface OrderService {
	public void sendOrder(Order order);
	
	public void updateOrder(InventoryResponse response);
	
	public Map<String, Order> getAllOrders();
}
