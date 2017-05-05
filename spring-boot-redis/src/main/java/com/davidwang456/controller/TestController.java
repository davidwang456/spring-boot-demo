package com.davidwang456.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.davidwang456.domain.User;

@Controller
@RequestMapping(value="/api")
public class TestController {

  @Autowired
  private RedisTemplate<String, User> redisTemplate;
  
  @RequestMapping(value="/test")
  @ResponseBody
  public String testKey(String userName) {
		// 保存对象
		User user = new User("ss", 20);
		redisTemplate.opsForValue().set(user.getUsername(), user);

		user = new User("ww", 30);
		redisTemplate.opsForValue().set(user.getUsername(), user);

		user = new User("dd", 40);
		redisTemplate.opsForValue().set(user.getUsername(), user);
		User uu=redisTemplate.opsForValue().get(userName);
		return uu.getUsername()+","+uu.getAge();
  }
  
  @RequestMapping(value="/testmap")
  @ResponseBody
  public String testmap(String userName) {
	  HashOperations<String, Object, Object> hash=redisTemplate.opsForHash();
	  
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("name", "dd");
      map.put("age", "26");
      hash.putAll("lpMap", map);
      Map<Object,Object> old=hash.entries("lpMap"); 
      StringBuffer sbf=new StringBuffer();
      for(Object obj:old.keySet()){
    	  sbf.append("key:"+obj+",value:"+old.get(obj)).append("\n");
      }
      hash.put("lpMap", "name", "ww");
      Map<Object,Object> last=hash.entries("lpMap"); 
      
      StringBuffer sb=new StringBuffer();
      for(Object obj:last.keySet()){
    	  sb.append("key:"+obj+",value:"+last.get(obj)).append("\n");
      }
      return "old key-value="+sbf.toString()+",last key-value="+sb.toString();
  }
}
