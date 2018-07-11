package me.liccon.simple.mapper;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import me.liccon.simple.model.DataColumn;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class SysModuleMapperTest {
	
	private static SqlSessionFactory sqlSessionFactory;
	
	@BeforeClass
	public static void init(){
		try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
        } catch (IOException ignore) {
        	ignore.printStackTrace();
        }
	}
	
	@Test
	public void testSelectAll(){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			//List<DataColumn> countryList = sqlSession.selectList("me.liccon.simple.mapper.DataColumnMapper.selectAll");
			DataColumnMapper mapper=sqlSession.getMapper(DataColumnMapper.class);
			//DataColumn sysModule =mapper.selectById(1);
			//printSysModule(sysModule);
			//List<DataColumn> countryList =mapper.selectAll();
			//		printCountryList(countryList);
		} finally {
			sqlSession.close();
		}
	}
	
	private void printCountryList(List<DataColumn> countryList){
		for(DataColumn country : countryList){
			printSysModule(country);
		}
	}
	private void printSysModule(DataColumn sysModule){
			//System.out.printf("%-4d%4s%4s\n",sysModule.getId(), sysModule.getCode(), sysModule.getName());
	}
}
