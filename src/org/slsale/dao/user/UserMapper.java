package org.slsale.dao.user;

import org.slsale.pojo.user.User;

public interface UserMapper {

	/**
	 * µÇÂ½
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User getLoginUser(User user) throws Exception;
	
	/**
	 * ×¢²áÓÃ»§
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int addUser(User user) throws Exception;
}
