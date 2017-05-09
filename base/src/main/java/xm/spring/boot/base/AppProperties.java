package xm.spring.boot.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@PropertySource("classpath:app.properties")
@ConfigurationProperties("app")
public class AppProperties {

	@Value("${name:World}")
	private String name;

	private Integer age;

}