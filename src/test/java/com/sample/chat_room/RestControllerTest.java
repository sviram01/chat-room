package com.sample.chat_room;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.chat.Application;
import com.chat.model.Message;
import com.chat.model.MessageDao;
import com.chat.model.User;
import com.chat.model.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test.properties")
public class RestControllerTest {

	private MockMvc mockMvc;

	private String userName = "sviramgama";

	private String receiver = "jdoe";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	List<User> userList;
	@Autowired
	MockHttpSession session;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	UserDao userDao;

	@Autowired
	MessageDao messageDao;

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		userList = userDao.findAll();

	}

	@Test
	public void testGetUsers() throws Exception {
		this.mockMvc.perform(get("/users/exclude/" + userName)).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].username", is(this.userList.get(0).getUsername())));
	}

	@Test
	public void testGetMessages() throws Exception {
		interstTestMessages(10);
		String url = "/messages/sender/" + userName + "/receiver/" + receiver;
		this.mockMvc.perform(get(url)).andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(10)));
	}

	public void interstTestMessages(int number) {

		int middle = (int) Math.floor(number / 2);
		for (int i = 0; i < middle; i++) {
			messageDao.save(createTestMessageSent());
			messageDao.save(createTestMessageReceived());
		}
	}

	public Message createTestMessageSent() {
		return new Message(new Date(), "hi there", userName, receiver);
	}

	public Message createTestMessageReceived() {
		return new Message(new Date(), "hi there", receiver, userName);
	}
}
