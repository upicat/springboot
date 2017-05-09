package xm.spring.boot.mybatis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import xm.spring.boot.mybatis.mapper.CityMapper;

@SpringBootApplication
public class SampleMapperApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SampleMapperApplication.class, args);
	}

	final private CityMapper cityMapper;

	public SampleMapperApplication(CityMapper cityMapper) {
		this.cityMapper = cityMapper;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.getClass().getName()+ " -->" +this.cityMapper.findByState("CA"));
	}

}
