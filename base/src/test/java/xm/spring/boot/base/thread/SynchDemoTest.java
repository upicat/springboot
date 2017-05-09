package xm.spring.boot.base.thread;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SynchDemoTest {

	@Test
	public void test() {
		for (int i = 0; i < 1000; i++) {
			new Thread(){
				public void run() {
					new SynchDemo().method1(1);
				}
			}.start();
			
//		TimeUnit.SECONDS.sleep(3);
			new Thread(){
				public void run() {
					new SynchDemo().method2(3);
				}
			}.start();
		}
	}

}
