package xm.spring.boot.jpa.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import xm.spring.boot.jpa.domain.Person;
import xm.spring.boot.jpa.support.CustomRepository;

@CacheConfig(cacheNames = "person")
public interface PersonRepository extends CustomRepository<Person, Long> {
	
	@Cacheable
	List<Person> findByAddress(String address);
	
	Person findByNameAndAddress(String name,String address);
	
	@Query("select p from Person p where p.name= :name and p.address= :address")
	
	Person withNameAndAddressQuery(@Param("name")String name,@Param("address")String address);
	
	Person withNameAndAddressNamedQuery(String name,String address);

}
