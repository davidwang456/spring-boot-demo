package com.davidwang456.controller;


import org.springframework.beans.factory.annotation.Autowired;
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
  public String test(String userName) {
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
}
