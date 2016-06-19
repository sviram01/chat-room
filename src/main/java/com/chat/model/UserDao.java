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
public interface UserDao extends CrudRepository<User, Long> {

  /**
   * Return the user having the passed email or null if no user is found.
   * 
   * @param email the user email.
   */
	public List<User> findByFirstName(String firstName);
	
	public User findByUsername(String username);
	
	public List<User> findAll();
	@Query(value = "select * from users where username != ?1", nativeQuery = true)
	public List<User> findAllExcludeUser(String username);
}
