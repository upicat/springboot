package xm.spring.boot.base;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
//@EnableConfigurationProperties(BaseProperties.class)
@Slf4j
@EnableAsync
public class SampleApplication extends AsyncConfigurerSupport implements CommandLineRunner{
	@Autowired
	private UserService userService;
	@Autowired
	private GitHubLookupService gitHubLookupService;
	
	@Override
	public void run(String... args) throws InterruptedException, ExecutionException {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!! " + this.userService.getHelloMessage());
		if (args.length > 0 && args[0].equals("exitcode")) {
			throw new ExitException();
		}
		
		// Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        Future<User> page1 = gitHubLookupService.findUser("PivotalSoftware");
        Future<User> page2 = gitHubLookupService.findUser("CloudFoundry");
        Future<User> page3 = gitHubLookupService.findUser("Spring-Projects");

        // Wait until they are all done
        while (!(page1.isDone() && page2.isDone() && page3.isDone())) {
            Thread.sleep(10); //10-millisecond pause between each check
        }

        // Print results, including elapsed time
        log.info("Elapsed time: " + (System.currentTimeMillis() - start));
        log.info("--> " + page1.get());
        log.info("--> " + page2.get());
        log.info("--> " + page3.get());
		
	}
	
	@Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
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
