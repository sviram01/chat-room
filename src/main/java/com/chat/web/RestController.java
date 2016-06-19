package com.chat.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chat.model.Message;
import com.chat.model.MessageDao;
import com.chat.model.User;
import com.chat.model.UserDao;

/**
 * @author Shreya Viramgama
 *
 */
@Controller
public class RestController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessageDao messageDao;


	/**
	 * Finds all users excluding user id passed in
	 * 
	 * @param userId
	 * @return List<User>
	 */
	@ResponseBody
	@RequestMapping(value = "/users/exclude/{user}", method = { RequestMethod.GET })
	public List<User> getUsers(@PathVariable("user") String userId) {
		List<User> users = userDao.findAllExcludeUser(userId);
		return users;
	}
	
	/**
	 * 
	 * Fetches message history from the database between two users
	 * 
	 * @param sender
	 * @param receiver
	 * @return List<Message>
	 */
	@ResponseBody
	@RequestMapping(value = "/messages/sender/{sender}/receiver/{receiver}", method = { RequestMethod.GET })
	public List<Message> getMessages(@PathVariable("sender") String sender, @PathVariable("receiver") String receiver) {
		List<Message> mesages = messageDao.getChatHistory(receiver, sender);
		return mesages;
	}

	/**
	 * 
	 * Gets the current user from Session
	 * 
	 * @return User
	 */
	@ResponseBody
	@RequestMapping(value = "/users/self", method = { RequestMethod.GET })
	public User getCurrentUser() {
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User currentUser = userDao.findByUsername(userName);
		return currentUser;
	}
}
