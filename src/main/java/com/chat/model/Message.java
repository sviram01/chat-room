package com.chat.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Shreya Viramgama
 *
 */
@Entity
@Table(name = "MESSAGES")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "MESSAGE_ID")
	private Integer messageId;

	@Column(name = "MESSAGE_TIME")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private Date messageTime;

	@NotNull
	@Column(name = "MESSAGE_TEXT")
	private String messageText;

	@NotNull
	@Column(name = "SENDER")
	private String sender;

	@NotNull
	@Column(name = "RECEIVER")
	private String receiver;

	@ManyToOne
	@JoinColumn(name = "SENDER", referencedColumnName = "username", nullable = true, insertable = false, updatable = false)
	private User senderUser;

	@ManyToOne
	@JoinColumn(name = "RECEIVER", referencedColumnName = "username", nullable = true, insertable = false, updatable = false)
	private User receiverUser;

	public Message() {
		super();
	}

	public Message(Date messageTime, String messageText, String sender, String receiver) {
		super();
		this.messageTime = messageTime;
		this.messageText = messageText;
		this.receiver = receiver;
		this.sender = sender;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Date getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	// @ManyToOne
	// @JoinColumn(name = "USERNAME")
	public User getSenderUser() {
		return senderUser;
	}

	public void setSenderUser(User senderUser) {
		this.senderUser = senderUser;
	}

	// @ManyToOne
	// @JoinColumn(name = "USERNAME")
	public User getReceiverUser() {
		return receiverUser;
	}

	public void setReceiverUser(User receiverUser) {
		this.receiverUser = receiverUser;
	}

}