package xm.spring.boot.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import xm.spring.boot.mybatis.domain.City;

/**
 * @author Eddú Meléndez
 */
@Mapper
public interface CityMapper {

	@Select("select * from city where state = #{state}")
	City findByState(@Param("state") String state);

}
