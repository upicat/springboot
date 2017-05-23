package xm.spring.boot.base;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@RequestMapping("/async")
	public Callable<String> asyncCall() {
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				TimeUnit.SECONDS.sleep(3);
				return "Async Hello";
			}
		};
	}

}