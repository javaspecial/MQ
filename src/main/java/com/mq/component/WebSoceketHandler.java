package com.mq.component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.mq.model.InventoryResponse;

@Component
public class WebSoceketHandler extends TextWebSocketHandler {

	private static Logger LOG = LoggerFactory.getLogger(WebSoceketHandler.class);
	private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

	// send socket to client
	public void sendSocketToClient(InventoryResponse response) {
		LOG.info("Sending response to client : {}", response);
	}

	//received from client and send it to client
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
		@SuppressWarnings("unchecked")
		Map<String, String> value = new Gson().fromJson(message.getPayload(), Map.class);
		session.sendMessage(new TextMessage("Server: " + value.get("client") + " !"));
	}

	// the messages will be broadcasted to all users.
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
	}
}
