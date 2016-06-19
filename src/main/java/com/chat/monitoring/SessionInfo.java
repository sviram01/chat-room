package com.chat.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

/**
 * @author Shreya Viramgama
 *
 */
@Service
public class SessionInfo {
	@Autowired
	private WebSocketMessageBrokerStats webSocketMessageBrokerStats;

	public String getWebSocketSessionStatsInfo() {
		return webSocketMessageBrokerStats.getWebSocketSessionStatsInfo();
	}

	public String getClientInboundExecutorStatsInfo() {
		return webSocketMessageBrokerStats.getClientInboundExecutorStatsInfo();
	}

	public String getClientOutboundExecutorStatsInfo() {
		return webSocketMessageBrokerStats.getClientOutboundExecutorStatsInfo();
	}

	public String getStompSubProtocolStatsInfo() {
		return webSocketMessageBrokerStats.getStompSubProtocolStatsInfo();
	}

	public long getLoggingPeriod() {
		return webSocketMessageBrokerStats.getLoggingPeriod();
	}
}