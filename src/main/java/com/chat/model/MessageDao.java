package com.chat.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


/**
 * @author Shreya Viramgama
 *
 */
@Transactional
public interface MessageDao extends CrudRepository<Message, Long> {

  /**
   * Return the user having the passed email or null if no user is found.
   * 
   * @param email the user email.
   */
	public List<Message> findBySender(String sender);
	
	@Query(value = "select TOP 30 * from messages where (SENDER = ?1 and RECEIVER = ?2) or (SENDER = ?2 and RECEIVER = ?1) ORDER BY MESSAGE_TIME ASC", nativeQuery = true)
	public List<Message> getChatHistory(String sender, String receiver);
	
	public List<Message> findAll();

}
