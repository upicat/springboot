/**
 * 
 */
var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
	var userid = $("#name").val(); 
    var socket = new SockJS('/stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/brocast', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/user/topic/single',function(greeting){  // equals '/user/' + userId + '/topic/single'
//            alert(JSON.parse(greeting.body).content);  
            showGreeting(JSON.parse(greeting.body).content);  
        });  
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {xmtopic:"greetings"}, JSON.stringify({'name': $("#name").val()}));
}

function sendMe() {
    stompClient.send("/app/message", {}, JSON.stringify({'toName': $("#name2").val()}));
}

function sendTo() {
    $.get("/send?toName="+$("#name3").val()+"&text=hello",function(data,status){
           //alert("Data: " + data + "\nStatus: " + status);
      });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#sendTo" ).click(function() { sendTo(); });
    $( "#sendMe" ).click(function() { sendMe(); });
});