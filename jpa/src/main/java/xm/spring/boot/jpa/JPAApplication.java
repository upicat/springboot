package xm.spring.boot.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import xm.spring.boot.jpa.dao.PersonRepository;
import xm.spring.boot.jpa.support.CustomRepositoryFactoryBean;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
@EnableCaching
public class JPAApplication {
	@Autowired
	PersonRepository personRepository;
	
	
    public static void main(String[] args) {
        SpringApplication.run(JPAApplication.class, args);
        
    }
    
 
}
