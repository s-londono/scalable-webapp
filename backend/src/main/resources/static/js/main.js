var stompClient = null;

// function setConnected(connected) {
//   $("#connect").prop("disabled", connected);
//   $("#disconnect").prop("disabled", !connected);
//   if (connected) {
//     $("#conversation").show();
//   }
//   else {
//     $("#conversation").hide();
//   }
//   $("#greetings").html("");
// }
//
// function connect() {
//   var socket = new SockJS("/stock-ws");
//   stompClient = Stomp.over(socket);
//   stompClient.connect({}, function (frame) {
//     setConnected(true);
//     console.log("Connected: " + frame);
//     stompClient.subscribe("/topic/test", function (greeting) {
//       showGreeting(JSON.parse(greeting.body).content);
//     });
//   });
// }

// function disconnect() {
//   if (stompClient !== null) {
//     stompClient.disconnect();
//   }
//   setConnected(false);
//   console.log("Disconnected");
// }
//
// function sendName() {
//   stompClient.send("/ws/stock/broadcast", {}, JSON.stringify({"name": $("#name").val()}));
// }
//
// function showGreeting(message) {
//   $("#greetings").append("<tr><td>" + message + "</td></tr>");
// }

function connect() {
  var socket = new SockJS("/stock-ws");

  stompClient = Stomp.over(socket);

  stompClient.connect({}, function (frame) {
    console.log("Connected: " + frame);

    stompClient.subscribe("/topic/test", function (greeting) {
      console.log("WS RESPONSE: ", greeting);
    });

    stompClient.send("/stock-app/broadcast", {}, JSON.stringify({"content": "REQ"}));
  });
}

$(document).ready(function () {
  console.debug("Document ready");

  connect();
});

console.debug("Main script loaded");