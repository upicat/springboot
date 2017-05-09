package xm.spring.boot.mybatis;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import xm.spring.boot.mybatis.domain.City;
import xm.spring.boot.mybatis.mapper.CityMapper;

/**
 *
 * @author wonwoo
 * @since 1.2.1
 */
@RunWith(SpringRunner.class)
@MybatisTest
public class SampleMapperTest {

	@Autowired
	private CityMapper cityMapper;

	@Test
	public void mapperIsNotNullTest() {
		assertThat(cityMapper).isNotNull();
	}

	@Test
	public void findByStateTest() {
		City city = cityMapper.findByState("CA");
		assertThat(city.getName()).isEqualTo("San Francisco");
		assertThat(city.getState()).isEqualTo("CA");
		assertThat(city.getCountry()).isEqualTo("US");
	}

	@ClassRule
	public static OutputCapture out = new OutputCapture();

	@Test
	public void test() {
		String output = out.toString();
		assertThat(output).contains("1,San Francisco,CA,US");
	}

}