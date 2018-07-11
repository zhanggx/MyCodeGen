package me.liccon.simple.service;

import com.google.common.base.CaseFormat;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import me.liccon.simple.mapper.DataColumnMapper;
import me.liccon.simple.model.DataTable;
import me.liccon.simple.service.impl.ControllerGenerator;
import me.liccon.simple.service.impl.ModelAndMapperGenerator;
import me.liccon.simple.service.impl.ServiceGenerator;
import me.liccon.simple.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 代码生成器基础项 (常量信息 & 通用方法)
 * Created by zhh on 2017/09/20.
 */
public class CodeGeneratorManager extends CodeGeneratorConfig {
	
	protected static final Logger logger = LoggerFactory.getLogger(CodeGeneratorManager.class);
	
	private static Configuration configuration = null;
	
	static {
		// 初始化配置信息
		init();
	}
	
	/**
	 * 获取 Freemarker 模板环境配置
	 * @return
	 */
	public Configuration getFreemarkerConfiguration() {
		if (configuration == null) {
			configuration = initFreemarkerConfiguration();
		}
		return configuration;
	}

	
	/**
	 * 生成简单名称代码
	 * eg: 
	 * 	genCode("gen_test_demo");  gen_test_demo ==> Demo
	 * @param tables 表名, 可以多表
	 */
	public void genCode(DataColumnMapper mapper,String dbName,List<DataTable> tables) {
		for (DataTable table : tables) {
			genCodeByTableName(mapper,dbName,table);
		}
	}

	
	/**
	 * 下划线转成驼峰, 首字符为大写
	 * eg: gen_test_demo ==> GenTestDemo
	 * @param tableName 表名, eg: gen_test_demo
	 * @return
	 */
	protected String tableNameConvertUpperCamel(String tableName) {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
	}
	
	/**
	 * 表名转成映射路径
	 * eg: gen_test_demo ==> /gen/test/demo
	 * @param tableName 表名
	 * @return
	 */
	protected String tableNameConvertMappingPath(String tableName) {
		tableName = tableName.toLowerCase();
		return File.separator + (tableName.contains("_") ? tableName.replaceAll("_", File.separator) : tableName);
	}
	
	/**
	 * ModelName转成映射路径
	 * eg: Demo ==> /demo
	 * @param modelName
	 * @return
	 */
	protected String modelNameConvertMappingPath(String modelName) {
		String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
		return tableNameConvertMappingPath(tableName);
	}
	
	/**
	 * 获取表的区分字段
	 * @param tableName 表名, eg: gen_test_demo
	 * @return 区分字段 eg: test
	 */
	protected String getSign(String tableName) {
		return getTableNameSplit(tableName)[0];
	}
	
	/**
	 * 获取默认 modelName
	 * @param tableName 表名
	 * @return
	 */
	protected String getDefModelName(String tableName) {
		String[] strs = getTableNameSplit(tableName);
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < strs.length; i++) {
			sb.append(StringUtils.toUpperCaseFirstOne(strs[i].toLowerCase()));
		}
		return sb.toString();
	}
	
	/**
	 * 获取表名切割后的数组
	 * @param tableName 表名
	 * @return
	 */
	private String[] getTableNameSplit(String tableName) {
		String[] strs = tableName.split("_");
		if (!tableName.contains("_") || strs.length < 3) {
			//throw new RuntimeException("表名格式不正确, 请按规定格式! 例如: gen_test_demo");
		}
		return strs;
	}

	/**
	 * 通过数据库表名, 和自定义 modelName 生成代码
	 * 如表名为 gen_test_demo, 自定义 modelName 为 IDemo
	 * 将生成  IDemo & IDemoMapper & IDemoService & IDemoServiceImpl & IDemoController
	 * @param table 表
	 */
	private void genCodeByTableName(DataColumnMapper mapper,String dbName,DataTable table) {
		String sign = getSign(table.getTableName());
		String modelName = getDefModelName(table.getTableName());

		new ModelAndMapperGenerator(mapper,dbName,table).genCode(modelName, sign);
		new ServiceGenerator(mapper,dbName,table).genCode(modelName, sign);
		new ControllerGenerator(mapper,dbName,table).genCode(modelName, sign);
	}
	
	/**
	 * Freemarker 模板环境配置
	 * @return
	 * @throws IOException
	 */
	private Configuration initFreemarkerConfiguration() {
		Configuration cfg = null;
		try {
			cfg = new Configuration(Configuration.VERSION_2_3_23);
			cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		} catch (IOException e) {
			throw new RuntimeException("Freemarker 模板环境初始化异常!", e);
		}
		return cfg;
	}

	/**
	 * 初始化配置信息
	 */
	private static void init() {
		Properties prop = loadProperties();

		JAVA_PATH = prop.getProperty("java.path");
		RESOURCES_PATH = prop.getProperty("resources.path");
		TEMPLATE_FILE_PATH = PROJECT_PATH + prop.getProperty("template.file.path");

		
		AUTHOR = prop.getProperty("author");
		String dateFormat = "".equals(prop.getProperty("date-format")) ? "yyyy/MM/dd" : prop.getProperty("date-format");
		DATE = new SimpleDateFormat(dateFormat).format(new Date());
	}
	
	/**
	 * 加载配置文件
	 * @return
	 */
	private static Properties loadProperties() {
		Properties prop = null;
		try {
			prop = new Properties();
			InputStream in = CodeGeneratorManager.class.getClassLoader().getResourceAsStream("generatorConfig.properties");
			prop.load(in);
		} catch (Exception e) {
			throw new RuntimeException("加载配置文件异常!", e);
		}
		return prop;
	}
	
}
