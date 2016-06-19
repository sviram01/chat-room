package com.chat.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chat.monitoring.SessionInfo;

/**
 * @author Shreya Viramgama
 *
 */
@Controller
public class WebController {

	@Autowired
	private SessionInfo sessionInfo;

	@Autowired
	SimpUserRegistry simpUserRegistry;
	
	/**
	 * 
	 * Redirects to the default chat page
	 * 
	 * @param model
	 * @return String
	 */
	@RequestMapping("/")
	public String onRootAccess(Model model) {

		return "redirect:/stb-jquery.html";
	}

	/**
	 * 
	 * Fetches all the session stat info for the app
	 * 
	 * @param request
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/sessions", method = { RequestMethod.GET })
	Map<String, Object> sessions(HttpServletRequest request) {
		String baseUrl = "http://" + request.getServerName() + ":" + request.getServerPort();
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("LoggingPeriod", sessionInfo.getLoggingPeriod() + "");
		map.put("ClientInboundExecutorStatsInfo", sessionInfo.getClientInboundExecutorStatsInfo());
		map.put("ClientOutboundExecutorStatsInfo", sessionInfo.getClientOutboundExecutorStatsInfo());
		map.put("StompSubProtocolStatsInfo", sessionInfo.getStompSubProtocolStatsInfo());
		map.put("WebSocketSessionStatsInfo", sessionInfo.getWebSocketSessionStatsInfo());
		map.put("users", size(simpUserRegistry, baseUrl));
		return map;
	}

	/**
	 * 
	 * Gets the size and url of current users in the session
	 * 
	 * @param simpUserRegistry
	 * @param baseUrl
	 * @return Map<String, Object>
	 */
	Map<String, Object> size(SimpUserRegistry simpUserRegistry, String baseUrl) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("size", simpUserRegistry.getUsers().size());
		map.put("url", baseUrl + "/usersSession");
		return map;
	}

	/**
	 * 
	 * Get the info on all the current users in the session
	 * 
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/usersSession", method = { RequestMethod.GET })
	Map<String, Object> users() {
		Map<String, Object> map = new LinkedHashMap<>();
		Set<SimpUser> listUsers = simpUserRegistry.getUsers();
		listUsers.forEach(simpUser -> {
			user(map, simpUser);
		});
		return map;
	}

	/**
	 * 
	 * Gets info on single user form the session
	 * 
	 * @param map
	 * @param simpUser
	 * @return Map<String, Object>
	 */
	Map<String, Object> user(Map<String, Object> map, SimpUser simpUser) {
		List<String> list = new ArrayList<>();
		map.put(simpUser.getName(), list);
		for (SimpSession session : simpUser.getSessions()) {
			list.add(session.getId());
		}
		return map;
	}
}
