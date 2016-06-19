package com.chat.web;

import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chat.model.Message;
import com.chat.model.MessageDao;
import com.chat.model.UserDao;
import com.chat.util.JsonUtils;

/**
 * @author Shreya Viramgama
 *
 */
@Controller
public class WebSocketController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageDao messageDao;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	/**
	 * 
	 * Gets the message from rest and sends it to the receiver user over websocket
	 * 
	 * @param json
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/server/chat", method = { RequestMethod.POST }, consumes = "application/json")
	public Message chat(@RequestBody String json) {
		Map<String, String> map = JsonUtils.jsonMap(json);
		DateTime dt = new DateTime(map.get("time"));
		Message message = new Message(dt.toDate(), map.get("text"), map.get("sender"), map.get("receiver"));
		messageDao.save(message);

		message.setSenderUser(userDao.findByUsername(message.getSender()));

		message.setReceiverUser(userDao.findByUsername(message.getReceiver()));

		this.simpMessagingTemplate.convertAndSendToUser(map.get("receiver"), "/queue/chatRoom", message);

		return message;
	}
}
