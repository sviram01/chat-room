var client;
var user;
var stomp;
var room = "chatRoom";
var connected;
var sender;
var chat;
var text_max = 255;

$(document).ready(function() {
	user = getUser();
	connectServer();
	chatCall();
	loadUsers();
	showCharRemain();

});

function chatCall() {

	chat = {
		messageToSend : '',
		init : function() {
			this.cacheDOM();
			this.bindEvents();
		},
		cacheDOM : function() {
			this.$chatHistory = $('.chat-history');
			this.$userList = $('#user-list');
			this.$button = $('#send-button');
			this.$textarea = $('#message-to-send');
			this.$chatHistoryList = this.$chatHistory.find('ul');
			this.$chatWith = $('.chat-with')
		},
		bindEvents : function() {
			this.$button.on('click', this.addMessage.bind(this));
			this.$textarea.on('keyup', this.addMessageEnter.bind(this));
		},
		renderReceive : function(message) {
			this.scrollToBottom();
			var templateResponse = Handlebars.compile($(
					"#message-response-template").html());
			var respTemp = templateResponse(message);
			this.$chatHistoryList.append(respTemp);
			this.scrollToBottom();
			this.$textarea.val('');
		},
		renderUser : function(message) {
			var templateUser = Handlebars.compile($("#user-template").html());
			var userTemplate = templateUser(message);
			this.$userList.append(userTemplate);
			console.log(this.$userList);
		},
		renderTitle : function(fistName, lastName) {
			this.$chatWith.text(fistName + " " +lastName);
		},
		renderSend : function(message) {
			this.scrollToBottom();
			var template = Handlebars.compile($("#message-template").html());
			var respTemp = template(message);
			this.$chatHistoryList.append(respTemp);
			this.scrollToBottom();
			this.$textarea.val('');
		},
		addMessage : function() {
			this.messageToSend = this.$textarea.val();
			submitMessage(this.messageToSend);
			resetCharCount();
		},
		addMessageEnter : function(event) {
			// enter was pressed
			if (event.keyCode === 13) {
				this.addMessage();
			}
		},
		scrollToBottom : function() {
			this.$chatHistory.scrollTop(this.$chatHistory[0].scrollHeight);
		},
		getCurrentTime : function() {
			return new Date().toLocaleTimeString().replace(
					/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
		},
		getRandomItem : function(arr) {
			return arr[Math.floor(Math.random() * arr.length)];
		},
		clearScreen : function(){
			this.$chatHistoryList.text('');
		}

	};

	chat.init();

}

function showCharRemain(){

    $('#textarea_feedback').html(text_max + ' characters remaining');

    $('#message-to-send').keyup(function() {
        var text_length = $('#message-to-send').val().length;
        if(event.keyCode != 13){
        	var text_remaining = text_max - text_length;
		}

        $('#textarea_feedback').html(text_remaining + ' characters remaining');
    });
}

function resetCharCount(){

	$('#textarea_feedback').html(text_max + ' characters remaining');
}
function updateUser(user, firstName, lastName) {

	loadMessages(user, firstName, lastName);

	document.getElementById("message-to-send").disabled = false;
}

function printResponse(response) {
	var date = new Date(response.messageTime);
	response.messageTime = formatDate(date);

	if (response.sender == user.username) {
		chat.renderSend(response);
	} else if (response.sender == sender) {
		chat.renderReceive(response);
	}
	resetCharCount();
}

function formatDate(date) {
	  var hours = date.getHours();
	  var minutes = date.getMinutes();
	  var ampm = hours >= 12 ? 'pm' : 'am';
	  hours = hours % 12;
	  hours = hours ? hours : 12;
	  minutes = minutes < 10 ? '0'+minutes : minutes;
	  var strTime = hours + ':' + minutes + ' ' + ampm;
	  return date.getMonth()+1 + "/" + date.getDate() + "/" + date.getFullYear() + "  " + strTime;
	}

function printResponses(response) {
	for (i in responses) {
		printResponse(responses);
}
}

var getUser = function getUser() {
	var url = "/users/self";
	var sesionUser;
	$.ajax({
		url : url,
		type : "GET",
		async : false,
		success : function(data, textStatus, jqXHR) {
			sesionUser = data;
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log('Error occurred!');
		}

	});
	return sesionUser;
}

var notify = function(message) {
	var msg = JSON.parse(message.body);
	printResponse(msg);
};

function loadMessages(receiver, firstName, lastName) {
	var url = "/messages/sender/" + user.username + "/receiver/" + receiver;
	$.ajax({
		url : url,
		type : "GET",
		async : false,
		success : function(data, textStatus, jqXHR) {
			chat.clearScreen();
			sender = receiver;
			chat.renderTitle(firstName, lastName);
			for (i in data) {
				printResponse(data[i]);
			}

		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log('Error occurred!');
		}

	});
}

function loadUsers() {
	var url = "/users/exclude/" + user.username;
	$.ajax({
		url : url,
		type : "GET",
		success : function(data, textStatus, jqXHR) {
			for (i in data) {
				console.log(data[i]);
				chat.renderUser(data[i]);			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log('Error occurred!');
		}
	});
}

function submitMessage(msg) {
	var url = "/server/chat";
	var data = {};
	data.text = msg;
	data.room = room;
	data.sender = user.username;
	data.receiver = sender;
	data.time = new Date();
	var original = data;
	data = JSON.stringify(data);
	$.ajax({
		url : url,
		type : "POST",
		data : data,
		dataType : "json",
		contentType : 'application/json',
		success : function(data, textStatus, jqXHR) {
			console.log('Success!');
			printResponse(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log('Error occurred!');
		}

	});
}

function connectServer() {
	client = new SockJS('/chat');
	client.onopen = function() {
		console.log('client open');
	};
	client.onclose = function() {
		console.log('client close');
		connected = false;
	};
	stomp = Stomp.over(client);
	stomp.connect({}, function(frame) {
		console.log('Connected: ' + frame);
		stomp.subscribe("/user/queue/chatRoom", notify);
		connected = true;
	}, function(err) {
		console.log("Error : " + err);
		connected = false;
	});

}