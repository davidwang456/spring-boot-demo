package com.mkyong.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/test")
public class TestController implements ApplicationContextAware{
	@Autowired
	private ApplicationContext applicationContext;
	@RequestMapping(value = "/getBeanNames")
	@ResponseBody
	public String getBeanNames() {
		StringBuffer sbf=new StringBuffer();
		String[] names=applicationContext.getBeanDefinitionNames();
		for(String name:names){			
			sbf.append(name).append(",").append("\r");
		}		
		return sbf.toString();
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}
}
