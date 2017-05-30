package xm.spring.boot.redis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xm.spring.boot.redis.dao.Person;
import xm.spring.boot.redis.domain.PersonDao;

@RestController
public class DataController {
	
	@Autowired
	PersonDao personDao;
	
	@RequestMapping("/set") //1
	public void set(){
		Person person = new Person("1","wyf", 32);
		personDao.save(person);
		personDao.stringRedisTemplateDemo();
	}
	
	@RequestMapping("/getStr") //2
	public String getStr(){
		return personDao.getString();
	}
	
	@RequestMapping("/getPerson") //3
	public Person getPerson(){
		return personDao.getPerson();
	}
}
