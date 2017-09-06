package org.slsale.dao.user;

import org.slsale.pojo.User;

public interface UserMapper {

	/**
	 * getLoginUser
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User getLoginUser(User user) throws Exception;

	/**
	 * loginCodeIsExist
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int loginCodeIsExist(User user) throws Exception;
	
	/**
	 * modifyUser
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int modifyUser(User user) throws Exception;
}
