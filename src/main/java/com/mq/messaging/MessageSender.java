package com.mq.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.mq.model.Order;
import com.mq.model.InventoryResponse;

@Component
public class MessageSender {
	private static final Logger LOG = LoggerFactory.getLogger(MessageSender.class);
	private static final String ORDER_QUEUE = "order-queue";
	private static final String ORDER_RESPONSE_QUEUE = "order-response-queue";

	@Autowired
	JmsTemplate jmsTemplate;

	public void sendMessage(final Order order) {
		LOG.info("----------------------------------------------------");
		LOG.info("MQ: sending order='{}'", order);

		jmsTemplate.convertAndSend(ORDER_QUEUE, order);

		LOG.info("MQ: order sent successful='{}'", order);
		LOG.info("----------------------------------------------------");
	}

	public void sendMessage(final InventoryResponse inventoryResponse) {
		LOG.info("----------------------------------------------------");
		LOG.info("Inventory: sending response='{}'", inventoryResponse);

		jmsTemplate.convertAndSend(ORDER_RESPONSE_QUEUE, inventoryResponse);

		LOG.info("Inventory: response sent successful='{}'", inventoryResponse);
		LOG.info("----------------------------------------------------");
	}
}
