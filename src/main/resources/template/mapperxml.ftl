<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basePackage}.${sign}.model.dao.${modelNameUpperCamel}Mapper">

    <select id="getList" resultType="${basePackage}.${sign}.model.bean.${modelNameUpperCamel}">
        SELECT * FROM ${tableName} ORDER BY id DESC
    </select>
    <select id="search" resultType="${basePackage}.${sign}.model.bean.${modelNameUpperCamel}" parameterType="${basePackage}.${sign}.model.bean.${modelNameUpperCamel}SearchRequestBean">
        SELECT * FROM ${tableName}
        <where>
            <#list columnList as column>
            <if test="${column.columnNameFormated} != null and ${column.columnNameFormated} !=''">
                AND ${column.columnName}=${r'#{'}${column.columnNameFormated}}
            </if>
            </#list>)
        </where>
        ORDER BY id DESC
    </select>
    <insert id="insert${modelNameUpperCamel}" useGeneratedKeys="true" keyProperty="id" parameterType="${basePackage}.${sign}.model.bean.${modelNameUpperCamel}">
        INSERT INTO ${tableName} (<#list columnList as column>${column.columnName}<#if column_index<columnList?size-1>,</#if></#list>)
        VALUES(<#list columnList as column>${r'#{'}${column.columnNameFormated}}<#if column_index<columnList?size-1>,</#if></#list>)
    </insert>
    <update id="update${modelNameUpperCamel}" parameterType="${basePackage}.${sign}.model.bean.${modelNameUpperCamel}">
        UPDATE ${tableName} SET
        <#list columnList as column>${column.columnName}=${r'#{'}${column.columnNameFormated}}<#if column_index<columnList?size-1>,</#if></#list>
        WHERE id=${r'#{id}'}
    </update>
</mapper>