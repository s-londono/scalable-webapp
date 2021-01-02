var USE_SOCKJS = true;

var stompClient = null;

// Using SockJS is not mandatory with @stomp/stomjs (https://stomp-js.github.io/#getting-started)
function socketFactory() {
  // Calling the SockJS constructor establishes a WS connection
  return new SockJS("/stock-ws");
}

// Reconsider using SockJS, which may have reconnection issues
function stompClientFactory() {
  if (USE_SOCKJS) {
    // It is possible to use other type of WebSockets (e.g. SockJS, native) by using the Stomp.over(ws) method.
    // This method expects an object that conforms to the WebSocket definition
    var sockJjClient = socketFactory();

    sockJjClient.onmessage = function (m) { console.info(`SOCKJS: Got message ${m}`); };
    sockJjClient.onopen = function () { console.info(`SOCKJS: Connection opened`); };
    sockJjClient.onclose = function () { console.info(`SOCKJS: Connection closed`); };

    return Stomp.over(sockJjClient);
  } else {
    // By default, stomp.js will use the Web browser native WebSocket class to create the WebSocket
    return Stomp.client("ws://localhost:8081/stock-ws")
  }
}

function sendDummyWSRequest() {
  var headers = {};

  stompClient.send("/stock-app/broadcast-to-test", headers, JSON.stringify({"content": "REQ"}));
}

function handleConnectedToStompWs() {
  console.log("Connected to WS");

  // Headers can include a subscription ID, otherwise a random ID will be generated
  var subsHeaders = {};

  // Subscribe to stock events topic, which receives genenral events all users can listen for. The initial status
  // of the stock will also be received through this topic, immediately upon subscription
  var subsStockTopicRes = stompClient.subscribe("/topic/stock", handleStockTopicEvent, subsHeaders);
  console.info(`Subscribed. ID: ${subsStockTopicRes.id}. Unsubscribe: ${subsStockTopicRes.unsubscribe}`);

  // Subscribe to the user queue, which receives events aimed specifically at the current user. Destinations
  // prefixed with /user/ are recognized by Spring's STOMP to target the specific user who submitted the request
  var subsCmdQueueRes = stompClient.subscribe("/user/queue/command", handleCommandQueueEvent);
  console.info(`Subscribed. ID: ${subsCmdQueueRes.id}. Unsubscribe: ${subsCmdQueueRes.unsubscribe}`);

  renderStockInContainer({}, $("#stockContainer"));

  sendDummyWSRequest();
}

function handleStockTopicEvent(msg) {
  console.log("Got stock topic WS message: %s", msg.body, msg);
}

function handleCommandQueueEvent(msg) {
  console.log("Got command queue WS message: %s", msg.body, msg);
}

function handleConnectError(stompErrorFrame) {
  console.log(`Error connecting to STOMP/WS ${stompErrorFrame}`);
}

function handleDisconnected() {
  console.info("Properly disconnected from STOMP/WS");
}

function connect() {
  stompClient = stompClientFactory();

  // Client will send heartbeats every 10s (Default: 10s?)
  stompClient.heartbeat.outgoing = 10000;

  // Client checks for heartbeats from the server every 15s (Default: 10s?)
  stompClient.heartbeat.incoming = 15000;

  // Add STOMP connection headers if necessary (e.g. login, passcode, client-id)
  var headers = {"client-id": "stockcl1"};

  stompClient.connect(headers, handleConnectedToStompWs, handleConnectError);
}

function disconnect() {
  if (stompClient) {
    stompClient.disconnect(handleDisconnected);
  }
}

$(document).ready(function () {
  connect();
});
