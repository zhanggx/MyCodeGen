package me.liccon.simple.service.impl;

import freemarker.template.Configuration;
import me.liccon.simple.Application;
import me.liccon.simple.mapper.DataColumnMapper;
import me.liccon.simple.model.DataTable;
import me.liccon.simple.service.CodeGenerator;
import me.liccon.simple.service.CodeGeneratorManager;
import me.liccon.simple.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Service层 代码生成器
 * Created by zhh on 2017/09/20.
 */
public class ServiceGenerator extends CodeGeneratorManager implements CodeGenerator {

	private final DataTable dataTable;
	private final DataColumnMapper mapper;
	private final String dbName;
	public ServiceGenerator(DataColumnMapper mapper, String dbName,DataTable table){
		this.mapper=mapper;
		this.dataTable=table;
		this.dbName=dbName;
	}
	@Override
	public void genCode(String modelName, String sign) {
		String tableName=dataTable.getTableName();
		Configuration cfg = getFreemarkerConfiguration();
		String customMapping = "/" + sign + "/service/";
		String modelNameUpperCamel = StringUtils.isNullOrEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
		
		Map<String, Object> data = getDataMapInit(modelName, sign, modelNameUpperCamel);
		try {
			String packagePath=StringUtils.packageConvertPath(Application.PACKAGE_NAME);
			// 创建 Service 接口
			File serviceFile = new File(PROJECT_PATH + JAVA_PATH + packagePath + customMapping
					+ modelNameUpperCamel + "Service.java");
			// 查看父级目录是否存在, 不存在则创建
			if (!serviceFile.getParentFile().exists()) {
				serviceFile.getParentFile().mkdirs();
			}
			cfg.getTemplate("service.ftl").process(data, new FileWriter(serviceFile));
			logger.info(modelNameUpperCamel + "Service.java 生成成功!");
			
			// 创建 Service 接口的实现类
			File serviceImplFile = new File(PROJECT_PATH + JAVA_PATH + packagePath + customMapping + "impl/"
					+ modelNameUpperCamel + "ServiceImpl.java");
			// 查看父级目录是否存在, 不存在则创建
			if (!serviceImplFile.getParentFile().exists()) {
				serviceImplFile.getParentFile().mkdirs();
			}
			cfg.getTemplate("service-impl.ftl").process(data, new FileWriter(serviceImplFile));
			logger.info(modelNameUpperCamel + "ServiceImpl.java 生成成功!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Service 生成失败!", e);
		}
	}
	
	/**
	 * 预置页面所需数据
	 * @param modelName 自定义实体类名, 为null则默认将表名下划线转成大驼峰形式
	 * @param sign 区分字段, 规定如表 gen_test_demo, 则 test 即为区分字段
	 * @param modelNameUpperCamel 首字为大写的实体类名
	 * @return
	 */
	private Map<String, Object> getDataMapInit(String modelName, String sign, String modelNameUpperCamel) {
		Map<String, Object> data = new HashMap<>();
		data.put("date", DATE);
		data.put("author", AUTHOR);
		data.put("sign", sign);
		data.put("modelNameUpperCamel", modelNameUpperCamel);
		data.put("modelNameLowerCamel", StringUtils.toLowerCaseFirstOne(modelNameUpperCamel));
		data.put("basePackage", Application.PACKAGE_NAME);
		
		return data;
	}
}
