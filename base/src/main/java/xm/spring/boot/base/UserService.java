package xm.spring.boot.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
	@Autowired
	private AppProperties baseProperties;

	public String getHelloMessage() {
		return "Hello, " + baseProperties.getName() + ". Age is " + baseProperties.getAge();
	}

}