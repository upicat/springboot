package xm.spring.boot.mybatis;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * @author Eddú Meléndez
 * @author Kazuki Shimizu
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleMybatisApplicationTest {

	@ClassRule
	public static OutputCapture out = new OutputCapture();

	@Test
	public void test() {
		String output = out.toString();
		assertTrue("Wrong output: " + output, output.contains("1,San Francisco,CA,US"));
	}

}