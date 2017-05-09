package xm.spring.boot.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
//@EnableConfigurationProperties(BaseProperties.class)
public class SampleApplication implements CommandLineRunner{
	@Autowired
	private UserService userService;
	
	@Override
	public void run(String... args) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!! " + this.userService.getHelloMessage());
		if (args.length > 0 && args[0].equals("exitcode")) {
			throw new ExitException();
		}
	}
	public static void main(String[] args) {
//		SpringApplication.run(SampleApplication.class, args);
		SpringApplication app = new SpringApplication(SampleApplication.class);
		app.addListeners(new ApplicationListener<ApplicationEvent>(){
			@Override
			public void onApplicationEvent(ApplicationEvent event) {
				
				System.out.println(event.getClass().getName() + " ---> "+event.getSource());
			}
		});
		app.run(args);
	}
}
