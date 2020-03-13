package com.mq.messaging;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.mq.model.InventoryResponse;
import com.mq.service.OrderService;
import com.mq.service.OrderInventoryService;
import com.mq.model.Order;

@Component
public class MessageReceiver {
	static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

	private static final String ORDER_RESPONSE_QUEUE = "order-response-queue";
	private static final String ORDER_QUEUE = "order-queue";

	@Autowired
	OrderService orderService;
	@Autowired
	OrderInventoryService orderInventoryService;

	@JmsListener(destination = ORDER_RESPONSE_QUEUE)
	public void receiveResponseMessage(final Message<InventoryResponse> message) throws JMSException {
		LOG.info("----------------------------------------------------");
		LOG.info("MQ : recieved response='{}'", message);

		MessageHeaders headers = message.getHeaders();
		LOG.info("Application : headers received : {}", headers);

		InventoryResponse response = message.getPayload();
		LOG.info("Application : response received : {}", response);

		orderService.updateOrder(response);

		LOG.info("MQ : order updated", response);
		LOG.info("----------------------------------------------------");
	}

	@JmsListener(destination = ORDER_QUEUE)
	public void receiveOrderMessage(final Message<Order> message) throws JMSException {
		LOG.info("----------------------------------------------------");
		LOG.info("Inventory : recieved order='{}'", message);

		MessageHeaders headers = message.getHeaders();
		LOG.info("Application : headers received : {}", headers);

		Order order = message.getPayload();
		LOG.info("Application : product : {}", order);

		orderInventoryService.processOrder(order);

		LOG.info("Inventory : order confirmed {}", order);
		LOG.info("----------------------------------------------------");

	}
}
