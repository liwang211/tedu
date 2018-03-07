package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {

	//有的工程需要，有的工程不需要。设置required=false，有就注入，没有就不注入。
    @Autowired(required=false) //redis的spring管理中配置bean标签
    private ShardedJedisPool shardedJedisPool;
	
	//定义set方法
	public void set(String key,String value){
		//通过池获取对象
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		//通过jedis操作数据
		shardedJedis.set(key, value);
		//将链接还回池中
		shardedJedisPool.returnResource(shardedJedis);
	}
	
	//定义get方法
	public String get(String key){
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		String value = shardedJedis.get(key);
		shardedJedisPool.returnResource(shardedJedis);
		return value;
	}	
}
