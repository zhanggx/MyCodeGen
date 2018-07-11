package me.liccon.simple;

import me.liccon.simple.mapper.DataColumnMapper;
import me.liccon.simple.mapper.DataTableMapper;
import me.liccon.simple.model.DataColumn;
import me.liccon.simple.model.DataTable;
import me.liccon.simple.service.CodeGeneratorManager;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class Application {
    private static SqlSessionFactory sqlSessionFactory;
    private static String DB_NAME="mango_admin";

    public static final String PACKAGE_NAME="com.trigms.mango.market.wechat";
    public static final String MAPPER_BASE_CLASS="com.trigms.market.dao.MyMapper";
    public static final String SERVICE_BASE_CLASS="com.trigms.mango.common.service.BaseService";
    public static final String SERVICEIMPL_BASE_CLASS="com.trigms.mango.common.service.BaseServiceImpl";

    private static final String[] TABLES={"wechat_module_path"};
    public static void main(String[] args) {
        genCodeWithTables(DB_NAME,TABLES);
        //genCodeWithTableGroup(DB_NAME,"poster");
    }

    public static void init(){
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
        } catch (IOException ignore) {
            ignore.printStackTrace();
        }
    }
    public static void genCodeWithTables(String dbName,String[] tableNames){
        init();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            //List<DataColumn> countryList = sqlSession.selectList("me.liccon.simple.mapper.DataColumnMapper.selectAll");
            DataColumnMapper mapper=sqlSession.getMapper(DataColumnMapper.class);
            DataTableMapper tableMapper=sqlSession.getMapper(DataTableMapper.class);
            List<DataTable> tables =tableMapper.selectByNames(dbName,tableNames);
            //List<DataColumn> sysModuleList =mapper.selectAll("mango_admin",tableName);
            CodeGeneratorManager cgm = new CodeGeneratorManager();
            cgm.genCode(mapper, dbName,tables);
        } finally {
            sqlSession.close();
        }
    }
    public static void genCodeWithTableGroup(String dbName,String tableGroup){
        init();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {

            DataTableMapper tableMapper=sqlSession.getMapper(DataTableMapper.class);

            List<DataTable> tables =tableMapper.selectAll(dbName,tableGroup);

            //List<DataColumn> countryList = sqlSession.selectList("me.liccon.simple.mapper.DataColumnMapper.selectAll");
            DataColumnMapper mapper=sqlSession.getMapper(DataColumnMapper.class);
            //List<DataColumn> sysModuleList =mapper.selectAll("mango_admin",tableName);
            CodeGeneratorManager cgm = new CodeGeneratorManager();
            cgm.genCode(mapper, dbName,tables);
        } finally {
            sqlSession.close();
        }
    }
}
