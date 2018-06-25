package com.lcl.cache.redis.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisMessageListener implements MessageListener {
	
	   @Autowired  
	    RedisTemplate redisTemplate;  
	  
	    RedisSerializer serializer  ;

	@Override
	public void onMessage(Message message, byte[] paramArrayOfByte) {
		serializer = redisTemplate.getValueSerializer()  ;
        String messageStr = (String) serializer.deserialize(message.getBody());  
       System.err.println("message received:" + messageStr)  ;
	}

}
