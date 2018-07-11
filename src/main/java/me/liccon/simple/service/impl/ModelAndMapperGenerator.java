package me.liccon.simple.service.impl;

import freemarker.template.Configuration;
import me.liccon.simple.Application;
import me.liccon.simple.mapper.DataColumnMapper;
import me.liccon.simple.model.DataColumn;
import me.liccon.simple.model.DataTable;
import me.liccon.simple.service.CodeGenerator;
import me.liccon.simple.service.CodeGeneratorManager;
import me.liccon.simple.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model & Mapper 代码生成器
 * Created by zhh on 2017/09/20.
 */
public class ModelAndMapperGenerator  extends CodeGeneratorManager implements CodeGenerator {
	private final DataTable dataTable;
	private final DataColumnMapper mapper;
	private final String dbName;
	public ModelAndMapperGenerator(DataColumnMapper mapper, String dbName,DataTable table){
		this.mapper=mapper;
		this.dataTable=table;
		this.dbName=dbName;
	}
	@Override
	public void genCode(String modelName, String sign) {
		String tableName=dataTable.getTableName();
		Configuration cfg = getFreemarkerConfiguration();
		String customMapping = "/" + sign + "/model/";
		String modelNameUpperCamel = StringUtils.isNullOrEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;

		List<DataColumn> columnList =mapper.selectAll(dbName,tableName);
		Map<String, Object> data = getDataMapInit(tableName,modelName, sign, modelNameUpperCamel,columnList);
		try {
			String packagePath=StringUtils.packageConvertPath(Application.PACKAGE_NAME);
			// 创建 Service 接口
			File beanFile = new File(PROJECT_PATH + JAVA_PATH + packagePath + customMapping + "bean/"
					+ modelNameUpperCamel + ".java");
			// 查看父级目录是否存在, 不存在则创建
			if (!beanFile.getParentFile().exists()) {
				beanFile.getParentFile().mkdirs();
			}
			cfg.getTemplate("bean.ftl").process(data, new FileWriter(beanFile));
			logger.info(modelNameUpperCamel + ".java 生成成功!");

			// 创建 Mapper 接口
			File mapperFile = new File(PROJECT_PATH + JAVA_PATH + packagePath + customMapping + "dao/"
					+ modelNameUpperCamel + "Mapper.java");
			// 查看父级目录是否存在, 不存在则创建
			if (!mapperFile.getParentFile().exists()) {
				mapperFile.getParentFile().mkdirs();
			}
			cfg.getTemplate("mapper.ftl").process(data, new FileWriter(mapperFile));
			logger.info(modelNameUpperCamel + "Mapper.java 生成成功!");


			// 创建 MapperXml 接口
			File mapperXmlFile = new File(PROJECT_PATH + RESOURCES_PATH + "/mappers/" + sign + "/"
					+ modelNameUpperCamel + "Mapper.xml");
			// 查看父级目录是否存在, 不存在则创建
			if (!mapperXmlFile.getParentFile().exists()) {
				mapperXmlFile.getParentFile().mkdirs();
			}
			cfg.getTemplate("mapperxml.ftl").process(data, new FileWriter(mapperXmlFile));
			logger.info(modelNameUpperCamel + "Mapper.xml 生成成功!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Bean&Mapper 生成失败!", e);
		}
	}

	/**
	 * 预置页面所需数据
	 * @param modelName 自定义实体类名, 为null则默认将表名下划线转成大驼峰形式
	 * @param sign 区分字段, 规定如表 gen_test_demo, 则 test 即为区分字段
	 * @param modelNameUpperCamel 首字为大写的实体类名
	 * @return
	 */
	private Map<String, Object> getDataMapInit(String tableName, String modelName, String sign, String modelNameUpperCamel, List<DataColumn> columnList) {
		Map<String, Object> data = new HashMap<>();
		data.put("tableName", tableName);
		data.put("date", DATE);
		data.put("author", AUTHOR);
		data.put("sign", sign);
		data.put("modelNameUpperCamel", modelNameUpperCamel);
		data.put("modelNameLowerCamel", StringUtils.toLowerCaseFirstOne(modelNameUpperCamel));
		logger.info( "dataTable.getTableComment()=" + dataTable.getTableComment());
		data.put("tableComment", dataTable.getTableComment()==null?"":dataTable.getTableComment());
		data.put("basePackage", Application.PACKAGE_NAME);
		List<DataColumn> columns=new ArrayList<>(columnList);
		columns.removeIf(column->column.getColumnName().equals("id"));
		columns.removeIf(column->column.getColumnName().equals("create_time"));
		data.put("columnList", columns);

		return data;
	}
}
