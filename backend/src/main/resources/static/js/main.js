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

function handleConnectedToStompWs() {
  console.log("Connected to WS");

  // Headers can include a subscription ID, otherwise a random ID will be generated
  var subsHeaders = {};

  // Subscribe to user queue 'response', which receives responses to subscriptions and to sent messages
  var subsResQueueRes = stompClient.subscribe("/user/queue/response", handleStockTopicEvent);
  console.info(`Subscribed queue response. ID: ${subsResQueueRes.id}. Unsubs: ${subsResQueueRes.unsubscribe}`);

  // Subscribe to stock events topic, which receives genenral events all users can listen for. The initial status
  // of the stock will also be received through this topic, immediately upon subscription
  var subsStockTopicRes = stompClient.subscribe("/topic/stock/event", handleStockTopicEvent, subsHeaders);
  console.info(`Subscribed topic stock. ID: ${subsStockTopicRes.id}. Unsubs: ${subsStockTopicRes.unsubscribe}`);

  // Subscribe to the user queue 'command', which receives events aimed specifically at the current user. Destinations
  // prefixed with /user/ are recognized by Spring's STOMP to target the specific user who submitted the request
  var subsCmdQueueRes = stompClient.subscribe("/user/queue/command", handleCommandQueueEvent);
  console.info(`Subscribed queue command. ID: ${subsCmdQueueRes.id}. Unsubs: ${subsCmdQueueRes.unsubscribe}`);

  renderStockInContainer({}, $("#stockContainer"));
}

function handleStockTopicEvent(msg) {
  var msgBody = JSON.parse(_.get(msg, "body", "{}"));
  var evType = _.get(msgBody, "type", null);
  var evPayload = _.get(msgBody, "payload", null);

  if (evType === null) {
    console.log("Unrecognized event: %s", evType);
  }

  switch (evType) {
    case "STOCK_SNAPSHOT":
      console.log("Processing StockSnapshot event. Payload: %s", JSON.stringify(evPayload));
      var itemsInStock = _.get(evPayload  , "skuItemsInStock", {});

      _.forOwn(itemsInStock, function (itemStock) { renderItemStock(itemStock, handleItemStockDelta); });

      break;

    case "STOCK_UPDATE":
      console.log("Processing StockUpdate event. Payload: %s", JSON.stringify(evPayload));
      var item = _.defaultTo(evPayload, null);

      renderItemStockUpdate(item, handleItemStockDelta);

      break;

    default:
      console.error("Unknown event type. Discarded. Body: %s. ", _.get(msg, "body", "{}"), msg);
  }
}

function handleCommandQueueEvent(msg) {
  console.log("Got command queue WS message: %s", msg.body, msg);
}

function handleItemStockDelta(sku, delta) {
  console.log("Delta on SKU: %s. Delta: %s", sku, delta);

  var headers = {};
  var actionBody = {
    type: "REQUEST_ALTER_ITEM_STOCK",
    payload: {
      item: { sku: sku },
      amountInStock: delta
    }
  };

  stompClient.send("/stock-app/stock/operation", headers, JSON.stringify(actionBody));
}

function handleConnectError(stompErrorFrame) {
  console.log(`Error connecting to STOMP/WS ${stompErrorFrame}`);
}

function handleDisconnected() {
  console.info("Properly disconnected from STOMP/WS");
}

$(document).ready(function () {
  connect();
});
