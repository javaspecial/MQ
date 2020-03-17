var ws;
function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	} else {
		$("#conversation").hide();
	}
	$("#greetings").html("");
}

function connect() {
	try {
		ws = new WebSocket('ws://localhost:8080/MQ/client');
		ws.onmessage = function(data) {
			showGreeting(data.data);
		}
		setConnected(true);
		console.log("Connected");
	} catch (e) {
		alert(e);
	}
}

function disconnect() {
	if (ws != null) {
		ws.close();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendToServer() {
	var data = JSON.stringify({
		'name' : $("#name").val()
	})
	ws.send(data);
}

function showGreeting(message) {
	$("#greetings").append("<tr><td> " + message + "</td></tr>");
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#connect").click(function() {
		connect();
	});
	$("#disconnect").click(function() {
		disconnect();
	});
	$("#send").click(function() {
		sendToServer();
	});
});
