package org.slsale.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisAPI {

	public JedisPool jedisPool;  //redis的连接池对象

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	/**
	 * set key and value to redis
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key,String value){
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();  //从连接池中获取一个连接对象
			jedis.set(key, value);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//返还到连接池
			returnResource(jedisPool, jedis);
		}
		return false;
	}
	/**
	 * 判断某个key是否存在
	 * @param key
	 * @return
	 */
	public boolean exists(String key){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.exists(key);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			//返还到连接池
			returnResource(jedisPool, jedis);
		}
		return false;
	}
	
	public static void returnResource(JedisPool pool,Jedis jedis){
		if(jedis !=null){
			pool.returnResource(jedis);
		}
	}
	/**
	 * 获取数据
	 * @param key
	 * @return
	 */
	public String get(String key){
		String value = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			//返还到连接池
			returnResource(jedisPool, jedis);
		}
		return value;
	}
}
