package me.liccon.simple.service;

/**
 * 配置信息变量
 * Created by zhh on 2017/09/27.
 */
public class CodeGeneratorConfig {

	
	// 项目在硬盘上的基础路径
	protected static final String PROJECT_PATH = System.getProperty("user.dir");
	// java文件路径
	protected static String JAVA_PATH;
	// 资源文件路径
	protected static String RESOURCES_PATH;
	// 模板存放位置
	protected static String TEMPLATE_FILE_PATH;

	
	// 模板注释中 @author
	protected static String AUTHOR;
	// 模板注释中 @date
	protected static String DATE;
	
}
