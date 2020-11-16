var USE_SOCKJS = true;

var stompClient = null;

// Using SockJS is not mandatory with @stomp/stomjs (https://stomp-js.github.io/#getting-started)
function socketFactory() {
  return new SockJS("/stock-ws");
}

// Reconsider using SockJS, which may have reconnection issues
function stompClientFactory() {
  if (USE_SOCKJS) {
    // It is possible to use other type of WebSockets (e.g. SockJS) by using the Stomp.over(ws) method.
    // This method expects an object that conforms to the WebSocket definition
    var sockJjClient = socketFactory();

    sockJjClient.onmessage = function (m) { console.info(`SOCKJS: Got message ${m}`); };
    sockJjClient.onopen = function () { console.info(`SOCKJS: Connection opened`); };
    sockJjClient.onclose = function () { console.info(`SOCKJS: Connection closed`); };

    return Stomp.over(socketFactory());
  } else {
    // By default, stomp.js will use the Web browser native WebSocket class to create the WebSocket
    return Stomp.client("ws://localhost:8081/stock-ws")
  }
}

function sendDummyWSRequest() {
  var headers = {};

  stompClient.send("/stock-app/broadcast", headers, JSON.stringify({"content": "REQ"}));
}

function handleConnectedToStompWs() {
  console.log("Connected to WS");

  // Headers can include a subscription ID, otherwise a random ID will be generated
  var subsHeaders = {};

  var subsResult = stompClient.subscribe("/topic/test", handleReceivedStompMsg, subsHeaders);

  console.info(`Subscribed. ID: ${subsResult.id}. Unsubscribe: ${subsResult.unsubscribe}`);

  sendDummyWSRequest();
}

function handleReceivedStompMsg(msg) {
  console.log("Got WS message: ", msg);
}

function handleConnectError(stompErrorFrame) {
  console.log(`Error connecting to STOMP/WS ${stompErrorFrame}`);
}

function handleDisconnected() {
  console.info("Properly disconnected from STOMP/WS");
}

function connect() {
  stompClient = stompClientFactory();

  // Client will send heartbeats every 20s (Default: 10s?)
  stompClient.heartbeat.outgoing = 10000;

  // Client checks for heartbeats from the server every 20s (Default: 10s?)
  // stompClient.heartbeat.incoming = 20000;

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
  console.debug("Document ready");

  connect();
});

console.debug("Main script loaded");