package org.slsale.dao.function;

import java.util.List;

import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;

public interface FunctionMapper {

	/**
	 * getMainFunctionList
	 * @param authority
	 * @return
	 * @throws Exception
	 */
	public List<Function> getMainFunctionList(Authority authority) throws Exception;

	/**
	 * getSubFunction
	 * @return
	 * @throws Exception
	 */
	public List<Function> getSubFunction(Function function) throws Exception;
}
