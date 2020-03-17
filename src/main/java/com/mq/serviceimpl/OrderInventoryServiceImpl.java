package com.mq.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mq.component.MessageSender;
import com.mq.model.InventoryResponse;
import com.mq.model.Order;
import com.mq.service.OrderInventoryService;

@Service("orderInventoryService")
public class OrderInventoryServiceImpl implements OrderInventoryService {
	static final Logger LOG = LoggerFactory.getLogger(OrderInventoryServiceImpl.class);

	@Autowired
	MessageSender messageSender;

	@Override
	public void processOrder(Order order) {
		LOG.info("----------------------------------------------------");
		InventoryResponse response = prepareResponse(order);
		LOG.info("Inventory : sending order confirmation {}", response);
		messageSender.sendMessage(response);
		LOG.info("----------------------------------------------------");
	}

	private InventoryResponse prepareResponse(Order order) {
		InventoryResponse response = new InventoryResponse();
		response.setOrderId(order.getOrderId());
		response.setReturnCode(200);
		response.setComment("Order Processed successfully");
		return response;
	}

}
